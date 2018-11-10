package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;

public class Apple extends Cell {

    protected final char type = 'A';

    protected Apple(int l, int c) {
        super(new Position(l,c));
    }

    protected Apple() {
        super();
    }

    public Apple(Position position) {
        this.position = position;
    }

    public char getType(){
        return type;
    }

}
