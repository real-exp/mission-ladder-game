package com.realex.ladder

/** 콘솔 진입점 — 예시 한 판을 돌려 결과를 보여 준다 */
fun main() {
    val names = listOf("pobi", "honux", "crong", "jk")
    val prizes = listOf("꽝", "5000", "꽝", "3000")

    val results = LadderGame().play(names, prizes, height = 5)

    println("실행 결과")
    names.zip(results).forEach { (name, prize) -> println("$name : $prize") }
}
