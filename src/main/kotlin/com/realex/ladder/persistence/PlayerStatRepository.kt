package com.realex.ladder.persistence

import org.springframework.data.jpa.repository.JpaRepository

interface PlayerStatRepository : JpaRepository<PlayerStatEntity, String> {
    fun findAllByOrderByPlayCountDesc(): List<PlayerStatEntity>
}
