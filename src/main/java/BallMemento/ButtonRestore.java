package BallMemento;

import Model.BallPit;

import javafx.scene.control.Button;

public class ButtonRestore {
    private Button restoreButton;
    private BallPit stateBallPit;

    public ButtonRestore(
        String displayText,
        BallPit ballPit
    ) {
        restoreButton = new Button(displayText);
        stateBallPit = ballPit;
        restoreButton.setOnAction(
            value -> {
                stateBallPit.getLatestState();
            }
        );
    }

    public Button getButton() { return restoreButton; }

}
