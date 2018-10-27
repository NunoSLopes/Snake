package isel.poo.snake.view.CellTile;


import isel.leic.pg.Console;
import isel.poo.console.tile.Tile;

import static isel.poo.snake.view.CellTile.CellTile.SIDE;


public class EmptyTile extends Tile {

    public EmptyTile() {
    }

    public void paint(){

        Console.color(Console.LIGHT_GRAY,Console.LIGHT_GRAY);
        print(SIDE, SIDE, " ");
        super.paint();
    }
}
