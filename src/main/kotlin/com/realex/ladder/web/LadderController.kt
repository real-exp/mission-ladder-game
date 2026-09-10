package com.realex.ladder.web

import com.realex.ladder.LadderGame
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

/** 사다리 게임 실행 API */
@RestController
@RequestMapping("/api/games")
class LadderController {

    @PostMapping
    fun play(@RequestBody request: PlayRequest): ResponseEntity<Any> {
        val names = request.names.orEmpty().map { it.trim() }
        val prizes = request.prizes.orEmpty().map { it.trim() }
        val height = request.height ?: 0

        if (names.isEmpty()) return badRequest("참가자를 입력해 주세요")
        if (prizes.isEmpty()) return badRequest("실행 결과를 입력해 주세요")
        if (names.size != prizes.size) return badRequest("참가자 수와 실행 결과 수가 같아야 합니다")

        return try {
            val game = LadderGame()
            val results = game.play(names, prizes, height)
            ResponseEntity.ok(
                PlayResponse(
                    rows = game.lastLadder().lines.map { it.points },
                    prizes = prizes,
                    results = names.zip(results).map { (name, prize) -> ResultEntry(name, prize) },
                ),
            )
        } catch (e: IllegalArgumentException) {
            badRequest(e.message ?: "요청을 처리할 수 없습니다")
        }
    }

    private fun badRequest(message: String): ResponseEntity<Any> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(message))
}

data class PlayRequest(val names: List<String>?, val prizes: List<String>?, val height: Int?)

data class PlayResponse(
    val rows: List<List<Boolean>>,
    val prizes: List<String>,
    val results: List<ResultEntry>,
)

data class ResultEntry(val name: String, val prize: String)

data class ErrorResponse(val message: String)
