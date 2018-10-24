package isel.poo.snake.model;

import isel.poo.snake.model.Cell.Cell;

import java.util.ArrayList;
import java.util.LinkedList;

public class Level {

    private final int levelNumber, height, width;
    private final int apples = 10;
    private final LinkedList<Cell> snake = new LinkedList<>();
    private final ArrayList<Cell> gameArea = new ArrayList<>();
    private Cell cell;
    private int dirL, dirC;

    public Level(int levelNumber, int height, int width) {

        this.levelNumber = levelNumber;
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getNumber() {
        return levelNumber;
    }

    public int getRemainingApples() {
        return apples;
    }

    public boolean isFinished() {
        return getRemainingApples() == 0 ? true : false;
    }


    /**
     * Set the position of the cell
     *
     * @param l    line value
     * @param c    column
     * @param cell Cell to be set the position
     */

    public void putCell(int l, int c, Cell cell) {

        cell.setPositionAt(l, c);
        gameArea.add(cell);

    }

    /**
     * Get Cell at position
     *
     * @param l line choosen
     * @param c column choosen
     * @return Cell in position (l,c)
     */

    public Cell getCell(int l, int c) {
        return cell.getCellAt(l, c);
    }

    /**
     * Set the direction of the snake
     *
     * @param dir Is the direction.
     */

    public void setSnakeDirection(Dir dir) {

        switch (dir) {
            case UP: {
                dirL = 1;
                dirC = 0;
            }

            case DOWN: {
                dirL = -1;
                dirC = 0;
            }

            case LEFT: {
                dirL = 0;
                dirC = -1;
            }

            case RIGHT: {
                dirL = 0;
                dirC = 1;
            }

            default:
                return;
        }

    }

    public void step() {

        cell.setPositionAt(cell.getPosition().l + dirL, cell.getPosition().c + dirC);

    }

    /**
     * Snake is dead when bumps into a wall or another snake body
     *
     * @return true if dead, false if alive
     */
    public boolean snakeIsDead() {
        return true;
    }

    //todo

    /**
     * @param observer
     */
    public void setObserver(Observer observer) {

    }

    /**
     * Initiate the game
     *
     * @param game
     */
    public void init(Game game) throws Loader.LevelFormatException {
        Level currLevel = new Level(game.loadNextLevel().levelNumber, game.loadNextLevel().height, game.loadNextLevel().width);
        currLevel.putCell(game.loadNextLevel().cell.getL(), game.loadNextLevel().cell.getC(), game.loadNextLevel().cell);
    }

    public interface Observer {
        void cellUpdated(int l, int c, Cell cell);

        void cellCreated(int l, int c, Cell cell);

        void cellRemoved(int l, int c);

        void cellMoved(int fromL, int fromC, int toL, int toC, Cell cell);

        void applesUpdated(int apples);
    }
}
