package com.game.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.game.dto.PlayerScore;
import com.game.model.Player;
import com.game.repository.PlayerRepository;
import com.game.service.GameManager;
import com.game.service.observers.Observer;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class GameManagerImpl implements GameManager {

    private PlayerRepository playerRepository;
    private List<Observer> observers;
    private final String filePath = "player_score.txt";



    @Override
    public void startGame(List<String> players) {
//        List<Player> playerList = new ArrayList<>();
//        players.forEach(playerName ->
//                playerList.add(Player.builder().name(playerName).build()));
//        playerRepository.saveAll(playerList);

        //assign score
        Random random = new Random();
        List<PlayerScore> playerScores = new ArrayList<>();
        playerRepository.findAll().forEach(player -> {
            int score = (int) random.nextInt(100);
            playerScores.add(PlayerScore.builder()
                    .id(player.getId())
                    .name(player.getName())
                    .score(score).build());
        });
        // Convert object to JSON
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonContent;
        try {
            jsonContent = objectMapper.writeValueAsString(playerScores);
            System.out.println("jsonContent"+ jsonContent);
        } catch (IOException e) {
            System.out.println("An error occurred while serializing object to JSON: " + e.getMessage());
            return;
        }

         // Write JSON content to file
        try {
            Path path = Paths.get(filePath);
            Files.write(path, jsonContent.getBytes());
            System.out.println("JSON content written to file successfully.");
            notifyObserver();
        } catch (IOException e) {
            System.out.println("An error occurred while writing JSON content to file: " + e.getMessage());
        }

    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObserver() {
        observers.forEach(observer -> observer.updateScore(filePath));
    }

}
