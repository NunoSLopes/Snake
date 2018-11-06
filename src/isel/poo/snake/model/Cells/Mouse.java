package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;
import isel.poo.snake.view.Tiles.CellTile;
import isel.poo.snake.view.Tiles.MouseTile;

public class Mouse extends Cell {

    protected final char type = 'M';

    protected Mouse(int l, int c) {
        super(new Position(l,c));
    }
    protected Mouse() {
        super();
    }

    public char getType(){
        return type;
    }

    public CellTile createTile() {
        return new MouseTile(this);
    }



}
