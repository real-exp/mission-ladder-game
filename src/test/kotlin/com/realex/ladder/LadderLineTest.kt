package com.realex.ladder

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe

class LadderLineTest : StringSpec({

    "가로선이 연달아 붙으면 만들 수 없다" {
        shouldThrow<IllegalArgumentException> {
            LadderLine(listOf(true, true))
        }
    }

    "왼쪽에 가로선이 있으면 왼쪽으로 간다" {
        LadderLine(listOf(true, false)).move(1) shouldBe 0
    }

    "오른쪽에 가로선이 있으면 오른쪽으로 간다" {
        LadderLine(listOf(true, false)).move(0) shouldBe 1
    }

    "양옆에 가로선이 없으면 제자리에 머문다" {
        LadderLine(listOf(true, false)).move(2) shouldBe 2
    }

    "생성기는 앞자리에 선이 있으면 다음 자리를 비운다" {
        LadderLine.of(width = 3) { true }.points shouldBe listOf(true, false, true)
    }
})
