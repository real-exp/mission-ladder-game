package com.realex.ladder

import org.springframework.stereotype.Service

/**
 * 한 판을 진행하는 응용 서비스.
 *
 * 콘솔과 웹이 같은 절차를 각자 조립하고 있었다. 화면이 무엇이든 "이름·실행 결과·높이를 받아
 * 사다리를 놓고 누가 무엇을 받는지 돌려준다"는 흐름은 하나뿐이므로 여기로 모은다.
 * 화면은 이 결과를 어떻게 보여 줄지만 정한다.
 *
 * 화면을 새로 고쳐도 방금 돌린 판을 다시 볼 수 있도록 마지막 결과를 들고 있는다.
 */
@Service
class LadderGameService {

    private var lastPlayed: GamePlayed? = null

    fun play(names: List<String>, prizes: List<String>, height: Int): GamePlayed {
        val game = LadderGame()
        val results = game.play(names, prizes, height)
        val played = GamePlayed(
            names = names,
            prizes = prizes,
            ladder = game.lastLadder(),
            results = names.zip(results).map { (name, prize) -> PlayerResult(name, prize) },
        )
        lastPlayed = played
        return played
    }

    /** 마지막으로 돌린 판 */
    fun lastPlayed(): GamePlayed = checkNotNull(lastPlayed) { "아직 실행한 판이 없습니다" }

    /** 마지막 판에서 그 사람이 받은 실행 결과 */
    fun prizeOf(name: String): String =
        requireNotNull(lastPlayed().prizeOf(name)) { "참가하지 않은 사람입니다: $name" }
}

/**
 * 한 판의 결과.
 *
 * 사다리 도면을 함께 들고 있는 이유는 결과만으로는 어떻게 그 자리에 갔는지 보여 줄 수 없기 때문이다.
 */
data class GamePlayed(
    val names: List<String>,
    val prizes: List<String>,
    val ladder: Ladder,
    val results: List<PlayerResult>,
) {
    fun prizeOf(name: String): String? = results.firstOrNull { it.name == name }?.prize
}

/** 참가자 한 명의 실행 결과 */
data class PlayerResult(val name: String, val prize: String)
