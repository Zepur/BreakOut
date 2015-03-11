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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;


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
    Circle playerCircle;
    @FXML
    Rectangle clickToPlay;
    @FXML
    Label clickToPlayLabel;
    public static double speedX;
    public static double speedY;
    private static int scoreInt = 0;
    private static GridPane gpane = new GridPane();
    private static Rectangle testRect;
    private static ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

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

    final Timeline loop = new Timeline(new KeyFrame(Duration.millis(15), new EventHandler<ActionEvent>() {

        double deltaX = 1;
        double deltaY = 1;

        @Override
        public void handle(final ActionEvent t) {
            playerCircle.setLayoutX(playerCircle.getLayoutX() + deltaX);
            playerCircle.setLayoutY(playerCircle.getLayoutY() - deltaY);

            boolean test = false;
            Shape intersectShape = Shape.intersect(playerCircle, gamePaddle);
            if (intersectShape.getBoundsInLocal().getWidth() != -1) {
                test = true;
            }
            boolean atRightBorder = playerCircle.getLayoutX() >= 800;
            boolean atLeftBorder = playerCircle.getLayoutX() <= 1;
            boolean atBottomBorder = playerCircle.getLayoutY() >= 500;
            boolean atTopBorder = playerCircle.getLayoutY() <= 4;

            if (atRightBorder || atLeftBorder)
                deltaX = -deltaX;
            if (atTopBorder)
                deltaY = -deltaY;
            if (test) {
                deltaY = -deltaY;
                scoreInt += 12;
            }
            if (atBottomBorder) {
                gameOver();
            }

            for(Rectangle testRect : rects) {
                boolean yy = ((playerCircle.getLayoutY() < testRect.getLayoutY() + playerCircle.getRadius() + testRect.getHeight()) && (playerCircle.getLayoutY() > testRect.getLayoutY() - playerCircle.getRadius()));
                boolean xx = ((playerCircle.getLayoutX() > (testRect.getLayoutX() - playerCircle.getRadius())) && (playerCircle.getLayoutX() < (testRect.getLayoutX() + testRect.getWidth() + playerCircle.getRadius())));
                boolean left = playerCircle.getLayoutX() == testRect.getLayoutX() + playerCircle.getRadius();
                boolean right = testRect.getLayoutX() + testRect.getWidth() + playerCircle.getRadius() - playerCircle.getLayoutX() == 0;
                boolean bottom = playerCircle.getLayoutY() == testRect.getLayoutY() + testRect.getHeight() + playerCircle.getRadius();
                boolean top = playerCircle.getLayoutY() == testRect.getLayoutY() + playerCircle.getRadius();

                if ((right || left) && yy)
                    deltaX = -deltaX;
                if (xx && (top || bottom))
                    deltaY = -deltaY;
            }
        }
    }));

    private void getBricks() {

        gpane.setHgap(3);
        gpane.setVgap(3);
        int num = 0;
        for(int i = 0; i < 10;i++){
            for(int j = 0; j < 8; j++){
                if(i<=1) {
                    Rectangle r = new Rectangle(65, 20, Color.RED);
                    r.setDisable(false);
                    rects.add(r);
                }
                else if(j==2)
                    rects.add(new Rectangle(65, 20, Color.YELLOW));
                else if(j==3)
                    rects.add(new Rectangle(65, 20, Color.DARKORANGE));
                else if(j==4)
                    rects.add(new Rectangle(65, 20, Color.CHOCOLATE));
                else if(j==5)
                    rects.add(new Rectangle(65, 20, Color.CRIMSON));
                else if(j==6)
                    rects.add(new Rectangle(65, 20, Color.DARKSEAGREEN));
                else if(j==7)
                    rects.add(new Rectangle(65, 20, Color.LIMEGREEN));
                else {
                    Rectangle r = new Rectangle(65, 20, Color.RED);
                    r.setDisable(true);
                    rects.add(r);
                }
                gpane.add(rects.get(num), i, j);
                System.out.println(i + " - " + j);
                num++;
            }
        }
        gameWindow.getChildren().addAll(gpane);
    }

    private void gameOver(){
        score.setText("YOU LOST");
        playerCircle.setVisible(false);
        loop.stop();
        clickToPlayLabel.setText("GAME OVER!");
    }

    public void setup(){
        gameWindow.setCursor(Cursor.NONE);
        clickToPlayLabel.setText("");
        getBricks();
        clickToPlay.setVisible(false);
        int diff = 1;
        setupBall(diff);
        playBall();
    }


}
