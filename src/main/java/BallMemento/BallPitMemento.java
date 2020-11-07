package BallMemento;

import java.util.List;

public class BallPitMemento {
    private List<BallMemento> ballsMomento;

    public BallPitMemento(List<BallMemento> ballsMomento) {
        this.ballsMomento = ballsMomento;
    }

    public List<BallMemento> getBallsMemento() { return this.ballsMomento; }
}
