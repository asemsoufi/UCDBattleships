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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;


public class Main extends Application {

    private Game newGame;
    private Scene welcomeScene;
    private Scene inputScene;
    private Scene p1Scene;
    private Scene p2Scene;
    private Scene gameOverScene;
    private Label gameOverStatsLabel;

    private void buildPlayerGrid(GridPane g, Stage s){
        int index = 0;
        for(int i =0; i<newGame.getLookupGrid().getRows().length; i++){
            for(int j = 0; j<newGame.getLookupGrid().getColumns().length; j++){
                Button b = new Button();
                b.setId(newGame.getLookupGrid().getCells().get(index));
                // b.setText(newGame.getLookupGrid().getCells().get(index1));
                b.setPrefSize(70,70);
                b.setOnAction((ActionEvent event) ->{
                    // make a guess
                    newGame.play(b.getId());
                    // update button text based on the outcome of player's shot
                    if(newGame.itIsAHit()){
                        b.setText("HIT");
                        b.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
                        b.setDisable(true);
                    } else{
                        b.setText(" X ");
                        b.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,24));
                        b.setDisable(true);
                    }
                    // end game if that was the last hit
                    if(newGame.isGameOver()){
                        g.setDisable(true);
                        //s.setScene(gameOverScene);
                        //gameOverStatsLabel.setText(newGame.getInfoMessage());
                        //s.centerOnScreen();
                    } else {

                        // show appropriate player's scene
                        if (newGame.getActivePlayer() == newGame.getPlayer1()) {
                            s.setScene(p1Scene);
                            s.centerOnScreen();
                        } else {
                            s.setScene(p2Scene);
                            s.centerOnScreen();
                        }
                    }
                });
                g.add(b, j, i);

                index++;
            }
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        newGame = new Game();

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
        welcomeScene = new Scene ( WelcomeRoot , 750 , 600) ;
        primaryStage.setScene(welcomeScene);
        primaryStage.centerOnScreen();

        Button btStartGame = new Button ( "Play" ) ;
        Button btCancel = new Button ( "Cancel" ) ;

        Label inputInfoLabel = new Label();

        VBox inputRoot = new VBox(30 , inputGrid, inputInfoLabel, btStartGame , btCancel);
        inputRoot.setAlignment(Pos.CENTER);

        // input scene asks players to enter their names and start playing or go back to main (welcome) window/scene
        inputScene = new Scene( inputRoot , 400 , 350);

        // setting-up a grid of cells for the player1 to play
        GridPane p1GuiGrid = new GridPane();
        p1GuiGrid.setPadding(new Insets(10,10,10,10));
        p1GuiGrid.setVgap(1);
        p1GuiGrid.setHgap(1);

        buildPlayerGrid(p1GuiGrid, primaryStage);

        Button btP1QuitGame = new Button ( "Quit Game!" ) ;

        // setting-up a grid of cells for the player2 to play
        GridPane p2GuiGrid = new GridPane();
        p2GuiGrid.setPadding(new Insets(10,10,10,10));
        p2GuiGrid.setVgap(1);
        p2GuiGrid.setHgap(1);

        buildPlayerGrid(p2GuiGrid, primaryStage);

        Button btP2QuitGame = new Button ( "Quit Game!" ) ;

        btNewGame.setOnAction((ActionEvent event) ->{
            primaryStage.setScene(inputScene);
            primaryStage.centerOnScreen();
            p1NameInput.setText("");
            p2NameInput.setText("");
        });


        btExit.setOnAction((ActionEvent event) ->{
            primaryStage.close();
        });

        Label p1SceneNameLabel = new Label();
        Label p2SceneNameLabel = new Label();


        VBox p1VRoot = new VBox(20, p1SceneNameLabel, p1GuiGrid, btP1QuitGame);
        p1Scene = new Scene( p1VRoot , 700 , 870);
        p1VRoot.setAlignment(Pos.CENTER);

        VBox p2VRoot = new VBox(20,  p2SceneNameLabel, p2GuiGrid, btP2QuitGame);
        p2Scene = new Scene ( p2VRoot , 700 , 870) ;
        p2VRoot.setAlignment(Pos.CENTER);

        btStartGame.setOnAction((ActionEvent event) ->{
            if (p1NameInput.getText().trim().isEmpty() || p2NameInput.getText().trim().isEmpty() ||
            p2NameInput.getText().trim().toUpperCase().equals(p1NameInput.getText().trim().toUpperCase())) {
                inputInfoLabel.setText("Players' names can't be empty or equal!");
            }
            else {
                // create a label to display the name of player
                newGame.getPlayer1().setName(p1NameInput.getText());
                newGame.getPlayer2().setName(p2NameInput.getText());
                p1SceneNameLabel.setText(p1NameInput.getText());
                p1SceneNameLabel.setFont(Font.font("Arial", FontWeight.BOLD,40));

                // create a label to display the name of player2
                p2SceneNameLabel.setText(p2NameInput.getText());
                p2SceneNameLabel.setFont(Font.font("Arial", FontWeight.BOLD,40));

                primaryStage.setScene(p1Scene);
                primaryStage.centerOnScreen();

            }
        });

        btCancel.setOnAction((ActionEvent event) ->{
            primaryStage.setScene(welcomeScene);
            primaryStage.centerOnScreen();
        });

        btP1QuitGame.setOnAction((ActionEvent event) ->{
            if(newGame.isGameOver()){
                primaryStage.setScene(gameOverScene);
                //gameOverStatsLabel.setText(newGame.getInfoMessage());
            } else {
                newGame.setActivePlayer(newGame.getPlayer1());
                newGame.play("QUIT");
                if (newGame.getPlayer2().isStillIn()) {
                    primaryStage.setScene(p2Scene);
                } else {
                    primaryStage.setScene(gameOverScene);
                }
            }
            primaryStage.centerOnScreen();
        });

        btP2QuitGame.setOnAction((ActionEvent event) ->{
            if(newGame.isGameOver()){
                primaryStage.setScene(gameOverScene);
                //gameOverStatsLabel.setText(newGame.getInfoMessage());
            } else {
                newGame.setActivePlayer(newGame.getPlayer2());
                newGame.play("QUIT");
                if (newGame.getPlayer1().isStillIn()) {
                    primaryStage.setScene(p1Scene);
                } else {
                    primaryStage.setScene(gameOverScene);
                }
            }
            primaryStage.centerOnScreen();
        });

        Label gameOverLabel = new Label("*** Game Over ***");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD,46));

        gameOverStatsLabel = new Label();
        gameOverStatsLabel.setFont(Font.font("Arial", FontWeight.NORMAL,20));

        Button gameOverExitBT = new Button("Exit Game");
        gameOverExitBT.setOnAction((ActionEvent event) ->{
            primaryStage.close();
        });
        VBox gameOverVRoot = new VBox(20,gameOverLabel,gameOverStatsLabel, gameOverExitBT);
        gameOverVRoot.setAlignment(Pos.CENTER);
        gameOverScene = new Scene( gameOverVRoot , 400 , 500);



        primaryStage.show();

    }

    public static void main(String[] args) {

        Application.launch(args);


    }

}
