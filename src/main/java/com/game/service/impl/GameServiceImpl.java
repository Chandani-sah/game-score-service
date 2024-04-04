package com.game.service.impl;

import com.game.repository.ScoreRepository;
import com.game.service.GameManager;
import com.game.service.GameService;
import com.game.service.observers.impl.FileReadObserver;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private GameManager gameManager;
    private FileReadObserver fileReadObserver;

    @Override
    public void startGame(List<String> players) {
        // add observer
        gameManager.addObserver(fileReadObserver);
        gameManager.startGame(players);
    }
}
