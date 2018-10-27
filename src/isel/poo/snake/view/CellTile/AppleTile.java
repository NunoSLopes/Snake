package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.Cell;

public class AppleTile extends CellTile {
    public AppleTile(Cell cell) {super(cell);
    }

    public void paint(){

        Console.color(Console.BLACK,Console.LIGHT_GRAY);
        print(SIDE, SIDE, 'O');
        super.paint();
    }
}
