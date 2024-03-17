package com.game.service;

import com.game.service.observers.Observer;

import java.util.List;

public interface GameManager {
    void startGame(List<String> players);
    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObserver();
}
