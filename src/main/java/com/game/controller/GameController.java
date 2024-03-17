package com.game.controller;

import com.game.dto.PlayerScore;
import com.game.service.GameManager;
import com.game.service.ScoreService;
import com.game.service.observers.impl.FileReadObserverImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("game")
@AllArgsConstructor
public class GameController {

    private GameManager gameManager;
    private ScoreService scoreService;

    @PostMapping("/start-game")
    public void startGame(
            @RequestBody
                    List<String> players
    ) {
        gameManager.addObserver(new FileReadObserverImpl());
        gameManager.startGame(players);
    }

    @GetMapping("/top-score/{playerCount}")
    public List<PlayerScore> getTopPlayersScore(
            @PathVariable(value = "playerCount")
            Integer playerCount
    ) {
return scoreService.getTopPlayersScore(playerCount);
    }
}
