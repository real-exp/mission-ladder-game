package com.realex.ladder.persistence

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param

interface PlayerStatRepository : JpaRepository<PlayerStatEntity, String> {

    fun findAllByOrderByPlayCountDesc(): List<PlayerStatEntity>

    /**
     * 참가 횟수를 하나 올린다.
     *
     * 읽어서 더한 뒤 쓰면 동시에 들어온 판끼리 서로의 결과를 덮어쓴다. 더하기를 DB 에 맡기면
     * 같은 행을 건드리는 요청이 줄을 서므로 세다가 빠뜨리지 않는다.
     *
     * @return 갱신된 행 수. 0 이면 아직 그 사람의 행이 없다는 뜻이다
     */
    @Modifying
    @Query("update PlayerStatEntity s set s.playCount = s.playCount + 1 where s.name = :name")
    fun increasePlayCount(@Param("name") name: String): Int
}
