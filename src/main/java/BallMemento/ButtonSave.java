package BallMemento;

import Model.BallPit;

import javafx.scene.control.Button;

public class ButtonSave {
    private Button saveButton;
    private BallPit stateBallPit;

    public ButtonSave(
        String displayText,
        BallPit ballPit
    ) {
        saveButton = new Button(displayText);
        stateBallPit = ballPit;
        saveButton.setOnAction(value -> {
            stateBallPit.saveState();
        });
    }

    public Button getButton() { return saveButton; }
}
