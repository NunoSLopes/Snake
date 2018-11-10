package isel.poo.snake.model.Cells;

import isel.poo.snake.model.*;


public class SnakeHead extends Cell {

    protected char type;

    public SnakeHead(boolean evil, boolean alive) {
        super();
        isEvil = evil;
        isAlive = alive;

        if(evil) {
            type = alive ? '*' : 'X';;
        }else {
            type = alive ? '@' : 'X';
        }
    }


    public SnakeHead(Position position){
        super(position);

    }

    public char getType(){
        return type;
    }

}
