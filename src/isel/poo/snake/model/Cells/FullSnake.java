package isel.poo.snake.model.Cells;


import isel.poo.snake.model.Position;
import isel.poo.snake.view.Tiles.CellTile;

import java.util.LinkedList;

public class FullSnake extends Cell {

    public Cell snakeHead;
    public LinkedList<Cell> snakeBody = new LinkedList<>();
    private boolean isEvil;

    @Override
    public CellTile createTile() {
        return null;
    }
    public FullSnake(Cell snakeHead, boolean isEvil){
        this.snakeHead = snakeHead;
        this.isEvil = isEvil;
    }

    public void addBody(Cell tail) {
        snakeBody.addFirst(tail);
    }

    public void addBody(Position pos) {
        Cell newTail = new SnakeTail(pos);
        snakeBody.addFirst(newTail);
    }

    public Cell removeBody(){
        return snakeBody.removeLast();
    }

    public int getSnakeSize(){
        return snakeBody.size()+1;
    }


}
