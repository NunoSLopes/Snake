package isel.poo.snake.model.Cell;

import isel.poo.snake.model.Position;

public class Mouse extends Cell {
    protected Mouse(int l, int c) {
        super(new Position(l,c));
        type = 'M';
    }
}
