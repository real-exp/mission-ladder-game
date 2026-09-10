package com.realex.ladder.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

/**
 * 사람별 누적 참가 횟수.
 *
 * 판을 돌릴 때마다 세어 두면 이력을 전부 훑지 않고도 "누가 몇 번 했는지" 바로 답할 수 있다.
 */
@Entity
@Table(name = "player_stats")
class PlayerStatEntity(
    @Id
    @Column(length = 20)
    var name: String = "",

    @Column(nullable = false)
    var playCount: Int = 0,
)
