package com.realex.ladder

/** 사다리 맨 아래에 놓인 실행 결과들. 위치가 곧 어느 세로선 아래인지다 */
class Prizes(val values: List<String>) {

    init {
        require(values.isNotEmpty()) { "실행 결과는 하나 이상이어야 합니다" }
        require(values.all { it.isNotBlank() }) { "실행 결과는 비어 있을 수 없습니다" }
    }

    val size: Int get() = values.size

    operator fun get(position: Int): String = values[position]
}
