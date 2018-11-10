package isel.poo.snake.model.Cells;


import isel.poo.snake.model.Position;

import java.util.LinkedList;

public class FullSnake extends Cell {

    public SnakeHead snakeHead;
    public LinkedList<Cell> snakeBody = new LinkedList<>();

    private int growth = 4;

    public FullSnake(SnakeHead snakeHead, boolean isEvil){
        super();
        this.snakeHead = snakeHead;
        this.isEvil = isEvil;
        this.position = snakeHead.position;
    }


    public void walk(Position pos){
        snakeHead.setPositionAt(pos);
        this.setPositionAt(pos);
    }


    public Cell addBody(Position pos) {
        Cell newTail = new SnakeTail(pos);
        snakeBody.addFirst(newTail);
        return newTail;
    }

    public Cell removeBody(){
        return snakeBody.removeLast();
    }

    public int getBodySize(){
        return snakeBody.size()+1;
    }

    public void kill(){
        snakeHead.isAlive = isAlive = false;
        snakeHead.type = 'X';
    }

    public int getGrowth() {
        return growth;
    }

    public void setGrowth(int growth) {
        this.growth += growth;
    }
}
