package isel.poo.snake.view.CellTile;

import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.*;

public class CellTile extends Tile {
    public static final int SIDE = 2;
    protected Cell cell;

    protected CellTile(Cell cell) {
        this.cell=cell;
    }

    public static Tile tileOf(Cell cell) {
        switch (cell.getType()){

            case 'A' : return new Apple(l,c);

            case 'X' : return new Obstacle(l,c);

            case 'M' : return new Mouse(l,c);

            case '@' : return new SnakeHeadCellTile(cell);

            case '*' : return new Snake(l,c);

            default: return null;

        }
    }
}
