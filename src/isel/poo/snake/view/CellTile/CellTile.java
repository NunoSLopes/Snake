package isel.poo.snake.view.CellTile;

import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;
import isel.poo.snake.model.Cell.Cell;

public abstract class CellTile extends Tile {
    public static final int SIDE = 1;
    protected Cell cell;

    protected CellTile(Cell cell) {
        this.cell=cell;
    }

    @Override
    public void paint(){
        super.paint();
        Console.setBackground(Console.BLACK);
        print(0, 0, cell.getType());
    }

    public static Tile tileOf(Cell cell) {
        switch (cell.getType()){

            case 'A' : return new AppleTile(cell);

            case 'X' : return new ObstacleTile(cell);

            case 'M' : return new MouseTile(cell);

            case '@' : return new SnakeHeadCellTile(cell);

            case '*' : return new EvilSnakeTile(cell);

            case '#' : return new SnakeTailTile(cell);

            case ' ' : return new EmptyTile();

            default: return null;

        }
    }
}
