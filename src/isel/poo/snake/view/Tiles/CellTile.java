package isel.poo.snake.view.Tiles;

import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cells.Cell;

public abstract class CellTile extends Tile {
    public static final int SIDE = 1;
    protected Cell cell;

    protected CellTile(Cell cell) {
        this.cell=cell;
        setSize(SIDE, SIDE);
    }
    protected CellTile() {

        setSize(SIDE, SIDE);
    }

    public static Tile tileOf(Cell cell) {
        return cell.createTile();
    }
}
