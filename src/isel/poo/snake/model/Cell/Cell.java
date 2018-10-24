package isel.poo.snake.model.Cell;

import isel.poo.snake.model.Position;

public abstract class Cell {

    protected static int l;
    protected static int c;

    protected Position position;


    protected Cell(int l, int c) {
        this.c = c;
        this.l = l;
    }

    public static Cell newInstance(char type) {
        switch (type){

            case 'A' : return new Apple(l,c);

            case 'X' : return new Obstacle(l,c);

            case 'M' : return new Mouse(l,c);

            case '@' : return new Snake(l,c);

            default: return null;

        }
    }

    public Cell getCellAt(int l, int c) {
        if(getPosition().l == l && getPosition().c == c)
            return this;
        return null;
    }

    /**
     * Gets the element's horizontal coordinate.
     * @return the horizontal coordinate.
     */
    public int getC() {
        return position.c;
    }

    /**
     * Gets the element's vertical coordinate.
     * @return the vertical coordinate.
     */
    public int getL() {
        return position.l;
    }

    /**
     * Gets the element's position.
     * @return the elements position
     */
    public Position getPosition() {
        return position;
    }

    /**
     * Changes the element position.
     * @param position the new position.
     */
    public void setPositionAt(Position position) {
        if (position == null)
            throw new IllegalArgumentException();
        this.position = position;
    }

    /**
     * Changes the element position.
     * @param x the new horizontal coordinate.
     * @param y the new vertical coordinate.
     */
    public void setPositionAt(int x, int y) {
        setPositionAt(new Position(x, y));
    }
}
