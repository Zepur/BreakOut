package breakOutPkge;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    static Label bigLabel = new Label();
    static Button bigButton = new Button();
    static Label score = new Label();
    public static int levelNumber = 1;
    public static int lives = 3;
    public static double hVelocity;
    public static double vVelocity;
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
    Label clickToPlayLabel;
    @FXML
    Button easy;
    @FXML
    Button hard;
    static Ball ball;
    static Paddle gamePaddle;

    protected static long startTime;

    public void setup(int width, int height, boolean isHard){
        score.setText(String.valueOf(lives));
        score.setTextFill(Color.WHITE);
        score.setLayoutX(85);
        score.setLayoutY(547);
        score.setStyle("-fx-font-weight: bold; -fx-font-size: 20;");

        bigLabel.setLayoutX(150);
        bigLabel.setLayoutY(240);
        bigLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 40;");

        bigButton.setLayoutX((gameWindow.getWidth() / 2) - (70));
        bigButton.setLayoutY(60);
        bigButton.setMinWidth(140);
        bigButton.setMinHeight(70);
        bigButton.setTextFill(Color.WHITE);

        gameWindow.setCursor(Cursor.CROSSHAIR);
        gamePaddle = new Paddle(gameWindow, width, height);
        clickToPlayLabel.setText(null);
        easy.setVisible(false);
        hard.setVisible(false);
        muteUnmute();
        muteImgView.setX(380);
        muteImgView.setY(545);
        double hVelocity = getSpeedParseIsH(true, isHard);
        double vVelocity = getSpeedParseIsH(false, isHard);
        ball = new Ball(gameWindow, (gameWindow.getWidth()/2), 380, hVelocity, vVelocity, gamePaddle);
        ball.setCenterY(380);
        ball.setCenterX(200);

        gameWindow.getChildren().add(score);
        gameWindow.getChildren().add(gamePaddle);
        gameWindow.getChildren().add(muteImgView);
        gameWindow.getChildren().add(ball);
        gameWindow.getChildren().add(bigLabel);
        gameWindow.getChildren().add(bigButton);
        startLVL(gameWindow);
    }

    private static void addBricks(Pane gameWindow) {
        gameWindow.getChildren().remove(bigLabel);
        gameWindow.getChildren().remove(bigButton);
        getBricks(gameWindow, 15, 10);
        get20percent();
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
    }

    private static void get20percent(){
        decrease(120, Brick.bricks.size());
    }

    private static void decrease(int target, int lvl) {
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
        vVelocity = (double)speedYY/10;
        double hVelTemp = (double)speedXX/10;
        hVelocity = leftOrRight ? hVelTemp : -hVelTemp;
        return isH ? hVelocity : vVelocity;
    }

    public void startEasy(){
        setup(80, 20, false);
    }
    public void startHard(){
        setup(70, 20, true);
    }

    public static void startLVL(Pane gameWindow) {
        bigLabel.setText("Lets start!");
        bigLabel.setTextFill(Color.FORESTGREEN);
        bigButton.setText("Start");
        bigButton.setStyle("-fx-background-color: green;");
        bigButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startTime = System.currentTimeMillis() / 1000;
                addBricks(gameWindow);
            }
        });
    }

    public static void betweenLVLs(Pane gameWindow) {
        bigLabel.setText("You beat lvl: " + levelNumber + "!\nYaay");
        bigLabel.setTextFill(Color.GREENYELLOW);
        bigButton.setText("Start lvl: "+(levelNumber+1));
        bigButton.setStyle("-fx-background-color: blue;");
        bigButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                levelNumber++;
                addBricks(gameWindow);
            }
        });
        gameWindow.getChildren().add(bigButton);
        gameWindow.getChildren().add(bigLabel);
    }

    public static void endGame(Pane gameWindow){
        bigLabel.setText("YOU LOST AT lvl: " + levelNumber + "!\nNOOB");
        bigLabel.setTextFill(Color.RED);
        bigButton.setStyle("-fx-background-color: blue;");
        bigButton.setText("Retry");
        bigButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                gameWindow.getChildren().removeAll(Brick.bricks);
                Brick.bricks = new ArrayList<>();
                levelNumber = 1;
                startLVL(gameWindow);
            }
        });
        gameWindow.getChildren().add(bigButton);
        gameWindow.getChildren().add(bigLabel);
    }

}
