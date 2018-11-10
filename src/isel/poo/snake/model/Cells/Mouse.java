package isel.poo.snake.model.Cells;


public class Mouse extends Cell {

    protected final char type = 'M';

    protected Mouse(boolean bot) {
        super();
    }

    public char getType(){
        return type;
    }


}
