package com.snake.infrastructure;

import com.snake.application.GameUseCase;
import com.snake.infrastructure.swingui.SwingGamePanel;

import javax.swing.Timer;

public class GameLoop {
    private static final int DELAY = 150;
    private Timer timer = null;
    private final GameUseCase gameUseCase;
    private final SwingGamePanel gamePanel;

    public GameLoop(GameUseCase gameUseCase, SwingGamePanel gamePanel) {
        this.gameUseCase = gameUseCase;
        this.gamePanel = gamePanel;
        this.timer = new Timer(DELAY, e -> {
            if (!gameUseCase.getGameState().isGameOver()) {
                gameUseCase.moveSnake();
                gamePanel.repaint();
            } else {
                timer.stop();
            }
        });
    }

    public void start() {
        timer.start();
    }

    public void restart() {
        gameUseCase.reset();
        gamePanel.repaint();
        timer.start();
    }
}