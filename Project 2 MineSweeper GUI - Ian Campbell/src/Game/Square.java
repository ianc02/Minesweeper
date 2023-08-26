package Game;


import javafx.animation.AnimationTimer;
import javafx.animation.ParallelTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Square {

    private int x;
    private int y;
    private Rectangle r;
    private Pane pane;
    private int squareSize;
    private Cell type;
    private boolean flagged;
    private boolean closed;
    private Movement clock;
    private ArrayList<Particle> particles;



    public Square(int y, int x, Rectangle r, Pane pane, int squareSize){
        this.r = r;
        this.x = x;
        this.y = y;
        this.pane = pane;
        this.squareSize = squareSize;
    }

    private class Movement extends AnimationTimer {

        private long FRAMES_PER_SEC = 50L;
        private long INTERVAL = 1000000000L / FRAMES_PER_SEC;

        private long last = 0;

        @Override
        public void handle(long now) {
            if (now - last > INTERVAL){
                for (Particle p: particles){
                    p.move();
                    p.draw();
                    last = now + 100000000L;
                }
            }
        }
    }
    public void initialize(){
        clock = new Movement();
        particles = new ArrayList<Particle>();
        for (int i = -1; i < 2; i++){
            for (int j = -1; j < 2; j++){
                if (i != 0 || j != 0) {
                    makeCircle(i,j);
                }
            }
        }
    }

    public void setColor(Color color){
        r.setFill(color);
    }

    public void setType(Cell cell){
        this.type = cell;
    }
    public Cell getType(){
        return type;
    }
    public int getX(){
        return x;
    }

    public int getY() {
        return y;
    }
    public void typeColor(){
        if (type == Cell.OpenField){
            r.setFill(Color.GREEN);
        }
        if (type == Cell.One){
            r.setFill(Color.YELLOW);
        }
        if (type == Cell.Two){
            r.setFill(Color.ORANGE);
        }
        if (type == Cell.Three){
            r.setFill(Color.RED);
        }
        if (type == Cell.Four){
            r.setFill(Color.PURPLE);
        }
        if (type == Cell.Five){
            r.setFill(Color.BLUE);
        }
        if(type == Cell.Six){
            r.setFill(Color.CYAN);
        }
        if(type == Cell.Seven){
            r.setFill(Color.LIMEGREEN);
        }
        if(type == Cell.Eight){
            r.setFill(Color.BROWN);
        }
    }

    public boolean getFlagStatus(){
        return flagged;
    }

    public void changeFlagStatus(){
        this.flagged = !flagged;
    }

    public boolean isClosed(){
        return closed;
    }

    public void setClosed(boolean c){
        this.closed = c;
    }
    public void makeCircle(int dy, int dx){
        Circle c = new Circle();
        c.setFill(Color.RED);
        Particle p = new Particle(c, x, y, dy, dx, pane,squareSize);
        particles.add(p);
        pane.getChildren().add(c);
    }

    public void start(){
        clock.start();
    }

    public void stop(){
        clock.stop();
    }
}
