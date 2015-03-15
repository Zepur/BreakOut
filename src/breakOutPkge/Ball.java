package breakOutPkge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ConcurrentModificationException;

public class Ball extends Circle {
    public Media boundS = new Media(getClass().getResource("plink.mp3").toString());
    public Media padS = new Media(getClass().getResource("plinklo.mp3").toString());
    public Media smashS = new Media(getClass().getResource("smash.mp3").toString());
    double xPos, yPos, speedY, speedX;
    static double radius = 8;

    public Ball(Pane gameWindow, double startX, double startY, double speedX, double speedY, Paddle gamePaddle) {
        super(startX, startY, radius);
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = speedX;
        this.speedY = speedY;
        Stop[] stops = {new Stop(0, Color.WHITE), new Stop(0.3, Color.PALEGOLDENROD), new Stop(0.5, Color.ORANGE), new Stop(1, Color.RED)};
        RadialGradient gradient = new RadialGradient(0, 0, 0, 0, 6, false, CycleMethod.NO_CYCLE, stops);
        this.setFill(Color.ORANGE);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(60),
                e -> ballMovement(gameWindow, gamePaddle)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setRate(7);
        animation.play();
    }

    private void ballMovement(Pane gameWindow, Paddle gamePaddle){
        xPos += speedX;
        yPos += -speedY;
        setCenterX(xPos);
        setCenterY(yPos);

        int ballPosLEFT = (int)(getCenterX() - getRadius());
        int ballPosRIGHT = (int)(getCenterX() + getRadius());
        int ballPosTOP = (int)(getCenterY() - getRadius());
        int ballPosDOWN = (int)(getCenterY() + getRadius());
        if(getCenterY() < 500 && getCenterY() > 10 && getCenterX() < 740 && getCenterX() > 20){
            try {
                for (Brick brick : Brick.bricks) {
                    boolean xx = ((getCenterX() > (brick.getLayoutX() - getRadius())) && (getCenterX() < (brick.getLayoutX() + brick.getWidth() + getRadius())));
                    boolean yy = ((getCenterY() < brick.getLayoutY() + getRadius() + brick.getHeight()) && (getCenterY() > brick.getLayoutY() - getRadius()));
                    boolean left = (((brick.getLayoutX() - ballPosRIGHT) <= 1) && ((brick.getLayoutX() - ballPosRIGHT) >= -1));
                    boolean right = (((getCenterX() - (getRadius() + brick.getWidth() + brick.getLayoutX())) <= 1) && ((getCenterX() - (getRadius() + brick.getWidth() + brick.getLayoutX())) >= -1));
                    boolean bottom = (((getCenterY() - (brick.getLayoutY() + brick.getHeight() + getRadius())) <= 2) && ((getCenterY() - (brick.getLayoutY() + brick.getHeight() + getRadius())) >= -2));
                    boolean top = (((ballPosDOWN - brick.getLayoutY()) <= 2 ) && ((ballPosDOWN - brick.getLayoutY()) >= -2 ));

                    if ((right || left) && yy) {
                        noiceMaker(smashS);
                        speedX = -speedX;
                        if(brick.getFill() == Color.DARKGRAY){
                            Brick.bricks.remove(brick);
                            gameWindow.getChildren().remove(brick);
                        } else
                            brick.setFill(Color.DARKGRAY);
                    }
                    if (xx && (top || bottom)) {
                        noiceMaker(smashS);
                        speedY = -speedY;
                        if(brick.getFill() == Color.DARKGRAY){
                            Brick.bricks.remove(brick);
                            gameWindow.getChildren().remove(brick);
                        } else
                            brick.setFill(Color.DARKGRAY);
                    }

                }
            } catch (ConcurrentModificationException e) {
//                    e.printStackTrace();
            }
        }
        if (gamePaddle.collides(this)){
            noiceMaker(padS);
            speedY = -speedY;
        }

        if(ballPosDOWN >= 437 || ballPosLEFT <= 0 || ballPosTOP <= 0 || ballPosRIGHT >= (gameWindow.getWidth())) {
            boolean atRightBorder = ballPosRIGHT >= gameWindow.getWidth();
            boolean atLeftBorder = ballPosLEFT == 0;
            boolean atBottomBorder = (int)(getLayoutY() + (2*getRadius())) == gameWindow.getHeight();
            boolean atTopBorder = ballPosTOP == 0;

            if (atRightBorder || atLeftBorder) {
                noiceMaker(boundS);
                speedX = -speedX;
            }
            if (atTopBorder) {
                noiceMaker(boundS);
                speedY = -speedY;
            }
            if (atBottomBorder) {
                noiceMaker(boundS);
            }
        }
    }

    public static void noiceMaker(Media sound) {
        if (sound != null){
            MediaPlayer playSound = new MediaPlayer(sound);
            playSound.setVolume(Controller.isMuted? 0 : 70);
            playSound.play();
        }
    }

}
