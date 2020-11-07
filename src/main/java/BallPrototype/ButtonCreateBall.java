package BallPrototype;

import BallBehaviour.BallBehaviour;
import Model.Ball;
import Model.BallPit;
import javafx.scene.control.Button;

public class ButtonCreateBall {
    private Button createButton;

    public ButtonCreateBall(
        String displayText,
        BallPit ballPit,
        Ball ballPrototype,
        BallBehaviour ballBehaviour
    ) {
        createButton = new Button(displayText);
        createButton.setOnAction(
                value -> {
                    Ball newBall = new Ball(ballPrototype);
                    newBall.setBallBehaviour(ballBehaviour);
                    ballPit.getBalls().add(newBall);
                }
        );
    }

    public Button getCreateButton() { return createButton; }
}
