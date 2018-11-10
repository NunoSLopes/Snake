package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;


public class SnakeTail extends Cell {
    protected final char type = '#';

    public SnakeTail(int l, int c){
        super(new Position(l,c));
    }

    public SnakeTail(Position position) {
        super(position);
    }

    public char getType(){
       return type;
    }


}
