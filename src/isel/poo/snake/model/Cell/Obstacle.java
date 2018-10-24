package isel.poo.snake.model.Cell;

import isel.poo.snake.model.Position;

public class Obstacle extends Cell {
    protected Obstacle(int l, int c) {
        super(new Position(l,c));
    }

    protected Obstacle(){
        super();
    }
}
