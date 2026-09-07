# mission-ladder-game

사다리 게임. realex 미션 프로젝트 저장소입니다.

참가자와 실행 결과를 사다리로 이어 누가 무엇을 받는지 정합니다.

```
 pobi honux crong    jk
     |-----|     |     |
     |     |-----|     |
     |-----|     |-----|
     |     |-----|     |
     |-----|     |     |

pobi : 5000
honux : 꽝
crong : 3000
jk : 꽝
```

## 실행

```bash
./gradlew run     # 예시 한 판
./gradlew build   # 컴파일 + 테스트
```

JDK 21 이 필요합니다.

## 미션 수행 규칙

이 저장소는 직접 고치지 않습니다. **fork 해서 작업하고, 이 저장소로 PR 을 올립니다.**

| | 규칙 | 예시 |
|---|---|---|
| 시작 태그 | 미션이 시작되는 리비전 | `m-01` |
| PR base | `mission/<태그>` | `mission/m-01` |
| 내 브랜치 | `<태그>/<GitHub login>` | `m-01/octocat` |

```bash
git clone git@github.com:<내계정>/mission-ladder-game.git
cd mission-ladder-game
git remote add upstream https://github.com/real-exp/mission-ladder-game.git
git fetch upstream --tags

git checkout -b m-01/<내 login> m-01
# ... 작업 후 커밋 ...
./realex-submit.sh
```

`realex-submit.sh` 가 push 와 PR 생성까지 합니다. PR 을 올린 뒤 realex 플랫폼에서 **제출** 버튼을 눌러야 제출로 인정됩니다. 그 시점의 커밋이 채점 대상으로 고정됩니다.

`mission/*` 브랜치는 채점의 기준선이라 머지하지 않습니다.
