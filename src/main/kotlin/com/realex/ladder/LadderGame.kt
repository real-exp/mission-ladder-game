package com.realex.ladder

/**
 * 한 판 — 참가자·사다리·실행 결과를 묶어 누가 무엇을 받는지 정한다.
 *
 * 세 값의 크기가 서로 맞아야 판이 성립한다. 사다리 폭은 세로선 사이의 간격 수이므로
 * 참가자 수보다 하나 적다.
 */
class LadderGame(
    private val players: Players,
    private val ladder: Ladder,
    private val prizes: Prizes,
) {

    init {
        require(players.size == prizes.size) {
            "참가자 수와 실행 결과 수가 같아야 합니다: 참가자 ${players.size}, 결과 ${prizes.size}"
        }
        require(ladder.width == players.size - 1) {
            "사다리 폭이 참가자 수와 맞지 않습니다: 폭 ${ladder.width}, 참가자 ${players.size}"
        }
    }

    fun play(): GameResult = GameResult(
        players.values.associateWith { prizes[ladder.climb(players.positionOf(it))] },
    )
}

/** 참가자별 당첨 결과 */
class GameResult(private val prizeByPlayer: Map<Player, String>) {

    fun of(player: Player): String = requireNotNull(prizeByPlayer[player]) {
        "참가하지 않은 사람입니다: ${player.name}"
    }

    fun all(): Map<Player, String> = prizeByPlayer
}
