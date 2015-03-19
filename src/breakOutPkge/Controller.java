package breakOutPkge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    @FXML
    static AnchorPane playWindow;
    @FXML
    Pane gameWindow;
    @FXML
    static Label scoreLabel;
    @FXML
    Label gameName;
    @FXML
    Label clickToPlayLabel;
    @FXML
    Button easy;
    @FXML
    Button hard;
    static Label bigLabel = new Label();
    static Button bigButton = new Button();
    static Label score = new Label();
    static Label bricksLeftInfoLabel = new Label("Bricks left:");
    static Label bricksLeftLabel = new Label();
    public static MediaPlayer jukeBox;
    public static int levelNumber = 1;
    public static int lives = 3;
    public static double hVelocity, vVelocity;
    public static Rectangle muteButton = new Rectangle();
    public static Circle powerUPpadSize = new Circle(40, 490, 15, Color.ALICEBLUE);
    static Image unmuted = new Image("http://lolcipher.com/pix/javafx/unmuted.png");
    static Image muted = new Image("http://lolcipher.com/pix/javafx/muted.png");
    static ImagePattern unmutedIMG = new ImagePattern(unmuted);
    static ImagePattern mutedIMG = new ImagePattern(muted);
    public static boolean isPlaying = false;
    public static boolean powerUP = false;
    static public boolean isMuted = false;
    static Ball ball;
    static Paddle gamePaddle;
    protected static long startTime;
    protected static long stopTime;

    public void setup(int width, int height, boolean isHard){
        powerUPpadSize.setOnMousePressed(e -> {
            gamePaddle.setWidth(100);
            gameWindow.getChildren().removeAll(powerUPpadSize);
        });

        bricksLeftInfoLabel.setLayoutX(35);
        bricksLeftInfoLabel.setLayoutY(580);
        bricksLeftInfoLabel.setTextFill(Color.GRAY);
        bricksLeftInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        bricksLeftLabel.setLayoutX(130);
        bricksLeftLabel.setLayoutY(580);
        bricksLeftLabel.setTextFill(Color.WHITE);
        bricksLeftLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        muteButton.setWidth(30);
        muteButton.setHeight(30);
        muteButton.setLayoutX(380);
        muteButton.setLayoutY(545);
        muteButton.setFill(unmutedIMG);

        score.setText(String.valueOf(lives));
        score.setTextFill(Color.WHITE);
        score.setLayoutX(155);
        score.setLayoutY(547);
        score.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");

        bigLabel.setLayoutX(250);
        bigLabel.setLayoutY(240);
        bigLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");

        bigButton.setLayoutX((gameWindow.getWidth() / 2) - (70));
        bigButton.setLayoutY(360);
        bigButton.setMinWidth(140);
        bigButton.setMinHeight(70);
        bigButton.setTextFill(Color.WHITE);

        gamePaddle = new Paddle(gameWindow, width, height);
        clickToPlayLabel.setText(null);
        easy.setVisible(false);
        hard.setVisible(false);

        double hVelocity = getSpeed(true);
        double vVelocity = getSpeed(false);
        ball = new Ball(gameWindow, (gameWindow.getWidth()/2), 380, hVelocity, vVelocity, gamePaddle, (isHard ? 15 : 10));
        ball.setCenterY(420);
        ball.setCenterX(396);

        gameWindow.setCursor(Cursor.CROSSHAIR);
        gameWindow.getChildren().add(bricksLeftInfoLabel);
        gameWindow.getChildren().add(bricksLeftLabel);
        gameWindow.getChildren().add(score);
        gameWindow.getChildren().add(gamePaddle);
        gameWindow.getChildren().add(muteButton);
        gameWindow.getChildren().add(ball);
        gameWindow.getChildren().add(bigLabel);
        gameWindow.getChildren().add(bigButton);
        startLVL(gameWindow);
    }

    private double getSpeed(boolean isH){
        Random randX = new Random();
        Random randY = new Random();
        int randomDirection = randX.nextInt(10)+2;
        boolean leftOrRight = randomDirection%2 != 0;
        int speedXX = (randX.nextInt(4) + 10);
        int speedYY = (randY.nextInt(4) + 10);
        vVelocity = (double)speedYY/10;
        double hVelTemp = (double)speedXX/10;
        hVelocity = leftOrRight ? hVelTemp : -hVelTemp;
        return isH ? hVelocity : vVelocity;
    }

    public static void startLVL(Pane gameWindow) {
        bigButton.setText("Start");
        bigButton.setStyle("-fx-background-color: green; -fx-font-size: 30; -fx-font-weight:bold;");
        bigButton.setOnAction(e -> {
            startTime = System.currentTimeMillis();
            addBricks(gameWindow);
        });
    }

    private static void addBricks(Pane gameWindow) {
        gameWindow.getChildren().remove(bigLabel);
        gameWindow.getChildren().remove(bigButton);
        getBricks(gameWindow, 15, 10);
        get20percent((levelNumber==2 ? 135 : (levelNumber==3 ? 150 : 120)));
        for(Brick brick : Brick.bricks){
            gameWindow.getChildren().add(brick);
        }
        isPlaying = true;
    }

    private static void getBricks(Pane gameWindow, int numRows, int numCols) {
        for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < numCols; column++) {
                new Brick(gameWindow, row, column, Controller.levelNumber, 15, 10);
            }
        }
        bricksLeftLabel.setText(String.valueOf(Brick.bricks.size()));
    }

    private static void get20percent(int target){
        if(Brick.bricks.size() <= target)
            return;
        else
            decrease(target, Brick.bricks.size());
    }

    private static void decrease(int target, int lvl) {
        Random rand = new Random();
        int brickNum = rand.nextInt(Brick.bricks.size());
        Brick.bricks.remove(brickNum);
        if(target == (lvl-1))
            return;
        int lvl2 = (lvl-1);
        decrease(target, lvl2);
    }


    public static void muteUnmute(){
        isMuted = !isMuted;
        muteButton.setFill((isMuted ? mutedIMG : unmutedIMG));
    }

    public static void powerMeUp(int number, Pane gameWindow){
        switch (number){
            case 0:case 1:case 2:case 3:
                if(!powerUP) {
                    powerUP = true;
                    gameWindow.getChildren().add(powerUPpadSize);

                    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1500), e -> {
                        powerUPpadSize.setCenterX(powerUPpadSize.getCenterX() + 10);
                        gameWindow.getChildren().removeAll(powerUPpadSize);
                        powerUP = false;
                    }));
                    timeline.play();
                }
                break;
            default:
                break;
        }
    }

    public void startEasy(){
        setup(80, 20, false);
    }
    public void startHard(){
        setup(70, 20, true);
    }

    public static void betweenLVLs(Pane gameWindow) {
        bigLabel.setText("You beat lvl: " + levelNumber + "!\n");
        bigLabel.setTextFill(Color.GREENYELLOW);
        bigButton.setText("Start lvl: "+(levelNumber+1));
        bigButton.setStyle("-fx-background-color: blue; -fx-font-size: 30; -fx-font-weight:bold;");
        gameWindow.getChildren().removeAll(Brick.bricks);
        bigButton.setOnAction(e -> { levelNumber++; addBricks(gameWindow); });
        gameWindow.getChildren().add(bigButton);
        gameWindow.getChildren().add(bigLabel);
    }

    public static void endGame(Pane gameWindow){
        bigLabel.setText("Game over @ lvl: " + levelNumber + "!");
        bigLabel.setTextFill(Color.RED);
        bigButton.setStyle("-fx-background-color: blue; -fx-font-size: 30; -fx-font-weight:bold;");
        bigButton.setText("Retry");
        gameWindow.getChildren().removeAll(Brick.bricks);
        bigButton.setOnAction(e -> {
            Brick.bricks = new ArrayList<>();
            levelNumber = 1;
            lives=3;
            score.setText(String.valueOf(lives));
            startLVL(gameWindow);
        });
        gameWindow.getChildren().add(bigButton);
        gameWindow.getChildren().add(bigLabel);
    }

}
