package com.realex.ladder

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.ints.shouldBeGreaterThan
import io.kotest.matchers.shouldBe

/**
 * 사다리 게임의 인수 테스트.
 *
 * 사다리는 매번 새로 놓이므로 "이 입력에 이 출력"을 기대할 수 없다. 대신 몇 번을 돌려도
 * 반드시 참인 성질을 확인한다 — 그중 첫째가 참가자와 실행 결과가 하나씩 짝지어진다는 것이다.
 */
class LadderGameTest : StringSpec({

    val trials = 100
    val height = 5
    val names = listOf("pobi", "honux", "crong", "jk")
    val prizes = listOf("꽝", "5000", "꽝", "3000")

    "참가자마다 실행 결과를 하나씩 나눠 받는다" {
        repeat(trials) {
            LadderGame().play(names, prizes, height).sorted() shouldBe prizes.sorted()
        }
    }

    "사다리는 매번 새로 놓이므로 결과가 한 가지로 고정되지 않는다" {
        val distinctPrizes = listOf("1등", "2등", "3등", "4등")

        val outcomes = List(trials) { LadderGame().play(names, distinctPrizes, height) }

        outcomes.distinct().size shouldBeGreaterThan 1
    }

    "참가자 수와 실행 결과 수가 다르면 판을 놓을 수 없다" {
        shouldThrow<IllegalArgumentException> {
            LadderGame().play(names, listOf("꽝"), height)
        }
    }

    "참가자가 두 명 미만이면 판을 놓을 수 없다" {
        shouldThrow<IllegalArgumentException> {
            LadderGame().play(listOf("pobi"), listOf("꽝"), height)
        }
    }

    "사다리가 한 층도 없으면 판을 놓을 수 없다" {
        shouldThrow<IllegalArgumentException> {
            LadderGame().play(names, prizes, height = 0)
        }
    }
})
