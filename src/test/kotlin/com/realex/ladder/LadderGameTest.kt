package com.realex.ladder

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LadderGameTest : StringSpec({

    val players = Players.of(listOf("pobi", "honux", "crong"))
    val prizes = Prizes(listOf("꽝", "5000", "3000"))

    "사다리를 타고 내려온 자리의 결과를 받는다" {
        val ladder = Ladder(listOf(LadderLine(listOf(true, false))))

        val result = LadderGame(players, ladder, prizes).play()

        result.of(Player("pobi")) shouldBe "5000"
        result.of(Player("honux")) shouldBe "꽝"
        result.of(Player("crong")) shouldBe "3000"
    }

    "참가자 수와 실행 결과 수가 다르면 판을 만들 수 없다" {
        shouldThrow<IllegalArgumentException> {
            LadderGame(players, Ladder(listOf(LadderLine(listOf(true, false)))), Prizes(listOf("꽝")))
        }
    }

    "사다리 폭이 참가자 수와 맞지 않으면 판을 만들 수 없다" {
        shouldThrow<IllegalArgumentException> {
            LadderGame(players, Ladder(listOf(LadderLine(listOf(true)))), prizes)
        }
    }
})
