package com.realex.ladder

import org.springframework.stereotype.Service
import java.time.Instant
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

/**
 * 한 판을 진행하는 응용 서비스.
 *
 * 콘솔과 웹이 같은 절차를 각자 조립하고 있었다. 화면이 무엇이든 "이름·실행 결과·높이를 받아
 * 사다리를 놓고 누가 무엇을 받는지 돌려준다"는 흐름은 하나뿐이므로 여기로 모은다.
 * 화면은 이 결과를 어떻게 보여 줄지만 정한다.
 *
 * 돌린 판은 번호를 붙여 보관하고 그 번호로만 꺼낸다. 이 서비스는 하나뿐인데 판을 돌리는
 * 사람은 여럿이라, 마지막 판 하나만 들고 있으면 서로의 결과를 덮어쓴다.
 */
@Service
class LadderGameService {

    private val played = ConcurrentHashMap<String, GamePlayed>()

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
        played[outcome.id] = outcome
        return outcome
    }

    fun find(id: String): GamePlayed =
        played[id] ?: throw NoSuchGameException("그런 판이 없습니다: $id")

    /** 최근에 돌린 판부터 */
    fun recent(limit: Int): List<GamePlayed> =
        played.values.sortedByDescending { it.playedAt }.take(limit)

    /** 그 판에서 그 사람이 받은 실행 결과 */
    fun prizeOf(id: String, name: String): String =
        requireNotNull(find(id).prizeOf(name)) { "참가하지 않은 사람입니다: $name" }
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
