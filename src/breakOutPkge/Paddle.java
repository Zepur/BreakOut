package breakOutPkge;

import javafx.scene.effect.Reflection;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Paddle extends Rectangle {

    public Paddle(double width, double height) {
        super(0, 500, width, height);
        setFill(Color.GOLDENROD);
        setStroke(Color.DODGERBLUE);
        setStrokeWidth(2);
        Reflection r = new Reflection();
        r.setTopOffset(4);
        r.setFraction(0.8);
        setEffect(r);
    }

    public boolean collides(Circle powerUP) {
        return powerUP.intersects(getX(), getY(), getWidth(), getHeight());
    }
}