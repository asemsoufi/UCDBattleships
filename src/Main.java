import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Window;
import javafx.stage.WindowEvent;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {

        // constructing primary stage
        Image welcomeImage = new Image("https://bit.ly/2CXAWUs");
        ImageView selectedImage = new ImageView(welcomeImage);

        primaryStage.setTitle("Battleships Game");
        primaryStage.getIcons().add(new Image("https://bit.ly/2pAUQkY"));
        primaryStage.setResizable(false);

        Button btNewGame = new Button ( "New Game" ) ;
        Button btExit = new Button ( "Exit" ) ;


        VBox WelcomeRoot = new VBox (30 , selectedImage, btNewGame , btExit) ;
        WelcomeRoot.setAlignment(Pos.CENTER) ;

        // initially setting scene to welcome scene asking the user to start a new game or exit application
        Scene welcomeScene = new Scene ( WelcomeRoot , 750 , 600) ;
        primaryStage.setScene(welcomeScene);

        Button btStartGame = new Button ( "Play" ) ;
        Button btCancel = new Button ( "Back" ) ;

        VBox inputRoot = new VBox(30 , btStartGame , btCancel);
        inputRoot.setAlignment(Pos.CENTER) ;

        // input scene asks players to enter their names and start playing or go back to main (welcome) window/scene
        Scene inputScene = new Scene( inputRoot , 750 , 600);

        btNewGame.setOnAction((ActionEvent event) ->{
            primaryStage.setScene(inputScene);
        });


        btExit.setOnAction((ActionEvent event) ->{
            primaryStage.close();
        });

        btStartGame.setOnAction((ActionEvent event) ->{
            System.out.println("Game is on..");
            Game game = new Game();
            game.play();
        });

        btCancel.setOnAction((ActionEvent event) ->{
            primaryStage.setScene(welcomeScene);
            //inputStage.close();
        });

        primaryStage.show();

    }

    public static void main(String[] args) {

        Application.launch(args);


    }

}
