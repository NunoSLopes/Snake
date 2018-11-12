package isel.poo.snake.model;

import isel.poo.snake.model.Cells.*;
import java.util.*;


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
    private int initialApples = 0;

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

    public int getLevelNumber() {
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

    public void putCell(int l, int c, Cell cell) {

        cell.setPositionAt(l, c);
        gameArea.add(cell);

        if (cell instanceof Mouse)mouseList.add((Mouse) cell);
        else if (cell instanceof Apple) initialApples++;
        else if (cell instanceof SnakeHead && !cell.isEvil()) {
            fGoodSnake = new FullSnake((SnakeHead) cell, false);
        }
        else if (cell instanceof SnakeHead && cell.isEvil()){
            fEvilSnake = new FullSnake((SnakeHead)cell, true);
            evilSnakes.add(fEvilSnake);
        }


    }

    /**
     * Get Cells at position
     *
     * @param l line choosen
     * @param c column choosen
     * @return Cells in position (l,c)
     */

    public Cell getCell(int l, int c) {

        for (Cell aGameArea : gameArea) {
            if (aGameArea.getL() == l && aGameArea.getC() == c) {
                return aGameArea;
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
        if (!checkCollision(newPos.l, newPos.c)) {
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
                    if (checkCollision(newPos.l, newPos.c)) {
                        directions.remove(mouse.getDirection());
                        Collections.shuffle(directions);
                        mouse.setDirection(directions.get(0));
                        newPos = checkMovement(mouse.getDirection(), mouse);

                    } else {
                        mouse.setRandomDir();
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
                    while (checkCollision(newPos.l, newPos.c)) {
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

    private boolean checkCollision(int l, int c) {

        for (Cell aGameArea : gameArea) {

            if (aGameArea instanceof Obstacle) {
                if (aGameArea.getPosition().comparePos(l, c)) {
                    return true;
                }
            } else if (aGameArea instanceof SnakeHead && aGameArea.isAlive()) {
                if (aGameArea.getPosition().comparePos(l, c)) {
                    return true;
                }
            }
        }

        for (Cell tail: fGoodSnake.snakeBody) {
            if (tail.getPosition().comparePos(l,c) ) return true;
        }

        for (FullSnake snake: evilSnakes) {
            for (Cell tail: snake.snakeBody) {
                if (tail.getPosition().comparePos(l,c)) return true;
            }
        }

        return false;
    }

    private boolean checkFoodCollision(int l, int c){

        for (Cell aGameArea : gameArea) {

            if (aGameArea instanceof Apple) {
                if (!aGameArea.getPosition().comparePos(l, c)) {
                    return false;
                }
            } else if (aGameArea instanceof Mouse) {
                if (!aGameArea.getPosition().comparePos(l, c)) {
                    return false;
                }
            }

        }
        return true;
    }

    /**
     * Initiate the game
     *
     * @param @game
     */
    public void init(Game game) {

        snakeIsDead = false;
        gameEnded = false;
        movesCounter = 0;
        this.game = game;

    }

    private int checkFood(Position pos){

        int c = pos.c;
        int l = pos.l;
        int growth = 0;

        for (int i = 0; i < gameArea.size() ; i++) {
            Cell curr = gameArea.get(i);
            if (curr.getPosition().comparePos(l,c)) {
                if (curr instanceof Apple) {
                    --apples;
                    growth += APPLESPOINTS;
                    observer.applesUpdated(apples);
                    gameArea.set(i, new EmptyCell(gameArea.get(i).getPosition()));
                    movesCounter = 0;
                    if (apples >= initialApples ) {
                        Cell newApple = new Apple(addNewApple());
                        gameArea.add(newApple);
                        observer.cellCreated(newApple.getL(), newApple.getC(), newApple);
                    }
                } else if (curr instanceof Mouse) {

                    growth += MOUSEPOINTS;
                    gameArea.set(i, new EmptyCell(curr.getPosition()));
                    for (int j = 0; j < mouseList.size(); ++j) {
                        if (mouseList.get(j).getPosition().comparePos(curr.getPosition())) {
                            mouseList.remove(mouseList.get(j));
                        }
                    }
                    observer.cellUpdated(l, c, curr);

                } else if (curr instanceof SnakeHead && !curr.isAlive()) {

                    for (FullSnake snake : evilSnakes) {
                        if (snake.getPosition().comparePos(l,c)){
                            growth += 10 + 2 * snake.getBodySize();
                            gameArea.set(i, new EmptyCell(curr.getPosition()));
                            observer.cellUpdated(l, c, curr);

                            while(snake.snakeBody.size()>0) {
                                Cell removed = snake.removeBody();
                                observer.cellRemoved(removed.getL(), removed.getC());

                            }
                        }
                    }

                }
            }
        }
        return growth;
    }

    private Position addNewApple() {

        boolean isEmpty;
        int newL;
        int newC;

        do {
            isEmpty = true;
            newL = (int) (Math.random() * this.height);
            newC = (int) (Math.random() * this.width);
            if(checkCollision(newL, newC) ) isEmpty = false;
            if (checkFoodCollision(newL,newC)) isEmpty = false;

        }while (!isEmpty);

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
}
