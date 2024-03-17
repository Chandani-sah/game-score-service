package com.game.repository;

import com.game.model.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Integer> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM game.score ORDER BY score DESC LIMIT :count")
    List<Score> getTopNPlayers(
            @Param("count")
            Integer count);
}
