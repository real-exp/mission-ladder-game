package com.realex.ladder

/** 콘솔 출력 */
object ResultView {

    fun printLadder(names: List<String>, ladder: Ladder, prizes: List<String>) {
        println()
        println("사다리 결과")
        println()
        print(LadderRenderer.render(names, ladder))
        prizes.forEach { print(it.padStart(LadderRenderer.COLUMN_WIDTH)) }
        println()
    }

    fun printAll(names: List<String>, results: List<String>) {
        println()
        println("실행 결과")
        names.zip(results).forEach { (name, prize) -> println("$name : $prize") }
    }

    fun printOne(result: String) {
        println()
        println("실행 결과")
        println(result)
    }

    fun printError(message: String) = println(message)
}
