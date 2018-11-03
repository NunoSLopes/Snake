package isel.poo.snake.model;

public enum Dir {
    DOWN(1,0), LEFT(0,-1), RIGHT(0,1), UP(-1,0);

    public final int l, c;

    Dir(int l, int c) {
        this.l = l;
        this.c = c;
    }
}
