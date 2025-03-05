package com.snake.core.domain;

public class GameState {
    private final Snake snake;
    private final Food food;
    private final GameMode gameMode;
    private int score;
    private boolean gameOver;

    public GameState(Snake snake, Food food, GameMode gameMode) {
        this.snake = snake;
        this.food = food;
        this.gameMode = gameMode;
        this.score = 0;
        this.gameOver = false;
    }

    public Snake getSnake() { return snake; }
    public Food getFood() { return food; }
    public int getScore() { return score; }
    public void incrementScore() { score++; }
    public boolean isGameOver() { return gameOver; }
    public void setGameOver(boolean gameOver) { this.gameOver = gameOver; }
    public GameMode getGameMode() { return gameMode; }
}
