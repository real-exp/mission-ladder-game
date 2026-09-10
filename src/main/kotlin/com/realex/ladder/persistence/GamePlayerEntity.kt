package com.realex.ladder.persistence

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Index
import jakarta.persistence.Table

/**
 * 한 판에 참가한 사람과 그가 받은 실행 결과.
 *
 * [position] 은 사다리에서의 시작 위치이자 화면에 늘어놓는 순서다.
 */
@Entity
@Table(
    name = "game_players",
    indexes = [Index(name = "idx_game_players_game_id", columnList = "gameId")],
)
class GamePlayerEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var gameId: String = "",

    @Column(nullable = false)
    var position: Int = 0,

    @Column(nullable = false, length = 20)
    var name: String = "",

    @Column(nullable = false, length = 20)
    var prize: String = "",
)
