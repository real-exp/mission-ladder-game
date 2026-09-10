package com.realex.ladder

/**
 * 콘솔 입력.
 *
 * 값이 규칙에 맞는지는 도메인이 판단한다 — 여기서는 도메인이 거절한 이유를 그대로 보여 주고
 * 다시 묻는 일만 한다.
 */
object InputView {

    const val SHOW_ALL = "all"
    const val QUIT = "quit"
    private const val DELIMITER = ","

    fun readNames(): List<String> {
        while (true) {
            println("참가할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)")
            val names = readCsv()
            runCatching { Players.of(names) }
                .onSuccess { return names }
                .onFailure { println(it.message) }
        }
    }

    fun readPrizes(playerCount: Int): List<String> {
        while (true) {
            println()
            println("실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)")
            val prizes = readCsv()
            runCatching {
                require(prizes.size == playerCount) {
                    "실행 결과는 참가자 수(${playerCount}개)와 같아야 합니다"
                }
                Prizes(prizes)
            }.onSuccess { return prizes }
                .onFailure { println(it.message) }
        }
    }

    fun readHeight(): Int {
        while (true) {
            println()
            println("최대 사다리 높이는 몇 개인가요?")
            val height = readText().toIntOrNull()
            if (height == null || height < 1) {
                println("사다리 높이는 1 이상의 숫자여야 합니다")
                continue
            }
            return height
        }
    }

    fun readTarget(): String {
        println()
        println("결과를 보고 싶은 사람은? (전체는 $SHOW_ALL, 종료는 $QUIT)")
        return readText()
    }

    private fun readCsv(): List<String> = readText().split(DELIMITER).map { it.trim() }

    private fun readText(): String = (readlnOrNull() ?: error("입력이 끝났습니다")).trim()
}
