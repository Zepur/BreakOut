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
import java.util.Random;

public class Ball extends Circle {
    public Media boundS = new Media(getClass().getResource("plink.mp3").toString());
    public Media padS = new Media(getClass().getResource("plinklo.mp3").toString());
    public Media smashS = new Media(getClass().getResource("smash.mp3").toString());
    double xPos, yPos, speedY, speedX, initialSpeedY;
    static double radius = 8;
    static int speedRate;

    public Ball(Pane gameWindow, double startX, double startY, double speedX, double speedY, Paddle gamePaddle) {
        super(startX, startY, radius);
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.initialSpeedY = speedY;
        this.setFill(Color.LEMONCHIFFON);

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(60),
                e -> ballMovement(gameWindow, gamePaddle)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setRate(speedRate);
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
                Controller.stopTime = System.currentTimeMillis();
                System.out.println((Controller.stopTime-Controller.startTime)/1000);
                setCenterX(400-getRadius());
                setCenterY(380);
                speedY = initialSpeedY;
                Controller.betweenLVLs(gameWindow);
                Controller.isPlaying = false;
            } else {
                if (getCenterY() < 280 && getCenterY() > 45 && getCenterX() < 857 && getCenterX() > 55) {
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
                                if (brick.getFill() == Color.DARKBLUE) {
                                    brick.setFill(Color.RED);
                                    brick.setArcHeight(10);
                                    brick.setArcWidth(10);
                                } else if(brick.getFill() == Color.RED) {
                                    brick.setFill(Color.WHITE);
                                    brick.setArcHeight(20);
                                    brick.setArcWidth(20);
                                } else {
                                    Brick.bricks.remove(brick);
                                    Controller.bricksLeftLabel.setText(String.valueOf(Brick.bricks.size()));
                                    gameWindow.getChildren().removeAll(brick);
                                    if(gameWindow.getChildren().indexOf(Controller.powerUPpadSize) != 0){
                                        Random rand = new Random();
                                        int lucky = rand.nextInt(11);
                                        Controller.powerMeUp(lucky, gameWindow);
                                    }
                                }
                            }

                            if (xx && ((top && (speedY < 0)) || (bottom && (speedY > 0)))) {
                                noiceMaker(smashS);
                                speedY = -speedY;
                                if (brick.getFill() == Color.DARKBLUE) {
                                    brick.setFill(Color.RED);
                                    brick.setArcHeight(10);
                                    brick.setArcWidth(10);
                                } else if(brick.getFill() == Color.RED) {
                                    brick.setFill(Color.WHITE);
                                    brick.setArcHeight(20);
                                    brick.setArcWidth(20);
                                } else {
                                    Brick.bricks.remove(brick);
                                    Controller.bricksLeftLabel.setText(String.valueOf(Brick.bricks.size()));
                                    gameWindow.getChildren().removeAll(brick);
                                }
                            }
                        }
                    } catch (ConcurrentModificationException e) {
//                    e.printStackTrace();
                    }
                }
            }

            if (((getCenterY() >= 440) && (getCenterY() <= 450))){
                boolean xx = ((getCenterX() > (gamePaddle.getX() - getRadius())) && (getCenterX() < (gamePaddle.getX() + gamePaddle.getWidth() + getRadius())));
                boolean yy = ((getCenterY() < gamePaddle.getY() + getRadius() + gamePaddle.getHeight()) && (getCenterY() > gamePaddle.getY() - getRadius()));
                boolean left = (((gamePaddle.getX() - ballPosRIGHT) <= 1) && ((gamePaddle.getX() - ballPosRIGHT) >= -1));
                boolean right = (((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) <= 1) && ((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) >= -1));
                boolean bottom = (((getCenterY() - (gamePaddle.getY() + gamePaddle.getHeight() + getRadius())) <= 1) && ((getCenterY() - (gamePaddle.getY() + gamePaddle.getHeight() + getRadius())) >= -1));
                boolean top = (((ballPosDOWN - gamePaddle.getY()) <= 1) && ((ballPosDOWN - gamePaddle.getY()) >= -1));
                if (((right && (speedX < 0)) || (left && (speedX > 0))) && yy) {
                    noiceMaker(padS);
                    speedX = -speedX;
                }
                if (xx && ((top && (speedY < 0)) || (bottom && (speedY > 0)))) {
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
