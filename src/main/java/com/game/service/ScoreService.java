package com.game.service;

import com.game.dto.PlayerScore;

import java.util.List;

public interface ScoreService {
    List<PlayerScore> getTopPlayersScore(int count);
}
