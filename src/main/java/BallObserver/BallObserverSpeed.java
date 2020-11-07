package BallObserver;

import javafx.scene.control.Label;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import java.text.DecimalFormat;

import Model.Ball;

public class BallObserverSpeed implements BallObserver {
    private Ball ball;
    private Label labelView;
    private DecimalFormat df = new DecimalFormat("0.00");

    public BallObserverSpeed(Ball ball){
        this.ball = ball;
        this.ball.addObserver(this);

        this.labelView = new Label();
        this.labelView.setFont(Font.font("Arial", 14));
        this.labelView.setTextAlignment(TextAlignment.CENTER);

    }

    public void update() {
        this.updateLabel();
    }

    public Label getLabel() {
        return this.labelView;
    }

    private void updateLabel() {
        this.labelView.setText(this.ball.getColourString() + ": " +
                "xVel=" + df.format(this.ball.getxVel()) + ", " +
                "yVel=" + df.format(this.ball.getyVel())
        );
    }
}