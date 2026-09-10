package com.realex.ladder

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class PlayersTest : StringSpec({

    "이름 순서가 곧 사다리에서의 시작 위치다" {
        val players = Players.of(listOf("pobi", "honux", "crong"))

        players.positionOf(Player("crong")) shouldBe 2
    }

    "이름이 중복되면 참가자가 될 수 없다" {
        shouldThrow<IllegalArgumentException> {
            Players.of(listOf("pobi", "pobi"))
        }
    }

    "혼자서는 사다리를 탈 수 없다" {
        shouldThrow<IllegalArgumentException> {
            Players.of(listOf("pobi"))
        }
    }

    "이름이 다섯 자를 넘으면 참가자가 될 수 없다" {
        shouldThrow<IllegalArgumentException> {
            Player("javajigi!")
        }
    }
})
