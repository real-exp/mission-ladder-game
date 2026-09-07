package com.realex.ladder

import kotlin.random.Random

/** 여러 층의 가로선. 참가자는 위에서 아래로 층을 하나씩 지난다 */
class Ladder(val lines: List<LadderLine>) {

    init {
        require(lines.isNotEmpty()) { "사다리는 한 층 이상이어야 합니다" }
        require(lines.distinctBy { it.width }.size == 1) { "모든 층의 폭이 같아야 합니다" }
    }

    val height: Int get() = lines.size

    val width: Int get() = lines.first().width

    /** 시작 위치에서 사다리를 다 내려온 뒤의 위치 */
    fun climb(start: Int): Int = lines.fold(start) { position, line -> line.move(position) }

    companion object {
        fun of(width: Int, height: Int, random: Random = Random.Default): Ladder =
            Ladder(List(height) { LadderLine.of(width) { random.nextBoolean() } })
    }
}
