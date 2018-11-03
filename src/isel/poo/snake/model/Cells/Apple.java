package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;
import isel.poo.snake.view.Tiles.AppleTile;
import isel.poo.snake.view.Tiles.CellTile;

public class Apple extends Cell {

    protected final char type = 'A';

    protected Apple(int l, int c) {
        super(new Position(l,c));
    }

    protected Apple() {
        super();
    }

    public Apple(Position position) {
        this.position = position;
    }

    public char getType(){
        return type;
    }

    public CellTile createTile() {
        return new AppleTile(this);
    }
}
