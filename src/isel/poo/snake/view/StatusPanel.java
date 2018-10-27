package isel.poo.snake.view;


import isel.leic.pg.Console;
import isel.poo.console.FieldView;
import isel.poo.console.ParentView;


public class StatusPanel {
    public static final int WIDTH = 0;

    protected FieldView fViewLevelLABEL = new FieldView("LEVEL",0,0 , "LEVEL");
    protected FieldView fViewLevelValue = new FieldView(2,0 , "");
    protected FieldView fViewAppleslABEL = new FieldView("APPLES",6,0 , "APPLES");
    protected FieldView fViewApplesValue = new FieldView(8,0 , "");
    protected FieldView fViewScorelABEL = new FieldView("SCORE",14,0 , "SCORE");
    protected FieldView fViewScoreValue = new FieldView(16,0 , "");
    protected ParentView pView;




    public StatusPanel(int winWidth) {
        pView = new ParentView(0,(2/3)*winWidth, Console.BLACK);
        pView.addView(fViewLevelLABEL);
        pView.addView(fViewLevelValue);
        pView.addView(fViewAppleslABEL);
        pView.addView(fViewApplesValue);
        pView.addView(fViewScorelABEL);
        pView.addView(fViewScoreValue);
    }

    public void setLevel(int number) {
        fViewLevelValue.setValue(number);
    }

    public void setApples(int remainingApples) {
        fViewApplesValue.setValue(remainingApples);
    }

    public void setScore(int score) {
        fViewScoreValue.setValue(score);
    }
}
