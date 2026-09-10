# mission-ladder-game

사다리 게임. realex 미션 프로젝트 저장소입니다.

참가자와 실행 결과를 사다리로 이어 누가 무엇을 받는지 정합니다.

```
  pobi honux crong    jk
     |-----|     |-----|
     |     |-----|     |
     |-----|     |     |
     |     |     |-----|
     |     |-----|     |
     꽝  5000     꽝  3000

실행 결과
pobi : 3000
honux : 꽝
crong : 5000
jk : 꽝
```

## 실행

```bash
./gradlew run     # 참가자·실행 결과·사다리 높이를 입력받아 한 판
./gradlew test    # 테스트
./gradlew build   # 컴파일 + 테스트
```

사다리를 다 놓으면 결과를 물어봅니다. 이름을 넣으면 그 사람 것만, `all` 은 전체,
`quit` 은 종료입니다.

JDK 21 이 필요합니다.

## 테스트가 확인하는 것

사다리는 매번 새로 놓이므로 "이 입력에 이 출력"을 기대할 수 없습니다. 대신 몇 번을 돌려도
반드시 참이어야 하는 성질을 확인합니다.

| 확인하는 성질 | 왜 |
|---|---|
| 참가자마다 실행 결과를 하나씩 나눠 받는다 | 사다리는 자리를 뒤섞을 뿐 없애거나 늘리지 않습니다 |
| 결과가 한 가지로 고정되지 않는다 | 가로선을 하나도 놓지 않아도 위 성질은 참이 됩니다 |
| 참가자 수와 실행 결과 수가 다르면 판을 놓을 수 없다 | 짝이 맞지 않는 판은 성립하지 않습니다 |
| 참가자가 두 명 미만이면 판을 놓을 수 없다 | 혼자서는 사다리를 탈 이유가 없습니다 |
| 사다리가 한 층도 없으면 판을 놓을 수 없다 | 층이 없으면 사다리가 아닙니다 |

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
