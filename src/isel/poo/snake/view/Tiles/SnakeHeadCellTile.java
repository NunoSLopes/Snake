package isel.poo.snake.view.Tiles;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cells.Cell;

public class SnakeHeadCellTile extends CellTile {

    public SnakeHeadCellTile(Cell cell) {
        super(cell);
    }

    public void paint(){
        super.paint();
        switch (cell.getType()){
            case '*': Console.color(Console.BLACK,Console.DARK_GRAY);
            default: Console.color(Console.BLACK,Console.YELLOW);
        }

        print(0, 0, cell.getType());

    }
}
