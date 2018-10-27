package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.Cell;

public class EvilSnakeTile extends CellTile {
    public EvilSnakeTile(Cell cell) {super(cell);
    }

    public void paint(){

        Console.color(Console.BLACK,Console.DARK_GRAY);
        print(SIDE, SIDE, '@');
        super.paint();
    }
}
