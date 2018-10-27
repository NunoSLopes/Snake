package isel.poo.snake.model.Cell;

import isel.poo.snake.model.Position;

public class EmptyCell extends Cell {
    public EmptyCell(int l, int c) {
        super(new Position(l,c));
        type = ' ';
    }
}
