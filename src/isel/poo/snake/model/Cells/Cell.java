package isel.poo.snake.model.Cells;

import isel.poo.snake.model.Dir;
import isel.poo.snake.model.Position;

public abstract class Cell{

    protected int l;
    protected int c;
    protected char type;
    protected boolean isEvil;
    protected boolean isAlive;
    protected Position position;
    private Dir direction = randomDir() ;


    public Cell() {
        setRandomDir();
    }

    public Cell(int l, int c) {
        this(new Position(l,c));
    }

    public Cell(Position position) {
        if (position == null) {
            throw new IllegalArgumentException();
        }
        this.position = position;
        this.l = position.l;
        this.c = position.c;

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
        return c;
    }

    /**
     * Gets the element's vertical coordinate.
     * @return the vertical coordinate.
     */
    public int getL() {
        return l;
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
        this.l = position.l;
        this.c = position.c;
    }

    /**
     * Changes the element position.
     * @param c the new horizontal coordinate.
     * @param l the new vertical coordinate.
     */
    public void setPositionAt(int l, int c) {
        setPositionAt(new Position(l, c));
    }

    public char getType() {
        return this.type;
    }

    public boolean isEvil(){
        return isEvil;
    }

    public boolean isAlive(){
        return isAlive;
    }

    public Dir getDirection() {
        return direction;
    }

    public void setDirection(Dir direction) {
        this.direction = direction;
    }

    public void setRandomDir() {
        direction = randomDir();
    }

    private Dir randomDir() {
        return Dir.values()[(int) (Math.random()*3) + 1];
    }

}
