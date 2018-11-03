package isel.poo.snake.view.Tiles;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cells.Cell;

public class AppleTile extends CellTile {
    public AppleTile(Cell cell) {
        super(cell);
    }

    public void paint(){

        super.paint();
        Console.color(Console.BLACK,Console.LIGHT_GRAY);
        print(0, 0, "O");

    }
}
