package com.game.service.impl;

import com.game.service.GameManager;
import com.game.service.GameService;
import com.game.service.observers.impl.FileReadObserverImpl;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class GameServiceImpl implements GameService {

    private GameManager gameManager;

    @Override
    public void startGame(List<String> players) {
        // add observer
        gameManager.addObserver(new FileReadObserverImpl());
        gameManager.startGame(players);
    }
}
