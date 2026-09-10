package com.realex.ladder.persistence

import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

interface GameRepository : JpaRepository<GameEntity, String> {
    fun findAllByOrderByPlayedAtDesc(pageable: Pageable): List<GameEntity>
}
