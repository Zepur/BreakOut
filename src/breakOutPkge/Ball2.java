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
    double xPos, yPos, speedY, speedX, initialSpeedY;
    static double radius = 8;
    static int speedRate;
    int updateRemains = 0;
    public static int ballsLeft;
    int[] gridX = {60, 113, 166, 219, 272, 325, 378, 431, 484, 537, 590, 643, 696, 749, 802};
    int[] gridY = {50, 73, 96, 119, 142, 165, 188, 211, 234, 257};
    int xB = 0;
    int yB = 0;
    public static Timeline animation = new Timeline();

    public Ball2(Pane gameWindow, double startX, double startY, double speedX, double speedY, Paddle gamePaddle) {
        super(startX, startY, radius);
        this.xPos = startX;
        this.yPos = startY;
        this.speedX = 1;
        this.speedY = 1;
        this.initialSpeedY = speedY;
        this.setFill(Color.LEMONCHIFFON);

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
                Controller.stopTime = System.currentTimeMillis();
                System.out.println((Controller.stopTime-Controller.startTime)/1000);
                setCenterX(400-getRadius());
                setCenterY(380);
                speedY = initialSpeedY;
                Controller.betweenLVLs(gameWindow);
                Controller.isPlaying = false;
            } else {
                if(((((getCenterX() + getRadius()) >= gridX[0]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[0] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[0]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[0])))) {
                    xB = 1;
                } else if(((((getCenterX() + getRadius()) >= gridX[1]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[1] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[1]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[1])))) {
                    xB = 2;
                } else if(((((getCenterX() + getRadius()) >= gridX[2]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[2] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[2]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[2])))) {
                    xB = 3;
                } else if(((((getCenterX() + getRadius()) >= gridX[3]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[3] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[3]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[3])))) {
                    xB = 4;
                } else if(((((getCenterX() + getRadius()) >= gridX[4]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[4] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[4]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[4])))) {
                    xB = 5;
                } else if(((((getCenterX() + getRadius()) >= gridX[5]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[5] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[5]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[5])))) {
                    xB = 6;
                } else if(((((getCenterX() + getRadius()) >= gridX[6]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[6] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[6]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[6])))) {
                    xB = 7;
                } else if(((((getCenterX() + getRadius()) >= gridX[7]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[7] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[7]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[7])))) {
                    xB = 8;
                } else if(((((getCenterX() + getRadius()) >= gridX[8]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[8] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[8]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[8])))) {
                    xB = 9;
                } else if(((((getCenterX() + getRadius()) >= gridX[9]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[9] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[9]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[9])))) {
                    xB = 10;
                } else if(((((getCenterX() + getRadius()) >= gridX[10]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[10] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[10]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[10])))) {
                    xB = 11;
                } else if(((((getCenterX() + getRadius()) >= gridX[11]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[11] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[11]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[11])))) {
                    xB = 12;
                } else if(((((getCenterX() + getRadius()) >= gridX[12]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[12] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[12]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[12])))) {
                    xB = 13;
                } else if(((((getCenterX() + getRadius()) >= gridX[13]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[13] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[13]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[13])))) {
                    xB = 14;
                } else if(((((getCenterX() + getRadius()) >= gridX[14]) && speedX > 0) && ((getCenterX() + getRadius()) <= (gridX[14] + Brick.BRICK_WIDTH)))
                        || ((((getCenterX() - getRadius()) <= (gridX[14]+Brick.BRICK_WIDTH)) && speedX < 0) && ((getCenterX() - getRadius()) >= (gridX[14])))) {
                    xB = 15;
                } else
                    xB = 0;

                if(((((getCenterY() + getRadius()) >= gridY[0]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[0] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[0]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[0]+Brick.BRICK_HEIGHT)))) {
                    yB = 1;
                } else if(((((getCenterY() + getRadius()) >= gridY[1]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[1] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[1]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[1]+Brick.BRICK_HEIGHT)))) {
                    yB = 2;
                } else if(((((getCenterY() + getRadius()) >= gridY[2]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[2] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[2]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[2]+Brick.BRICK_HEIGHT)))) {
                    yB = 3;
                } else if(((((getCenterY() + getRadius()) >= gridY[3]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[3] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[3]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[3]+Brick.BRICK_HEIGHT)))) {
                    yB = 4;
                } else if(((((getCenterY() + getRadius()) >= gridY[4]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[4] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[4]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[4]+Brick.BRICK_HEIGHT)))) {
                    yB = 5;
                } else if(((((getCenterY() + getRadius()) >= gridY[5]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[5] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[5]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[5]+Brick.BRICK_HEIGHT)))) {
                    yB = 6;
                } else if(((((getCenterY() + getRadius()) >= gridY[6]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[6] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[6]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[6]+Brick.BRICK_HEIGHT)))) {
                    yB = 7;
                } else if(((((getCenterY() + getRadius()) >= gridY[7]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[7] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[7]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[7]+Brick.BRICK_HEIGHT)))) {
                    yB = 8;
                } else if(((((getCenterY() + getRadius()) >= gridY[8]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[8] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[8]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[8]+Brick.BRICK_HEIGHT)))) {
                    yB = 9;
                } else if(((((getCenterY() + getRadius()) >= gridY[9]) && speedY < 0) && ((getCenterY() + getRadius()) <= (gridY[9] + Brick.BRICK_HEIGHT)))
                        || ((((getCenterY() - getRadius()) <= (gridY[9]+Brick.BRICK_HEIGHT)) && speedY > 0) && ((getCenterY() - getRadius()) >= (gridY[9]+Brick.BRICK_HEIGHT)))) {
                    yB = 10;
                }else
                    yB = 0;
            }

            if(((yB >= 1) && (yB <= 10) && (xB >= 1) && (xB <= 15)) && (Brick.bricks.get((15 * (yB - 1)) + (xB - 1)) != null)) {
                Brick brick = Brick.bricks.get((15 * (yB - 1)) + (xB - 1));
                if (brick != null) {
                    boolean xx = ((getCenterX() > (brick.getLayoutX() - getRadius())) && (getCenterX() < (brick.getLayoutX() + brick.getWidth() + getRadius())));
                    boolean yy = ((getCenterY() < brick.getLayoutY() + getRadius() + brick.getHeight()) && (getCenterY() > brick.getLayoutY() - getRadius()));
                    boolean left = (((brick.getLayoutX() - ballPosRIGHT) <= 1) && ((brick.getLayoutX() - ballPosRIGHT) >= -1));
                    boolean right = (((ballPosLEFT - (brick.getWidth() + brick.getLayoutX())) <= 1) && ((ballPosLEFT - (brick.getWidth() + brick.getLayoutX())) >= -1));
                    boolean bottom = (((getCenterY() - (brick.getLayoutY() + brick.getHeight() + getRadius())) <= 1) && ((getCenterY() - (brick.getLayoutY() + brick.getHeight() + getRadius())) >= -1));
                    boolean top = (((ballPosDOWN - brick.getLayoutY()) <= 0.9) && ((ballPosDOWN - brick.getLayoutY()) >= -0.9));

//                    System.out.println(brick.getLayoutX()+" - "+brick.getLayoutY());
//                    System.out.print(getCenterX() + getRadius());
//                    System.out.println("  -  " + getCenterY() + getRadius());
//                    System.out.println("TOP: "+top+". BOTTOM: "+bottom+". LEFT: "+left+". RIGHT: "+right+"\n");

                    if ((right || left) && yy) {
                        noiceMaker(SMASH_SOUND);
                        speedX = -speedX;
                        if (brick.getFill() == Color.DARKBLUE) {
                            brick.setFill(Color.RED);
                            brick.setArcHeight(10);
                            brick.setArcWidth(10);
                        } else if (brick.getFill() == Color.RED) {
                            brick.setFill(Color.WHITE);
                            brick.setArcHeight(20);
                            brick.setArcWidth(20);
                        } else {
                            gameWindow.getChildren().removeAll(brick);
                            Brick.bricks.set(Brick.bricks.indexOf(brick), null);
                            ballsLeft--;
//                            if (gameWindow.getChildren().indexOf(Controller.powerUPpadSize) != 0) {
//                                Random rand = new Random();
//                                int lucky = rand.nextInt(11);
//                                Controller.powerMeUp(lucky, gameWindow);
//                            }
                        }
                    }

                    if (xx && (top|| bottom)) {
                        noiceMaker(SMASH_SOUND);
                        speedY = -speedY;
                        if (brick.getFill() == Color.DARKBLUE) {
                            brick.setFill(Color.RED);
                            brick.setArcHeight(10);
                            brick.setArcWidth(10);
                        } else if (brick.getFill() == Color.RED) {
                            brick.setFill(Color.WHITE);
                            brick.setArcHeight(20);
                            brick.setArcWidth(20);
                        } else {
                            gameWindow.getChildren().removeAll(brick);
                            Brick.bricks.set(Brick.bricks.indexOf(brick), null);
                            ballsLeft--;
                        }
                    }

                    /*
                    boolean xx = (((getCenterX() + getRadius()) == brick.getLayoutX()) && ((getCenterX() - getRadius()) <= (brick.getLayoutX() + brick.getWidth())));
                    boolean yy = (((getCenterY() - getRadius()) <= brick.getLayoutY() + brick.getHeight()) && ((getCenterY() + getRadius()) >= brick.getLayoutY()));
                    boolean left = ((getCenterX() + getRadius()) == (brick.getLayoutX()));
                    boolean right = ((getCenterX() - getRadius()) == (brick.getLayoutX() + brick.getWidth()));
                    boolean bottom = ((getCenterY() - getRadius()) == (brick.getLayoutY() + brick.getHeight()));
                    boolean top = ((getCenterY() + getRadius()) == (brick.getLayoutY()));

                    try {
                        if ((right || left) && yy) {
                            noiceMaker(SMASH_SOUND);
                            speedX = -speedX;
                            if (brick.getFill() == Color.DARKBLUE) {a
                                brick.setFill(Color.RED);
                                brick.setArcHeight(10);
                                brick.setArcWidth(10);
                            } else if (brick.getFill() == Color.RED) {
                                brick.setFill(Color.WHITE);
                                brick.setArcHeight(20);
                                brick.setArcWidth(20);
                            } else {
                                gameWindow.getChildren().removeAll(brick);
                                Brick.bricks.set(Brick.bricks.indexOf(brick), null);
                                ballsLeft--;
                                if (gameWindow.getChildren().indexOf(Controller.powerUPpadSize) != 0) {
                                    Random rand = new Random();
                                    int lucky = rand.nextInt(11);
                                    Controller.powerMeUp(lucky, gameWindow);
                                }
                            }
                        }

                        if (xx && (top || bottom)) {
                            noiceMaker(SMASH_SOUND);
                            speedY = -speedY;
                            if (brick.getFill() == Color.DARKBLUE) {
                                brick.setFill(Color.RED);
                                brick.setArcHeight(10);
                                brick.setArcWidth(10);
                            } else if (brick.getFill() == Color.RED) {
                                brick.setFill(Color.WHITE);
                                brick.setArcHeight(20);
                                brick.setArcWidth(20);
                            } else {
                                gameWindow.getChildren().removeAll(brick);
                                Brick.bricks.set(Brick.bricks.indexOf(brick), null);
                                ballsLeft--;
                            }
                        }
                    }
                    catch (NullPointerException e){
                        speedX = 0;
                        speedY = 0;
                        e.printStackTrace();
                    }
                    */
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
