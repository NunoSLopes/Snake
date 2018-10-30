package isel.poo.snake.view;


import isel.leic.pg.Console;
import isel.poo.console.FieldView;
import isel.poo.console.ParentView;


public class StatusPanel extends ParentView{
    public static int WIDTH = 20;

    protected FieldView fViewLevelLABEL = new FieldView("LEVEL",0,0 , "");
    //protected FieldView fViewLevelValue = new FieldView(2,0 , "");
    protected FieldView fViewApplesLABEL = new FieldView("APPLES",6,0 , "");
    //protected FieldView fViewApplesValue = new FieldView(8,0 , "");
    protected FieldView fViewScoreLABEL = new FieldView("SCORE",14,0 , "");
    //protected FieldView fViewScoreValue = new FieldView(16,0 , "");





    public StatusPanel(int winWidth) {

        addView(fViewLevelLABEL);
        addView(fViewApplesLABEL);
        addView(fViewScoreLABEL);
        repaint();
    }

    public void setLevel(int number) {
        fViewLevelLABEL.setValue(""+number);
    }

    public void setApples(int remainingApples) {
        fViewApplesLABEL.setValue(""+remainingApples);
    }

    public void setScore(int score) {
        fViewScoreLABEL.setValue(""+score);
    }
}
