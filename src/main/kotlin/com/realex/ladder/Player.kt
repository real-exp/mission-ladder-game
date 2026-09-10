package com.realex.ladder

/**
 * 사다리에 오르는 참가자.
 *
 * 이름 길이를 제한하는 이유는 출력 때문이다 — 사다리는 이름 폭에 맞춰 세로선을 그리므로
 * 긴 이름이 하나 섞이면 도면 전체가 어긋나 보인다.
 */
@JvmInline
value class Player(val name: String) {
    init {
        require(name.isNotBlank()) { "참가자 이름은 비어 있을 수 없습니다" }
        require(name.length <= MAX_NAME_LENGTH) {
            "참가자 이름은 ${MAX_NAME_LENGTH}자 이하여야 합니다: $name"
        }
    }

    companion object {
        const val MAX_NAME_LENGTH = 5
    }
}
