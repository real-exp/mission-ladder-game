package com.realex.ladder.web

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * 도메인이 거절한 이유를 그대로 400 으로 내보낸다.
 *
 * 규칙은 값 객체가 알고 있으므로 컨트롤러가 같은 검사를 한 번 더 하지 않는다.
 */
@RestControllerAdvice
class ApiExceptionHandler {

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleInvalidRequest(e: IllegalArgumentException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .body(ErrorResponse(e.message ?: "요청을 처리할 수 없습니다"))

    /** 아직 판을 돌리지 않았는데 결과를 물은 경우 */
    @ExceptionHandler(IllegalStateException::class)
    fun handleNotReady(e: IllegalStateException): ResponseEntity<ErrorResponse> =
        ResponseEntity.status(HttpStatus.CONFLICT)
            .body(ErrorResponse(e.message ?: "아직 처리할 수 없습니다"))
}

data class ErrorResponse(val message: String)
