package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;


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
    Label clickToPlayLabel;
    @FXML
    Circle playerCircle;
    @FXML
    Button easy;
    @FXML
    Button hard;
//    private static int luck = 0;
    Timeline loop;
    public static double speedX;
    public static double speedY;
    private static int scoreInt = 0;
    private static ArrayList<Rectangle> rects = new ArrayList<Rectangle>();

    public void movePad(MouseEvent e) {
        double boundsRight = (810 - gamePaddle.getWidth());
        score.setText(String.valueOf(scoreInt));
        gamePaddle.setX(e.getX()-(gamePaddle.getWidth()/2));
        if (gamePaddle.getX() >= boundsRight)
            gamePaddle.setX(boundsRight - 1);
        if (gamePaddle.getX() <= 0)
            gamePaddle.setX(1);
    }

    public static void setupBall(int diff) {
        speedX = 1;
        switch (diff) {
            case 1:
                speedY = 1;
                break;
            case 2:
                speedY = 1;
                break;
            default:
                speedY = 1;
        }
    }

    public void playBall(int speedValue) {
        test(speedValue);
        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();
    }

    public void test(int oli){
        loop = new Timeline(new KeyFrame(Duration.millis(oli), new EventHandler<ActionEvent>() {

            @Override
            public void handle(final ActionEvent t) {
                playerCircle.setLayoutX(playerCircle.getLayoutX() + speedX);
                playerCircle.setLayoutY(playerCircle.getLayoutY() - speedY);

                boolean test = false;
                Shape intersectShape = Shape.intersect(playerCircle, gamePaddle);
                if (intersectShape.getBoundsInLocal().getWidth() != -1) {
                    test = true;
                }
                boolean atRightBorder = playerCircle.getLayoutX()+playerCircle.getRadius() >= gameWindow.getWidth();
                boolean atLeftBorder = playerCircle.getLayoutX()-playerCircle.getRadius() <= 0;
                boolean atBottomBorder = playerCircle.getLayoutY() >= gameWindow.getHeight()-20;
                boolean atTopBorder = playerCircle.getLayoutY()-playerCircle.getRadius() <= 0;

                if (atRightBorder || atLeftBorder)
                    speedX = -speedX;
                if (atTopBorder)
                    speedY = -speedY;
                if (test) {
                    speedY = -speedY;
                    scoreInt += 12;
                }
                if (atBottomBorder) {
                    gameOver();
                }
                try {
                    for (Rectangle testRect : rects) {

                        boolean xx = ((playerCircle.getLayoutX() > (testRect.getLayoutX() - playerCircle.getRadius())) && (playerCircle.getLayoutX() < (testRect.getLayoutX() + testRect.getWidth() + playerCircle.getRadius())));
                        boolean yy = ((playerCircle.getLayoutY() < testRect.getLayoutY() + playerCircle.getRadius() + testRect.getHeight()) && (playerCircle.getLayoutY() > testRect.getLayoutY() - playerCircle.getRadius()));
                        boolean left = (playerCircle.getLayoutX() - (testRect.getLayoutX() + playerCircle.getRadius())) == 0.5;
                        boolean right = ((testRect.getLayoutX() + testRect.getWidth() + playerCircle.getRadius()) - playerCircle.getLayoutX()) == 0.5;
                        boolean bottom = playerCircle.getLayoutY() == testRect.getLayoutY() + testRect.getHeight() + playerCircle.getRadius();
                        boolean top = playerCircle.getLayoutY() == (testRect.getLayoutY() - playerCircle.getRadius());

                        if ((right || left) && yy) {
                            rects.remove(testRect);
                            gameWindow.getChildren().remove(testRect);
                            speedX = -speedX;
//                                Random rand = new Random();
//                                luck = rand.nextInt(10);
                        }
                        if (xx && (top || bottom)) {
                            rects.remove(testRect);
                            gameWindow.getChildren().remove(testRect);
                            speedY = -speedY;
//                                Random rand = new Random();
//                                luck = rand.nextInt(10);
                        }
                    }
//                    luckyYou(luck);
//                    luck = 0;
                } catch (ConcurrentModificationException e) {
                    //e.printStackTrace();
                }

            }
        }));
    }

//    private void luckyYou(int luck) {
//        if(luck >= 3) {
//            Circle luckyCircle = new Circle(20, Color.HOTPINK);
//            Label luckyLabel = new Label("<- +++ ->");
//            luckyCircle.setLayoutX(400 - luckyCircle.getRadius());
//            luckyCircle.setLayoutY(380);
//            luckyLabel.setTextFill(Color.WHITE);
//            luckyLabel.setLayoutX(400 - luckyLabel.getWidth());
//            luckyLabel.setLayoutY(380);
//            gameWindow.getChildren().add(luckyCircle);
//            gameWindow.getChildren().add(luckyLabel);
//        }
//    }

    private void getBricks() {
        Light.Distant light = new Light.Distant();
        light.setAzimuth(-75.0f);

        Lighting lightEffect = new Lighting();
        lightEffect.setLight(light);
        lightEffect.setSurfaceScale(3.4f);

        int hgap = 2, vgap = 2, brickWidth = 38, brickHeight = 20;
        double snax = (gameWindow.getWidth()-(581))/2;

        for(int row = 0; row < 15; row++){
            for(int column = 0; column < 10; column++){
                switch (column){
                    case 0:
                    case 5:
                        Rectangle brickDODGERBLUE = new Rectangle(brickWidth, brickHeight, Color.DODGERBLUE);
                        brickDODGERBLUE.setLayoutX(snax+(row*brickDODGERBLUE.getWidth())+(hgap*row));
                        brickDODGERBLUE.setLayoutY(brickWidth +(column * brickDODGERBLUE.getHeight())+ (vgap*column));
                        rects.add(brickDODGERBLUE);
                        break;
                    case 1:
                    case 6:
                        Rectangle brickLAWNGREEN = new Rectangle(brickWidth, brickHeight, Color.LAWNGREEN);
                        brickLAWNGREEN.setLayoutX(snax+(row*brickLAWNGREEN.getWidth())+(hgap*row));
                        brickLAWNGREEN.setLayoutY(brickWidth +(column * brickLAWNGREEN.getHeight())+ (vgap*column));
                        rects.add(brickLAWNGREEN);
                        break;
                    case 2:
                    case 7:
                        Rectangle brickORANGE = new Rectangle(brickWidth, brickHeight, Color.ORANGE);
                        brickORANGE.setLayoutX(snax+(row*brickORANGE.getWidth())+(hgap*row));
                        brickORANGE.setLayoutY(brickWidth +(column * brickORANGE.getHeight())+ (vgap*column));
                        rects.add(brickORANGE);
                        break;
                    case 3:
                    case 8:
                        Rectangle brickINDIANRED = new Rectangle(brickWidth, brickHeight, Color.INDIANRED);
                        brickINDIANRED.setLayoutX(snax+(row*brickINDIANRED.getWidth())+(hgap*row));
                        brickINDIANRED.setLayoutY(brickWidth +(column * brickINDIANRED.getHeight())+ (vgap*column));
                        rects.add(brickINDIANRED);
                        break;
                    case 4:
                    case 9:
                        Rectangle brickFUCHSIA = new Rectangle(brickWidth, brickHeight, Color.FUCHSIA);
                        brickFUCHSIA.setLayoutX(snax+(row*brickFUCHSIA.getWidth())+(hgap*row));
                        brickFUCHSIA.setLayoutY(brickWidth +(column * brickFUCHSIA.getHeight())+ (vgap*column));
                        rects.add(brickFUCHSIA);
                        break;
                }
            }
        }

        for(Rectangle rect : rects){
            rect.setEffect(lightEffect);
            gameWindow.getChildren().add(rect);
        }
    }

    private void gameOver(){
        playerCircle.setVisible(false);
        loop.stop();
        clickToPlayLabel.setText("GAME OVER!");
    }

    public void setup(int num){
        gameWindow.setStyle("-fx-background-color: cornsilk;");
        gameWindow.setCursor(Cursor.NONE);
        clickToPlayLabel.setText("");
        getBricks();
        easy.setVisible(false);
        hard.setVisible(false);
        int diff = 1;
        setupBall(diff);
        playBall(num);
    }

    public void startEasy(){
        gamePaddle.setWidth(80);
        setup(12);
    }

    public void startHard(){
        gamePaddle.setWidth(50);
        setup(7);
    }


}
