package BallMomento;

import java.util.ArrayList;
import java.util.List;

public class CareTaker {
    private List<BallPitMomento> ballPitMomentos;

    public CareTaker() {
        ballPitMomentos = new ArrayList<BallPitMomento>();
    }

    public void addState(BallPitMomento ballPitMomento) {
        ballPitMomentos.add(ballPitMomento);
    }

    public BallPitMomento getLatestState() {
        BallPitMomento ballPitMomento = null;
        if (ballPitMomentos.size() > 0) {
            int idx = ballPitMomentos.size() - 1;
            ballPitMomento = ballPitMomentos.get(idx);

            ballPitMomentos.remove(idx);
        }

        return ballPitMomento;
    }

    public int getBallPitMomentosSize() { return ballPitMomentos.size(); }
}
