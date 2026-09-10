package com.realex.ladder

private const val NAME_MAX_LENGTH = 5
private const val MIN_PLAYERS = 2
private const val COLUMN_WIDTH = 6
private const val RUNG = "-----"
private const val GAP = "     "

/** 콘솔 진입점 — 참가자와 실행 결과를 입력받아 한 판을 돌린다 */
fun main() {
    println("참가할 사람 이름을 입력하세요. (이름은 쉼표(,)로 구분하세요)")
    val names = readNames()

    println()
    println("실행 결과를 입력하세요. (결과는 쉼표(,)로 구분하세요)")
    val prizes = readPrizes(names.size)

    println()
    println("최대 사다리 높이는 몇 개인가요?")
    val height = readHeight()

    val game = LadderGame()
    val results = game.play(names, prizes, height)

    println()
    println("사다리 결과")
    println()
    printLadder(names, game.lastLadder())
    printPrizes(prizes)

    println()
    println("실행 결과")
    names.zip(results).forEach { (name, prize) -> println("$name : $prize") }
}

private fun readNames(): List<String> {
    while (true) {
        val names = readLine("참가자 이름")
        when {
            names.size < MIN_PLAYERS -> println("참가자는 ${MIN_PLAYERS}명 이상이어야 합니다.")
            names.any { it.isBlank() } -> println("이름은 비어 있을 수 없습니다.")
            names.any { it.length > NAME_MAX_LENGTH } -> println("이름은 ${NAME_MAX_LENGTH}자 이하여야 합니다.")
            names.distinct().size != names.size -> println("이름은 중복될 수 없습니다.")
            else -> return names
        }
    }
}

private fun readPrizes(playerCount: Int): List<String> {
    while (true) {
        val prizes = readLine("실행 결과")
        when {
            prizes.size != playerCount -> println("실행 결과는 참가자 수(${playerCount}개)와 같아야 합니다.")
            prizes.any { it.isBlank() } -> println("실행 결과는 비어 있을 수 없습니다.")
            prizes.any { it.length > NAME_MAX_LENGTH } -> println("실행 결과는 ${NAME_MAX_LENGTH}자 이하여야 합니다.")
            else -> return prizes
        }
    }
}

private fun readHeight(): Int {
    while (true) {
        val height = (readlnOrNull() ?: error("입력이 끝났습니다")).trim().toIntOrNull()
        if (height == null || height < 1) {
            println("사다리 높이는 1 이상의 숫자여야 합니다.")
            continue
        }
        return height
    }
}

private fun readLine(label: String): List<String> =
    (readlnOrNull() ?: error("입력이 끝났습니다: $label")).split(",").map { it.trim() }

private fun printLadder(names: List<String>, ladder: List<List<Boolean>>) {
    names.forEach { print(it.padStart(COLUMN_WIDTH)) }
    println()
    ladder.forEach { row ->
        print(" ".repeat(COLUMN_WIDTH - 1))
        print("|")
        row.forEach { drawn ->
            print(if (drawn) RUNG else GAP)
            print("|")
        }
        println()
    }
}

private fun printPrizes(prizes: List<String>) {
    prizes.forEach { print(it.padStart(COLUMN_WIDTH)) }
    println()
}
