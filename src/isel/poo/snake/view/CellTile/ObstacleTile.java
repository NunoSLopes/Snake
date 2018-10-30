package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.Cell;

public class ObstacleTile extends CellTile {
    public ObstacleTile(Cell cell) {
        super(cell);
    }

    @Override
    public void paint(){

        Console.color(Console.BROWN,Console.BROWN);
        print(SIDE, SIDE, "");
    }
}
