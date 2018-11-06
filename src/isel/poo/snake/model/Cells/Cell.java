package isel.poo.snake.model.Cells;

import isel.poo.snake.model.CreateTile;
import isel.poo.snake.model.Position;

public abstract class Cell implements CreateTile {

    protected static int l;
    protected static int c;
    protected char type;
    protected boolean isEvil;
    protected boolean isAlive;
    protected Position position;

    protected Cell(int l, int c) {
        Cell.c = c;
        Cell.l = l;
    }

    public Cell(Position position) {
        this.position = position;
    }

    public Cell() {

    }

    public static Cell newInstance(char type) {
        switch (type){

            case 'A' : return new Apple();

            case 'X' : return new Obstacle();

            case 'M' : return new Mouse();

            case '@' : return new SnakeHead(false, true);

            case '*' : return new SnakeHead(true, true);

            default: return null;

        }
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

    public char getType() {
        return type;
    }

    public boolean isEvil(){
        return isEvil;
    }

    public boolean isAlive(){
        return isAlive;
    }

}
