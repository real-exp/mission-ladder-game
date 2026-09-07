package com.realex.ladder

/**
 * 이번 판의 참가자들.
 *
 * 목록의 순서가 곧 사다리에서의 시작 위치다. 이름이 중복되면 결과를 되돌려 줄 때
 * 누구의 것인지 가릴 수 없으므로 허용하지 않는다.
 */
class Players(val values: List<Player>) {

    init {
        require(values.size >= MIN_COUNT) { "참가자는 ${MIN_COUNT}명 이상이어야 합니다" }
        require(values.distinct().size == values.size) { "참가자 이름은 중복될 수 없습니다" }
    }

    val size: Int get() = values.size

    fun positionOf(player: Player): Int {
        val position = values.indexOf(player)
        require(position >= 0) { "참가하지 않은 사람입니다: ${player.name}" }
        return position
    }

    companion object {
        const val MIN_COUNT = 2

        fun of(names: List<String>): Players = Players(names.map(::Player))
    }
}
