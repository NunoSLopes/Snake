package isel.poo.snake.view.Tiles;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cells.Cell;

public class SnakeTailTile extends CellTile {

    public SnakeTailTile(Cell cell) {
        super(cell);
    }

    public void paint(){
        super.paint();
        Console.color(Console.BLACK,Console.RED);
        print(0, 0, cell.getType());
    }
}
