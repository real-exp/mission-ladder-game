package com.realex.ladder.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.Instant

/**
 * 돌린 판 한 건.
 *
 * 사다리 도면은 층마다 가로선 유무를 0/1 로 적고 층은 쉼표로 잇는다 ("110,011").
 * 결과만으로는 어떻게 그 자리에 갔는지 다시 그릴 수 없어 도면도 함께 남긴다.
 */
@Entity
@Table(name = "games")
class GameEntity(
    @Id
    var id: String = "",

    @Column(nullable = false)
    var playedAt: Instant = Instant.EPOCH,

    @Column(name = "ladder_rows", nullable = false, length = 2000)
    var rows: String = "",
)
