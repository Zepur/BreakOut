package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class Controller {
    @FXML
    AnchorPane playWindow;
    @FXML
    Pane gameWindow;
    @FXML
    Rectangle gamePaddle;
    @FXML
    Label gameName;
    @FXML
    Label scoreLabel;
    @FXML
    Label score;
    @FXML
    Rectangle player;
    @FXML
    Rectangle clickToPlay;
    public static double speedX;
    public static double speedY;
    private static int scoreInt = 0;

    public void movePad(MouseEvent e) {
        double boundsRight = (810- gamePaddle.getWidth());
        score.setText(String.valueOf(scoreInt));
        gamePaddle.setX(e.getX());
        if(gamePaddle.getX()>=boundsRight)
            gamePaddle.setX(boundsRight-1);
        if(gamePaddle.getX()<=0)
            gamePaddle.setX(1);
    }

    public static void setupBall(int diff) {
        speedX = diff;
        switch(diff){
            case 1:
                speedY = 3;
                break;
            case 2:
                speedY = 3.5;
                break;
            default:
                speedY = 4;
        }
    }

    public void playBall(){
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    final Timeline loop = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {

        double deltaX = 3;
        double deltaY = 4;

        @Override
        public void handle(final ActionEvent t) {
            player.setLayoutX(player.getLayoutX() + deltaX);
            player.setLayoutY(player.getLayoutY() - deltaY);

            final boolean atRightBorder = player.getLayoutX() >= 800;
            final boolean atLeftBorder = player.getLayoutX() <= 1;
            final boolean atBottomBorder = player.getLayoutY() >= 500;
            final boolean atTopBorder = player.getLayoutY() <= 4;
            final boolean atPaddle = player.getLayoutY() >= 438 && gamePaddle.getY()<=433;
            final boolean underPaddle = player.getLayoutY() > 440;
            final boolean insidePaddleArea = (player.getLayoutX()>=gamePaddle.getX()) && (((player.getLayoutX()+10) - gamePaddle.getWidth())<=gamePaddle.getX());

            if (atRightBorder || atLeftBorder)
                deltaX = -deltaX;
            if (atTopBorder)
                deltaY = -deltaY;
            if(atPaddle && insidePaddleArea){
                deltaY = -deltaY;
                scoreInt += 12;
                if(underPaddle){
                    loop.stop();
                    player.setVisible(false);
                }
            }
            if(atBottomBorder) {
                getBricks();
                score.setText("YOU LOST");
                player.setVisible(false);
                loop.stop();
            }
        }
    }));

    private void getBricks() {

    }


    public void setup(){
        gameWindow.setCursor(Cursor.NONE);
        clickToPlay.setVisible(false);
        int diff = 1;
        setupBall(diff);
        playBall();
    }
}
