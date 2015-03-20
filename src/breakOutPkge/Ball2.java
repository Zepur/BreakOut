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

public class Ball2 extends Circle {
    public final Media BOUNDS_SOUND = new Media(getClass().getResource("plink.mp3").toString());
    public final Media PAD_SOUND = new Media(getClass().getResource("plinklo.mp3").toString());
    public final Media SMASH_SOUND = new Media(getClass().getResource("smash.mp3").toString());
    double xPos, yPos, speedY, speedX, initialSpeedY;
    static double radius = 8;
    static int speedRate;

    public Ball2(Pane gameWindow, double startX, double startY, double speedX, double speedY, Paddle gamePaddle) {
        super(startX, startY, radius);
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = speedX;
        this.speedY = speedY;
        this.initialSpeedY = speedY;
        this.setFill(Color.LEMONCHIFFON);
        int gridX1 = 60;
        int gridX2 = 113;
        int gridX3 = 166;
        int gridX4 = 166;
        int gridX5 = 219;
        int gridX6 = 272;
        int gridX7 = 325;
        int gridX8 = 378;
        int gridX9 = 431;
        int gridX10 = 484;
        int gridX11 = 537;
        int gridX12 = 590;
        int gridX13 = 643;
        int gridX14 = 696;
        int gridX15 = 749;
        int gridX16 = 802;

        Timeline animation = new Timeline(new KeyFrame(Duration.millis(60),
                e -> ballMovement(gameWindow, gamePaddle)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setRate(speedRate);
        animation.play();
    }

    private void ballMovement(Pane gameWindow, Paddle gamePaddle) {
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
                if(getCenterX() == 60)
                    System.out.println("ok");
            }

            if (((getCenterY() >= 440) && (getCenterY() <= 450))){
                boolean xx = ((getCenterX() > (gamePaddle.getX() - getRadius())) && (getCenterX() < (gamePaddle.getX() + gamePaddle.getWidth() + getRadius())));
                boolean yy = ((getCenterY() < gamePaddle.getY() + getRadius() + gamePaddle.getHeight()) && (getCenterY() > gamePaddle.getY() - getRadius()));
                boolean left = (((gamePaddle.getX() - ballPosRIGHT) <= 1) && ((gamePaddle.getX() - ballPosRIGHT) >= -1));
                boolean right = (((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) <= 1) && ((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) >= -1));
                boolean bottom = (((getCenterY() - (gamePaddle.getY() + gamePaddle.getHeight() + getRadius())) <= 1) && ((getCenterY() - (gamePaddle.getY() + gamePaddle.getHeight() + getRadius())) >= -1));
                boolean top = (((ballPosDOWN - gamePaddle.getY()) <= 1) && ((ballPosDOWN - gamePaddle.getY()) >= -1));
                if (((right && (speedX < 0)) || (left && (speedX > 0))) && yy) {
                    noiceMaker(PAD_SOUND);
                    speedX = -speedX;
                }
                if (xx && ((top && (speedY < 0)) || (bottom && (speedY > 0)))) {
                    noiceMaker(PAD_SOUND);
                    speedY = -speedY;
                }
            }

            if (ballPosDOWN >= 400 || ballPosLEFT <= 0 || ballPosTOP <= 0 || ballPosRIGHT >= (gameWindow.getWidth())) {
                boolean atRightBorder = ballPosRIGHT >= gameWindow.getWidth();
                boolean atLeftBorder = ballPosLEFT <= 1;
                boolean atBottomBorder = getCenterY() > (gamePaddle.getY() + gamePaddle.getHeight() + getRadius());
                boolean atTopBorder = ballPosTOP <= 1;
                if (atRightBorder || atLeftBorder) {
                    noiceMaker(BOUNDS_SOUND);
                    speedX = -speedX;
                }
                if (atTopBorder) {
                    noiceMaker(BOUNDS_SOUND);
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
