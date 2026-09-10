package com.realex.ladder.web

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

/** 웹 진입점. 콘솔 진입점은 com.realex.ladder.MainKt 에 따로 있다 */
@SpringBootApplication
class LadderApplication

fun main(args: Array<String>) {
    runApplication<LadderApplication>(*args)
}
