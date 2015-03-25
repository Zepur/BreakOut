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
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    static Image unmuted                = new Image("http://lolcipher.com/pix/javafx/unmuted.png");
    static Image muted                  = new Image("http://lolcipher.com/pix/javafx/muted.png");
    static Image back1                  = new Image("http://lolcipher.com/pix/javafx/backjack.jpg"); // http://www.lolcipher.com/pix/javafx/2mas.jpg");
    static Image back2                  = new Image("http://lolcipher.com/pix/javafx/backsolar.jpg");
    static Image back3                  = new Image("http://lolcipher.com/pix/javafx/backnorge.jpg");
    static Image muteGuide              = new Image("http://lolcipher.com/pix/javafx/muteGuide.png");
    static Image bricksGuide            = new Image("http://lolcipher.com/pix/javafx/bricksGuide.png");
    static Image livesGuide             = new Image("http://lolcipher.com/pix/javafx/livesGuide.png");
    static Image pauseGuide             = new Image("http://lolcipher.com/pix/javafx/pauseGuide.png");
    static ImageView muteGuideImg       = new ImageView(muteGuide);
    static ImageView bricksGuideImg     = new ImageView(bricksGuide);
    static ImageView livesGuideImg      = new ImageView(livesGuide);
    static ImageView pauseGuideImg      = new ImageView(pauseGuide);
    static ImagePattern unmutedIMG      = new ImagePattern(unmuted);
    static ImagePattern mutedIMG        = new ImagePattern(muted);
    static Button easyButton            = new Button("[ easy ]");
    static Button hardButton            = new Button("[ hard ]");
    static Button bigButton             = new Button();
    static Label bigLabel               = new Label();
    static Label pauseLabel             = new Label("[ paused ]");
    static Label ballsRemainingInfoLabel= new Label("balls remaining:");
    static Label ballsRemainginLabel    = new Label();
    static Label bricksLeftInfoLabel    = new Label("bricks remaining:");
    static Label bricksLeftLabel        = new Label("150");
    static Label timeElapsedInfoLabel   = new Label("time:");
    static Label timeElapsedLabel       = new Label("0.0");
    public static Rectangle muteButton  = new Rectangle();
    public static Rectangle pauseRect   = new Rectangle();
    public static Circle powerUPpadSize = new Circle(40, 490, 15, Color.ALICEBLUE);
    public static int lives             = 3;
    public static int levelNumber       = 1;
    static ImageView background;
    public static MediaPlayer jukeBox;
    public static boolean isPlaying, powerUP, isMuted, isPaused;
    protected static long startTime, stopTime;
    public static Paint oldPaddleColor, oldBallColor;
    static Ball2 ball;
    static Paddle gamePaddle;

    public void setup(){
        ballsRemainingInfoLabel.setLayoutX(30);
        ballsRemainingInfoLabel.setLayoutY(635);
        ballsRemainingInfoLabel.setTextFill(Color.DEEPSKYBLUE);
        ballsRemainingInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        ballsRemainginLabel.setText(String.valueOf(lives));
        ballsRemainginLabel.setLayoutX(148);
        ballsRemainginLabel.setLayoutY(633);
        ballsRemainginLabel.setTextFill(Color.WHITE);
        ballsRemainginLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        bricksLeftInfoLabel.setLayoutX(30);
        bricksLeftInfoLabel.setLayoutY(675);
        bricksLeftInfoLabel.setTextFill(Color.DEEPSKYBLUE);
        bricksLeftInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        bricksLeftLabel.setLayoutX(150);
        bricksLeftLabel.setLayoutY(675);
        bricksLeftLabel.setTextFill(Color.WHITE);
        bricksLeftLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        timeElapsedInfoLabel.setLayoutX(30);
        timeElapsedInfoLabel.setLayoutY(570);
        timeElapsedInfoLabel.setTextFill(Color.DEEPSKYBLUE);
        timeElapsedInfoLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");

        timeElapsedLabel.setLayoutY(570);
        timeElapsedLabel.setLayoutX(70);
        timeElapsedLabel.setTextFill(Color.WHITE);
        timeElapsedLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16;");

        LinearGradient pauseRectGradient = LinearGradient.valueOf("from 100% 0% to 100% 100%, #009FFF 50%, transparent 100%");
        pauseRect.setFill(pauseRectGradient);
        pauseRect.setWidth(gameWindow.getWidth());
        pauseRect.setHeight(500);

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
                    gameWindow.getChildren().addAll(pauseRect);
                    gameWindow.getChildren().addAll(pauseLabel);
                    pauseRect.toFront();
                    pauseLabel.toFront();
                    Ball2.animation.pause();
                } else {
                    gamePaddle.setFill(oldPaddleColor);
                    ball.setFill(oldBallColor);
                    gameWindow.getChildren().removeAll(pauseRect);
                    gameWindow.getChildren().removeAll(pauseLabel);
                    Ball2.animation.play();
                }
            }
            if(e.getCode() == KeyCode.M) {
                muteUnmute();
            }
        });

        muteButton.setWidth(30);
        muteButton.setHeight(30);
        muteButton.setLayoutX((gameWindow.getWidth() - muteButton.getWidth()) / 2);
        muteButton.setLayoutY(650);
        muteButton.setFill(unmutedIMG);

        bigLabel.setLayoutX(700);
        bigLabel.setLayoutY(608);
        bigLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 22;");

        bigButton.setLayoutX(700);
        bigButton.setLayoutY(640);
        bigButton.setMinWidth(120);
        bigButton.setMinHeight(45);
        bigButton.setTextFill(Color.BLACK);
        bigButton.setStyle("-fx-background-color: #009FFF; -fx-font-size: 22; -fx-font-weight:bold;");

        muteGuideImg.setX(460);
        muteGuideImg.setY(605);
        bricksGuideImg.setX(183);
        bricksGuideImg.setY(663);
        livesGuideImg.setX(165);
        livesGuideImg.setY(600);
        livesGuideImg.setRotate(-20);
        pauseGuideImg.setX(303);
        pauseGuideImg.setY(420);

        gamePaddle = new Paddle(70, 20);
        gameWindow.setOnMouseMoved(e -> gamePaddle.setX((e.getX() - (gamePaddle.getWidth() / 2))));
        gameWindow.getChildren().remove(startButton);
        gameWindow.getChildren().remove(clickToPlayLabel);
        gameWindow.getChildren().remove(clickLabel);

        gameWindow.getChildren().addAll(muteGuideImg);
        gameWindow.getChildren().addAll(bricksGuideImg);
        gameWindow.getChildren().addAll(livesGuideImg);
        gameWindow.getChildren().addAll(pauseGuideImg);
        gameWindow.setCursor(Cursor.CROSSHAIR);
        gameWindow.getChildren().add(bricksLeftInfoLabel);
        gameWindow.getChildren().add(bricksLeftLabel);
        gameWindow.getChildren().add(timeElapsedInfoLabel);
        gameWindow.getChildren().add(timeElapsedLabel);
        gameWindow.getChildren().add(ballsRemainginLabel);
        gameWindow.getChildren().add(ballsRemainingInfoLabel);
        gameWindow.getChildren().add(gamePaddle);
        gameWindow.getChildren().add(muteButton);
        gameWindow.getChildren().add(bigLabel);
        startNewGame(gameWindow);
    }

    private static void startNewGame(Pane gameWindow){
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
            gameWindow.getChildren().remove(muteGuideImg);
            gameWindow.getChildren().remove(bricksGuideImg);
            gameWindow.getChildren().remove(livesGuideImg);
            gameWindow.getChildren().remove(pauseGuideImg);
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
            gameWindow.getChildren().remove(muteGuideImg);
            gameWindow.getChildren().remove(bricksGuideImg);
            gameWindow.getChildren().remove(livesGuideImg);
            gameWindow.getChildren().remove(pauseGuideImg);
            startTime = System.currentTimeMillis();
            addBricks(gameWindow);
        });
        gameWindow.getChildren().add(easyButton);
        gameWindow.getChildren().add(hardButton);
    }

    private static void addBricks(Pane gameWindow) {
        gameWindow.getChildren().removeAll(background);
        Brick.bricks = new ArrayList<>();
        background = new ImageView((levelNumber == 3 ? back3 : (levelNumber == 2 ? back2 : back1)));
        gameWindow.getChildren().add(background);
        background.setRotate(-90);
        background.setLayoutY(0);
        background.setLayoutX(-455);
        background.toBack();
        getBricks(15, 10);
        reduceNumberOfBlocks((levelNumber == 3 ? 150 : (levelNumber == 2 ? 140 : 120)), 150);
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
                Brick newBrick = new Brick(row, column, Controller.levelNumber);
                newBrick.setLayoutX(60 + (row * 50) + (3 * row));
                newBrick.setLayoutY(50 + (column * 20) + (3 * column));
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
        Ball2.bricksLeft = (levelNumber == 3 ? 150 : (levelNumber == 2 ? 140 : 120));
    }

    private static void reduceNumberOfBlocks(int target, int start){
        ArrayList<Integer> randomBricks = new ArrayList<>(start-1);
        for(int i = 0; i < start; i++){
            randomBricks.add(i);
        }
        Collections.shuffle(randomBricks);
        List<Integer> randomBricksShuffled = randomBricks.subList(0, (start-target));
        for(int i : randomBricksShuffled){
            Brick.bricks.set(i, null);
        }
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
        gameWindow.getChildren().removeAll(Brick.bricks);
        gameWindow.getChildren().addAll(bigButton);
        bigButton.setOnAction(e -> {
            gameWindow.getChildren().removeAll(bigButton);
            gameWindow.getChildren().removeAll(bigLabel);
            levelNumber++;
            ball.speedX = 1.1;
            ball.speedY = 1.3;
            addBricks(gameWindow);
        });
    }

    public static void endGame(Pane gameWindow){
        gameWindow.getChildren().removeAll(bigLabel);
        gameWindow.getChildren().removeAll(bigButton);
        bigLabel.setText("Game over @ lvl: " + levelNumber + "!");
        bigLabel.setTextFill(Color.TOMATO);
        bigButton.setText("Retry");
        bigButton.setOnAction(e -> {
            gameWindow.getChildren().removeAll(Controller.ball);
            gameWindow.getChildren().removeAll(Brick.bricks);
            gameWindow.getChildren().removeAll(bigLabel);
            gameWindow.getChildren().removeAll(bigButton);
            Brick.bricks = new ArrayList<>();
            levelNumber = 1;
            lives = 3;
            ballsRemainginLabel.setText(String.valueOf(lives));
            startNewGame(gameWindow);
        });
        gameWindow.getChildren().add(bigButton);
        gameWindow.getChildren().add(bigLabel);
    }

}
