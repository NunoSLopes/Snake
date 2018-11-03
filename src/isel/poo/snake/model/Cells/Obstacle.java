package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;
import isel.poo.snake.view.Tiles.CellTile;
import isel.poo.snake.view.Tiles.ObstacleTile;

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

    public CellTile createTile() {
        return new ObstacleTile(this);
    }
}
