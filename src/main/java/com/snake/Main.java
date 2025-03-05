package com.snake;

import com.snake.application.GameService;
import com.snake.application.GameUseCase;
import com.snake.infrastructure.GameLoop;
import com.snake.infrastructure.swingui.SwingGamePanel;
import com.snake.infrastructure.swingui.SwingGameWindow;

public class Main {
    public static void main(String[] args) {
        int gridWidth = 20;
        int gridHeight = 15;

        GameUseCase gameService = new GameService(gridWidth, gridHeight);
        SwingGamePanel gamePanel = new SwingGamePanel(gameService);
        GameLoop gameLoop = new GameLoop(gameService, gamePanel);
        SwingGameWindow window = new SwingGameWindow(gameService, gamePanel, gameLoop);

        window.setVisible(true);
        gameLoop.start();
    }
}
