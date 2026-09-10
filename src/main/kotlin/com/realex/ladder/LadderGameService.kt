package com.realex.ladder

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID

/**
 * 한 판을 진행하는 응용 서비스.
 *
 * 콘솔과 웹이 같은 절차를 각자 조립하고 있었다. 화면이 무엇이든 "이름·실행 결과·높이를 받아
 * 사다리를 놓고 누가 무엇을 받는지 돌려준다"는 흐름은 하나뿐이므로 여기로 모은다.
 * 화면은 이 결과를 어떻게 보여 줄지만 정한다.
 *
 * 돌린 판에는 번호를 붙인다. 이 서비스는 하나뿐인데 판을 돌리는 사람은 여럿이라,
 * 마지막 판 하나만 들고 있으면 서로의 결과를 덮어쓴다.
 *
 * 보관은 하지 않는다 — 콘솔은 한 판 돌리고 끝이라 이력이 필요 없다. 웹이 남기는 것은
 * [com.realex.ladder.persistence.GameArchive] 가 맡는다.
 */
@Service
class LadderGameService {

    fun play(names: List<String>, prizes: List<String>, height: Int): GamePlayed {
        val game = LadderGame()
        val results = game.play(names, prizes, height)
        val outcome = GamePlayed(
            id = UUID.randomUUID().toString(),
            playedAt = Instant.now(),
            names = names,
            prizes = prizes,
            ladder = game.lastLadder(),
            results = names.zip(results).map { (name, prize) -> PlayerResult(name, prize) },
        )
        return outcome
    }
}

/** 없는 판을 찾았을 때 */
class NoSuchGameException(message: String) : RuntimeException(message)

/**
 * 한 판의 결과.
 *
 * 사다리 도면을 함께 들고 있는 이유는 결과만으로는 어떻게 그 자리에 갔는지 보여 줄 수 없기 때문이다.
 */
data class GamePlayed(
    val id: String,
    val playedAt: Instant,
    val names: List<String>,
    val prizes: List<String>,
    val ladder: Ladder,
    val results: List<PlayerResult>,
) {
    fun prizeOf(name: String): String? = results.firstOrNull { it.name == name }?.prize
}

/** 참가자 한 명의 실행 결과 */
data class PlayerResult(val name: String, val prize: String)
