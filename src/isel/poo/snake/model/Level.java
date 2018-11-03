package isel.poo.snake.model;

import isel.poo.snake.ctrl.Snake;
import isel.poo.snake.model.Cells.*;

import java.util.ArrayList;
import java.util.LinkedList;


public class Level {

    private final int levelNumber, height, width;
    private static final int APPLESPOINTS=4 , MOUSEPOINTS = 10, MINTAILS = 4;
    private int apples = 10;
    private Cell snakeHead;
    private static LinkedList<Cell> snakeBody = new LinkedList<>();
    private ArrayList<Cell> gameArea = new ArrayList<>();
    private Observer observer;
    private int growthLeft = 4, movesCounter = 0;
    private boolean snakeIsDead = false, gameEnded = false;
    private Dir snakeDirection = Dir.UP;
    private Game game;

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

        if (getRemainingApples() == 0 )
            return true;
        else if (gameEnded)
            return true;

        return false;
    }

    /**
     * SnakeHead is dead when bumps into a wall or another snakeBody body
     *
     * @return true if dead, false if alive
     */
    public boolean snakeIsDead() {
        return snakeIsDead;
    }

    /**
     * Set the position of the cell
     *
     * @param l    line value
     * @param c    column
     * @param cell Cells to be set the position
     */

    public void putCell(int l, int c, Cell cell) {

        cell.setPositionAt(l, c);

        if (cell instanceof SnakeHead) snakeHead = cell;

        gameArea.add(cell);

    }

    /**
     * Get Cells at position
     *
     * @param l line choosen
     * @param c column choosen
     * @return Cells in position (l,c)
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
     * Set the direction of the snakeBody
     *
     * @param dir Is the direction.
     */

    public void setSnakeDirection(Dir dir) {
        if(
                ((snakeDirection.equals(Dir.UP) || snakeDirection.equals(Dir.DOWN)) &&
                (dir.equals(Dir.LEFT)||dir.equals(Dir.RIGHT))
                ) ||
                ((snakeDirection.equals(Dir.LEFT) || snakeDirection.equals(Dir.RIGHT)) &&
                (dir.equals(Dir.DOWN)||dir.equals(Dir.UP))
                )
        ) snakeDirection = dir;


    }

    public boolean moveSnake(){
        Position oldPos = snakeHead.getPosition(); //get Head Position
        int newPosL = snakeHead.getL()+snakeDirection.l;
        int newPosC = snakeHead.getC()+snakeDirection.c;

        if (newPosL < 0) newPosL = height-1;
        else if (newPosL > height-1) newPosL = 0;
        else if (newPosC < 0) newPosC = width-1;
        else if (newPosC > width-1) newPosC = 0;

        Position newPos = new Position(newPosL, newPosC); //calculate new position
        if (!checkCollision(newPos)){
            snakeHead.setPositionAt(newPos); //set new position
            observer.cellMoved(oldPos.l, oldPos.c, newPos.l, newPos.c, snakeHead); //call observer
            addTail(oldPos); //
            ++movesCounter;
            return true;
        } else {
            snakeHead = new SnakeHead(false, false);
            snakeHead.setPositionAt(oldPos);
            observer.cellCreated(oldPos.l, oldPos.c, snakeHead);
            return false;
        }

    }

    public void addTail(Position position){
        Cell tail = new SnakeTail(position);
        snakeBody.addFirst(tail);
        observer.cellCreated(position.l, position.c, tail);
    }

    public void step() {

//        snakeHead = snakeBody.getFirst();

        if (!checkCollision(snakeHead.getPosition())) {
            if(moveSnake()) {
                checkFood(snakeHead.getPosition());
                if (growthLeft <= 0)
                    deletedLast();

                growthLeft = growthLeft > 0 ? --growthLeft : 0;
            }else {
                snakeIsDead = true;
                gameEnded = true;

            }
        }

        if(movesCounter == 10){
            movesCounter = 0;
            game.addScore(-1);
            snakeIsDead = deletedLast();
        }

    }

    private boolean deletedLast() {

        if (snakeBody.size() > 0) {
            Cell deleted = snakeBody.removeLast(); //remove last tail
            observer.cellRemoved(deleted.getL(), deleted.getC());
        }
        if (snakeBody.size() == 0) {

            gameEnded = true;
            return true;

        }

        return false;
    }

    private boolean checkCollision(Position pos) {

        for(int i = 0 ; i < gameArea.size(); ++i) {

            if (gameArea.get(i) instanceof Obstacle) {
                if (gameArea.get(i).getC() == pos.c && gameArea.get(i).getL() == pos.l) {
                    snakeIsDead = true;
                    return true;
                }
            } else if (gameArea.get(i) instanceof SnakeHead && gameArea.get(i).isEvil() && gameArea.get(i).isAlive()) {
                if (gameArea.get(i).getC() == pos.c && gameArea.get(i).getL() == pos.l) {
                    snakeIsDead = true;
                    return true;
                }
            }
        }

        for (Cell tail: snakeBody) {
            if (tail.getPosition().c == pos.c && tail.getPosition().l == pos.l ) return true;
        }

        return false;
    }

    /**
     * Initiate the game
     *
     * @param game
     */
    public void init(Game game) {

        snakeIsDead = false;
        gameEnded = false;
        movesCounter = 0;


        this.game = game;

        //growthLeft = 4;

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
                    gameArea.add(new EmptyCell(gameArea.get(i).getL(),gameArea.get(i).getC()));
                    gameArea.remove(i);
                    game.addScore(APPLESPOINTS);
                    movesCounter = 0;
                    if(apples >= 4) {
                        Cell newApple = new Apple(addNewApple());
                        gameArea.add(newApple);
                        observer.cellCreated(newApple.getL(), newApple.getC(), newApple);
                    }
                }
            }else if( gameArea.get(i) instanceof Mouse){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){
                    observer.cellUpdated(l,c,gameArea.get(i));
                    growthLeft += MOUSEPOINTS;
                }
            }else if( gameArea.get(i) instanceof SnakeHead && gameArea.get(i).isEvil() && !gameArea.get(i).isAlive()){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){

                    observer.cellUpdated(l,c,gameArea.get(i));
                    growthLeft += (10+(2*gameArea.get(i).snakeSize));
                }
            }
        }
    }

    private Position addNewApple() {

        boolean hasApple = true;
        int newL;
        int newC;

        do {
            newL = (int) (Math.random() * this.height);
            newC = (int) (Math.random() * this.width);

            hasApple = true;

            for (int i = 0; i<gameArea.size();++i) {
                if (gameArea.get(i).getC() == newC && gameArea.get(i).getL() == newL)
                    hasApple = false;
            }
            for(int i = 0; i<snakeBody.size();++i){
                if( snakeBody.get(i).getC() == newC && snakeBody.get(i).getL() == newL)
                    hasApple = false;
            }
        }while (!hasApple);
        return new Position(newL, newC);
    }

    public static int setSize(){
        return snakeBody.size();
    }
}
