package isel.poo.snake.model.Cells;

public class Obstacle extends Cell {
    protected final char type = 'X';

    protected Obstacle() {
        super();
    }

    public char getType(){
        return type;
    }

}
