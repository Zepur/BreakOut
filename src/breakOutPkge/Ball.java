package breakOutPkge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.ConcurrentModificationException;

public class Ball extends Circle {
    public Media boundS = new Media(getClass().getResource("plink.mp3").toString());
    public Media padS = new Media(getClass().getResource("plinklo.mp3").toString());
    public Media smashS = new Media(getClass().getResource("smash.mp3").toString());
    double xPos, yPos, speedY, speedX, initialSpeedY;
    static double radius = 8;

    public Ball(Pane gameWindow, double startX, double startY, double speedX, double speedY, Paddle gamePaddle) {
        super(startX, startY, radius);
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.initialSpeedY = speedY;
        this.setFill(Color.ORANGE);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(60),
                e -> ballMovement(gameWindow, gamePaddle)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setRate(7);
        animation.play();
    }

    private void ballMovement(Pane gameWindow, Paddle gamePaddle) {

        Controller.muteButton.setOnMousePressed(e -> Controller.muteUnmute());

        if(Controller.isPlaying){
            setCenterX(getCenterX() + speedX);
            setCenterY(getCenterY()-speedY);
            double ballPosLEFT = (getCenterX() - getRadius());
            double ballPosRIGHT = (getCenterX() + getRadius());
            double ballPosTOP = (getCenterY() - getRadius());
            double ballPosDOWN = (getCenterY() + getRadius());
            if(Brick.bricks.size()==0) {
                setCenterX(400-getRadius());
                setCenterY(380);
                speedY = initialSpeedY;
                Controller.betweenLVLs(gameWindow);
                Controller.isPlaying = false;
            } else {
                Controller.bricksLeft = Brick.bricks.size();
                if (getCenterY() < 270 && getCenterY() > 10 && getCenterX() < 747 && getCenterX() > 40) {
                    try {
                        for (Brick brick : Brick.bricks) {
                            boolean xx = ((getCenterX() > (brick.getLayoutX() - getRadius())) && (getCenterX() < (brick.getLayoutX() + brick.getWidth() + getRadius())));
                            boolean yy = ((getCenterY() < brick.getLayoutY() + getRadius() + brick.getHeight()) && (getCenterY() > brick.getLayoutY() - getRadius()));
                            boolean left = (((brick.getLayoutX() - ballPosRIGHT) <= 1) && ((brick.getLayoutX() - ballPosRIGHT) >= -1));
                            boolean right = (((ballPosLEFT - (brick.getWidth() + brick.getLayoutX())) <= 1) && ((ballPosLEFT - (brick.getWidth() + brick.getLayoutX())) >= -1));
                            boolean bottom = (((getCenterY() - (brick.getLayoutY() + brick.getHeight() + getRadius())) <= 1) && ((getCenterY() - (brick.getLayoutY() + brick.getHeight() + getRadius())) >= -1));
                            boolean top = (((ballPosDOWN - brick.getLayoutY()) <= 1) && ((ballPosDOWN - brick.getLayoutY()) >= -1));

                            if (((right && (speedX < 0)) || (left && (speedX > 0))) && yy) {
                                noiceMaker(smashS);
                                speedX = -speedX;
                                if (brick.getFill() == Color.DARKRED) {
                                    brick.setFill(Color.DARKGRAY);
                                } else {
                                    Brick.bricks.remove(brick);
                                    gameWindow.getChildren().remove(brick);
                                }
                            }
                            if (xx && ((top && (speedY < 0)) || (bottom && (speedY > 0)))) {
                                noiceMaker(smashS);
                                speedY = -speedY;
                                if (brick.getFill() == Color.DARKRED) {
                                    brick.setFill(Color.DARKGRAY);
                                } else {
                                    Brick.bricks.remove(brick);
                                    gameWindow.getChildren().remove(brick);
                                }
                            }
                        }
                    } catch (ConcurrentModificationException e) {
//                    e.printStackTrace();
                    }
                }
            }
            if (gamePaddle.collides(this)) {
                boolean xx = ((getCenterX() >= gamePaddle.getX()) && (getCenterX() <= (gamePaddle.getX() + gamePaddle.getWidth())));
                boolean left = (((gamePaddle.getX() - ballPosRIGHT) <= 1) && ((gamePaddle.getX() - ballPosRIGHT) >= -1));
                boolean right = (((getCenterX() - (getRadius() + gamePaddle.getWidth() + gamePaddle.getX())) <= 1) && ((getCenterX() - (getRadius() + gamePaddle.getWidth() + gamePaddle.getX())) >= -1));
                if ((right && (speedX < 0)) || (left && (speedX > 0))) {
                    noiceMaker(padS);
                    speedX = -speedX;
                }
                if (xx) {
                    noiceMaker(padS);
                    speedY = -speedY;
                }
            }

            if (ballPosDOWN >= 400 || ballPosLEFT <= 0 || ballPosTOP <= 0 || ballPosRIGHT >= (gameWindow.getWidth())) {
                boolean atRightBorder = ballPosRIGHT >= gameWindow.getWidth();
                boolean atLeftBorder = ballPosLEFT <= 1;
                boolean atBottomBorder = getCenterY() > (gamePaddle.getY() + gamePaddle.getHeight() + getRadius());
                boolean atTopBorder = ballPosTOP <= 1;

                if (atRightBorder || atLeftBorder) {
                    noiceMaker(boundS);
                    speedX = -speedX;
                }
                if (atTopBorder) {
                    noiceMaker(boundS);
                    speedY = -speedY;
                }
                if (atBottomBorder) {
                    if(Controller.lives>1) {
                        setCenterY(13);
                        speedY = 1;
                        Controller.lives--;
                        Controller.score.setText(String.valueOf(Controller.lives));
                    } else {
                        setCenterY(380);
                        setCenterX(400 - getRadius());
                        speedY = -speedY;
                        Controller.lives--;
                        Controller.score.setText(String.valueOf(Controller.lives));
                        Controller.isPlaying = false;
                        Controller.endGame(gameWindow);
                    }
                }
            }
        }
    }

    public static void noiceMaker(Media sound) {
        if (sound != null){
            Controller.jukeBox = new MediaPlayer(sound);
            Controller.jukeBox.setVolume((Controller.isMuted ? 0 : 100));
            Controller.jukeBox.play();
        }
    }

}
