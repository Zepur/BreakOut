package breakOutPkge;

import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Brick extends Rectangle {
    private Light.Distant light = new Light.Distant();
    final static int BRICK_WIDTH = 40;
    final static int BRICK_HEIGHT = 16;
    int numRows = 15;
    int numCols = 10;
    int hGap = 3;
    int vGap = 3;

    public static ArrayList<Brick> bricks = new ArrayList<>();

    public Brick(Pane gameWindow, int row, int column) {
        super(BRICK_WIDTH, BRICK_HEIGHT);
        double startPosX = ((gameWindow.getWidth() - ((BRICK_WIDTH*numRows)+(14*hGap))) / 2);

        light.setAzimuth(-75.0f);

        Lighting lightEffect = new Lighting();
        lightEffect.setLight(light);
        lightEffect.setSurfaceScale(3.4f);

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
    }
}