package isel.poo.snake.model.Cells;

public class SnakeHead extends Cell {

    protected char type;

    public SnakeHead(boolean evil, boolean alive) {
        super();
        isEvil = evil;
        isAlive = alive;

        if(evil) {
            type = alive ? '*' : 'X';;
        }else {
            type = alive ? '@' : 'X';
        }
    }

    public char getType(){
        return type;
    }

}
