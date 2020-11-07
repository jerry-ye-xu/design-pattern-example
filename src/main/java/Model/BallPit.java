package Model;

import BallMemento.*;
import BallPrototype.ButtonCreateBall;
import javafx.geometry.Point2D;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import BallBehaviour.BallBehaviourAccelerateToCorner;
import BallBehaviour.BallBehaviourConstantSpeed;
import BallBehaviour.BallBehaviourHaltImmediately;

import BallObserver.BallObserver;
import BallObserver.BallObserverSpeed;

public class BallPit {
    private final double height;
    private final double width;
    private List<Ball> balls = new ArrayList<>();
    private long tickCount = 0;

    private VBox statsBox;
    private VBox createBallBox;
    private VBox momentoBox;

    private HashMap<String, Double> envVar;

    public CareTaker careTaker;

    public BallPit(double width, double height) {
        this.height = height;
        this.width = width;

        this.envVar = new HashMap<>();
        this.envVar.put("gameWidth", this.width);
        this.envVar.put("gameHeight", this.height);

        this.careTaker = new CareTaker();

        Ball redBall = new Ball(
            100,
            100,
            20,
            Paint.valueOf("RED"),
            "RED",
            this.envVar
        );
        Ball blackBall = new Ball(
                100,
                100,
                20,
                Paint.valueOf("BLACK"),
                "BLACK",
                this.envVar
        );
        Ball blueBall = new Ball(
                100,
                100,
                20,
                Paint.valueOf("BLUE"),
                "BLUE",
                this.envVar
        );

        balls.add(redBall);
        balls.add(blackBall);
        balls.add(blueBall);

        BallObserver redBallObserver = new BallObserverSpeed(redBall);
        BallObserver blackBallObserver = new BallObserverSpeed(blackBall);
        BallObserver blueBallObserver = new BallObserverSpeed(blueBall);

        redBall.setBallBehaviour(new BallBehaviourConstantSpeed());
        blackBall.setBallBehaviour(new BallBehaviourAccelerateToCorner());
        blueBall.setBallBehaviour(new BallBehaviourHaltImmediately());

        ButtonCreateBall redBallCreator = new ButtonCreateBall("Create red ball  ", this, redBall, new BallBehaviourConstantSpeed());
        ButtonCreateBall blueBallCreator = new ButtonCreateBall("Create blue ball ", this, blueBall, new BallBehaviourHaltImmediately());
        ButtonCreateBall blackBallCreator = new ButtonCreateBall("Create black ball", this, blackBall, new BallBehaviourAccelerateToCorner());

        ButtonSave buttonSave = new ButtonSave("Save State   ", this);
        ButtonRestore buttonRestore = new ButtonRestore("Restore State", this);

        this.createBallBox = new VBox();
        this.createBallBox.setLayoutX(375);
        this.createBallBox.getChildren().addAll(
            blueBallCreator.getCreateButton(),
            blackBallCreator.getCreateButton(),
            redBallCreator.getCreateButton()
        );

        this.momentoBox = new VBox();
        this.momentoBox.setLayoutX(200);
        this.momentoBox.getChildren().addAll(
            buttonSave.getButton(),
            buttonRestore.getButton()
        );

        this.statsBox = new VBox();
        this.statsBox.getChildren().addAll(
            redBallObserver.getLabel(),
            blackBallObserver.getLabel(),
            blueBallObserver.getLabel()
        );
    }

    public VBox getStatsBox() { return statsBox; }

    public VBox getMomentoBox() { return momentoBox; }

    public VBox getCreateBallBox() { return createBallBox; }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public void tick() {
        tickCount++;

        for(Ball ball: balls) {
            ball.tick();
            ball.notifyObservers();

            ball.handleEdgeCollision(
                this.height,
                this.width
            );

            // Handle the edges (balls don't get a choice here)
            for(Ball ballB: balls) {
                if (checkCollision(ball, ballB)) {
//                    System.out.println("collided");
                    handleCollision(ball, ballB);
                }
            }
//            System.out.println("model.Ball is... " + ball.getColourString());
            ball.think();
//            System.out.println("AFTER ball.think()");
        }
    }

    public List<Ball> getBalls() {
        return balls;
    }

    /*
        Momento methods
     */

    public void saveState() {
        System.out.println("Saving state...");
        List<BallMemento> ballsMemento = new ArrayList<>();

        for (Ball ball: balls) {
            ballsMemento.add(new BallMemento(ball));
        }
        BallPitMemento ballPitMemento = new BallPitMemento(ballsMemento);

        careTaker.addState(ballPitMemento);

    }

    public void getLatestState() {
        if (careTaker.getBallPitMomentosSize() == 0) {
            System.out.println("No saved state");
            return;
        }
        System.out.println("Restoring state...");
        BallPitMemento ballPitMemento = careTaker.getLatestState();
        List<Ball> ballsRestored = new ArrayList<>();

        for (BallMemento ballMemento : ballPitMemento.getBallsMemento()) {
            ballMemento.printInfo();
            ballsRestored.add(ballMemento.getBall());
        }

        this.balls = ballsRestored;
    }

    /*
        Handling Collision
     */

    private boolean checkCollision(Ball ballA, Ball ballB) {
        if (ballA == ballB) {
            return false;
        }

        return Math.abs(ballA.getxPos() - ballB.getxPos()) < ballA.getRadius() + ballB.getRadius() &&
                Math.abs(ballA.getyPos() - ballB.getyPos()) < ballA.getRadius() + ballB.getRadius();
    }

    private void handleCollision(Ball ballA, Ball ballB) {

        if (!ballA.hasCollidedWithBall()) {
            ballA.setCollidedWithBall(true);
        }
        if (!ballB.hasCollidedWithBall()) {
            ballB.setCollidedWithBall(true);
        }

        ballA.setCollidedWithWall(false);
        ballB.setCollidedWithWall(false);

        //Properties of two colliding balls
        Point2D posA = new Point2D(ballA.getxPos(), ballA.getyPos());
        Point2D posB = new Point2D(ballB.getxPos(), ballB.getyPos());
        Point2D velA = new Point2D(ballA.getxVel(), ballA.getyVel());
        Point2D velB = new Point2D(ballB.getxVel(), ballB.getyVel());

        //calculate the axis of collision
        Point2D collisionVector = posB.subtract(posA);
        collisionVector = collisionVector.normalize();

        //the proportion of each balls velocity along the axis of collision
        double vA = collisionVector.dotProduct(velA);
        double vB = collisionVector.dotProduct(velB);

        //if balls are moving away from each other do nothing
        if (vA <= 0 && vB >= 0) {
            return;
        }

        // We're working with equal mass balls today
        //double mR = massB/massA;
        double mR = 1;

        //The velocity of each ball after a collision can be found by solving the quadratic equation
        //given by equating momentum energy and energy before and after the collision and finding the
        //velocities that satisfy this
        //-(mR+1)x^2 2*(mR*vB+vA)x -((mR-1)*vB^2+2*vA*vB)=0
        //first we find the discriminant
        double a = -(mR + 1);
        double b = 2 * (mR * vB + vA);
        double c = -((mR - 1) * vB * vB + 2 * vA * vB);
        double discriminant = Math.sqrt(b * b - 4 * a * c);
        double root = (-b + discriminant)/(2 * a);

        //only one of the roots is the solution, the other pertains to the current velocities
        if (root - vB < 0.01) {
            root = (-b - discriminant)/(2 * a);
        }

        //The resulting changes in velocity for ball A and B
        Point2D deltaVA = collisionVector.multiply(mR * (vB - root));
        Point2D deltaVB = collisionVector.multiply(root - vB);

        ballA.setxVel(ballA.getxVel() + deltaVA.getX());
        ballA.setyVel(ballA.getyVel() + deltaVA.getY());
        ballB.setxVel(ballB.getxVel() + deltaVB.getX());
        ballB.setyVel(ballB.getyVel() + deltaVB.getY());
    }
}
