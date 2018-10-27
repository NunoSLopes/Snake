package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cell.Cell;

public class SnakeHeadCellTile extends CellTile {

    protected SnakeHeadCellTile(Cell cell) {
        super(cell);
    }

    public void paint(){

        Console.color(Console.BLACK,Console.YELLOW);
        print(SIDE, SIDE, '@');
        super.paint();
    }
}
