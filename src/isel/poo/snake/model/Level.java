package isel.poo.snake.model;

import isel.poo.snake.model.Cells.*;

import java.net.SocketOption;
import java.util.ArrayList;


public class Level {

    private static FullSnake fGoodSnake;
    private final int levelNumber, height, width;
    private int growthLeft = 4, movesCounter = 0, apples = 10, mouseMove = 0, evilGrowth = 4;
    private static final int APPLESPOINTS=4 , MOUSEPOINTS = 10, MINTAILS = 4;
    //private Cell snakeHead;
    //private static LinkedList<Cell> snakeBody ;
    private ArrayList<Cell> gameArea = new ArrayList<>();
    private ArrayList<Cell> mouseArrayList = new ArrayList<>();
    private ArrayList<FullSnake> evilSnakes = new ArrayList<>();
    private Observer observer;
    private boolean snakeIsDead = false, gameEnded = false;
    private Dir snakeDirection = Dir.UP;
    private Dir autoDir = Dir.RIGHT;
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

    public void putCell(int l, int c, Cell cell) {

        cell.setPositionAt(l, c);

        if (cell instanceof SnakeHead && !cell.isEvil()) fGoodSnake = new FullSnake(cell, false);

        if (cell instanceof SnakeHead && cell.isEvil()){
            fEvilSnake = new FullSnake(cell, true);
            evilSnakes.add(fEvilSnake);
        }

        if (cell instanceof Mouse) mouseArrayList.add(cell);

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
        Position oldPos = fGoodSnake.snakeHead.getPosition(); //get Head Position
        int newPosL = fGoodSnake.snakeHead.getL()+snakeDirection.l;
        int newPosC = fGoodSnake.snakeHead.getC()+snakeDirection.c;

        Position newPos = checkLimits(newPosL,newPosC);

         //calculate new position
        if (!checkCollision(newPos)){
            fGoodSnake.snakeHead.setPositionAt(newPos); //set new position
            observer.cellMoved(oldPos.l, oldPos.c, newPos.l, newPos.c, fGoodSnake.snakeHead); //call observer
            addTail(oldPos); //
            ++movesCounter;
            ++mouseMove;
            return true;
        } else {
            fGoodSnake.snakeHead = new SnakeHead(false, false);
            fGoodSnake.snakeHead.setPositionAt(oldPos);
            observer.cellCreated(oldPos.l, oldPos.c, fGoodSnake.snakeHead);
            return false;
        }

    }

    public void addTail(Position position){
        Cell tail = new SnakeTail(position);
        fGoodSnake.addBody(tail);
        observer.cellCreated(position.l, position.c, tail);
    }


    public void step() {

        if (evilSnakes != null) {
            autoSnakeStep();

        }

        if (mouseArrayList != null && mouseMove == 4){
            for (Cell mouse: mouseArrayList) {
                autoMouseStep(mouse);
            }
            mouseMove = 0;
        }

        if (!checkCollision(fGoodSnake.snakeHead.getPosition())) {
            if(moveSnake()) {
                checkFood(fGoodSnake.snakeHead.getPosition());
                if (growthLeft <= 0) {
                    deletedLast(fGoodSnake);
                }

                growthLeft = growthLeft > 0 ? --growthLeft : 0;
            }else {
                snakeIsDead = true;
                gameEnded = true;

            }
        }

        if(movesCounter == 10){
            movesCounter = 0;
            game.addScore(-1);
            snakeIsDead = deletedLast(fGoodSnake);
        }

    }

    private void autoMouseStep(Cell mouse) {

        boolean hasStepped = false;

        do {
            int randomDir = (int) (Math.random()*40)/10;
            Position oldPos = mouse.getPosition(); //get Head Position
            System.out.println("MOUSE OldL" + oldPos.l + " & autoDirL=" + autoDir.l);
            int newPosL = oldPos.l + autoDir.l;
            System.out.println("MOUSE OldC" + oldPos.c + " & autoDirC=" + autoDir.c);
            int newPosC = oldPos.c + autoDir.c;
            System.out.println("  ");
            System.out.println("MOUSE C=" + newPosC + " & L=" + newPosL);

            Position newPos = checkLimits(newPosL, newPosC); //calculate new position

            if (!checkCollision(newPos)) {
                mouse.setPositionAt(newPos); //set new position
                observer.cellMoved(oldPos.l, oldPos.c, newPos.l, newPos.c, mouse); //call observer
                hasStepped = true;
            } else {
                autoDir = randomDirection(randomDir);

                if (autoDir == null)
                    hasStepped = true;
            }
        }while (!hasStepped);

    }

    private Position checkLimits(int newPosL, int newPosC){
        if (newPosL < 0) newPosL = height-1;
        else if (newPosL > height-1) newPosL = 0;
        else if (newPosC < 0) newPosC = width-1;
        else if (newPosC > width-1) newPosC = 0;

        return  new Position(newPosL, newPosC);
    }

    private void autoSnakeStep() {

        boolean hasStepped = false;

        Position newPos, oldPos;
        for (FullSnake snake: evilSnakes) {
            oldPos = snake.snakeHead.getPosition(); //get Head Position

            do {


                System.out.println("SNAKE OldL" + oldPos.l + " & autoDirL=" + autoDir.l);
                int newPosL = oldPos.l + autoDir.l;
                System.out.println("  ");
                System.out.println("SNAKE OldC" + oldPos.c + " & autoDirC=" + autoDir.c);
                int newPosC = oldPos.c + autoDir.c;
                System.out.println("  ");
                System.out.println("SNAKE C=" + newPosC + " & L=" + newPosL);

                newPos = checkLimits(newPosL, newPosC); //calculate new position
                if (!checkCollision(newPos)) {
                    snake.snakeHead.setPositionAt(newPos); //set new position
                    observer.cellMoved(oldPos.l, oldPos.c, newPos.l, newPos.c, snake); //call observer
                    snake.addBody(newPos); //
                    observer.cellCreated(newPosL,newPosC,new SnakeTail());
                    checkFood(snake.snakeHead.getPosition());
                    if (evilGrowth <= 0)
                        deletedLast(snake);

                    evilGrowth = evilGrowth > 0 ? --evilGrowth : 0;
                    hasStepped = true;
                } else {
                    int counter = (int) (Math.random()*40)/10;
                    autoDir = randomDirection(counter);

                    if ( counter <= 0 && counter > 4)
                        autoDir = Dir.RIGHT;
                    else if (autoDir == null) {
                        break;
                    }
                }
            } while (!hasStepped);

            if (!hasStepped) {

                fEvilSnake.snakeHead = new SnakeHead(true, false);
                fEvilSnake.snakeHead.setPositionAt(oldPos);
                observer.cellCreated(oldPos.l, oldPos.c, fEvilSnake.snakeHead);

            }
        }
    }

    private Dir randomDirection(int choose) {

        switch(choose){
            case 1 : return Dir.UP;

            case 2 : return Dir.LEFT;

            case 3 : return Dir.DOWN;

            case 4 : return Dir.RIGHT;

            default: return null;
        }
    }

    private boolean deletedLast(FullSnake snake) {

        if (snake.snakeBody.size() > 0) {
            Cell deleted = snake.removeBody(); //remove last tail
            observer.cellRemoved(deleted.getL(), deleted.getC());
        }
        if (snake.snakeBody.size() == 0) {

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
            } else if (gameArea.get(i) instanceof FullSnake && gameArea.get(i).isEvil() && gameArea.get(i).isAlive()) {
                if (gameArea.get(i).getC() == pos.c && gameArea.get(i).getL() == pos.l) {
                    snakeIsDead = true;
                    return true;
                }
            }
        }

        for (Cell tail: fGoodSnake.snakeBody) {
            if (tail.getPosition().c == pos.c && tail.getPosition().l == pos.l ) return true;
        }

        /*for (Cell tail: fEvilSnake.snakeBody) {
            if (tail.getPosition().c == pos.c && tail.getPosition().l == pos.l ) return true;
        }*/

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
                    growthLeft += MOUSEPOINTS;
                    gameArea.add(new EmptyCell(gameArea.get(i).getL(),gameArea.get(i).getC()));
                    for (int j = 0; j < mouseArrayList.size(); ++j) {
                        if(mouseArrayList.get(j).getL() == gameArea.get(i).getL() && mouseArrayList.get(j).getC() == gameArea.get(i).getC())
                            mouseArrayList.remove(mouseArrayList.get(j));
                    }
                    gameArea.remove(i);
                    observer.cellUpdated(l,c,gameArea.get(i));
                    game.addScore(MOUSEPOINTS);
                }
            }else if( gameArea.get(i) instanceof SnakeHead && gameArea.get(i).isEvil() && !gameArea.get(i).isAlive()){
                if(gameArea.get(i).getC()== c && gameArea.get(i).getL()==l){

                    observer.cellUpdated(l,c,gameArea.get(i));
                    growthLeft += (10+(2* fGoodSnake.getSnakeSize()));
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
            for(int i = 0; i< fGoodSnake.snakeBody.size(); ++i){
                if( fGoodSnake.snakeBody.get(i).getC() == newC && fGoodSnake.snakeBody.get(i).getL() == newL)
                    hasApple = false;
            }
        }while (!hasApple);
        return new Position(newL, newC);
    }

    public static int setSize(){
        return fGoodSnake.snakeBody == null ? 0 : fGoodSnake.snakeBody.size();
    }
}
