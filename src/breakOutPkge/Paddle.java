package breakOutPkge;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    public Paddle(Pane gameWindow, double width, double height) {
        super(0, 450, width, height);
        setFill(Color.GOLDENROD);

        if(!Controller.isPlaying)
            gameWindow.setOnMouseMoved(e -> setX((e.getX() - 50)));
    }

    public boolean collides(Ball ball) {
        return ball.intersects(getX(), getY(), getWidth(), getHeight());
    }
}