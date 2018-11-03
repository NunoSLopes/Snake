package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Position;
import isel.poo.snake.view.Tiles.CellTile;
import isel.poo.snake.view.Tiles.SnakeTailTile;

public class SnakeTail extends Cell {
    protected final char type = '#';

    public SnakeTail(int l, int c){
        super(new Position(l,c));
    }

    public SnakeTail(){
        super();
    }

    public SnakeTail(Position position) {
        super(position);
    }

    public char getType(){
       return type;
    }

    public CellTile createTile() {
        return new SnakeTailTile(this);
    }
}
