package isel.poo.snake.view.Tiles;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cells.Cell;

public class SnakeHeadTile extends CellTile {

    public SnakeHeadTile(Cell cell) {
        super(cell);
    }

    public void paint(){
        super.paint();

        if (cell.isEvil())
            Console.color(Console.BLACK,Console.DARK_GRAY);
        else
            Console.color(Console.BLACK,Console.YELLOW);

        print(0, 0, cell.getType());

    }
}
