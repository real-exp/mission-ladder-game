package com.realex.ladder.persistence

import com.realex.ladder.GamePlayed
import com.realex.ladder.Ladder
import com.realex.ladder.LadderLine
import com.realex.ladder.NoSuchGameException
import com.realex.ladder.PlayerResult
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * 돌린 판을 남기고 다시 꺼내 온다.
 *
 * 서버를 껐다 켜면 메모리에 있던 이력이 사라져 지난 판을 볼 수 없었다. 이제 H2 에 남긴다.
 *
 * 사다리 도면은 층마다 가로선 유무를 0/1 로 적고 층은 쉼표로 잇는다 ("110,011").
 *
 * 한 판을 남기는 일은 판·참가자·통계 세 군데를 건드린다. 도중에 실패하면 판만 남고 통계는
 * 어긋난 채로 굳으므로 [save] 는 한 트랜잭션으로 묶는다.
 */
@Service
class GameArchive(
    private val games: GameRepository,
    private val players: GamePlayerRepository,
    private val stats: PlayerStatRepository,
) {

    @Transactional
    fun save(played: GamePlayed) {
        games.save(GameEntity(id = played.id, playedAt = played.playedAt, rows = encode(played.ladder)))
        played.results.forEachIndexed { position, result ->
            players.save(
                GamePlayerEntity(
                    gameId = played.id,
                    position = position,
                    name = result.name,
                    prize = result.prize,
                ),
            )
        }
        played.names.forEach { countPlay(it) }
    }

    /** 사람별 누적 참가 횟수 — 많이 한 사람부터 */
    fun playCounts(): List<PlayerStatEntity> = stats.findAllByOrderByPlayCountDesc()

    private fun countPlay(name: String) {
        if (stats.increasePlayCount(name) == 0) {
            stats.save(PlayerStatEntity(name = name, playCount = 1))
        }
    }

    fun find(id: String): GamePlayed {
        val game = games.findById(id).orElseThrow { NoSuchGameException("그런 판이 없습니다: $id") }
        return toPlayed(game, players.findByGameIdOrderByPosition(id))
    }

    /**
     * 최근에 돌린 판부터.
     *
     * 참가자를 판마다 따로 읽으면 목록 길이만큼 쿼리가 늘어난다. 판 번호를 모아 한 번에 읽고
     * 메모리에서 판별로 나눈다 — 목록이 몇 건이든 쿼리는 둘이다.
     */
    fun recent(limit: Int): List<GamePlayed> {
        val found = games.findAllByOrderByPlayedAtDesc(PageRequest.of(0, limit))
        if (found.isEmpty()) return emptyList()

        val playersByGame = players.findByGameIdInOrderByPosition(found.map { it.id })
            .groupBy { it.gameId }
        return found.map { game -> toPlayed(game, playersByGame[game.id].orEmpty()) }
    }

    /** 그 판에서 그 사람이 받은 실행 결과 */
    fun prizeOf(id: String, name: String): String =
        requireNotNull(find(id).prizeOf(name)) { "참가하지 않은 사람입니다: $name" }

    private fun toPlayed(game: GameEntity, rows: List<GamePlayerEntity>): GamePlayed = GamePlayed(
        id = game.id,
        playedAt = game.playedAt,
        names = rows.map { it.name },
        prizes = rows.sortedBy { it.position }.map { it.prize },
        ladder = decode(game.rows),
        results = rows.map { PlayerResult(it.name, it.prize) },
    )

    private fun encode(ladder: Ladder): String =
        ladder.lines.joinToString(",") { line -> line.points.joinToString("") { if (it) "1" else "0" } }

    private fun decode(rows: String): Ladder =
        Ladder(rows.split(",").map { row -> LadderLine(row.map { it == '1' }) })
}
