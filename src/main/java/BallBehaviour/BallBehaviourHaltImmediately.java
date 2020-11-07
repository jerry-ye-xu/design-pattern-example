package BallBehaviour;

import Model.Ball;

import static java.lang.Math.abs;

public class BallBehaviourHaltImmediately implements BallBehaviour {

    private final double decelerationSpeed = 0.017;
    private final double threshold = 0.1;

    public BallBehaviourHaltImmediately() { }

    public void changeBallMovement(Ball ball) {
        if (ball.hasCollidedWithBall()) {
            ball.setxVel(ball.getxVel() * 0.98);
            ball.setyVel(ball.getyVel() * 0.98);

            if (Math.abs(ball.getxVel()) < 0.001) {
                ball.setxVel(0);
            }
            if (Math.abs(ball.getyVel()) < 0.001) {
                ball.setyVel(0);
            }
        }
    }

    public void changeBallMovement(Ball ball, double width, double height) {

    }
}
