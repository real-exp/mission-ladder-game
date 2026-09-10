package com.realex.ladder

/** 콘솔 출력 */
object ResultView {

    fun printLadder(played: GamePlayed) {
        println()
        println("사다리 결과")
        println()
        print(LadderRenderer.render(played.names, played.ladder))
        played.prizes.forEach { print(it.padStart(LadderRenderer.COLUMN_WIDTH)) }
        println()
    }

    fun printAll(results: List<PlayerResult>) {
        println()
        println("실행 결과")
        results.forEach { println("${it.name} : ${it.prize}") }
    }

    fun printOne(prize: String) {
        println()
        println("실행 결과")
        println(prize)
    }

    fun printError(message: String) = println(message)
}
