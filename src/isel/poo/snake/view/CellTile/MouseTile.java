package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.Cell;

public class MouseTile extends CellTile {
    public MouseTile(Cell cell) {
        super(cell);
    }

    public void paint(){

        Console.color(Console.BLACK,Console.BLUE);
        print(SIDE, SIDE, 'M');
        super.paint();
    }
}
