package com.realex.ladder

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LadderTest : StringSpec({

    "여러 층을 지나 최종 위치가 정해진다" {
        val ladder = Ladder(
            listOf(
                LadderLine(listOf(true, false)),
                LadderLine(listOf(false, true)),
            ),
        )

        ladder.climb(0) shouldBe 2
    }

    "층마다 폭이 다르면 사다리가 될 수 없다" {
        shouldThrow<IllegalArgumentException> {
            Ladder(
                listOf(
                    LadderLine(listOf(true, false)),
                    LadderLine(listOf(false)),
                ),
            )
        }
    }
})
