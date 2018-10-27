package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.Cell;

public class SnakeTailTile extends CellTile {
    public SnakeTailTile(Cell cell) {super(cell);
    }

    public void paint(){

        Console.color(Console.BLACK,Console.RED);
        print(SIDE, SIDE, '#');
        super.paint();
    }
}
