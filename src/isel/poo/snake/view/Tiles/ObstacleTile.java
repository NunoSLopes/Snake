package isel.poo.snake.view.Tiles;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cells.Cell;

public class ObstacleTile extends CellTile {
    public ObstacleTile(Cell cell) {
        super(cell);
    }

    public void paint(){
        super.paint();
        Console.color(Console.WHITE,Console.BROWN);
        print(0, 0, " ");

    }
}
