package com.snake.application;

import com.snake.core.domain.*;

import java.util.List;
import java.util.Random;

public class GameService implements GameUseCase {
    private GameState gameState;
    private final int gridWidth;
    private final int gridHeight;
    private final Random random = new Random();

    public GameService(int gridWidth, int gridHeight) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        reset(); // initialize the game
        Position startPosition = new Position(gridWidth / 2, gridHeight / 2);
        Snake snake = new Snake(startPosition);
        Food food = new Food(generateRandomPosition(snake.getBody()));
        this.gameState = new GameState(snake, food);

    }

    @Override
    public void moveSnake() {
        if (gameState.isGameOver()) return;

        Snake snake = gameState.getSnake();
        Position newHead = snake.calculateNewHeadPosition();

        if (isOutOfBounds(newHead)) {
            gameState.setGameOver(true);
            return;
        }

        boolean grow = newHead.equals(gameState.getFood().getPosition());
        snake.move(grow);

        if (grow) {
            gameState.incrementScore();
            generateNewFood();
        }

        if (snake.isHeadCollidingWithBody()) {
            gameState.setGameOver(true);
        }
    }

    @Override
    public void reset() {
        Position startPosition = new Position(gridWidth / 2, gridHeight / 2);
        Snake snake = new Snake(startPosition);
        Food food = new Food(generateRandomPosition(snake.getBody()));
        this.gameState = new GameState(snake, food);
    }

    @Override
    public void changeDirection(Direction newDirection) {
        gameState.getSnake().setDirection(newDirection);
    }

    @Override
    public void generateNewFood() {
        List<Position> snakeBody = gameState.getSnake().getBody();
        gameState.getFood().setPosition(generateRandomPosition(snakeBody));
    }

    private Position generateRandomPosition(List<Position> exclude) {
        Position position;
        do {
            int x = random.nextInt(gridWidth);
            int y = random.nextInt(gridHeight);
            position = new Position(x, y);
        } while (exclude.contains(position));
        return position;
    }

    private boolean isOutOfBounds(Position position) {
        return position.x() < 0 || position.x() >= gridWidth ||
                position.y() < 0 || position.y() >= gridHeight;
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    @Override
    public int getGridWidth() { return gridWidth; }

    @Override
    public int getGridHeight() { return gridHeight; }
}
