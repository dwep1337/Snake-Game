package com.snake.application;

import com.snake.core.domain.*;

import java.util.List;
import java.util.Random;

public class GameService implements GameUseCase {
    private GameState gameState;
    private final int gridWidth;
    private final int gridHeight;
    private final Random random = new Random();
    private int applesEaten;
    private final int initialDelay = 150;
    private int currentDelay;

    public GameService(int gridWidth, int gridHeight, GameMode gameMode) {
        this.gridWidth = gridWidth;
        this.gridHeight = gridHeight;
        this.currentDelay = initialDelay;
        reset(gameMode);
    }

    @Override
    public void reset(GameMode gameMode) {
        Position startPosition = new Position(gridWidth / 2, gridHeight / 2);
        Snake snake = new Snake(startPosition);
        Food food = new Food(generateRandomPosition(snake.getBody()));
        this.gameState = new GameState(snake, food, gameMode);
        this.applesEaten = 0;
        this.currentDelay = initialDelay;
    }

    @Override
    public int getCurrentDelay() {
        return currentDelay;
    }

    @Override
    public void moveSnake() {
        if (gameState.isGameOver()) return;

        Snake snake = gameState.getSnake();
        Position newHead = snake.calculateNewHead();

        // Modo Easy: Atravessar paredes
        if (gameState.getGameMode() == GameMode.EASY && isOutOfBounds(newHead)) {
            newHead = wrapAround(newHead);
        }
        // Modo Hard: Colisão com paredes
        else if (gameState.getGameMode() == GameMode.HARD && isOutOfBounds(newHead)) {
            gameState.setGameOver(true);
            return;
        }

        // Verifica colisão com o corpo
        Position finalNewHead = newHead;
        if (snake.getBody().stream().skip(1).anyMatch(p -> p.equals(finalNewHead))) {
            gameState.setGameOver(true);
            return;
        }

        // Verifica se a cobra comeu a comida
        boolean grow = newHead.equals(gameState.getFood().getPosition());
        snake.move(newHead, grow); // Passa a nova posição ajustada

        if (grow) {
            gameState.incrementScore();
            applesEaten++;
            generateNewFood();
            updateSpeed();
        }
    }

    private Position wrapAround(Position position) {
        int x = position.x();
        int y = position.y();

        if (x < 0) x = gridWidth - 1; // Sai pela esquerda, entra na direita
        else if (x >= gridWidth) x = 0; // Sai pela direita, entra na esquerda

        if (y < 0) y = gridHeight - 1; // Sai por cima, entra por baixo
        else if (y >= gridHeight) y = 0; // Sai por baixo, entra por cima

        return new Position(x, y);
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

    private void updateSpeed() {
        if (applesEaten % 2 == 0) { // A cada 2 maçãs comidas
            int speedIncrease = (gameState.getGameMode() == GameMode.EASY) ? 1 : 2;
            currentDelay = Math.max(50, currentDelay - (10 * speedIncrease)); // Aumenta a velocidade
        }
    }
    @Override
    public int getCurrentSpeed() {
        return (int) (1000.0 / currentDelay);
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
