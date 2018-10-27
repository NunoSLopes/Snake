package isel.poo.snake.model;

import isel.poo.snake.ctrl.Snake;
import isel.poo.snake.model.Cell.Apple;
import isel.poo.snake.model.Cell.Cell;
import isel.poo.snake.model.Cell.Mouse;
import isel.poo.snake.model.Cell.SnakeTail;

import java.util.ArrayList;
import java.util.LinkedList;


public class Level {

    private final int levelNumber, height, width;
    protected final int applesPoint=4,mousePoints = 10;
    private int apples = 10;
    private LinkedList<Cell> snake;
    private ArrayList<Cell> gameArea;
    private Cell cell;
    private int dirL, dirC;
    protected Observer observer;

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


    //todo

    /**
     * Snake is dead when bumps into a wall or another snake body
     *
     * @return true if dead, false if alive
     */
    public boolean snakeIsDead() {
        return true;
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
        observer.cellCreated(l,c,cell);

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
        observer.cellUpdated(cell.getL(),cell.getC(),cell);
        checkFood(cell.getL(),cell.getC());
    }


    /**
     * @param observer
     */

    /**
     * Initiate the game
     *
     * @param game
     */
    public void init(Game game) {

        snake = new LinkedList<>();
        gameArea = new ArrayList<>();


    }

    public void setObserver(Observer observer) {

        this.observer = observer;

    }

    public interface Observer {
        void cellUpdated(int l, int c, Cell cell);

        void cellCreated(int l, int c, Cell cell);

        void cellRemoved(int l, int c);

        void cellMoved(int fromL, int fromC, int toL, int toC, Cell cell);

        void applesUpdated(int apples);
    }

    private void checkFood(int l, int c){

        for(int i = 0 ; i < gameArea.size(); ++i){
            if (gameArea.get(i) instanceof Apple){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){
                    --apples;
                    observer.applesUpdated(apples);
                    snakeGrow(applesPoint);
                    observer.cellUpdated(l,c,gameArea.get(i));

                }
            }else if( gameArea.get(i) instanceof Mouse){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){

                    snakeGrow(mousePoints);
                    observer.cellUpdated(l,c,gameArea.get(i));

                }
            }
        }
    }

    public void snakeGrow(int size){
        for (int j = 0; j < size; ++j) {
            snake.addLast(new SnakeTail());
        }
    }
}
