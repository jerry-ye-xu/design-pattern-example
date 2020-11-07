package BallObserver;

import BallObserver.BallObserver;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;

import java.util.List;

public class BallSpeedPane {

    private VBox statsBox;
    private List<BallObserver> ballObservers;
    private List<Label> labels;

    public BallSpeedPane(List<BallObserver> ballObservers){
        this.ballObservers = ballObservers;
        this.statsBox = new VBox();
    }

}
