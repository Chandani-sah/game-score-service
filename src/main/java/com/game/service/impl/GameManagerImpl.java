package com.game.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.dto.PlayerScore;
import com.game.repository.PlayerRepository;
import com.game.service.GameManager;
import com.game.service.observers.Observer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Slf4j
@Service
@AllArgsConstructor
public class GameManagerImpl implements GameManager {

    private PlayerRepository playerRepository;
    private List<Observer> observers;
    private final String filePath = "player_score.txt";

    @Override
    public void startGame(List<String> players) {

        //assign random score
        Random random = new Random();
        List<PlayerScore> playerScores = new ArrayList<>();
        playerRepository.findAll().forEach(player -> {
            int score = random.nextInt(100);
            playerScores.add(PlayerScore.builder()
                    .id(player.getId())
                    .name(player.getName())
                    .score(score).build());
        });

        // Convert player score to JSON String
        String playerScoresJsonFormat = getJsonStringData(playerScores);

         // Write JSON format score to file
        if(!StringUtils.isBlank(playerScoresJsonFormat)) {
            writeScoreToFile(playerScoresJsonFormat);
        }
    }

    private void writeScoreToFile(String playerScoresJsonFormat) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, playerScoresJsonFormat.getBytes());
            log.info("Player score json data written to file {} successfully.", filePath);
            notifyObservers();
        } catch (IOException e) {
            log.error("An error occurred while writing JSON content to file: " + e.getMessage());
        }
    }

    private String getJsonStringData(List<PlayerScore> playerScores) {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent = "";
        try {
            jsonContent = objectMapper.writeValueAsString(playerScores);
            log.info("Mapping player score data to json string");
        } catch (IOException e) {
            log.error("An error occurred while serializing object to JSON: " + e.getMessage());
        }
        return jsonContent;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(observer -> observer.updateScore(filePath));
    }

}
