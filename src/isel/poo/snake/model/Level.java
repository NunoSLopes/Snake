package isel.poo.snake.model;

import isel.poo.snake.model.Cell.*;

import java.util.ArrayList;
import java.util.LinkedList;


public class Level {


    private final int levelNumber, height, width;
    private static final int APPLESPOINTS=4 , MOUSEPOINTS = 10, MINTAILS = 3;
    private int apples = 10;
    private static LinkedList<Cell> snake = new LinkedList<>();
    private ArrayList<Cell> gameArea = new ArrayList<>();
    private Cell cell;
    private Snake dirSnake;
    protected Observer observer;
    private int growthLeft;
    private boolean gameOver = false;

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
     * Snake is dead when bumps into a wall or another snake body
     *
     * @return true if dead, false if alive
     */
    public boolean snakeIsDead() {
        return gameOver;
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
        if (cell instanceof Snake){
            snake.add(0,cell);
            gameArea.add(cell);
        }else{
            gameArea.add(cell);
        }
        //observer.cellCreated(l,c,cell);

    }

    /**
     * Get Cell at position
     *
     * @param l line choosen
     * @param c column choosen
     * @return Cell in position (l,c)
     */

    public Cell getCell(int l, int c) {


        for( int i = 0; i < gameArea.size(); ++i){
            if (gameArea.get(i).getL() == l && gameArea.get(i).getC() == c){
                return gameArea.get(i);
            }
        }

        return new EmptyCell(l,c);
    }


    /**
     * Set the direction of the snake
     *
     * @param dir Is the direction.
     */

    public void setSnakeDirection(Dir dir) {

        int headL = cell.getPosition().l;
        int headC = cell.getPosition().c;

        switch (dir) {
            case UP: {
                dirSnake = new Snake(new Position(++headL,headC));
            }

            case DOWN: {
                dirSnake = new Snake(new Position(--headL,headC));
            }

            case LEFT: {
                dirSnake = new Snake(new Position(headL,--headC));
            }

            case RIGHT: {
                dirSnake = new Snake(new Position(headL,++headC));
            }

            default:
                return;
        }

    }

    public void step() {

        if (!checkCollision(dirSnake)) {

            Cell deleted = snake.removeFirst(); //remove old head
            snake.addFirst(new SnakeTail(deleted.getPosition())); //add Tail in position of old head
            snake.addFirst(dirSnake); //add new Head
            checkFood(snake.getFirst().getPosition());
            if (growthLeft == 0) {
                deleted = snake.removeLast(); //remove last tail
                observer.cellRemoved(deleted.getL(),deleted.getC());
            }

            observer.cellUpdated(deleted.getPosition().l,deleted.getPosition().c,snake.getFirst());
            --growthLeft;
        }else{
           gameOver = true;
        }
    }

    private boolean checkCollision(Snake snk) {

        for(int i = 0 ; i < gameArea.size(); ++i) {
            if (gameArea.get(i) instanceof Obstacle) {
                if (gameArea.get(i).getC() == snk.getPosition().c && gameArea.get(i).getL() == snk.getPosition().l) {
                    return true;
                }
            } else if (gameArea.get(i) instanceof SnakeTail) {
                if (gameArea.get(i).getC() == snk.getPosition().c && gameArea.get(i).getL() == snk.getPosition().l) {
                    return true;
                }
            } else if (gameArea.get(i) instanceof Snake && gameArea.get(i).isEvil() && gameArea.get(i).isAlive()) {
                if (gameArea.get(i).getC() == snk.getPosition().c && gameArea.get(i).getL() == snk.getPosition().l) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Initiate the game
     *
     * @param game
     */
    public void init(Game game) {



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

    private void checkFood(Position pos){

        int c = pos.c;
        int l = pos.l;

        for(int i = 0 ; i < gameArea.size(); ++i){
            if (gameArea.get(i) instanceof Apple){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){
                    --apples;
                    observer.applesUpdated(apples);
                    observer.cellUpdated(l,c, gameArea.get(i));
                    growthLeft += APPLESPOINTS;
                }
            }else if( gameArea.get(i) instanceof Mouse){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){
                    observer.cellUpdated(l,c,gameArea.get(i));
                    growthLeft += MOUSEPOINTS;
                }
            }else if( gameArea.get(i) instanceof Snake && gameArea.get(i).isEvil() && !gameArea.get(i).isAlive()){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){

                    observer.cellUpdated(l,c,gameArea.get(i));
                    growthLeft += (10+(2*gameArea.get(i).snakeSize));
                }
            }
        }
    }

    public static int setSize(){
        return snake.size();
    }
}
