package Model;

import BallBehaviour.BallBehaviour;
import BallMemento.BallMemento;
import BallObserver.BallObserver;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Ball implements Cloneable {
    private final double startX;
    private final double startY;
    private double xPos;
    private double yPos;
    private double radius;
    private double xVel;
    private double yVel;
    private Paint colour;
    private String colourString;

    private List<BallObserver> ballObservers = new ArrayList<>();

    private HashMap<String, Double> envVar;

    private boolean collidedWithBall = false;
    private boolean collidedWithWall = false;
    private BallBehaviour ballBehaviour;

    public Ball(
        double startX,
        double startY,
        double startRadius,
        Paint colour,
        String colourString,
        HashMap<String, Double> envVar
    ) {
        this.startX = startX;
        this.startY = startY;
        this.xPos = startX;
        this.yPos = startY;
        this.radius = startRadius;
        this.colour = colour;
        this.colourString = colourString;
        this.envVar = envVar;

        xVel = new Random().nextInt(5);
        yVel = new Random().nextInt(5);
    }

    public void tick() {
        xPos += xVel;
        yPos += yVel;
    }

    public void handleEdgeCollision(
            double gameBorderHeight,
            double gameBorderWidth
    ) {
        // Handle the edges (balls don't get a choice here)
        if (this.getxPos() + this.getRadius() > gameBorderWidth) {
            this.setxPos(gameBorderWidth - this.getRadius());
            this.setxVel(this.getxVel() * -1);

            this.setCollidedWithWall(true);
        }
        if (this.getyPos() + this.getRadius() > gameBorderHeight) {
            this.setyPos(gameBorderHeight - this.getRadius());
            this.setyVel(this.getyVel() * -1);

            this.setCollidedWithWall(true);
        }
        if (this.getxPos() - this.getRadius() < 0) {
            this.setxPos(0 + this.getRadius());
            this.setxVel(this.getxVel() * -1);

            this.setCollidedWithWall(true);
        }
        if (this.getyPos() - this.getRadius() < 0) {
            this.setyPos(0 + this.getRadius());
            this.setyVel(this.getyVel() * -1);

            this.setCollidedWithWall(true);
        }
    }

    /*
        Getters and Setters
     */

    public void setxVel(double xVel) {
        this.xVel = xVel;
    }

    public void setyVel(double yVel) {
        this.yVel = yVel;
    }

    public double getRadius() {
        return radius;
    }

    public double getxPos() {
        return xPos;
    }

    public double getyPos() {
        return yPos;
    }

    public void setColour(Paint colour) { this.colour = colour; }

    public Paint getColour() { return colour; }

    public String getColourString() {
        return colourString;
    }

    public double getxVel() {
        return xVel;
    }

    public double getyVel() {
        return yVel;
    }

    public void setxPos(double xPos) {
        this.xPos = xPos;
    }

    public void setyPos(double yPos) {
        this.yPos = yPos;
    }

    public HashMap<String, Double> getEnvVar() {
        return this.envVar;
    }

    public void setBallBehaviour(BallBehaviour ballBehaviour) {
        this.ballBehaviour = ballBehaviour;
    }

    public boolean hasCollidedWithBall() {
        return this.collidedWithBall;
    }

    public void setCollidedWithWall(boolean collidedWithWall) {
        this.collidedWithWall = collidedWithWall;
    }

    public boolean hasCollidedWithWall() {
        return this.collidedWithWall;
    }

    public void setCollidedWithBall(boolean hasCollided) {
        this.collidedWithBall = hasCollided;
    }

    /*
        Strategy methods
     */

    public void think() {
        this.ballBehaviour.changeBallMovement(this);
    }

    /*
        Observer methods
     */

    public void addObserver(BallObserver ballObserver) {
        this.ballObservers.add(ballObserver);
    }

    public void removeObserver(BallObserver ballObserver) {
        this.ballObservers.remove(ballObserver);
    }

    public void notifyObservers() {
        for(BallObserver ballObserver: this.ballObservers) {
            ballObserver.update();
        }
    }

    /*
        Prototype methods
     */

    public Ball clone() throws CloneNotSupportedException {
        Ball that = (Ball) super.clone();
        that.setColour(Paint.valueOf(that.getColourString()));

        return that;
    }

    /*
        Copy constructor
     */

    public Ball (Ball original) {
        this.startX = original.xPos;
        this.startY = original.yPos;
        this.xPos = original.xPos;
        this.yPos = original.yPos;
        this.radius = original.radius;
        this.colour = original.colour;
        this.colourString = original.colourString;

        this.envVar = original.envVar;

        this.ballBehaviour = original.ballBehaviour;

        this.xVel = original.xVel;
        this.yVel = original.yVel;
    }

    /*
        BallMemento.BallMemento methods
     */

    public BallMemento saveState() {
        return new BallMemento(this);
    }

    public void restoreState(BallMemento stateBackup) {
        Ball prevState = stateBackup.getBall();

        this.xPos = prevState.xPos;
        this.yPos = prevState.yPos;
        this.radius = prevState.radius;
        this.colour = prevState.colour;
        this.colourString = prevState.colourString;
        this.envVar = prevState.envVar;

        this.ballBehaviour = prevState.ballBehaviour;

        this.xVel = prevState.xVel;
        this.yVel = prevState.yVel;
    }
}
