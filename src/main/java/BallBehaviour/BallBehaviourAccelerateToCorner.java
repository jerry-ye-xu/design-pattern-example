package BallBehaviour;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

import java.util.HashMap;

import Model.Ball;

public class BallBehaviourAccelerateToCorner implements BallBehaviour {

    private final double accelerationSpeed = 0.017;
    private final double threshold = 0.1;

    public BallBehaviourAccelerateToCorner() { }

    public void changeBallMovement(Ball ball) {
        String closestCorner = calculateClosestCorner(ball);
        double currxVel = ball.getxVel();
        double curryVel = ball.getyVel();

//        System.out.println("closestCorner:" + closestCorner);
        if (closestCorner.equals("SE")) {
            ball.setxVel(currxVel + this.accelerationSpeed);
            ball.setyVel(curryVel + this.accelerationSpeed);
        } else if (closestCorner.equals("SW")) {
            ball.setxVel(currxVel - this.accelerationSpeed);
            ball.setyVel(curryVel + this.accelerationSpeed);
        } else if (closestCorner.equals("NE")) {
            ball.setxVel(currxVel + this.accelerationSpeed);
            ball.setyVel(curryVel - this.accelerationSpeed);
        } else if (closestCorner.equals("NW")) {
            ball.setxVel(currxVel - this.accelerationSpeed);
            ball.setyVel(curryVel - this.accelerationSpeed);
        }
    }

    public String calculateClosestCorner(Ball ball) {
        HashMap<String, Double> envVar = ball.getEnvVar();

        double width = envVar.get("gameWidth");
        double height = envVar.get("gameHeight");

        double distSE = sqrt(
            pow(width - ball.getxPos(), 2) + pow(height - ball.getyPos(), 2)
        );

        double distSW = sqrt(
                pow(ball.getxPos(), 2) + pow(height - ball.getyPos(), 2)
        );

        double distNE = sqrt(
                pow(width - ball.getxPos(), 2) + pow(ball.getyPos(), 2)
        );

        double distNW = sqrt(
                pow(ball.getxPos(), 2) + pow(ball.getyPos(), 2)
        );

        double[] distArr = new double[4];
        distArr[0] = distSE;
        distArr[1] = distSW;
        distArr[2] = distNE;
        distArr[3] = distNW;

        String[] dirArr = new String[4];
        dirArr[0] = "SE";
        dirArr[1] = "SW";
        dirArr[2] = "NE";
        dirArr[3] = "NW";

        double minDist = distArr[0];
        int minIdx = 0;

        for (int i=0; i<4; i++) {
            if (minDist > distArr[i]) {
                minDist = distArr[i];
                minIdx = i;
            }
        }

        return dirArr[minIdx];
    }
}

