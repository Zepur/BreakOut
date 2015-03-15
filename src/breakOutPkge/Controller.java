package breakOutPkge;

import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.Random;

public class Controller {
    ImageView imgView = new ImageView();
    Image unmuted = new Image("http://lolcipher.com/pix/javafx/unmuted.png");
    Image muted = new Image("http://lolcipher.com/pix/javafx/muted.png");
    static public Boolean isMuted = true;
    @FXML
    static AnchorPane playWindow;
    @FXML
    Pane gameWindow;
    @FXML
    Label gameName;
    @FXML
    static Label scoreLabel;
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

    public void setup(int width, int height){
        gameWindow.setCursor(Cursor.CROSSHAIR);
        gamePaddle = new Paddle(gameWindow, width, height);
        gameWindow.getChildren().add(gamePaddle);
        clickToPlayLabel.setText(null);
        easy.setVisible(false);
        hard.setVisible(false);
        muteUnmute();
        imgView.setX(380);
        imgView.setY(545);
        gameWindow.getChildren().add(imgView);
        double hVelocity = getSpeedParseIsH(true);
        double vVelocity = getSpeedParseIsH(false);
        ball = new Ball(gameWindow, (gameWindow.getWidth()/2), (gamePaddle.getY()-20), hVelocity, vVelocity, gamePaddle);
        gameWindow.getChildren().add(ball);
        getBricks(15, 10);
        startTime = System.currentTimeMillis() / 1000;
    }

    private void getBricks(int numRows, int numCols) {
        for (int row = 0; row < numRows; row++) {
            for (int column = 0; column < numCols; column++) {
                gameWindow.getChildren().add(new Brick(gameWindow, row, column));
            }
        }
    }

    public void muteUnmute(){
        imgView.setImage(isMuted? unmuted : muted);
        isMuted = !isMuted;
    }

    private double getSpeedParseIsH(Boolean isH){
        Random rand = new Random();
        int randomDirection = rand.nextInt(10)+2;
        Boolean leftOrRight = randomDirection%2 != 0;
        int speed = (rand.nextInt(10) + 10);
        double vVelocity = (double)speed/10;
        double hVelTemp = (double)speed/10;
        double hVelocity = leftOrRight ? hVelTemp : -hVelTemp;
        return isH ? hVelocity : vVelocity;
    }

    public void startEasy(){
        setup(80, 20);
    }
    public void startHard(){
        setup(50, 20);
    }

}
