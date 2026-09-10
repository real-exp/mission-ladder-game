#!/usr/bin/env bash
# realex 미션 제출 스크립트 — 미션 프로젝트 저장소 루트에 동봉해 멘티가 실행한다.
#
# 브랜치 규칙:  <미션 태그>/<GitHub login>   (예: m-01/octocat)
# PR base:      mission/<미션 태그>          (예: mission/m-01, 플랫폼 저장소)
#
# 하는 일:
#   1. 현재 브랜치에서 태그와 login 을 파싱한다
#   2. 태그가 저장소에 있는지, login 이 origin(fork) 소유자와 같은지 확인한다
#   3. origin 으로 push 한다
#   4. gh CLI 가 있으면 PR 을 만들고, 없으면 브라우저에서 PR 생성 화면을 연다
#
# PR 을 올린 뒤 realex 플랫폼의 "제출완료" 버튼을 눌러야 제출로 인정된다.
set -euo pipefail

UPSTREAM_REMOTE="${REALEX_UPSTREAM_REMOTE:-upstream}"

die() { echo "❌ $*" >&2; exit 1; }
info() { echo "▶ $*"; }
# macOS 기본 bash 는 3.2 라 ${var,,} 를 쓸 수 없다
lower() { printf '%s' "$1" | tr '[:upper:]' '[:lower:]'; }

# 1. 브랜치 파싱 ---------------------------------------------------------------
branch="$(git rev-parse --abbrev-ref HEAD 2>/dev/null)" || die "git 저장소가 아닙니다"
[[ "$branch" == */* ]] || die "브랜치 이름이 '<미션 태그>/<GitHub login>' 형식이 아닙니다: $branch"
tag="${branch%%/*}"
login="${branch#*/}"
[[ -n "$tag" && -n "$login" && "$login" != */* ]] || die "브랜치 이름이 '<미션 태그>/<GitHub login>' 형식이 아닙니다: $branch"
base_branch="mission/$tag"

# 2. 검증 ------------------------------------------------------------------------
git rev-parse -q --verify "refs/tags/$tag" >/dev/null 2>&1 \
  || die "태그 '$tag' 가 없습니다. 'git fetch $UPSTREAM_REMOTE --tags' 로 태그를 받아 주세요"

origin_url="$(git remote get-url origin 2>/dev/null)" || die "origin 리모트가 없습니다 (fork 를 origin 으로 두세요)"
origin_owner="$(echo "$origin_url" | sed -E 's#.*github\.com[:/]([^/]+)/.*#\1#')"
[[ "$(lower "$origin_owner")" == "$(lower "$login")" ]] \
  || die "origin(fork) 소유자 '$origin_owner' 와 브랜치의 login '$login' 이 다릅니다"

upstream_url="$(git remote get-url "$UPSTREAM_REMOTE" 2>/dev/null)" \
  || die "'$UPSTREAM_REMOTE' 리모트가 없습니다. 플랫폼 저장소를 '$UPSTREAM_REMOTE' 로 추가해 주세요"
upstream_repo="$(echo "$upstream_url" | sed -E 's#.*github\.com[:/]([^/]+/[^/.]+)(\.git)?$#\1#')"

info "미션 태그: $tag  /  브랜치: $branch  →  base: $upstream_repo:$base_branch"

# 3. push ----------------------------------------------------------------------
info "origin 으로 push 합니다"
git push -u origin "$branch"

# 4. PR ------------------------------------------------------------------------
pr_title="[$tag] $login 미션 제출"
if command -v gh >/dev/null 2>&1 && gh auth status >/dev/null 2>&1; then
  existing="$(gh pr list --repo "$upstream_repo" --base "$base_branch" --head "$login:$branch" --state open --json url --jq '.[0].url' 2>/dev/null || true)"
  if [[ -n "$existing" ]]; then
    info "이미 열린 PR 이 있습니다: $existing"
  else
    gh pr create --repo "$upstream_repo" --base "$base_branch" --head "$login:$branch" \
      --title "$pr_title" --body "realex 미션 $tag 제출" \
      || die "PR 생성에 실패했습니다"
  fi
else
  compare_url="https://github.com/$upstream_repo/compare/$base_branch...$login:$branch?expand=1"
  info "gh CLI 가 없어 브라우저에서 PR 생성 화면을 엽니다"
  echo "   $compare_url"
  if command -v open >/dev/null 2>&1; then open "$compare_url"
  elif command -v xdg-open >/dev/null 2>&1; then xdg-open "$compare_url"
  fi
fi

echo
echo "✅ PR 을 올렸으면 realex 에서 '제출완료' 버튼을 눌러 주세요. 그 시점의 커밋이 채점됩니다."
