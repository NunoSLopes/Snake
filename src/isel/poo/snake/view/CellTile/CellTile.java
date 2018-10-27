package isel.poo.snake.view.CellTile;

import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.*;

public abstract class CellTile extends Tile {
    public static final int SIDE = 2;
    protected Cell cell;

    protected CellTile(Cell cell) {
        this.cell=cell;
    }

    public static Tile tileOf(Cell cell) {
        switch (cell.getType()){

            case 'A' : return new AppleTile(cell);

            case 'X' : return new ObstacleTile(cell);

            case 'M' : return new MouseTile(cell);

            case '@' : return new SnakeHeadCellTile(cell);

            case '*' : return new EvilSnakeTile(cell);

            case '#' : return new SnakeTailTile(cell);

            case ' ' : return new EmptyTile();

            default: return null;

        }
    }
}
