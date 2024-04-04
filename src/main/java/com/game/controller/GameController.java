package com.game.controller;

import com.game.dto.PlayerScore;
import com.game.service.GameService;
import com.game.service.ScoreService;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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

    @GetMapping("/top-score/{count}")
    public ResponseEntity<List<PlayerScore>> getTopPlayersScore(
            @PathVariable(value = "count")
            Integer count
    ) {
        if(count <= 0) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(scoreService.getTopPlayersScore(count), HttpStatus.OK);
    }
}
