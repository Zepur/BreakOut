package breakOutPkge;

import javafx.animation.FadeTransition;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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

    static Image unmuted                = new Image("http://lolcipher.com/pix/javafx/unmuted.png");
    static Image muted                  = new Image("http://lolcipher.com/pix/javafx/muted.png");
    static Image back1                  = new Image("http://lolcipher.com/pix/javafx/backjack.jpg");
    static Image back2                  = new Image("http://lolcipher.com/pix/javafx/backsolar.jpg");
    static Image back3                  = new Image("http://lolcipher.com/pix/javafx/backnorge.jpg");
    static Image legend                 = new Image("http://lolcipher.com/pix/javafx/legend.png");
    static ImageView legendImg          = new ImageView(legend);
    static ImagePattern unmutedIMG      = new ImagePattern(unmuted);
    static ImagePattern mutedIMG        = new ImagePattern(muted);
    static Button easyButton            = new Button("[ play ]");
    static Button bigButton             = new Button();
    static Label bigLabel               = new Label();
    static Label levelInfoLabel         = new Label("lvl:");
    static Label levelLabel             = new Label("0");
    static Label powerUpInfo            = new Label();
    static Label pauseLabel             = new Label("[ paused ]");
    static Label ballsRemainingInfoLabel= new Label("balls remaining:");
    static Label ballsRemainginLabel    = new Label();
    static Label bricksLeftInfoLabel    = new Label("bricks remaining:");
    static Label bricksLeftLabel        = new Label("150");
    static Label scoreInfoLabel         = new Label("score:");
    static Label scoreLabel             = new Label("0");
    public static Rectangle muteButton  = new Rectangle();
    public static Rectangle pauseRect   = new Rectangle();
    public static int lives             = 3;
    public static int levelNumber       = 1;
    public static int score             = 1;
    public static Circle powerUPcircle;
    static ImageView background;
    public static MediaPlayer jukeBox;
    public static boolean isPlaying, powerUP, isMuted, isPaused;
    static Ball2 ball;
    static Paddle gamePaddle;
    static Timeline powerUpAnimation;
    static Random rand = new Random();

    public void setup(){
        gameWindow.setCursor(Cursor.CROSSHAIR);

        levelInfoLabel.setLayoutY(650);
        levelInfoLabel.setLayoutX(300);
        levelInfoLabel.setTextFill(Color.WHITE);
        levelInfoLabel.setStyle("-fx-font-size: 14;");

        levelLabel.setLayoutY(650);
        levelLabel.setLayoutX(330);
        levelLabel.setTextFill(Color.web("0x009fff"));
        levelLabel.setStyle("-fx-font-size: 14;");

        powerUpInfo.setLayoutY(600);
        powerUpInfo.setLayoutX((gameWindow.getWidth()/ 2)-40);
        powerUpInfo.setTextFill(Color.DEEPPINK);
        powerUpInfo.setStyle("-fx-font-size: 26;");

        LinearGradient pauseRectGradient = LinearGradient.valueOf("from 100% 0% to 100% 100%, #009FFF 50%, transparent 100%");
        pauseRect.setFill(pauseRectGradient);
        pauseRect.setWidth(gameWindow.getWidth());
        pauseRect.setHeight(500);

        pauseLabel.setMinWidth(120);
        pauseLabel.setLayoutX((gameWindow.getWidth() - 120) / 2);
        pauseLabel.setLayoutY(200);
        pauseLabel.setTextFill(Color.WHITE);
        pauseLabel.setStyle("-fx-font-size: 40; -fx-font-weight: bold;");

        gameWindow.setOnKeyPressed(e -> {
            if(e.getCode() == KeyCode.SPACE) {
                isPaused = !isPaused;
                pause(gameWindow);
            }
            if(e.getCode() == KeyCode.M) { muteUnmute(); }
            if(e.getCode() == KeyCode.ESCAPE) { System.exit(0); }
            if(e.getCode() == KeyCode.R) { System.out.println("restarting");
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

        legendImg.setY(30);
        legendImg.setX((gameWindow.getWidth() - legend.getWidth()) / 2);

        gamePaddle = new Paddle(70, 10);
        gameWindow.setOnMouseMoved(e ->
            gamePaddle.setX((e.getX() - (gamePaddle.getWidth() / 2))) );

        gameWindow.getChildren().remove(startButton);
        gameWindow.getChildren().remove(clickToPlayLabel);
        gameWindow.getChildren().remove(clickLabel);

        gameWindow.getChildren().add(legendImg);
        gameWindow.getChildren().add(gamePaddle);
        gameWindow.getChildren().add(muteButton);
        gameWindow.getChildren().add(bigLabel);

        bricksLeftInfoLabel.setLayoutX(30);
        bricksLeftInfoLabel.setLayoutY(680);
        bricksLeftInfoLabel.setTextFill(Color.WHITE);
        bricksLeftInfoLabel.setStyle("-fx-font-size: 14;");

        bricksLeftLabel.setLayoutX(150);
        bricksLeftLabel.setLayoutY(680);
        bricksLeftLabel.setTextFill(Color.web("0x009FFF"));
        bricksLeftLabel.setStyle("-fx-font-size: 14;");

        scoreInfoLabel.setLayoutX(30);
        scoreInfoLabel.setLayoutY(650);
        scoreInfoLabel.setTextFill(Color.WHITE);
        scoreInfoLabel.setStyle("-fx-font-size: 14;");

        scoreLabel.setLayoutY(650);
        scoreLabel.setLayoutX(150);
        scoreLabel.setTextFill(Color.web("0x009FFF"));
        scoreLabel.setStyle("-fx-font-size: 14;");

        ballsRemainginLabel.setText(String.valueOf(lives));
        ballsRemainginLabel.setLayoutX(150);
        ballsRemainginLabel.setLayoutY(620);
        ballsRemainginLabel.setTextFill(Color.web("0x009FFF"));
        ballsRemainginLabel.setStyle("-fx-font-size: 14;");

        ballsRemainingInfoLabel.setLayoutX(30);
        ballsRemainingInfoLabel.setLayoutY(620);
        ballsRemainingInfoLabel.setTextFill(Color.WHITE);
        ballsRemainingInfoLabel.setStyle("-fx-font-size: 14;");

        gameWindow.getChildren().add(levelInfoLabel);
        gameWindow.getChildren().add(levelLabel);
        gameWindow.getChildren().add(bricksLeftInfoLabel);
        gameWindow.getChildren().add(bricksLeftLabel);
        gameWindow.getChildren().add(scoreInfoLabel);
        gameWindow.getChildren().add(scoreLabel);
        gameWindow.getChildren().add(ballsRemainginLabel);
        gameWindow.getChildren().add(ballsRemainingInfoLabel);

        startNewGame(gameWindow);
    }

    private static void startNewGame(Pane gameWindow){
        easyButton.setTextFill(Color.web("0x009FFF"));
        easyButton.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        easyButton.setStyle("-fx-border-color: #009FFF");
        easyButton.setLayoutX(400);
        easyButton.setLayoutY((gameWindow.getWidth()-100)/2);
        easyButton.setMinWidth(100);
        easyButton.setMinHeight(40);
        easyButton.setOnMousePressed(e -> {
            Ball2.speedRate = 10;
            gameWindow.getChildren().remove(easyButton);
            gameWindow.getChildren().remove(legendImg);
            ball = new Ball2(gameWindow, 396, 420, 1.3, 1.4, gamePaddle);
            gameWindow.getChildren().addAll(ball);
            if(gameWindow.getChildren().contains(ball)) addBricks(gameWindow);
        });
        gameWindow.getChildren().add(easyButton);
    }

    private static void addBricks(Pane gameWindow) {
        gameWindow.getChildren().removeAll(background);
        Brick.bricks = new ArrayList<>();
        background = new ImageView((levelNumber == 3 ? back3 : (levelNumber == 2 ? back2 : back1)));
        gameWindow.getChildren().add(background);
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

    public static void pause(Pane gameWindow) {
        if (isPaused){
            gameWindow.getChildren().addAll(pauseRect);
            gameWindow.getChildren().addAll(pauseLabel);
            gameWindow.getChildren().removeAll(powerUPcircle);
            pauseRect.toFront();
            pauseLabel.toFront();
            Ball2.animation.pause();
        } else {
            gameWindow.getChildren().removeAll(pauseRect);
            gameWindow.getChildren().removeAll(pauseLabel);
            Ball2.animation.play();
        }
    }

    public static void powerMeUp(int number, Pane gameWindow){
        if(number == 3 || number == 14){
            if(!powerUP) {
                powerUP = true;
                powerUPcircle = new Circle(475, 20, 25, Color.ALICEBLUE);
                powerUPcircle.setOnMousePressed(e -> {
                    gamePaddle.setWidth(100);
                    gameWindow.getChildren().removeAll(powerUPcircle);
                });
                gameWindow.getChildren().add(powerUPcircle);
                powerUpAnimation = new Timeline(new KeyFrame(Duration.millis(60), e -> movePowerUP(gameWindow)));
                powerUpAnimation.setCycleCount(150);
                powerUpAnimation.setRate(4);
                powerUpAnimation.play();
            }
        }
    }

    private static void movePowerUP(Pane gameWindow){
        powerUPcircle.setCenterY(powerUPcircle.getCenterY() + 5);
        if(gamePaddle.collides(powerUPcircle)) {
            int powerUpPicker = rand.nextInt(7);
            gameWindow.getChildren().removeAll(powerUPcircle);
            gameWindow.getChildren().add(powerUpInfo);
            powerUpAnimation.stop();
            if      (powerUpPicker == 0){
                gamePaddle.setWidth(gamePaddle.getWidth() - 25);
                Ball2.powerUpTimer = 1337;
                powerUpInfo.setText("small pad");
            }
            else if (powerUpPicker == 1) {
                gamePaddle.setWidth(gamePaddle.getWidth() + 30);
                Ball2.powerUpTimer = 1404;
                powerUpInfo.setText("big pad");
            }
            else if (powerUpPicker == 2) {
                lives++;
                ballsRemainginLabel.setText(String.valueOf(lives));
                powerUpInfo.setText("extra life");
                FadeTransition ft = new FadeTransition(Duration.millis(3000), powerUpInfo);
                ft.setFromValue(1.0);
                ft.setToValue(0.2);
                ft.setCycleCount(4);
                ft.setAutoReverse(true);
                ft.play();
                Ball2.powerUpTimer = 2300;
            }
            else if (powerUpPicker == 3) {
                gamePaddle.setFill(Color.LIMEGREEN);
                Ball2.powerUpTimer = 1400;
                powerUpInfo.setText("green pad");
            }
            else if (powerUpPicker == 4) {
                ball.setFill(Color.TRANSPARENT);
                Ball2.powerUpTimer = 800;
                powerUpInfo.setText("\"invisible\" ball");
            }
            else if (powerUpPicker == 5) {
                gamePaddle.setFill(Color.TRANSPARENT);
                gamePaddle.setStroke(Color.TRANSPARENT);
                Ball2.powerUpTimer = 1100;
                powerUpInfo.setText("invisible pad");
            }
            else if (powerUpPicker == 6) {
                Ball2.superBall = true;
                ball.setFill(Color.YELLOW);
                ball.setStrokeWidth(5);
                ball.setStroke(Color.GOLD);
                Ball2.powerUpTimer = 2135;
                powerUpInfo.setText("SMASH");
            }
        }
        if(powerUPcircle.getCenterY() >= 650){
            gameWindow.getChildren().removeAll(powerUPcircle);
            powerUpAnimation.stop();
            powerUP = false;
        }
    }

    public static void betweenLVLs(Pane gameWindow) {
        bigLabel.setText("You beat lvl: " + levelNumber + "!\n");
        bigLabel.setTextFill(Color.GREENYELLOW);
        bigButton.setText(levelNumber<3 ? "Start lvl: " + (levelNumber + 1): "Game won!");
        if(levelNumber<3) {
            gameWindow.getChildren().removeAll(Brick.bricks);
            gameWindow.getChildren().addAll(bigButton);
            bigButton.setOnAction(e -> {
                gameWindow.getChildren().removeAll(bigButton);
                gameWindow.getChildren().removeAll(bigLabel);
                levelNumber++;
                ball.speedX = Ball2.startXspeed;
                ball.speedY = Ball2.startYspeed;
                addBricks(gameWindow);
            });
        } else {
            bigButton.setOnAction(e -> {
                ball = null;
                gameWindow.getChildren().removeAll(Brick.bricks);
                gameWindow.getChildren().removeAll(bigLabel);
                gameWindow.getChildren().removeAll(bigButton);
                Brick.bricks = new ArrayList<>();
                levelNumber = 1;
                lives = 3;
                ballsRemainginLabel.setText(String.valueOf(lives));
                startNewGame(gameWindow);
            });
        }
    }

    public static void endGame(Pane gameWindow){
        gameWindow.getChildren().removeAll(bigLabel);
        gameWindow.getChildren().removeAll(bigButton);
        bigLabel.setText("Game over @ lvl: " + levelNumber + "!");
        bigLabel.setTextFill(Color.TOMATO);
        bigButton.setText("Retry");
        bigButton.setOnAction(e -> {
            ball = null;
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
