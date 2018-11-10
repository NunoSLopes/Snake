package isel.poo.snake.model;

import isel.poo.snake.model.Cells.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


public class Level {

    private Observer observer;
    private static FullSnake fGoodSnake;
    private final int levelNumber, height, width;
    private int  movesCounter = 0, apples = 10, mouseMoves = 0;
    private static final int APPLESPOINTS=4 , MOUSEPOINTS = 10;
    private ArrayList<Cell> gameArea = new ArrayList<>();
    private ArrayList<Mouse> mouseList = new ArrayList<>();
    private ArrayList<FullSnake> evilSnakes = new ArrayList<>();
    private boolean snakeIsDead = false, gameEnded = false;
    private Dir snakeDirection = Dir.UP;
    private Game game;
    private FullSnake fEvilSnake;

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
        else return gameEnded;
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

    void putCell(int l, int c, Cell cell) {

        cell.setPositionAt(l, c);

        if (cell instanceof SnakeHead && !cell.isEvil())
            fGoodSnake = new FullSnake((SnakeHead) cell, false);
        if (cell instanceof SnakeHead && cell.isEvil()){
            fEvilSnake = new FullSnake((SnakeHead)cell, true);
            evilSnakes.add(fEvilSnake);
        }
        if (cell instanceof Mouse) mouseList.add((Mouse) cell); //TODO: observar o cast

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
         snakeDirection = dir;
    }

    public void step() {

        Position oldPos = fGoodSnake.getPosition();
        Position newPos = checkMovement(snakeDirection, fGoodSnake);
        if (!checkCollision(newPos)) {
            fGoodSnake.walk(newPos);
            observer.cellMoved(oldPos.l, oldPos.c, fGoodSnake.getL(), fGoodSnake.getC(), fGoodSnake); //call observer
            observer.cellCreated(oldPos.l, oldPos.c, fGoodSnake.addBody(oldPos)); //
            int food = checkFood(fGoodSnake.snakeHead.getPosition());
            fGoodSnake.setGrowth(food);
            game.addScore(food);
            if (fGoodSnake.getGrowth() <= 0) {
                deletedLast(fGoodSnake);
            }
            fGoodSnake.setGrowth(fGoodSnake.getGrowth() > 0 ? -1:0);
            ++movesCounter;
            ++mouseMoves;

        } else {
            fGoodSnake.snakeHead = new SnakeHead(false, false);
            fGoodSnake.snakeHead.setPositionAt(oldPos);
            observer.cellCreated(oldPos.l, oldPos.c, fGoodSnake.snakeHead);
            snakeIsDead = true;
            gameEnded = true;
        }

        if(movesCounter == 10 && !snakeIsDead){
            movesCounter = 0;
            game.addScore(-1);
            snakeIsDead = deletedLast(fGoodSnake);
        }


        if (mouseList != null && mouseMoves == 4) {

            for (Mouse mouse : mouseList) {
                ArrayList<Dir> directions = new ArrayList<>(Arrays.asList(Dir.DOWN, Dir.UP, Dir.LEFT, Dir.RIGHT));
                oldPos = mouse.getPosition();
                newPos = checkMovement(mouse.getDirection(), mouse);
                while (true) {
                    if (checkCollision(newPos)) {
                        directions.remove(mouse.getDirection());
                        Collections.shuffle(directions);
                        mouse.setDirection(directions.get(0));
                        newPos = checkMovement(mouse.getDirection(), mouse);

                    } else {
                        break;
                    }
                }
                mouse.setPositionAt(newPos);
                observer.cellMoved(oldPos.l, oldPos.c, mouse.getL(), mouse.getC(), mouse);
            }
            mouseMoves = 0;
        }


        if (evilSnakes != null) {
            for (FullSnake snake: evilSnakes) {
                if (snake.snakeHead.isAlive()) {
                    ArrayList<Dir> directions = new ArrayList<>(Arrays.asList(Dir.DOWN, Dir.UP, Dir.LEFT, Dir.RIGHT));
                    oldPos = snake.getPosition();
                    newPos = checkMovement(snake.getDirection(), snake);
                    while (checkCollision(newPos)) {
                        if (directions.size()>1) {
                            directions.remove(snake.getDirection());
                            Collections.shuffle(directions);
                            snake.setDirection(directions.get(0));
                            newPos = checkMovement(snake.getDirection(), snake);

                        } else {
                            snake.kill();
                            observer.cellUpdated(oldPos.l, oldPos.c, snake.snakeHead);
                            break;
                        }
                    }

                     if (snake.snakeHead.isAlive()) {

                        if (!oldPos.comparePos(newPos)) {
                            snake.walk(newPos);
                            observer.cellMoved(oldPos.l, oldPos.c, snake.getL(), snake.getC(), snake); //call observer
                            observer.cellCreated(oldPos.l, oldPos.c, snake.addBody(oldPos));
                            snake.setGrowth(checkFood(snake.snakeHead.getPosition()));
                            if (snake.getGrowth() <= 0) deletedLast(snake);
                            snake.setGrowth(snake.getGrowth() > 0 ? -1:0);
                        }
                    }
                } else {
                    deletedLast(snake);
                }
            }

        }
    }

    private boolean deletedLast(FullSnake snake) {

        if (snake.snakeBody.size() > 0) {
            Cell deleted = snake.removeBody(); //remove last tail
            observer.cellRemoved(deleted.getL(), deleted.getC());
        }
        if (snake.snakeBody.size() == 0 && movesCounter == 0) {

            if(!snake.isEvil())gameEnded = true;
            return true;

        }

        return false;
    }

    private boolean checkCollision(Position pos) {

        for(int i = 0 ; i < gameArea.size(); ++i) {

            if (gameArea.get(i) instanceof Obstacle) {
                if (gameArea.get(i).getC() == pos.c && gameArea.get(i).getL() == pos.l) {
                    return true;
                }
            } else if (gameArea.get(i) instanceof SnakeHead && gameArea.get(i).isAlive()) {
                if (gameArea.get(i).getC() == pos.c && gameArea.get(i).getL() == pos.l) {
                    return true;
                }
            }
        }

        for (Cell tail: fGoodSnake.snakeBody) {
            if (tail.getPosition().c == pos.c && tail.getPosition().l == pos.l ) return true;
        }

        for (FullSnake snake: evilSnakes) {
            for (Cell tail: snake.snakeBody) {
                if (tail.getPosition().c == pos.c && tail.getPosition().l == pos.l ) return true;
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

        snakeIsDead = false;
        gameEnded = false;
        movesCounter = 0;
        this.game = game;

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

    private int checkFood(Position pos){

        int c = pos.c;
        int l = pos.l;
        int growth = 0;

        for(int i = 0 ; i < gameArea.size(); ++i){
            if (gameArea.get(i) instanceof Apple){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){
                    --apples;
                    observer.applesUpdated(apples);
                    growth += APPLESPOINTS;
                    gameArea.add(new EmptyCell(gameArea.get(i).getL(),gameArea.get(i).getC()));
                    movesCounter = 0;
                    if(apples >= 3) {
                        Cell newApple = new Apple(addNewApple());
                        gameArea.add(newApple);
                        observer.cellCreated(newApple.getL(), newApple.getC(), newApple);
                    }
                }
            }else if( gameArea.get(i) instanceof Mouse){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){
                    growth += MOUSEPOINTS;
                    gameArea.add(new EmptyCell(gameArea.get(i).getL(),gameArea.get(i).getC()));
                    for (int j = 0; j < mouseList.size(); ++j) {
                        if(mouseList.get(j).getL() == gameArea.get(i).getL() && mouseList.get(j).getC() == gameArea.get(i).getC())
                            mouseList.remove(mouseList.get(j));
                    }
                    observer.cellUpdated(l,c,gameArea.get(i));
                }
            }else if( gameArea.get(i) instanceof SnakeHead && !gameArea.get(i).isAlive()){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){
                    for(FullSnake snake: evilSnakes){
                        if (snake.getL() == l || snake.getL() == c) growth += 10+2*snake.getBodySize();
                    }
                    observer.cellUpdated(l,c,gameArea.get(i));
                }
            }
        }
        return growth;
    }

    private Position addNewApple() {

        boolean hasApple;
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
            for(int i = 0; i< fGoodSnake.snakeBody.size(); ++i){
                if( fGoodSnake.snakeBody.get(i).getC() == newC && fGoodSnake.snakeBody.get(i).getL() == newL)
                    hasApple = false;
            }
        }while (!hasApple);
        return new Position(newL, newC);
    }

    private Position checkMovement(Dir dir, Cell cell) {

        Position oldPos = cell.getPosition(); //get Head Position
        int newPosL = oldPos.l + dir.l;
        int newPosC = oldPos.c + dir.c;

        if (newPosL < 0) newPosL = height-1;
        else if (newPosL > height-1) newPosL = 0;
        else if (newPosC < 0) newPosC = width-1;
        else if (newPosC > width-1) newPosC = 0;

        Position newPos = new Position(newPosL, newPosC);

        return !newPos.comparePos(oldPos) ? newPos : oldPos;
    }
}
