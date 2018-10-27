package isel.poo.snake.model.Cell;

import isel.poo.snake.model.Position;

public class Apple extends Cell {


    protected Apple(int l, int c) {
        super(new Position(l,c));
        type = 'A';
    }

}
