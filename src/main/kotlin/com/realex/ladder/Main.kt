package com.realex.ladder

/** 콘솔 진입점 — 입력을 받아 한 판 돌리고 결과를 보여 준다 */
fun main() {
    val names = InputView.readNames()
    val prizes = InputView.readPrizes(names.size)
    val height = InputView.readHeight()

    val played = LadderGameService().play(names, prizes, height)

    ResultView.printLadder(played)
    showResults(played)
}

private fun showResults(played: GamePlayed) {
    while (true) {
        when (val target = InputView.readTarget()) {
            InputView.QUIT -> return
            InputView.SHOW_ALL -> ResultView.printAll(played.results)
            else -> printOneOrError(played, target)
        }
    }
}

private fun printOneOrError(played: GamePlayed, target: String) {
    val prize = played.prizeOf(target)
    if (prize == null) {
        ResultView.printError("참가하지 않은 사람입니다: $target")
    } else {
        ResultView.printOne(prize)
    }
}
