package com.game.controller;

import com.game.dto.PlayerScore;
import com.game.service.GameService;
import com.game.service.ScoreService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("game")
@AllArgsConstructor
public class GameController {

    private GameService gameService;
    private ScoreService scoreService;

    @PostMapping("/start-game")
    public void startGame(
            @RequestBody
                    List<String> players
    ) {
        gameService.startGame(players);
    }

    @GetMapping("/top-score/{playerCount}")
    public List<PlayerScore> getTopPlayersScore(
            @PathVariable(value = "playerCount")
            Integer playerCount
    ) {
return scoreService.getTopPlayersScore(playerCount);
    }
}
