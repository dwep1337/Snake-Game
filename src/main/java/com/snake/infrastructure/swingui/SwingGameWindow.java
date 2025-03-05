package com.snake.infrastructure.swingui;

import com.snake.application.GameUseCase;
import com.snake.core.domain.Direction;
import com.snake.core.domain.GameMode;
import com.snake.infrastructure.GameLoop;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SwingGameWindow extends JFrame {
    private final GameLoop gameLoop;
    private final GameUseCase gameUseCase;

    public SwingGameWindow(GameUseCase gameUseCase, SwingGamePanel gamePanel, GameLoop gameLoop) {
        this.gameLoop = gameLoop;
        this.gameUseCase = gameUseCase;
        setTitle("Snake Game");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        add(gamePanel);
        pack();
        setLocationRelativeTo(null);

        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (gameUseCase.getGameState().isGameOver()) {
                    if (e.getKeyCode() == KeyEvent.VK_R) {
                        gameLoop.restart(GameMode.EASY); // Reinicia no modo Easy
                    } else if (e.getKeyCode() == KeyEvent.VK_H) {
                        gameLoop.restart(GameMode.HARD); // Reinicia no modo Hard
                    }
                } else {
                    Direction newDirection = switch (e.getKeyCode()) {
                        case KeyEvent.VK_UP -> Direction.UP;
                        case KeyEvent.VK_DOWN -> Direction.DOWN;
                        case KeyEvent.VK_LEFT -> Direction.LEFT;
                        case KeyEvent.VK_RIGHT -> Direction.RIGHT;
                        default -> null;
                    };
                    if (newDirection != null) {
                        gameUseCase.changeDirection(newDirection);
                    }
                }
            }
        });
    }
}
