package com.snake.application;

import com.snake.core.domain.Direction;
import com.snake.core.domain.GameState;

public interface GameUseCase {
    void moveSnake();
    void changeDirection(Direction newDirection);
    void generateNewFood();
    GameState getGameState();
    int getGridWidth();
    int getGridHeight();
    void reset();
}