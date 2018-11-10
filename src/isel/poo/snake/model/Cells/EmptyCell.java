package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;


public class EmptyCell extends Cell {
    protected final char type = ' ';
    public EmptyCell(int l, int c) {
        super(new Position(l,c));
    }

    public EmptyCell() {
        super();
    }

    public char getType(){
        return type;
    }

}
