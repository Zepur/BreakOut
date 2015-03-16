package breakOutPkge;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.Random;

public class Controller {
    Pane level1 = new Pane();
    Pane level2 = new Pane();
    Pane level3 = new Pane();
    public static int levelNumber = 1;
    ImageView muteImgView = new ImageView();
    Image unmuted = new Image("http://lolcipher.com/pix/javafx/unmuted.png");
    Image muted = new Image("http://lolcipher.com/pix/javafx/muted.png");
    public static boolean isPlaying = false;
    static public boolean isMuted = true;
    @FXML
    static AnchorPane playWindow;
    @FXML
    Pane gameWindow;
    @FXML
    static Label scoreLabel;
    @FXML
    Label gameName;
    @FXML
    Label score;
    @FXML
    Label clickToPlayLabel;
    @FXML
    Button easy;
    @FXML
    Button hard;
    Ball ball;
    Paddle gamePaddle;

    protected static long startTime;

    public void setup(int width, int height, boolean isHard){
        gameWindow.setCursor(Cursor.CROSSHAIR);
        gamePaddle = new Paddle(gameWindow, width, height);
        gameWindow.getChildren().add(gamePaddle);
        clickToPlayLabel.setText(null);
        easy.setVisible(false);
        hard.setVisible(false);
        muteUnmute();
        muteImgView.setX(380);
        muteImgView.setY(545);
        gameWindow.getChildren().add(muteImgView);
        double hVelocity = getSpeedParseIsH(true, isHard);
        double vVelocity = getSpeedParseIsH(false, isHard);
        ball = new Ball(gameWindow, (gameWindow.getWidth()/2), (gamePaddle.getY()-20), hVelocity, vVelocity, gamePaddle);
        gameWindow.getChildren().add(ball);
        addBricks();
        isPlaying = true;
        startTime = System.currentTimeMillis() / 1000;
    }

    private void addBricks() {
        getBricks(15, 10);
        get20percent();
        for(Brick brick : Brick.bricks){
            gameWindow.getChildren().add(brick);
        }
    }

    private void getBricks(int numRows, int numCols) {
        for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < numCols; column++) {
                new Brick(gameWindow, row, column, Controller.levelNumber, 15, 10);
            }
        }
    }
    private void get20percent(){
        decrease(120, Brick.bricks.size());
    }

    private void decrease(int target, int lvl) {
        Random rand = new Random();
        int brickNum = rand.nextInt(Brick.bricks.size());
        Brick.bricks.remove(brickNum);
        if(target == lvl)
            return;
        int lvl2 = (lvl-1);
        decrease(target, lvl2);

    }

    public void muteUnmute(){
        muteImgView.setImage(isMuted ? unmuted : muted);
        isMuted = !isMuted;
    }

    private double getSpeedParseIsH(boolean isH, boolean isHard){
        Random randX = new Random();
        Random randY = new Random();
        int randomDirection = randX.nextInt(10)+2;
        boolean leftOrRight = randomDirection%2 != 0;
        int speedXX = isHard ? (randX.nextInt(4) + 16) : (randX.nextInt(4) + 10);
        int speedYY = isHard ? (randY.nextInt(4) + 16) : (randY.nextInt(4) + 10);
        double vVelocity = (double)speedYY/10;
        double hVelTemp = (double)speedXX/10;
        double hVelocity = leftOrRight ? hVelTemp : -hVelTemp;
        return isH ? hVelocity : vVelocity;
    }

    public void startEasy(){
        setup(80, 20, false);
    }
    public void startHard(){
        setup(70, 20, true);
    }

    public static void betweenLVLs(Pane gameWindow) {
        Label betweenLabel = new Label("YOU LOST AT lvl: "+levelNumber+"!\nNOOB");
        betweenLabel.setLayoutX(150);
        betweenLabel.setLayoutY(240);
        betweenLabel.setTextFill(Color.RED);
        betweenLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");
        Button nextLVLbutton = new Button("PLEB");
        nextLVLbutton.setLayoutX((gameWindow.getWidth()/2)-(nextLVLbutton.getWidth())/2);
        nextLVLbutton.setLayoutY(400);
        nextLVLbutton.setStyle("-fx-background-color: green; -fx-color: blue;");
        gameWindow.getChildren().add(betweenLabel);
        gameWindow.getChildren().add(nextLVLbutton);
    }

}
