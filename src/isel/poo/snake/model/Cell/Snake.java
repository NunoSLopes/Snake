package isel.poo.snake.model.Cell;

public class Snake extends Cell {
        protected Snake(int l, int c, boolean evil) {
        super(l, c);
        isEvil = evil;
    }
}
