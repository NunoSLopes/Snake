package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;

public class Apple extends Cell {

    protected final char type = 'A';

    public Apple() {
        super();
    }

    public Apple(Position position) {
        super(position);
    }

    public Apple(int l, int c) {
        super(l,c);
    }

    public char getType(){
        return type;
    }

}
