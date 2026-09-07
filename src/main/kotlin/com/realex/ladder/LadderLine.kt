package com.realex.ladder

/**
 * 사다리 한 층의 가로선 배치. `points[i]` 는 i번과 i+1번 세로선을 잇는 가로선이 있는지다.
 *
 * 이웃한 가로선이 연달아 붙으면 한 지점에서 갈 곳이 둘이 되어 이동이 정해지지 않는다.
 * 그래서 생성 시점에 막고, [of] 는 앞자리에 선이 있으면 다음 자리를 비워 둔다.
 */
class LadderLine(val points: List<Boolean>) {

    init {
        require(points.isNotEmpty()) { "가로선 자리는 하나 이상이어야 합니다" }
        require(points.zipWithNext().none { (left, right) -> left && right }) {
            "가로선이 연달아 붙을 수 없습니다: $points"
        }
    }

    val width: Int get() = points.size

    /** 이 층을 지난 뒤의 위치 */
    fun move(position: Int): Int = when {
        position > 0 && points[position - 1] -> position - 1
        position < points.size && points[position] -> position + 1
        else -> position
    }

    companion object {
        fun of(width: Int, drawn: (Int) -> Boolean): LadderLine {
            val points = mutableListOf<Boolean>()
            repeat(width) { index ->
                points += if (index > 0 && points[index - 1]) false else drawn(index)
            }
            return LadderLine(points)
        }
    }
}
