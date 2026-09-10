package com.realex.ladder

import kotlin.random.Random

/**
 * 사다리 게임 한 판.
 *
 * 참가자와 실행 결과를 사다리로 이어 누가 무엇을 받는지 정한다.
 *
 * 한 층에서 가로선이 연달아 붙으면 한 지점에서 갈 곳이 둘이 되어 이동이 정해지지 않는다.
 * 그래서 가로선을 놓을 때 앞자리에 이미 선이 있으면 다음 자리는 비워 둔다.
 *
 * 놓인 사다리는 [lastLadder] 로 꺼내 볼 수 있다 — 결과만으로는 사다리를 그릴 수 없기 때문이다.
 */
class LadderGame {

    private var ladder: List<List<Boolean>> = emptyList()

    /**
     * 사다리를 놓고 참가자별 실행 결과를 구한다.
     *
     * @param names 참가자 이름. 목록의 순서가 곧 사다리에서의 시작 위치다
     * @param prizes 사다리 맨 아래에 놓인 실행 결과. 위치가 곧 어느 세로선 아래인지다
     * @param height 사다리 층 수
     * @return [names] 와 같은 순서로 대응하는 실행 결과
     */
    fun play(names: List<String>, prizes: List<String>, height: Int): List<String> {
        require(names.size >= MIN_PLAYERS) {
            "참가자는 ${MIN_PLAYERS}명 이상이어야 합니다: ${names.size}명"
        }
        require(names.size == prizes.size) {
            "참가자 수와 실행 결과 수가 같아야 합니다: 참가자 ${names.size}, 결과 ${prizes.size}"
        }
        require(height >= MIN_HEIGHT) {
            "사다리는 ${MIN_HEIGHT}층 이상이어야 합니다: ${height}층"
        }

        ladder = buildLadder(width = names.size - 1, height = height)
        return names.indices.map { start -> prizes[climb(ladder, start)] }
    }

    /** 마지막 판에서 놓인 사다리. `points[층][i]` 는 i번과 i+1번 세로선을 잇는 가로선이 있는지다 */
    fun lastLadder(): List<List<Boolean>> = ladder

    private fun buildLadder(width: Int, height: Int): List<List<Boolean>> = List(height) { buildRow(width) }

    private fun buildRow(width: Int): List<Boolean> {
        val row = mutableListOf<Boolean>()
        repeat(width) { index ->
            row += if (index > 0 && row[index - 1]) false else Random.nextBoolean()
        }
        return row
    }

    private fun climb(rows: List<List<Boolean>>, start: Int): Int =
        rows.fold(start) { position, row -> move(row, position) }

    private fun move(row: List<Boolean>, position: Int): Int = when {
        position > 0 && row[position - 1] -> position - 1
        position < row.size && row[position] -> position + 1
        else -> position
    }

    companion object {
        private const val MIN_PLAYERS = 2
        private const val MIN_HEIGHT = 1
    }
}
