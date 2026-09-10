package com.realex.ladder

/**
 * 사다리 게임 한 판.
 *
 * 참가자와 실행 결과를 사다리로 이어 누가 무엇을 받는지 정한다.
 */
class LadderGame {

    /**
     * 사다리를 놓고 참가자별 실행 결과를 구한다.
     *
     * @param names 참가자 이름. 목록의 순서가 곧 사다리에서의 시작 위치다
     * @param prizes 사다리 맨 아래에 놓인 실행 결과. 위치가 곧 어느 세로선 아래인지다
     * @param height 사다리 층 수
     * @return [names] 와 같은 순서로 대응하는 실행 결과
     */
    fun play(names: List<String>, prizes: List<String>, height: Int): List<String> =
        TODO("사다리를 놓고 참가자별 실행 결과를 구하세요")
}
