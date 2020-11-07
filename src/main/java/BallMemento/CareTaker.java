package BallMemento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<BallPitMemento> ballPitMomentos;

    public CareTaker() {
        ballPitMomentos = new ArrayList<BallPitMemento>();
    }

    public void addState(BallPitMemento ballPitMomento) {
        ballPitMomentos.add(ballPitMomento);
    }

    public BallPitMemento getLatestState() {
        BallPitMemento ballPitMomento = null;
        if (ballPitMomentos.size() > 0) {
            int idx = ballPitMomentos.size() - 1;
            ballPitMomento = ballPitMomentos.get(idx);

            ballPitMomentos.remove(idx);
        }

        return ballPitMomento;
    }

    public int getBallPitMomentosSize() { return ballPitMomentos.size(); }
}
