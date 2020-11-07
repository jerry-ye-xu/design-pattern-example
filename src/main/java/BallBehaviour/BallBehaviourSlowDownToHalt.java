package BallBehaviour;

import Model.Ball;

public class BallBehaviourSlowDownToHalt implements BallBehaviour {

    private final double decelerationSpeed = 0.017;
    private final double threshold = 0.1;

    public BallBehaviourSlowDownToHalt() { }

    public void changeBallMovement(Ball ball) {
        if (ball.hasCollidedWithBall()) {
            while(
                (Math.abs(ball.getxVel()) > this.threshold) &&
                (Math.abs(ball.getyVel()) > this.threshold)
            ) {
//                System.out.println("ball.getxVel()");
//                System.out.println(ball.getxVel());
//                System.out.println("ball.getyVel()");
//                System.out.println(ball.getyVel());
                this.decelerate(ball, "x");
                this.decelerate(ball, "y");
            }
        }
    }

    private void decelerate(Ball ball, String axis) {
        if (axis.equals("x")) {
            double currVel = ball.getxVel();
            if (currVel > 0) {
                ball.setxVel(currVel - this.decelerationSpeed);
            } else {
                ball.setxVel(currVel + this.decelerationSpeed);
            }
        } else if (axis.equals("y")) {
            double currVel = ball.getyVel();
            if (currVel < 0) {
                ball.setyVel(currVel - this.decelerationSpeed);
            } else {
                ball.setyVel(currVel + this.decelerationSpeed);
            }
        }
    }
}
