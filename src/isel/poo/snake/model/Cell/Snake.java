package isel.poo.snake.model.Cell;

import isel.poo.snake.model.Level;
import isel.poo.snake.model.Position;

public class Snake extends Cell {

    public Snake(int l, int c, boolean evil, boolean alive) {
        super(new Position(l,c));
        isEvil = evil;
        isAlive = alive;
        snakeSize = Level.setSize();
        if(evil)
            type='*';
        else
            type='@';
    }

    public Snake (Position position){
        this.position = position;
    }
}
