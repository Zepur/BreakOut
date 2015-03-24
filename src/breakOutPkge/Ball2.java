package breakOutPkge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Ball2 extends Circle {
    public final Media BOUNDS_SOUND = new Media(getClass().getResource("plink.mp3").toString());
    public final Media PAD_SOUND = new Media(getClass().getResource("plinklo.mp3").toString());
    public final Media SMASH_SOUND = new Media(getClass().getResource("smash.mp3").toString());
    double xPos, yPos, speedY, speedX, initialSpeedY, initialSpeedX;
    static final double RADIUS = 8;
    static int speedRate;
    int updateRemains = 0;
    public static int ballsLeft;
    int[] gridX = {60, 113, 166, 219, 272, 325, 378, 431, 484, 537, 590, 643, 696, 749, 802};
    int[] gridY = {50, 73, 96, 119, 142, 165, 188, 211, 234, 257};
    int yVERT = 0;
    int yHOR = 0;
    int xVERT = 0;
    int xHOR = 0;
    public static Timeline animation = new Timeline();

    public Ball2(Pane gameWindow, double startX, double startY, Paddle gamePaddle) {
        super(startX, startY, RADIUS);
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = 1.1;
        this.speedY = 1.3;
        initialSpeedY = 1;
        initialSpeedX = 1;
        this.setFill(Color.web("0x009FFF"));

        animation = new Timeline(new KeyFrame(Duration.millis(60),
                e -> ballMovement(gameWindow, gamePaddle)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setRate(speedRate);
        animation.play();
    }

    private void ballMovement(Pane gameWindow, Paddle gamePaddle) {
        if(Controller.isPlaying){
            updateRemains++;

            if(updateRemains == 15) {
                Controller.bricksLeftLabel.setText(String.valueOf(ballsLeft));
                updateRemains = 0;
            }

            setCenterX(getCenterX() + speedX);
            setCenterY(getCenterY() - speedY);
            double ballPosLEFT = (getCenterX() - getRadius());
            double ballPosRIGHT = (getCenterX() + getRadius());
            double ballPosTOP = (getCenterY() - getRadius());
            double ballPosDOWN = (getCenterY() + getRadius());

            if(Brick.bricks.size()==0) {
                System.out.println("OKKOK WONNERED!!!");
                Controller.stopTime = System.currentTimeMillis();
                System.out.println((Controller.stopTime - Controller.startTime) / 1000);
                setCenterX(400 - getRadius());
                setCenterY(380);
                Controller.betweenLVLs(gameWindow);
                Controller.isPlaying = false;
            } else {
                if        (((getCenterY() + getRadius() >= gridY[0])  && (getCenterY() + getRadius() <= gridY[0] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[0])  && (getCenterY() - getRadius() <= gridY[0] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 1;
                else if   (((getCenterY() + getRadius() >= gridY[1])  && (getCenterY() + getRadius() <= gridY[1] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[1])  && (getCenterY() - getRadius() <= gridY[1] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 2;
                else if   (((getCenterY() + getRadius() >= gridY[2])  && (getCenterY() + getRadius() <= gridY[2] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[2])  && (getCenterY() - getRadius() <= gridY[2] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 3;
                else if   (((getCenterY() + getRadius() >= gridY[3])  && (getCenterY() + getRadius() <= gridY[3] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[3])  && (getCenterY() - getRadius() <= gridY[3] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 4;
                else if   (((getCenterY() + getRadius() >= gridY[4])  && (getCenterY() + getRadius() <= gridY[4] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[4])  && (getCenterY() - getRadius() <= gridY[4] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 5;
                else if   (((getCenterY() + getRadius() >= gridY[5])  && (getCenterY() + getRadius() <= gridY[5] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[5])  && (getCenterY() - getRadius() <= gridY[5] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 6;
                else if   (((getCenterY() + getRadius() >= gridY[6])  && (getCenterY() + getRadius() <= gridY[6] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[6])  && (getCenterY() - getRadius() <= gridY[6] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 7;
                else if   (((getCenterY() + getRadius() >= gridY[7])  && (getCenterY() + getRadius() <= gridY[7] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[7])  && (getCenterY() - getRadius() <= gridY[7] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 8;
                else if   (((getCenterY() + getRadius() >= gridY[8])  && (getCenterY() + getRadius() <= gridY[8] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[8])  && (getCenterY() - getRadius() <= gridY[8] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 9;
                else if   (((getCenterY() + getRadius() >= gridY[9])  && (getCenterY() + getRadius() <= gridY[9] + Brick.BRICK_HEIGHT) && (speedY < 0))
                        || ((getCenterY() - getRadius() >= gridY[9])  && (getCenterY() - getRadius() <= gridY[9] + Brick.BRICK_HEIGHT) && (speedY > 0)))
                    yVERT = 10;
                else
                    yVERT = 0;


                if      (((getCenterY()+getRadius() >= gridY[0] + 8)) && ((getCenterY() + getRadius() <= gridY[0] + 8 + Brick.BRICK_HEIGHT))) yHOR = 1;
                else if (((getCenterY()+getRadius() >= gridY[1] + 8)) && ((getCenterY() + getRadius() <= gridY[1] + 8 + Brick.BRICK_HEIGHT))) yHOR = 2;
                else if (((getCenterY()+getRadius() >= gridY[2] + 8)) && ((getCenterY() + getRadius() <= gridY[2] + 8 + Brick.BRICK_HEIGHT))) yHOR = 3;
                else if (((getCenterY()+getRadius() >= gridY[3] + 8)) && ((getCenterY() + getRadius() <= gridY[3] + 8 + Brick.BRICK_HEIGHT))) yHOR = 4;
                else if (((getCenterY()+getRadius() >= gridY[4] + 8)) && ((getCenterY() + getRadius() <= gridY[4] + 8 + Brick.BRICK_HEIGHT))) yHOR = 5;
                else if (((getCenterY()+getRadius() >= gridY[5] + 8)) && ((getCenterY() + getRadius() <= gridY[5] + 8 + Brick.BRICK_HEIGHT))) yHOR = 6;
                else if (((getCenterY()+getRadius() >= gridY[6] + 8)) && ((getCenterY() + getRadius() <= gridY[6] + 8 + Brick.BRICK_HEIGHT))) yHOR = 7;
                else if (((getCenterY()+getRadius() >= gridY[7] + 8)) && ((getCenterY() + getRadius() <= gridY[7] + 8 + Brick.BRICK_HEIGHT))) yHOR = 8;
                else if (((getCenterY()+getRadius() >= gridY[8] + 8)) && ((getCenterY() + getRadius() <= gridY[8] + 8 + Brick.BRICK_HEIGHT))) yHOR = 9;
                else if (((getCenterY()+getRadius() >= gridY[9] + 8)) && ((getCenterY() + getRadius() <= gridY[9] + 8 + Brick.BRICK_HEIGHT))) yHOR = 10;
                else yHOR = 0;


                if        (((getCenterX() + getRadius() >= gridX[0])  && (getCenterX() + getRadius() <= gridX[0] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[0])  && (getCenterX() - getRadius() <= gridX[0] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 1;
                else if   (((getCenterX() + getRadius() >= gridX[1])  && (getCenterX() + getRadius() <= gridX[1] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[1])  && (getCenterX() - getRadius() <= gridX[1] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 2;
                else if   (((getCenterX() + getRadius() >= gridX[2])  && (getCenterX() + getRadius() <= gridX[2] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[2])  && (getCenterX() - getRadius() <= gridX[2] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 3;
                else if   (((getCenterX() + getRadius() >= gridX[3])  && (getCenterX() + getRadius() <= gridX[3] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[3])  && (getCenterX() - getRadius() <= gridX[3] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 4;
                else if   (((getCenterX() + getRadius() >= gridX[4])  && (getCenterX() + getRadius() <= gridX[4]  + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[4])  && (getCenterX() - getRadius() <= gridX[4] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 5;
                else if   (((getCenterX() + getRadius() >= gridX[5])  && (getCenterX() + getRadius() <= gridX[5] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[5])  && (getCenterX() - getRadius() <= gridX[5] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 6;
                else if   (((getCenterX() + getRadius() >= gridX[6])  && (getCenterX() + getRadius() <= gridX[6] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[6])  && (getCenterX() - getRadius() <= gridX[6] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 7;
                else if   (((getCenterX() + getRadius() >= gridX[7])  && (getCenterX() + getRadius() <= gridX[7] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[7])  && (getCenterX() - getRadius() <= gridX[7] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 8;
                else if   (((getCenterX() + getRadius() >= gridX[8])  && (getCenterX() + getRadius() <= gridX[8] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[8])  && (getCenterX() - getRadius() <= gridX[8] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 9;
                else if   (((getCenterX() + getRadius() >= gridX[9])  && (getCenterX() + getRadius() <= gridX[9] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[9])  && (getCenterX() - getRadius() <= gridX[9] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 10;
                else if   (((getCenterX() + getRadius() >= gridX[10]) && (getCenterX() + getRadius() <= gridX[10] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[10]) && (getCenterX() - getRadius() <= gridX[10] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 11;
                else if   (((getCenterX() + getRadius() >= gridX[11]) && (getCenterX() + getRadius() <= gridX[11] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[11]) && (getCenterX() - getRadius() <= gridX[11] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 12;
                else if   (((getCenterX() + getRadius() >= gridX[12]) && (getCenterX() + getRadius() <= gridX[12] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[12]) && (getCenterX() - getRadius() <= gridX[12] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 13;
                else if   (((getCenterX() + getRadius() >= gridX[13]) && (getCenterX() + getRadius() <= gridX[13] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[13]) && (getCenterX() - getRadius() <= gridX[13] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 14;
                else if   (((getCenterX() + getRadius() >= gridX[14]) && (getCenterX() + getRadius() <= gridX[14] + Brick.BRICK_WIDTH) && (speedX > 0))
                        || ((getCenterX() - getRadius() >= gridX[14]) && (getCenterX() - getRadius() <= gridX[14] + Brick.BRICK_WIDTH) && (speedX < 0)))
                    xHOR = 15;
                else
                    xHOR = 0;


                if      (((getCenterX() >= gridX[0])) && ((getCenterX() <= gridX[0] + Brick.BRICK_WIDTH)))  xVERT = 1;
                else if (((getCenterX() >= gridX[1])) && ((getCenterX() <= gridX[1] + Brick.BRICK_WIDTH)))  xVERT = 2;
                else if (((getCenterX() >= gridX[2])) && ((getCenterX() <= gridX[2] + Brick.BRICK_WIDTH)))  xVERT = 3;
                else if (((getCenterX() >= gridX[3])) && ((getCenterX() <= gridX[3] + Brick.BRICK_WIDTH)))  xVERT = 4;
                else if (((getCenterX() >= gridX[4])) && ((getCenterX() <= gridX[4] + Brick.BRICK_WIDTH)))  xVERT = 5;
                else if (((getCenterX() >= gridX[5])) && ((getCenterX() <= gridX[5] + Brick.BRICK_WIDTH)))  xVERT = 6;
                else if (((getCenterX() >= gridX[6])) && ((getCenterX() <= gridX[6] + Brick.BRICK_WIDTH)))  xVERT = 7;
                else if (((getCenterX() >= gridX[7])) && ((getCenterX() <= gridX[7] + Brick.BRICK_WIDTH)))  xVERT = 8;
                else if (((getCenterX() >= gridX[8])) && ((getCenterX() <= gridX[8] + Brick.BRICK_WIDTH)))  xVERT = 9;
                else if (((getCenterX() >= gridX[9])) && ((getCenterX() <= gridX[9] + Brick.BRICK_WIDTH)))  xVERT = 10;
                else if (((getCenterX() >= gridX[10])) && ((getCenterX() <= gridX[10] + Brick.BRICK_WIDTH))) xVERT = 11;
                else if (((getCenterX() >= gridX[11])) && ((getCenterX() <= gridX[11] + Brick.BRICK_WIDTH))) xVERT = 12;
                else if (((getCenterX() >= gridX[12])) && ((getCenterX() <= gridX[12] + Brick.BRICK_WIDTH))) xVERT = 13;
                else if (((getCenterX() >= gridX[13])) && ((getCenterX() <= gridX[13] + Brick.BRICK_WIDTH))) xVERT = 14;
                else if (((getCenterX() >= gridX[14])) && ((getCenterX() <= gridX[14] + Brick.BRICK_WIDTH))) xVERT = 15;
                else xVERT = 0;
            }

            if (yVERT >= 1 && xVERT >= 1) {
                if(Brick.bricks.get(((15 * (yVERT - 1)) + (xVERT - 1))) != null) {
                    noiceMaker(SMASH_SOUND);
                    gameWindow.getChildren().remove(Brick.bricks.get(((15 * (yVERT - 1)) + (xVERT - 1))));
                    Brick.bricks.set(((15 * (yVERT - 1)) + (xVERT - 1)), null);
                    speedY = -speedY;
                }
            }

            if (yHOR >= 1 && xHOR >= 1) {
                if(Brick.bricks.get(((15 * (yHOR - 1)) + (xHOR - 1))) != null) {
                    noiceMaker(SMASH_SOUND);
                    gameWindow.getChildren().remove(Brick.bricks.get(((15 * (yHOR - 1)) + (xHOR - 1))));
                    Brick.bricks.set(((15 * (yHOR - 1)) + (xHOR - 1)), null);
                    speedX = -speedX;
                }
            }

            if (((getCenterY() >= gamePaddle.getY()-getRadius()) && (getCenterY() <= 520))){
                boolean xx = ((getCenterX() > (gamePaddle.getX() - getRadius())) && (getCenterX() < (gamePaddle.getX() + gamePaddle.getWidth() + getRadius())));
                boolean yy = ((getCenterY() < gamePaddle.getY() + getRadius() + gamePaddle.getHeight()) && (getCenterY() > gamePaddle.getY() - getRadius()));
                boolean left = (((gamePaddle.getX() - ballPosRIGHT) <= 1) && ((gamePaddle.getX() - ballPosRIGHT) >= -1));
                boolean right = (((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) <= 1) && ((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) >= -1));
                boolean top = (ballPosDOWN >= gamePaddle.getY());
                if (((right && (speedX < 0)) || (left && (speedX > 0))) && yy) {
                    noiceMaker(PAD_SOUND);
                    speedX = -speedX;
                }
                if (xx && (top && (speedY < 0))) {
                    noiceMaker(PAD_SOUND);
                    speedY = -speedY;
                }
            }

            if (ballPosDOWN >= 530 || ballPosLEFT <= 0 || ballPosTOP <= 0 || ballPosRIGHT >= (gameWindow.getWidth())) {
                boolean atRightBorder = ballPosRIGHT >= gameWindow.getWidth();
                boolean atLeftBorder = ballPosLEFT <= 1;
                boolean atBottomBorder = getCenterY() > 555;
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
                        Controller.isPaused = true;
                        setCenterY(450);
                        speedY = -1;
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
