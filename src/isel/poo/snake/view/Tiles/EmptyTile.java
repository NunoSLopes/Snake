package isel.poo.snake.view.Tiles;


import isel.leic.pg.Console;


public class EmptyTile extends CellTile {

    public EmptyTile() {
        super();
    }

    public void paint(){

        super.paint();
        Console.color(Console.LIGHT_GRAY,Console.LIGHT_GRAY);
        print(0, 0, " ");

    }
}
