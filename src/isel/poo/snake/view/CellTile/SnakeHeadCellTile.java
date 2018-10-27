package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.snake.model.Cell.Cell;

public class SnakeHeadCellTile extends CellTile {

    protected SnakeHeadCellTile(Cell cell) {
        super(cell);
    }

    public void paint(){

        if(cell.isEvil()) Console.color(Console.BLACK,Console.GRAY);
        else Console.color(Console.BLACK,Console.RED);
        print(SIDE/2, SIDE/2, '@');
        super.paint();
    }



}
