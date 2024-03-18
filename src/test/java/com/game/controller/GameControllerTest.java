package com.game.controller;

import com.game.dto.PlayerScore;
import com.game.service.GameService;
import com.game.service.ScoreService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GameControllerTest {

    @Mock
    private GameService gameService;

    @Mock
    private ScoreService scoreService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testStartGame() throws Exception {
        List<String> players = Arrays.asList("Player1", "Player2");
        doNothing().when(gameService).startGame(players);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(MockMvcRequestBuilders.post("/game/start-game")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[\"Player1\",\"Player2\"]"))
                .andExpect(MockMvcResultMatchers.status().isOk());

        verify(gameService, times(1)).startGame(players);
        verifyNoMoreInteractions(gameService);
    }

    @Test
    public void testGetTopPlayersScore() throws Exception {
        List<PlayerScore> playerScores = Arrays.asList(
                new PlayerScore(1, "Player1", 100),
                new PlayerScore(2, "Player2", 90)
        );
        when(scoreService.getTopPlayersScore(2)).thenReturn(playerScores);

        MockMvc mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
        mockMvc.perform(MockMvcRequestBuilders.get("/game/top-score/2"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.length()").value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].name").value("Player1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].score").value(100))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].name").value("Player2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].score").value(90));

        verify(scoreService, times(1)).getTopPlayersScore(2);
        verifyNoMoreInteractions(scoreService);
    }
}

