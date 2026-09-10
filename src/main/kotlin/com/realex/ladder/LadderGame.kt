package com.realex.ladder

/**
 * 사다리 게임 한 판.
 *
 * 참가자·사다리·실행 결과를 짝지어 누가 무엇을 받는지 정한다. 값 하나하나의 불변식은
 * 각 값 객체가 지키므로 여기서는 셋의 크기가 서로 맞는지만 본다.
 *
 * 놓인 사다리는 [lastLadder] 로 꺼내 볼 수 있다 — 결과만으로는 도면을 그릴 수 없기 때문이다.
 */
class LadderGame {

    private var ladder: Ladder? = null

    /**
     * 사다리를 놓고 참가자별 실행 결과를 구한다.
     *
     * @param names 참가자 이름. 목록의 순서가 곧 사다리에서의 시작 위치다
     * @param prizes 사다리 맨 아래에 놓인 실행 결과. 위치가 곧 어느 세로선 아래인지다
     * @param height 사다리 층 수
     * @return [names] 와 같은 순서로 대응하는 실행 결과
     */
    fun play(names: List<String>, prizes: List<String>, height: Int): List<String> {
        val players = Players.of(names)
        val results = Prizes(prizes)
        require(players.size == results.size) {
            "참가자 수와 실행 결과 수가 같아야 합니다: 참가자 ${players.size}, 결과 ${results.size}"
        }

        return Ladder.of(width = players.size - 1, height = height)
            .also { ladder = it }
            .let { placed -> players.values.map { results[placed.climb(players.positionOf(it))] } }
    }

    /** 마지막 판에서 놓인 사다리 */
    fun lastLadder(): Ladder = requireNotNull(ladder) { "아직 판을 돌리지 않았습니다" }
}
