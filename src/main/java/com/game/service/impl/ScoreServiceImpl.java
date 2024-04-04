package com.game.service.impl;

import com.game.dto.PlayerScore;
import com.game.repository.ScoreRepository;
import com.game.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ScoreServiceImpl implements ScoreService {

    private ScoreRepository scoreRepository;

    @Override
    public List<PlayerScore> getTopPlayersScore(int count) {
        return scoreRepository.getTopNPlayers(count).stream()
                .map(score -> PlayerScore.builder().id(score.getPlayerId())
                .name(score.getPlayer().getName())
                .score(score.getScore()).build()).collect(Collectors.toList());
    }
}
