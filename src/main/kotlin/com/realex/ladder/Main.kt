package com.realex.ladder

/**
 * 콘솔 진입점 — 예시 한 판을 돌려 사다리와 결과를 보여 준다.
 *
 * 입력 처리는 아직 없다. 도메인이 먼저 자리를 잡아야 입력 형식을 정할 수 있다.
 */
fun main() {
    val players = Players.of(listOf("pobi", "honux", "crong", "jk"))
    val prizes = Prizes(listOf("꽝", "5000", "꽝", "3000"))
    val ladder = Ladder.of(width = players.size - 1, height = 5)

    println("사다리 결과")
    println()
    print(LadderRenderer.render(players, ladder))
    println()

    LadderGame(players, ladder, prizes).play().all().forEach { (player, prize) ->
        println("${player.name} : $prize")
    }
}
