package isel.poo.snake.view.Tiles;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cells.Cell;

public class MouseTile extends CellTile {
    public MouseTile(Cell cell) {
        super(cell);
    }

    public void paint(){
        super.paint();
        Console.color(Console.BLUE,Console.LIGHT_GRAY);
        print(0, 0, cell.getType());

    }
}
