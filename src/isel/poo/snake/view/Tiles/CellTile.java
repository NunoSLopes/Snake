package isel.poo.snake.view.Tiles;

import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cells.Cell;
import isel.poo.snake.model.Cells.FullSnake;
import isel.poo.snake.model.Cells.SnakeHead;

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

         switch (cell.getType()){

             case '@': return new SnakeHeadTile(cell);
             case 'M': return new MouseTile(cell);
             case 'A': return new AppleTile(cell);
             case '*': return new SnakeHeadTile(cell);
             case '#': return new SnakeTailTile(cell);
             case ' ': return new EmptyTile();
             case 'X': {
                 if (cell instanceof SnakeHead)return new SnakeHeadTile(cell);
                 else return new ObstacleTile(cell);
             }
             default: return null;
         }

    }
}
