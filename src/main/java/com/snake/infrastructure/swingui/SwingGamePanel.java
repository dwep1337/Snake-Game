package com.snake.infrastructure.swingui;

import com.snake.application.GameUseCase;
import com.snake.core.domain.GameState;
import com.snake.core.domain.Position;

import javax.swing.*;
import java.awt.*;

public class SwingGamePanel extends JPanel {
    private static final int TILE_SIZE = 30;
    private final GameUseCase gameUseCase;

    public SwingGamePanel(GameUseCase gameUseCase) {
        this.gameUseCase = gameUseCase;
        setPreferredSize(new Dimension(
                gameUseCase.getGridWidth() * TILE_SIZE,
                gameUseCase.getGridHeight() * TILE_SIZE
        ));
        setBackground(Color.BLACK);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameState state = gameUseCase.getGameState();

        // Draw snake
        g.setColor(Color.GREEN);
        for (Position p : state.getSnake().getBody()) {
            g.fillRect(p.x() * TILE_SIZE, p.y() * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);
        }

        // Draw food
        g.setColor(Color.RED);
        Position foodPos = state.getFood().getPosition();
        g.fillOval(foodPos.x() * TILE_SIZE, foodPos.y() * TILE_SIZE, TILE_SIZE - 1, TILE_SIZE - 1);

        // Draw score
        g.setColor(Color.WHITE);
        g.drawString("Score: " + state.getScore(), 10, 20);

        // Game over message
        if (state.isGameOver()) {
            g.setColor(Color.RED);
            g.setFont(new Font("Arial", Font.BOLD, 30));
            String message = "Game Over! Score: " + state.getScore();
            int textWidth = g.getFontMetrics().stringWidth(message);
            g.drawString(message, (getWidth() - textWidth) / 2, getHeight() / 2);


            //restart message

            g.setFont(new Font("Arial", Font.PLAIN, 20));
            String restartMessage = "Press 'R' to Restart";
            int restartWidth = g.getFontMetrics().stringWidth(restartMessage);
            g.drawString(restartMessage, (getWidth() - restartWidth) / 2, getHeight() / 2 + 40);
        }
    }
}
