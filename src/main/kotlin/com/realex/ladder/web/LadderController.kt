package com.realex.ladder.web

import com.realex.ladder.GamePlayed
import com.realex.ladder.LadderGameService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/** 사다리 게임 실행 API */
@RestController
@RequestMapping("/api/games")
class LadderController(private val gameService: LadderGameService) {

    @PostMapping
    fun play(@RequestBody request: PlayRequest): PlayResponse {
        val played = gameService.play(
            names = request.names.orEmpty().map { it.trim() },
            prizes = request.prizes.orEmpty().map { it.trim() },
            height = request.height ?: 0,
        )
        return PlayResponse.from(played)
    }
}

data class PlayRequest(val names: List<String>?, val prizes: List<String>?, val height: Int?)

data class PlayResponse(
    val rows: List<List<Boolean>>,
    val prizes: List<String>,
    val results: List<ResultEntry>,
) {
    companion object {
        fun from(played: GamePlayed): PlayResponse = PlayResponse(
            rows = played.ladder.lines.map { it.points },
            prizes = played.prizes,
            results = played.results.map { ResultEntry(it.name, it.prize) },
        )
    }
}

data class ResultEntry(val name: String, val prize: String)
