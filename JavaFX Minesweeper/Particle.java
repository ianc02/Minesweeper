package Game;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

public class Particle {

    private Circle circle;
    private int x;
    private int y;
    private int dx;
    private int dy;
    private Pane pane;
    private int squareSize;

    public Particle(Circle c, int x, int y, int dy, int dx, Pane pane, int squareSize) {
        this.circle = c;
        this.x = x;
        this.y = y;
        this.dx = dx;
        this.dy = dy;
        this.pane = pane;
        this.squareSize = squareSize;
    }

    public void move(){
        x += dx*2;
        if (-10 > x+5 || 5+x > 400){
            pane.getChildren().remove(circle);
        }
        y += dy*2;
        if (-10 > y+5 || y+5 > 400){
            pane.getChildren().remove(circle);
        }
    }

    public void draw(){
        circle.setRadius(5);
        circle.setTranslateX(y+squareSize/2);
        circle.setTranslateY(x+squareSize/2);
    }
}
