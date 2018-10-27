package isel.poo.snake.model.Cell;

import isel.poo.snake.model.Position;

public class SnakeTail extends Cell {
    protected char type;

    public SnakeTail(int l, int c){
        super(new Position(l,c));
        type = '#';
    }

    public SnakeTail(Position position) {
        type = '#';
        this.position = position;
    }
}
