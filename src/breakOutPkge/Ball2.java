package breakOutPkge;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Random;

public class Ball2 extends Circle {
    public final Media BOUNDS_SOUND = new Media(getClass().getResource("plink.mp3").toString());
    public final Media PAD_SOUND = new Media(getClass().getResource("plinklo.mp3").toString());
    public final Media SMASH_SOUND = new Media(getClass().getResource("smash.mp3").toString());
    double xPos, yPos, speedY, speedX;
    public static double initialXspeed, initialYspeed;
    static final double RADIUS = 8;
    static int speedRate;
    int updateRemains = 0;
    public static int powerUpTimer = 0;
    public static int bricksLeft;
    int[] gridX = {60, 113, 166, 219, 272, 325, 378, 431, 484, 537, 590, 643, 696, 749, 802};
    int[] gridY = {50, 73, 96, 119, 142, 165, 188, 211, 234, 257};
    int yVERT = 0;
    int yHOR = 0;
    int xVERT = 0;
    int xHOR = 0;
    public static Timeline animation = new Timeline();

    public Ball2(Pane gameWindow, double startX, double startY, double speed_X, double speed_Y, Paddle gamePaddle) {
        super(startX, startY, RADIUS);
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = speed_X;
        this.speedY = speed_Y;
        initialXspeed = speed_X;
        initialYspeed = speed_Y;
        this.setFill(Color.web("0x009FFF"));

        animation = new Timeline(new KeyFrame(Duration.millis(60), e -> ballMovement(gameWindow, gamePaddle)));
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.setRate(speedRate);
        animation.play();
    }

    private void ballMovement(Pane gameWindow, Paddle gamePaddle) {
        if(Controller.isPlaying) {
            updateRemains++;
            if(powerUpTimer>0) powerUpTimer++;

            if(powerUpTimer == 2200){
                gamePaddle.setFill(Color.GOLDENROD);
                gamePaddle.setWidth(70);
                Controller.powerUP = false;
            }

            if(updateRemains == 15) {
                Controller.bricksLeftLabel.setText(String.valueOf(bricksLeft));
                updateRemains = 0;
                long seconds = (((System.currentTimeMillis()-Controller.startTime)- Controller.pauseDuration)/1000)%60;
                long minutes = ((((System.currentTimeMillis()-Controller.startTime)- Controller.pauseDuration)-seconds)/1000)/60;
                boolean shouldDisplayZero = (minutes < 0) && (seconds > 10);
                Controller.timeElapsedLabel.setText((minutes > 0 ? String.valueOf(minutes+" min  ") : "")+(shouldDisplayZero ? "0"+seconds : seconds)+" sec");
            }

            setCenterX(getCenterX() + speedX);
            setCenterY(getCenterY() - speedY);

            double ballPosLEFT  = (getCenterX() - getRadius());
            double ballPosRIGHT = (getCenterX() + getRadius());
            double ballPosTOP   = (getCenterY() - getRadius());
            double ballPosDOWN  = (getCenterY() + getRadius());

            if(bricksLeft == 0) {
                Controller.bricksLeftLabel.setText("0");
                Controller.stopTime = System.currentTimeMillis();
                System.out.println("time used: " +(Controller.stopTime - Controller.startTime) / 1000);
                setCenterX(400 - getRadius());
                setCenterY(380);
                Controller.isPlaying = false;
                Controller.betweenLVLs(gameWindow);
            } else {

                if ((ballAtBrickYUp(gridY[0]) && ballMovingDown()) || (ballAtBrickDown(gridY[0]) && ballMovingUp())) yVERT = 1;
                if ((ballAtBrickYUp(gridY[1]) && ballMovingDown()) || (ballAtBrickDown(gridY[1]) && ballMovingUp())) yVERT = 2;
                if ((ballAtBrickYUp(gridY[2]) && ballMovingDown()) || (ballAtBrickDown(gridY[2]) && ballMovingUp())) yVERT = 3;
                if ((ballAtBrickYUp(gridY[3]) && ballMovingDown()) || (ballAtBrickDown(gridY[3]) && ballMovingUp())) yVERT = 4;
                if ((ballAtBrickYUp(gridY[4]) && ballMovingDown()) || (ballAtBrickDown(gridY[4]) && ballMovingUp())) yVERT = 5;
                if ((ballAtBrickYUp(gridY[5]) && ballMovingDown()) || (ballAtBrickDown(gridY[5]) && ballMovingUp())) yVERT = 6;
                if ((ballAtBrickYUp(gridY[6]) && ballMovingDown()) || (ballAtBrickDown(gridY[6]) && ballMovingUp())) yVERT = 7;
                if ((ballAtBrickYUp(gridY[7]) && ballMovingDown()) || (ballAtBrickDown(gridY[7]) && ballMovingUp())) yVERT = 8;
                if ((ballAtBrickYUp(gridY[8]) && ballMovingDown()) || (ballAtBrickDown(gridY[8]) && ballMovingUp())) yVERT = 9;
                if ((ballAtBrickYUp(gridY[9]) && ballMovingDown()) || (ballAtBrickDown(gridY[9]) && ballMovingUp())) yVERT = 10;
                if ((ballOutsideBrickY()      && ballMovingDown()) || (ballOutsideBrickY()       && ballMovingUp())) yVERT = 0;

                if (ballAtBrickYHorizontally(gridY[0])) yHOR = 1;
                if (ballAtBrickYHorizontally(gridY[1])) yHOR = 2;
                if (ballAtBrickYHorizontally(gridY[2])) yHOR = 3;
                if (ballAtBrickYHorizontally(gridY[3])) yHOR = 4;
                if (ballAtBrickYHorizontally(gridY[4])) yHOR = 5;
                if (ballAtBrickYHorizontally(gridY[5])) yHOR = 6;
                if (ballAtBrickYHorizontally(gridY[6])) yHOR = 7;
                if (ballAtBrickYHorizontally(gridY[7])) yHOR = 8;
                if (ballAtBrickYHorizontally(gridY[8])) yHOR = 9;
                if (ballAtBrickYHorizontally(gridY[9])) yHOR = 10;
                if (ballOutsideBrickYHorizontally())    yHOR = 0;

                if ((ballAtBrickXRight(gridX[0])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[0])  && ballMovingLeft())) xHOR = 1;
                if ((ballAtBrickXRight(gridX[1])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[1])  && ballMovingLeft())) xHOR = 2;
                if ((ballAtBrickXRight(gridX[2])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[2])  && ballMovingLeft())) xHOR = 3;
                if ((ballAtBrickXRight(gridX[3])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[3])  && ballMovingLeft())) xHOR = 4;
                if ((ballAtBrickXRight(gridX[4])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[4])  && ballMovingLeft())) xHOR = 5;
                if ((ballAtBrickXRight(gridX[5])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[5])  && ballMovingLeft())) xHOR = 6;
                if ((ballAtBrickXRight(gridX[6])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[6])  && ballMovingLeft())) xHOR = 7;
                if ((ballAtBrickXRight(gridX[7])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[7])  && ballMovingLeft())) xHOR = 8;
                if ((ballAtBrickXRight(gridX[8])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[8])  && ballMovingLeft())) xHOR = 9;
                if ((ballAtBrickXRight(gridX[9])  && ballMovingRight()) || (ballAtBrickXLeft(gridX[9])  && ballMovingLeft())) xHOR = 10;
                if ((ballAtBrickXRight(gridX[10]) && ballMovingRight()) || (ballAtBrickXLeft(gridX[10]) && ballMovingLeft())) xHOR = 11;
                if ((ballAtBrickXRight(gridX[11]) && ballMovingRight()) || (ballAtBrickXLeft(gridX[11]) && ballMovingLeft())) xHOR = 12;
                if ((ballAtBrickXRight(gridX[12]) && ballMovingRight()) || (ballAtBrickXLeft(gridX[12]) && ballMovingLeft())) xHOR = 13;
                if ((ballAtBrickXRight(gridX[13]) && ballMovingRight()) || (ballAtBrickXLeft(gridX[13]) && ballMovingLeft())) xHOR = 14;
                if ((ballAtBrickXRight(gridX[14]) && ballMovingRight()) || (ballAtBrickXLeft(gridX[14]) && ballMovingLeft())) xHOR = 15;
                if (ballOutsideBrickX()) xHOR = 0;

                if (ballAtBrickXVertically(gridX[0]))  xVERT = 1;
                if (ballAtBrickXVertically(gridX[1]))  xVERT = 2;
                if (ballAtBrickXVertically(gridX[2]))  xVERT = 3;
                if (ballAtBrickXVertically(gridX[3]))  xVERT = 4;
                if (ballAtBrickXVertically(gridX[4]))  xVERT = 5;
                if (ballAtBrickXVertically(gridX[5]))  xVERT = 6;
                if (ballAtBrickXVertically(gridX[6]))  xVERT = 7;
                if (ballAtBrickXVertically(gridX[7]))  xVERT = 8;
                if (ballAtBrickXVertically(gridX[8]))  xVERT = 9;
                if (ballAtBrickXVertically(gridX[9]))  xVERT = 10;
                if (ballAtBrickXVertically(gridX[10])) xVERT = 11;
                if (ballAtBrickXVertically(gridX[11])) xVERT = 12;
                if (ballAtBrickXVertically(gridX[12])) xVERT = 13;
                if (ballAtBrickXVertically(gridX[13])) xVERT = 14;
                if (ballAtBrickXVertically(gridX[14])) xVERT = 15;
                if (ballOutsideBrickXVertically())     xVERT = 0;
            }
            
            if (yHOR >= 1 && xHOR >= 1) {
                if(Brick.bricks.get(((15 * (yHOR - 1)) + (xHOR - 1))) != null) {
                    noiceMaker(SMASH_SOUND);
                    gameWindow.getChildren().remove(Brick.bricks.get(((15 * (yHOR - 1)) + (xHOR - 1))));
                    Brick.bricks.set(((15 * (yHOR - 1)) + (xHOR - 1)), null);
                    bricksLeft--;
                    speedX = -speedX;
                    Random rand = new Random();
                    int lucky = rand.nextInt(20);
                    Controller.powerMeUp(lucky, gameWindow);
                }
            }

            if (yVERT >= 1 && xVERT >= 1) {
                if(Brick.bricks.get(((15 * (yVERT - 1)) + (xVERT - 1))) != null) {
                    noiceMaker(SMASH_SOUND);
                    gameWindow.getChildren().remove(Brick.bricks.get(((15 * (yVERT - 1)) + (xVERT - 1))));
                    Brick.bricks.set(((15 * (yVERT - 1)) + (xVERT - 1)), null);
                    bricksLeft--;
                    speedY = -speedY;
                    Random rand = new Random();
                    int lucky = rand.nextInt(4);
                    Controller.powerMeUp(lucky, gameWindow);
                }
            }

            if (((getCenterY() >= gamePaddle.getY()-getRadius()) && (getCenterY() <= 520))){
                boolean xx = ((getCenterX() > (gamePaddle.getX() - getRadius())) && (getCenterX() < (gamePaddle.getX() + gamePaddle.getWidth() + getRadius())));
                boolean yy = ((getCenterY() < gamePaddle.getY() + getRadius() + gamePaddle.getHeight()) && (getCenterY() > gamePaddle.getY() - getRadius()));
                boolean left = (((gamePaddle.getX() - ballPosRIGHT) <= 1) && ((gamePaddle.getX() - ballPosRIGHT) >= -1));
                boolean right = (((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) <= 1) && ((ballPosLEFT - (gamePaddle.getWidth() + gamePaddle.getX())) >= -1));
                boolean top = (ballPosDOWN >= gamePaddle.getY());
                if (((right && ballMovingLeft()) || (left && ballMovingRight())) && yy) {
                    noiceMaker(PAD_SOUND);
                    speedX = -speedX;
                }
                if (xx && (top && ballMovingDown())) {
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
                        setCenterY(420);
                        setCenterX(420);
                        speedX = initialXspeed;
                        speedY = initialYspeed;
                        Controller.lives--;
                        Controller.ballsRemainginLabel.setText(String.valueOf(Controller.lives));
                        Controller.isPaused = true;
                        Controller.pause(gameWindow);
                    } else {
                        setCenterY(380);
                        setCenterX(400 - getRadius());
                        speedY = -speedY;
                        Controller.lives--;
                        Controller.ballsRemainginLabel.setText(String.valueOf(Controller.lives));
                        Controller.isPlaying = false;
                        Controller.endGame(gameWindow);
                        gameWindow.getChildren().removeAll(Controller.ball);
                        animation.stop();
                    }
                }
            }
        }
    }

    private boolean ballOutsideBrickY() {           return (((getCenterY() + getRadius()) < gridY[0]) || ((getCenterY() - getRadius()) > (gridY[9] + Brick.BRICK_HEIGHT)));}
    private boolean ballOutsideBrickYHorizontally(){return ((getCenterY() < gridY[0]) || (getCenterY() > (gridY[9] + Brick.BRICK_HEIGHT))); }
    private boolean ballOutsideBrickXVertically() { return ((getCenterX() < gridX[0]) || (getCenterX() > (gridX[14] + Brick.BRICK_WIDTH))); }
    private boolean ballOutsideBrickX() {           return (((getCenterX() + getRadius()) < gridX[0]) || ((getCenterX() - getRadius()) > (gridX[14] + Brick.BRICK_WIDTH))); }
    
    private boolean ballAtBrickXRight(int i) {      return ((getCenterX() + getRadius() >= i)  && (getCenterX() + getRadius() <= i + Brick.BRICK_WIDTH)); }
    private boolean ballAtBrickXLeft(int i) {       return ((getCenterX() - getRadius() >= i)  && (getCenterX() - getRadius() <= i + Brick.BRICK_WIDTH)); }
    private boolean ballAtBrickXVertically(int i) { return ((getCenterX() >= i)  && ((getCenterX() <= i  + Brick.BRICK_WIDTH)));}
    private boolean ballAtBrickYHorizontally(int i){return ((getCenterY() >= i)  && (getCenterY() <= i + Brick.BRICK_HEIGHT)); }
    private boolean ballAtBrickYUp(int i){          return ((getCenterY() + getRadius() >= i)  && (getCenterY() + getRadius() <= i + Brick.BRICK_HEIGHT)); }
    private boolean ballAtBrickDown(int i){         return ((getCenterY() - getRadius() >= i)  && (getCenterY() - getRadius() <= i + Brick.BRICK_HEIGHT)); }
    
    private boolean ballMovingUp(){                 return speedY > 0; }
    private boolean ballMovingDown(){               return speedY < 0; }
    private boolean ballMovingLeft(){               return speedX < 0; }
    private boolean ballMovingRight(){              return speedX > 0; }
    
    public static void noiceMaker(Media sound) {
        if (sound != null){
            Controller.jukeBox = new MediaPlayer(sound);
            Controller.jukeBox.setVolume((Controller.isMuted ? 0 : 100));
            Controller.jukeBox.play();
        }
    }

}
