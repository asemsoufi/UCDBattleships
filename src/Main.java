import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;



public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

    }

    public static void main(String[] args) {

        System.out.println("Game is on..");
        Game game = new Game();
        game.play();

    }

}
