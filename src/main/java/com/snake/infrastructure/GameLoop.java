package com.snake.infrastructure;

import com.snake.application.GameUseCase;
import com.snake.core.domain.GameMode;
import com.snake.infrastructure.swingui.SwingGamePanel;

import javax.swing.Timer;

public class GameLoop {
    private Timer timer;
    private final GameUseCase gameUseCase;
    private final SwingGamePanel gamePanel;

    public GameLoop(GameUseCase gameUseCase, SwingGamePanel gamePanel) {
        this.gameUseCase = gameUseCase;
        this.gamePanel = gamePanel;
        createTimer();
    }

    private void createTimer() {
        timer = new Timer(gameUseCase.getCurrentDelay(), e -> {
            if (!gameUseCase.getGameState().isGameOver()) {
                gameUseCase.moveSnake();
                gamePanel.repaint();
            } else {
                timer.stop();
            }
        });
    }

    public void start() {
        timer.setDelay(gameUseCase.getCurrentDelay());
        timer.start();
    }

    public void restart(GameMode gameMode) {
        gameUseCase.reset(gameMode);
        timer.setDelay(gameUseCase.getCurrentDelay());
        gamePanel.repaint();
        timer.start();
    }
}