package com.realex.ladder.web

import com.realex.ladder.GamePlayed
import com.realex.ladder.LadderGameService
import com.realex.ladder.persistence.GameArchive
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

/** 사다리 게임 실행 API */
@RestController
@RequestMapping("/api/games")
class LadderController(
    private val gameService: LadderGameService,
    private val archive: GameArchive,
) {

    @PostMapping
    fun play(@RequestBody request: PlayRequest): PlayResponse {
        val played = gameService.play(
            names = request.names.orEmpty().map { it.trim() },
            prizes = request.prizes.orEmpty().map { it.trim() },
            height = request.height ?: 0,
        )
        archive.save(played)
        return PlayResponse.from(played)
    }

    @GetMapping
    fun recent(@RequestParam(defaultValue = "20") limit: Int): List<GameSummary> =
        archive.recent(limit).map { GameSummary.from(it) }

    @GetMapping("/{id}")
    fun find(@PathVariable id: String): PlayResponse = PlayResponse.from(archive.find(id))

    @GetMapping("/{id}/results/{name}")
    fun resultOf(@PathVariable id: String, @PathVariable name: String): ResultEntry =
        ResultEntry(name, archive.prizeOf(id, name))
}

/** 사람별 누적 통계 */
@RestController
@RequestMapping("/api/stats")
class StatsController(private val archive: GameArchive) {

    @GetMapping
    fun playCounts(): List<PlayCount> =
        archive.playCounts().map { PlayCount(it.name, it.playCount) }
}

data class PlayCount(val name: String, val playCount: Int)

data class PlayRequest(val names: List<String>?, val prizes: List<String>?, val height: Int?)

data class PlayResponse(
    val id: String,
    val rows: List<List<Boolean>>,
    val prizes: List<String>,
    val results: List<ResultEntry>,
) {
    companion object {
        fun from(played: GamePlayed): PlayResponse = PlayResponse(
            id = played.id,
            rows = played.ladder.lines.map { it.points },
            prizes = played.prizes,
            results = played.results.map { ResultEntry(it.name, it.prize) },
        )
    }
}

data class ResultEntry(val name: String, val prize: String)

/** 이력 목록에 한 줄로 나가는 요약 */
data class GameSummary(
    val id: String,
    val playedAt: String,
    val names: List<String>,
    val results: List<ResultEntry>,
) {
    companion object {
        fun from(played: GamePlayed): GameSummary = GameSummary(
            id = played.id,
            playedAt = played.playedAt.toString(),
            names = played.names,
            results = played.results.map { ResultEntry(it.name, it.prize) },
        )
    }
}
