package isel.poo.snake.model;


public final class Position {

    public final int l, c;

    /**
     * Initiates an instance with the given coordinates.
     * @param l the line coordinate
     * @param c the column coordinate
     */
    public Position(int l, int c) {
        this.c = c;
        this.l = l;
    }

    public boolean comparePos(Position pos) {
        return this.c == pos.c && this.l == pos.l;
    }

    public boolean comparePos(int l, int c) {
        return this.c == c && this.l == l;
    }


}
