import javafx.application.Application;
import javafx.stage.Stage;

import Model.BallPit;
import View.GameWindow;

public class App extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GameWindow window = new GameWindow(new BallPit(640, 400));
        window.run();

        primaryStage.setTitle("Strategy Balls");
        primaryStage.setScene(window.getScene());
        primaryStage.show();

        window.run();
    }
}
