package com.game.service.observers.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.dto.PlayerScore;
import com.game.model.Score;
import com.game.repository.ScoreRepository;
import com.game.service.observers.Observer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@AllArgsConstructor
@NoArgsConstructor
public class FileReadObserverImpl implements Observer {

    @Autowired
    private ScoreRepository scoreRepository;

    @Override
    public void updateScore(String filePath) {
        try {
            if(!Files.exists(Paths.get(filePath))) {
                log.info("File: {} does not exist", filePath);
                return;
            }
            // Read JSON content from file
            ObjectMapper objectMapper = new ObjectMapper();
            List<PlayerScore> playerScores = objectMapper.readValue(new File(filePath),
                    new TypeReference<List<PlayerScore>>() {});

            List<Score> scoreList = new ArrayList<>();
            playerScores.forEach(playerScore -> {
                scoreList.add(Score.builder().playerId(playerScore.getId())
                        .score(playerScore.getScore()).build());
            });
            scoreRepository.saveAll(scoreList);

            // Print deserialized object
            System.out.println("Deserialized object: " + playerScores.toString());
//            Files.delete(Paths.get(filePath));
        } catch (IOException e) {
            System.out.println("An error occurred while reading or deserializing the file: " + e.getMessage());
        }
    }
}
