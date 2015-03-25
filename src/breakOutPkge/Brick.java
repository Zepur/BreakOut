package breakOutPkge;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Brick extends Rectangle {
    final static int BRICK_WIDTH = 50;
    final static int BRICK_HEIGHT = 20;

    public static ArrayList<Brick> bricks = new ArrayList<>();

    public Brick(int row, int column, int lvl) {
        super(BRICK_WIDTH, BRICK_HEIGHT);

        switch (lvl){
            case 1:
                switch (row){
                    case 0:case 8:
                        setFill(Color.HOTPINK);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                    case 1:case 9:
                        setFill(Color.TOMATO);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                    case 2:case 10:
                        setFill(Color.ORANGE);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                    case 3:case 11:
                        setFill(Color.YELLOW);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                    case 4:case 12:
                        setFill(Color.GREEN);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                    case 5:case 13:
                        setFill(Color.BLUE);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                    case 6:case 14:
                        setFill(Color.INDIGO);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                    case 7:
                        setFill(Color.PURPLE);
                         setArcHeight(15);
                         setArcWidth(15);
                        bricks.add(this);
                        break;
                }
                break;
            case 2:
                if (((row == 2 || row == 12) && (column == 2 || column == 3 || column == 4)) ||
                        ((row == 3 || row == 11) && (column == 4 || column == 5 || column == 8)) ||
                        ((row == 4 || row == 10) && (column == 1 || column == 3 || column == 4 || column == 5 || column == 6 || column == 7 || column == 8)) ||
                        ((row == 5 || row == 9) && (column == 2 || column == 3 || column == 5 || column == 6)) ||
                        ((row == 6 || row == 7 || row == 8) && (column == 3 || column == 4 || column == 5 || column == 6))) {
                    setFill(Color.SKYBLUE);
                     setArcHeight(10);
                     setArcWidth(10);
                    bricks.add(this);
                } else {
                    setFill(Color.ROSYBROWN);
                     setArcHeight(20);
                     setArcWidth(20);
                    bricks.add(this);
                }
                break;
            case 3:
                if (row == 6 || row == 7 || column == 4 || column == 5){
                    setFill(Color.DARKBLUE);
                    bricks.add(this);
                } else if((row == 5 && column != 4 && column != 5) || (row == 8 && column != 4 && column != 5) || (column == 3 && row != 6 && row != 7) || (column == 6 && row != 6 && row != 7)) {
                    setFill(Color.WHITE);
                     setArcHeight(20);
                     setArcWidth(20);
                    bricks.add(this);
                } else  {
                    setFill(Color.RED);
                     setArcHeight(10);
                     setArcWidth(10);
                    bricks.add(this);
                }
                break;
        }
    }

    public boolean collides(Ball2 ball) {
        return ball.intersects(getLayoutX(), getLayoutY(), getWidth(), getHeight());
    }
}