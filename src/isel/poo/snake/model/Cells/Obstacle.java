package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;


public class Obstacle extends Cell {
    protected final char type = 'X';
    protected Obstacle(int l, int c) {
        super(new Position(l,c));
    }
    protected Obstacle() {
        super();
    }
    public char getType(){
        return type;
    }

}
