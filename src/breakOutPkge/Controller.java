package breakOutPkge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.*;
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
    Label clickLabel;
    @FXML
    Label clickToPlayLabel;
    @FXML
    Rectangle startButton;

    static Random rand = new Random();
    public static Paint oldPaddleColor, oldBallColor;
    static Button easyButton = new Button("[ easy ]");
    static Button hardButton = new Button("[ hard ]");
    static Label bigLabel = new Label();
    static Label pauseLabel = new Label("[ paused ]");
    static Button bigButton = new Button();
    static Label score = new Label();
    static Label scoreLabel = new Label("balls remaining:");
    static Label bricksLeftInfoLabel = new Label("bricks remaining:");
    static Label timeElapsed = new Label("time:");
    static Label bricksLeftLabel = new Label();
    public static MediaPlayer jukeBox;
    public static int levelNumber = 1;
    public static int lives = 3;
    public static Rectangle muteButton = new Rectangle();
    public static Rectangle pauseRekt = new Rectangle();
    public static Circle powerUPpadSize = new Circle(40, 490, 15, Color.ALICEBLUE);
    static Image unmuted = new Image("http://lolcipher.com/pix/javafx/unmuted.png");
    static Image muted = new Image("http://lolcipher.com/pix/javafx/muted.png");
    static Image back1 = new Image("http://lolcipher.com/pix/javafx/backjack.jpg");
    static Image back2 = new Image("http://lolcipher.com/pix/javafx/backsolar.jpg");
    static Image back3 = new Image("http://lolcipher.com/pix/javafx/backnorge.jpg");
    static ImagePattern unmutedIMG = new ImagePattern(unmuted);
    static ImagePattern mutedIMG = new ImagePattern(muted);
    public static boolean isPlaying, powerUP, isMuted, isPaused;
    static Ball2 ball;
    static Paddle gamePaddle;
    protected static long startTime, stopTime;

    public void setup(){
        scoreLabel.setLayoutX(30);
        scoreLabel.setLayoutY(550);
        scoreLabel.setTextFill(Color.DEEPSKYBLUE);

        timeElapsed.setLayoutX(30);
        timeElapsed.setLayoutY(570);
        timeElapsed.setTextFill(Color.DEEPSKYBLUE);

        LinearGradient pauseRektGradient = LinearGradient.valueOf("from 100% 0% to 100% 100%, #009FFF  50%,  transparent 100%");
        pauseRekt.setFill(pauseRektGradient);
        pauseRekt.setWidth(gameWindow.getWidth());
        pauseRekt.setHeight(500);

        pauseLabel.setMinWidth(120);
        pauseLabel.setLayoutX((gameWindow.getWidth() / 2) - (pauseLabel.getWidth() / 2));
        pauseLabel.setLayoutY(200);
        pauseLabel.setTextFill(Color.WHITE);
        pauseLabel.setStyle("-fx-font-size: 40; -fx-font-weight: bold;");

        powerUPpadSize.setOnMousePressed(e -> {
            gamePaddle.setWidth(100);
            gameWindow.getChildren().removeAll(powerUPpadSize);
        });

        gameWindow.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.SPACE) {
                isPaused = !isPaused;
                if (isPaused){
                      oldPaddleColor = gamePaddle.getFill();
                      oldBallColor = ball.getFill();
                    gamePaddle.setFill(Color.TRANSPARENT);
                    ball.setFill(Color.TRANSPARENT);
                    gameWindow.getChildren().addAll(pauseRekt);
                    gameWindow.getChildren().addAll(pauseLabel);
                    pauseRekt.toFront();
                    pauseLabel.toFront();
                    Ball2.animation.pause();
                } else {
                    gamePaddle.setFill(oldPaddleColor);
                    ball.setFill(oldBallColor);
                    gameWindow.getChildren().removeAll(pauseRekt);
                    gameWindow.getChildren().removeAll(pauseLabel);
                    Ball2.animation.play();
                }
            }
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
        muteButton.setOnMousePressed(e -> muteUnmute() );

        score.setText(String.valueOf(lives));
        score.setTextFill(Color.WHITE);
        score.setLayoutX(155);
        score.setLayoutY(547);
        score.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");

        bigLabel.setLayoutX(330);
        bigLabel.setLayoutY(360);
        bigLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");

        bigButton.setLayoutX((gameWindow.getWidth() / 2) - (70));
        bigButton.setLayoutY(360);
        bigButton.setMinWidth(140);
        bigButton.setMinHeight(70);
        bigButton.setTextFill(Color.WHITE);

        easyButton.setTextFill(Color.WHITE);
        easyButton.setBackground(new Background(new BackgroundFill(Color.DARKGREEN, null, null)));
        easyButton.setLayoutX(300);
        easyButton.setLayoutY(300);
        easyButton.setMinWidth(100);
        easyButton.setMinHeight(40);
        easyButton.setOnMousePressed(e -> {
            Ball2.speedRate = 10;
            ball = new Ball2(gameWindow, 396, 420, gamePaddle);
            ball.setCenterY(420);
            ball.setCenterX(396);
            gameWindow.getChildren().add(ball);
            gameWindow.getChildren().remove(easyButton);
            gameWindow.getChildren().remove(hardButton);
            startTime = System.currentTimeMillis();
            addBricks(gameWindow);
        });

        hardButton.setTextFill(Color.WHITE);
        hardButton.setBackground(new Background(new BackgroundFill(Color.DARKRED, null, null)));
        hardButton.setLayoutX(500);
        hardButton.setLayoutY(300);
        hardButton.setMinWidth(100);
        hardButton.setMinHeight(40);
        hardButton.setOnMousePressed(e -> {
            Ball2.speedRate = 15;
            ball = new Ball2(gameWindow, (gameWindow.getWidth() / 2), 380, gamePaddle);
            ball.setCenterY(420);
            ball.setCenterX(396);
            gameWindow.getChildren().add(ball);
            gameWindow.getChildren().remove(easyButton);
            gameWindow.getChildren().remove(hardButton);
            startTime = System.currentTimeMillis();
            addBricks(gameWindow);
        });
        gamePaddle = new Paddle(70, 20);
        gameWindow.setOnMouseMoved(e -> gamePaddle.setX((e.getX() - (gamePaddle.getWidth() / 2))));
        gameWindow.getChildren().remove(startButton);
        gameWindow.getChildren().remove(clickToPlayLabel);
        gameWindow.getChildren().remove(clickLabel);

        gameWindow.setCursor(Cursor.CROSSHAIR);
        gameWindow.getChildren().add(bricksLeftInfoLabel);
        gameWindow.getChildren().add(bricksLeftLabel);
        gameWindow.getChildren().add(score);
        gameWindow.getChildren().add(gamePaddle);
        gameWindow.getChildren().add(muteButton);
        gameWindow.getChildren().add(bigLabel);

        startLVL(gameWindow);
    }

//    private static double getSpeed(boolean isH){
//        Random randX = new Random();
//        Random randY = new Random();
//        int randomDirection = randX.nextInt(10)+2;
//        boolean leftOrRight = randomDirection%2 != 0;
//        int speedXX = (randX.nextInt(4) + 10);
//        int speedYY = (randY.nextInt(4) + 10);
//        vVelocity = (double)speedYY/10;
//        double hVelTemp = (double)speedXX/10;
//        hVelocity = leftOrRight ? hVelTemp : -hVelTemp;
//        return isH ? hVelocity : vVelocity;
//    }

    public static void startLVL(Pane gameWindow) {
        gameWindow.getChildren().add(easyButton);
        gameWindow.getChildren().add(hardButton);
    }

    private static void addBricks(Pane gameWindow) {
        gameWindow.getChildren().remove(bigLabel);
        gameWindow.getChildren().remove(bigButton);
        ImageView background = new ImageView(back1);
        gameWindow.getChildren().add(background);
        background.toBack();
        getBricks(15, 10);
        get20percent((levelNumber == 2 ? 135 : (levelNumber == 3 ? 145 : 8)), 150);
        for(Brick brick : Brick.bricks){
            if(brick != null)
                gameWindow.getChildren().add(brick);
        }
        gameWindow.requestFocus();
        isPlaying = true;
    }

    private static void getBricks(int numRows, int numCols) {
        for (int column = 0; column < numCols; column++) {
            for (int row = 0; row < numRows; row++) {
                new Brick(row, column, Controller.levelNumber);
            }
        }
        Light.Distant light = new Light.Distant();
        Lighting l = new Lighting();
        light.setAzimuth(-75.0f);

        Lighting lightEffect = new Lighting();
        lightEffect.setLight(light);
        lightEffect.setSurfaceScale(3.4f);
        for(Brick brick : Brick.bricks){
            light.setAzimuth(-70.0f);
            l.setLight(light);
            l.setSurfaceScale(3.0f);
            brick.setEffect(l);
        }
        Ball2.ballsLeft = Brick.bricks.size();
    }

    private static void get20percent(int target, int start){
        if(target == start)
            return;

        int[] bricksToRemove = new int[start-target];

        for(int i = 0; i < bricksToRemove.length; i++){
            int random = rand.nextInt();
            bricksToRemove[i] = random;  // <-<-<-<-<-<-<-<-<-<-<-<-<-
        }

        if(Brick.bricks.size() <= target)
            return;
        else
            decrease(target, start);
    }

    private static void decrease(int target, int current) {
        if(current == target)
            return;
        System.out.println(current);
        int brickNum = rand.nextInt(current);
        if(Brick.bricks.get(brickNum) != null) {
            Brick.bricks.set(brickNum, null);
        }
        else {
            decrease(target, current);
        }

        decrease(target, --current);
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

    public static void betweenLVLs(Pane gameWindow) {
        bigLabel.setText("You beat lvl: " + levelNumber + "!\n");
        bigLabel.setTextFill(Color.GREENYELLOW);
        bigButton.setText("Start lvl: " + (levelNumber + 1));
        bigButton.setStyle("-fx-background-color: blue; -fx-font-size: 30; -fx-font-weight:bold;");
        gameWindow.getChildren().removeAll(Brick.bricks);
        bigButton.setOnAction(e -> {
            levelNumber++;
            addBricks(gameWindow);
        });
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
            gameWindow.getChildren().removeAll(bigLabel);
            gameWindow.getChildren().removeAll(bigButton);
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
