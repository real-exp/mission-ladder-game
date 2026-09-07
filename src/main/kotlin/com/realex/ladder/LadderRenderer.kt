package com.realex.ladder

/** 사다리를 콘솔 한 장으로 그린다 — 세로선은 이름 폭에 맞춰 정렬한다 */
object LadderRenderer {

    private const val COLUMN_WIDTH = 6
    private const val RUNG = "-----"
    private const val GAP = "     "

    fun render(players: Players, ladder: Ladder): String = buildString {
        players.values.forEach { append(it.name.padStart(COLUMN_WIDTH)) }
        appendLine()
        ladder.lines.forEach { line ->
            append(" ".repeat(COLUMN_WIDTH - 1))
            append("|")
            line.points.forEach { drawn ->
                append(if (drawn) RUNG else GAP)
                append("|")
            }
            appendLine()
        }
    }
}
