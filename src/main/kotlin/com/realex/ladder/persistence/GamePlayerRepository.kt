package com.realex.ladder.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface GamePlayerRepository : JpaRepository<GamePlayerEntity, Long> {
    fun findByGameIdOrderByPosition(gameId: String): List<GamePlayerEntity>
}
