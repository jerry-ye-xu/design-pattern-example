package BallMomento;

import java.util.List;

public class BallPitMomento {
    private List<BallMomento> ballsMomento;

    public BallPitMomento(List<BallMomento> ballsMomento) {
        this.ballsMomento = ballsMomento;
    }

    public List<BallMomento> getBallsMomento() { return this.ballsMomento; }
}
