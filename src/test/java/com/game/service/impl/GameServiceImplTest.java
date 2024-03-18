package com.game.service.impl;

import com.game.service.GameManager;
import com.game.service.observers.impl.FileReadObserverImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

public class GameServiceImplTest {

    @Mock
    private GameManager gameManager;

    @InjectMocks
    private GameServiceImpl gameService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void startGame_AddsObserverAndStartsGame() {
        List<String> players = Arrays.asList("Player1", "Player2");

        // Call the method to be tested
        gameService.startGame(players);

        // Verify that addObserver and startGame methods are called
        verify(gameManager, times(1)).addObserver(any(FileReadObserverImpl.class));
        verify(gameManager, times(1)).startGame(players);
        // Verify no other methods are called on gameManager
        verifyNoMoreInteractions(gameManager);
    }
}
