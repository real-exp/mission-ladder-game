package com.realex.ladder

/** 콘솔 진입점 — 입력을 받아 한 판 돌리고 결과를 보여 준다 */
fun main() {
    val names = InputView.readNames()
    val prizes = InputView.readPrizes(names.size)
    val height = InputView.readHeight()

    val game = LadderGame()
    val results = game.play(names, prizes, height)

    ResultView.printLadder(names, game.lastLadder(), prizes)
    showResults(names, results)
}

private fun showResults(names: List<String>, results: List<String>) {
    while (true) {
        when (val target = InputView.readTarget()) {
            InputView.QUIT -> return
            InputView.SHOW_ALL -> ResultView.printAll(names, results)
            in names -> ResultView.printOne(results[names.indexOf(target)])
            else -> ResultView.printError("참가하지 않은 사람입니다: $target")
        }
    }
}
