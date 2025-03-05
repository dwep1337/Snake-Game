package com.snake.core.domain;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Snake {
    private final LinkedList<Position> body;
    private Direction direction;

    public Snake(Position startPosition) {
        this.body = new LinkedList<>();
        this.body.add(startPosition);
        this.direction = Direction.RIGHT;
    }

    public Position calculateNewHead() {
        Position head = body.getFirst();
        return switch (direction) {
            case UP -> new Position(head.x(), head.y() - 1);
            case DOWN -> new Position(head.x(), head.y() + 1);
            case LEFT -> new Position(head.x() - 1, head.y());
            case RIGHT -> new Position(head.x() + 1, head.y());
        };
    }

    public void move(Position newHead, boolean grow) {
        body.addFirst(newHead); // Adiciona a nova cabeça
        if (!grow) {
            body.removeLast(); // Remove a cauda se não crescer
        }
    }
    public void setDirection(Direction direction) {
        if (!isOppositeDirection(direction)) {
            this.direction = direction;
        }
    }

    private boolean isOppositeDirection(Direction direction) {
        return (this.direction == Direction.UP && direction == Direction.DOWN)
                || (this.direction == Direction.DOWN && direction == Direction.UP)
                || (this.direction == Direction.LEFT && direction == Direction.RIGHT)
                || (this.direction == Direction.RIGHT && direction == Direction.LEFT);
    }

    public List<Position> getBody() { return new ArrayList<>(body); }
}
