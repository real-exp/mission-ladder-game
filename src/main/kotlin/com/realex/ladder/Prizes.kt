package com.realex.ladder

/**
 * 사다리 맨 아래에 놓인 실행 결과들. 위치가 곧 어느 세로선 아래인지다.
 *
 * 길이 제한은 [Player] 와 같은 이유다 — 도면 아래에 열 맞춰 찍히므로 길면 어긋난다.
 */
class Prizes(val values: List<String>) {

    init {
        require(values.isNotEmpty()) { "실행 결과는 하나 이상이어야 합니다" }
        require(values.all { it.isNotBlank() }) { "실행 결과는 비어 있을 수 없습니다" }
        require(values.all { it.length <= MAX_LENGTH }) {
            "실행 결과는 ${MAX_LENGTH}자 이하여야 합니다: ${values.first { it.length > MAX_LENGTH }}"
        }
    }

    val size: Int get() = values.size

    operator fun get(position: Int): String = values[position]

    companion object {
        const val MAX_LENGTH = 5
    }
}
