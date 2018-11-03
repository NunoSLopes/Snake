package isel.poo.snake.model.Cells;

import isel.poo.snake.model.*;
import isel.poo.snake.view.Tiles.CellTile;
import isel.poo.snake.view.Tiles.SnakeHeadCellTile;


public class SnakeHead extends Cell {

    protected char type;

    public SnakeHead(boolean evil, boolean alive) {
        super();
        isEvil = evil;
        isAlive = alive;
        snakeSize = Level.setSize();
        if(evil)
            type='*';
        else

            type= alive? '@': 'X';

    }

    public SnakeHead(Position position){
        super(position);
    }

    public char getType(){
        return type;
    }

    public CellTile createTile() {
       return new SnakeHeadCellTile(this);
    }
}
