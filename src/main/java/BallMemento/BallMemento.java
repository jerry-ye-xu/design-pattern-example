package BallMemento;

import Model.Ball;

public class BallMemento {
    private Ball ball;

    public BallMemento(Ball ball) {
        this.ball =  new Ball(ball);
    }

    public Ball getBall() { return this.ball; }

    public void printInfo() {
        System.out.println(this.ball.getColour());
        System.out.println(this.ball.getColourString());
        System.out.println(this.ball.getxPos());
        System.out.println(this.ball.getyPos());
        System.out.println(this.ball.getxVel());
        System.out.println(this.ball.getyVel());
        System.out.println(this.ball.getRadius());
        System.out.println(this.ball.getEnvVar());
    }
}
