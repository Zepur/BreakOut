package breakOutPkge;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Brick extends Rectangle {
    final static int BRICK_WIDTH = 40;
    final static int BRICK_HEIGHT = 16;
    int hGap = 3;
    int vGap = 3;

    public static ArrayList<Brick> bricks = new ArrayList<>();
    private static Light.Distant light = new Light.Distant();
    private static Lighting l = new Lighting();

    public Brick(Pane gameWindow, int row, int column, int lvl, int numRows, int numColms) {
        super(BRICK_WIDTH, BRICK_HEIGHT);
        double startPosX = ((gameWindow.getWidth() - ((BRICK_WIDTH*numRows)+(14*hGap))) / 2);

        light.setAzimuth(-75.0f);

        Lighting lightEffect = new Lighting();
        lightEffect.setLight(light);
        lightEffect.setSurfaceScale(3.4f);
        switch (lvl){
            case 1:
                switch (row){
                    case 0:case 8:
                        setFill(Color.HOTPINK);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                        bricks.add(this);
                        break;
                    case 1:case 9:
                        setFill(Color.RED);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                        bricks.add(this);
                        break;
                    case 2:case 10:
                        setFill(Color.ORANGE);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                        bricks.add(this);
                        break;
                    case 3:case 11:
                        setFill(Color.YELLOW);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                        bricks.add(this);
                        break;
                    case 4:case 12:
                        setFill(Color.GREEN);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                        bricks.add(this);
                        break;
                    case 5:case 13:
                        setFill(Color.BLUE);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                        bricks.add(this);
                        break;
                    case 6:case 14:
                        setFill(Color.INDIGO);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                        bricks.add(this);
                        break;
                    case 7:
                        setFill(Color.PURPLE);
                        setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                        setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
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
                    setFill(Color.DARKRED);
                    setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                    setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                    bricks.add(this);
                } else {
                    setFill(Color.DARKGRAY);
                    setLayoutX(startPosX + (row * BRICK_WIDTH) + (hGap * row));
                    setLayoutY(BRICK_WIDTH + (column * BRICK_HEIGHT) + (vGap * column));
                    bricks.add(this);
                }
                break;
        }

        for(Brick brick : bricks){
            light.setAzimuth(-70.0f);
            l.setLight(light);
            l.setSurfaceScale(3.0f);
            setEffect(l);
            setArcHeight(10);
            setArcWidth(10);
        }
    }

    public boolean collides(Ball ball) {
        return ball.intersects(getLayoutX(), getLayoutY(), getWidth(), getHeight());
    }
}