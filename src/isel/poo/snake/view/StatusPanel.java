package isel.poo.snake.view;



import isel.leic.pg.Console;
import isel.poo.console.FieldView;
import isel.poo.console.ParentView;
import isel.poo.console.tile.TilePanel;


public class StatusPanel extends ParentView {

    public static final int HEIGHT = 10, WIDTH = 7 ;

    protected FieldView fViewLevel = new FieldView("Level ",1,0 , "---");
    protected FieldView fViewApples = new FieldView("Apples",4,0 , "---");
    protected FieldView fViewScore = new FieldView("Score ",7,0 , "---");


    public StatusPanel(int winWidth) {
        super(0,winWidth,HEIGHT,WIDTH,Console.BLACK);
        this.addView(fViewLevel);
        this.addView(fViewApples);
        this.addView(fViewScore);
    }

    public void setLevel(int number) {
        fViewLevel.setValue(number);
    }

    public void setApples(int remainingApples) {
        fViewApples.setValue(remainingApples);
    }

    public void setScore(int score) {
        fViewScore.setValue(score);
    }

    public void paint(){
        clear();
        fViewLevel.paint();
        fViewApples.paint();
        fViewScore.paint();
    }

}
