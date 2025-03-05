package com.snake;

import com.snake.application.GameService;
import com.snake.application.GameUseCase;
import com.snake.core.domain.GameMode;
import com.snake.infrastructure.GameLoop;
import com.snake.infrastructure.swingui.SwingGamePanel;
import com.snake.infrastructure.swingui.SwingGameWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    public static void main(String[] args) {
        // Tela de seleção de modo
        JFrame modeSelectionFrame = new JFrame("Selecione o Modo");
        modeSelectionFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        modeSelectionFrame.setSize(300, 150);
        modeSelectionFrame.setLayout(new FlowLayout());
        modeSelectionFrame.setLocationRelativeTo(null);

        JButton easyButton = new JButton("Modo Easy");
        JButton hardButton = new JButton("Modo Hard");

        modeSelectionFrame.add(easyButton);
        modeSelectionFrame.add(hardButton);

        easyButton.addActionListener(e -> {
            startGame(GameMode.EASY);
            modeSelectionFrame.dispose();
        });

        hardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(GameMode.HARD);
                modeSelectionFrame.dispose();
            }
        });

        modeSelectionFrame.setVisible(true);
    }

    private static void startGame(GameMode gameMode) {
        int gridWidth = 20;
        int gridHeight = 15;

        GameService gameService = new GameService(gridWidth, gridHeight, gameMode);
        SwingGamePanel gamePanel = new SwingGamePanel(gameService);
        GameLoop gameLoop = new GameLoop(gameService, gamePanel);
        gameService.setGameLoop(gameLoop); // Vinculação essencial
        SwingGameWindow window = new SwingGameWindow(gameService, gamePanel, gameLoop);

        window.setVisible(true);
        gameLoop.start();
    }
}