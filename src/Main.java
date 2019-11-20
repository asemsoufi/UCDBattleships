import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class Main extends Application {

    private Grid guiGrid = new Grid();
    private Game newGame = new Game();

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

        // setting-up a panel to get players' names
        GridPane inputGrid = new GridPane();
        inputGrid.setPadding(new Insets(10,10,10,10));
        inputGrid.setVgap(8);
        inputGrid.setHgap(10);

        // adding labels and text fields to get players' names
        Label p1Namelabel = new Label("Player1 Name:");
        TextField p1NameInput = new TextField();
        p1NameInput.setPromptText("type first player's name");
        Label p2Namelabel = new Label("Player1 Name:");
        TextField p2NameInput = new TextField();
        p2NameInput.setPromptText("type second player's name");


        // add all to input grid
        inputGrid.add(p1Namelabel,0,0);
        inputGrid.add(p2Namelabel,0,1);
        inputGrid.add(p1NameInput,1,0);
        inputGrid.add(p2NameInput,1,1);
        inputGrid.setAlignment(Pos.CENTER);

        // initially setting scene to welcome scene asking the user to start a new game or exit application
        Scene welcomeScene = new Scene ( WelcomeRoot , 750 , 600) ;
        primaryStage.setScene(welcomeScene);

        Button btStartGame = new Button ( "Play" ) ;
        Button btCancel = new Button ( "Back" ) ;

        VBox inputRoot = new VBox(30 , inputGrid, btStartGame , btCancel);
        inputRoot.setAlignment(Pos.CENTER);

        // input scene asks players to enter their names and start playing or go back to main (welcome) window/scene
        Scene inputScene = new Scene( inputRoot , 400 , 350);


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

        // setting-up a panel to get players' names
        GridPane p1GuiGrid = new GridPane();
        p1GuiGrid.setPadding(new Insets(10,10,10,10));
        p1GuiGrid.setVgap(1);
        p1GuiGrid.setHgap(1);

        int index = 0;
        for(int i =0; i<10; i++){
            for(int j = 0; j<10; j++){
                Button b = new Button();
                b.setId(guiGrid.getCells().get(index));
                b.setText(guiGrid.getCells().get(index));
                b.setPrefSize(70,70);
                p1GuiGrid.add(b, j, i);

                index++;
            }
        }

        Scene p1GuiScene = new Scene ( p1GuiGrid , 750 , 600) ;
        Stage s = new Stage();
        s.setScene(p1GuiScene);
        //s.show();

        //newGame.

        primaryStage.show();

    }

    public static void main(String[] args) {

        Application.launch(args);


    }

}
