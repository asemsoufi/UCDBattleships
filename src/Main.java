import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.sql.Connection;
import java.sql.ResultSet;


public class Main extends Application {

    private Game newGame;
    private Scene welcomeScene;
    private Scene inputScene;
    private Scene p1Scene;
    private Scene p2Scene;
    private Scene gameOverScene;
    private Label p1SceneNameLabel;
    private Label p2SceneNameLabel;
    private Label gameOverStatsLabel;
    private Stage originalStage;
    private Button btP1ShowOriginal;
    private Button btP2ShowOriginal;
    private Stage scoreBoardStage;

    //TABLE VIEW AND DATA
    private ObservableList<ObservableList> data;
    private TableView tableview;

    private void buildPlayerGrid(Grid g, GridPane gp, Stage s){
        int index = 0;
        for(int i =0; i<g.getRows().length; i++){
            Label c = new Label(g.getColumns()[i]);
            c.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,26));
            c.setMaxWidth(Double.MAX_VALUE);
            c.setAlignment(Pos.CENTER);
            gp.add(c, i+1,0);

            Label r = new Label(g.getRows()[i]);
            r.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,26));
            r.setMinWidth(Double.MIN_VALUE);
            r.setAlignment(Pos.CENTER);
            gp.add(r, 0,i+1);

            for(int j = 0; j<g.getColumns().length; j++){
                Button b = new Button();
                if (g.equals(newGame.getDeployedGrid()) &&
                        (g.getCells().get(index).equals("BAT")||g.getCells().get(index).equals("CRU")||
                                g.getCells().get(index).equals("DES")||g.getCells().get(index).equals("SUB"))){
                    b.setId(g.getCells().get(index));
                    b.setText(b.getId());
                    b.setPrefSize(70,70);
                    b.setFont(Font.font("Arial", FontWeight.BOLD,18));
                    b.setTextFill(Color.BLUEVIOLET);
                    b.setStyle("-fx-background-color: YELLOW");
                    b.setDisable(true);
                    gp.setDisable(true);
                }
                else {
                    b.setId(g.getCells().get(index));
                }

                b.setPrefSize(70,70);
                b.setOnAction((ActionEvent event) ->{
                    // make a guess
                    newGame.play(b.getId());
                    // update button text based on the outcome of player's shot
                    if(newGame.itIsAHit()){
                        b.setText("HIT");
                        b.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,20));
                        b.setStyle("-fx-background-color: MediumSeaGreen");
                        b.setDisable(true);
                    } else{
                        b.setText(" X ");
                        b.setFont(Font.font("Arial", FontWeight.EXTRA_BOLD,24));
                        b.setStyle("-fx-background-color: IndianRed");
                        b.setDisable(true);
                    }
                    // end game if that was the last hit
                    if(newGame.isGameOver()){
                        if(newGame.getActivePlayer()==newGame.getPlayer1()){
                            p1SceneNameLabel.setText(newGame.getPlayer1().getName()+", You Won!!!");
                            p1SceneNameLabel.setTextFill(Color.GREEN);
                            btP1ShowOriginal.setVisible(true);
                        } else {
                            p2SceneNameLabel.setText(newGame.getPlayer2().getName()+", You Won!!!");
                            p2SceneNameLabel.setTextFill(Color.GREEN);
                            btP2ShowOriginal.setVisible(true);
                        }
                        gp.setDisable(true);
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
                gp.add(b, j+1, i+1);

                index++;
            }
        }
    }

    //CONNECTION DATABASE
    public void buildData(){
        Connection c ;
        data = FXCollections.observableArrayList();
        try{
            c = GameDB.dbCon();
            //SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = "SELECT CONCAT(game_id,'') AS Game, CONCAT(player_name,'') AS Player, " +
                    "CONCAT(aiming_accuracy, '%') AS Score, CONCAT(date,'') AS Date " +"FROM battleshipdb.scores " +
                    "ORDER BY aiming_accuracy DESC";
            //ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             **********************************/
            for(int i=0 ; i<rs.getMetaData().getColumnCount(); i++){
                //We are using non property style for making dynamic table
                final int j = i;
                TableColumn col = new TableColumn(rs.getMetaData().getColumnName(i+1));
                col.setCellValueFactory((Callback<TableColumn.CellDataFeatures<ObservableList, String>,
                        ObservableValue<String>>) param -> new SimpleStringProperty(param.getValue().get(j).toString()));

                tableview.getColumns().addAll(col);
            }

            /********************************
             * Data added to ObservableList *
             ********************************/
            while(rs.next()){
                //Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for(int i=1 ; i<=rs.getMetaData().getColumnCount(); i++){
                    //Iterate Column
                    row.add(rs.getString(i));
                }
                data.add(row);
            }

            //FINALLY ADDED TO TableView
            tableview.setItems(data);
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("Error on Building ScoreBoard Data");
        }
    }


    @Override
    public void start(Stage primaryStage) {

        newGame = new Game();

        //TableView
        tableview = new TableView();
        buildData();

        // constructing primary stage
        Image welcomeImage = new Image("https://bit.ly/2CXAWUs");
        ImageView selectedImage = new ImageView(welcomeImage);

        primaryStage.setTitle("Battleships Game");
        primaryStage.getIcons().add(new Image("https://bit.ly/2pAUQkY"));
        primaryStage.setResizable(false);

        Button btNewGame = new Button ( "New Game" ) ;
        Button btShowScores = new Button ( "Show Scores Board" ) ;
        Button btExit = new Button ( "Exit" ) ;


        VBox WelcomeRoot = new VBox (20 , selectedImage, btNewGame , btShowScores, btExit) ;
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
        Label p2Namelabel = new Label("Player2 Name:");
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
        inputInfoLabel.setTextFill(Color.RED);

        VBox inputRoot = new VBox(30 , inputGrid, inputInfoLabel, btStartGame , btCancel);
        inputRoot.setAlignment(Pos.CENTER);

        // input scene asks players to enter their names and start playing or go back to main (welcome) window/scene
        inputScene = new Scene( inputRoot , 400 , 350);

        // setting-up a grid of cells for the player1 to play
        GridPane p1GuiGrid = new GridPane();
        p1GuiGrid.setPadding(new Insets(10,10,10,10));
        p1GuiGrid.setVgap(1);
        p1GuiGrid.setHgap(1);
        p1GuiGrid.setAlignment(Pos.CENTER);

        buildPlayerGrid(newGame.getLookupGrid(), p1GuiGrid, primaryStage);

        Button btP1QuitGame = new Button ( "Quit Game!" ) ;

        btP1ShowOriginal = new Button("Show Original Map");
        btP1ShowOriginal.setOnAction((ActionEvent event) -> {
            originalStage.show();
            originalStage.centerOnScreen();
        });
        btP1ShowOriginal.setVisible(false);

        // setting-up a grid of cells for the player2 to play
        GridPane p2GuiGrid = new GridPane();
        p2GuiGrid.setPadding(new Insets(10,10,10,10));
        p2GuiGrid.setVgap(1);
        p2GuiGrid.setHgap(1);
        p2GuiGrid.setAlignment(Pos.CENTER);

        buildPlayerGrid(newGame.getLookupGrid(), p2GuiGrid, primaryStage);

        Button btP2QuitGame = new Button ( "Quit Game!" ) ;

        btP2ShowOriginal = new Button("Show Original Map");
        btP2ShowOriginal.setOnAction((ActionEvent event) -> {
            originalStage.show();
            originalStage.centerOnScreen();
        });
        btP2ShowOriginal.setVisible(false);

        btNewGame.setOnAction((ActionEvent event) ->{
            primaryStage.setScene(inputScene);
            primaryStage.centerOnScreen();
            p1NameInput.setText("");
            p2NameInput.setText("");
        });


        btExit.setOnAction((ActionEvent event) -> primaryStage.close());

        p1SceneNameLabel = new Label();
        p2SceneNameLabel = new Label();


        VBox p1VRoot = new VBox(10, p1SceneNameLabel, p1GuiGrid, btP1ShowOriginal, btP1QuitGame);
        //p1VRoot.setStyle("-fx-background-color:#FDF877;");
        p1VRoot.setAlignment(Pos.CENTER);
        p1Scene = new Scene( p1VRoot , 900 , 930);

        VBox p2VRoot = new VBox(10,  p2SceneNameLabel, p2GuiGrid, btP2ShowOriginal, btP2QuitGame);
        p2VRoot.setAlignment(Pos.CENTER);
        p2VRoot.setStyle("-fx-background-color:#C4F1CE;");
        p2Scene = new Scene ( p2VRoot , 900 , 930) ;

        btStartGame.setOnAction((ActionEvent event) ->{
            if (p1NameInput.getText().trim().isEmpty() || p2NameInput.getText().trim().isEmpty() ||
                    p2NameInput.getText().trim().toUpperCase().equals(p1NameInput.getText().trim().toUpperCase())) {
                inputInfoLabel.setText("Players' names can't be empty or equal!");
            }
            else if (p1NameInput.getText().trim().length()>15 || p2NameInput.getText().trim().length()>15){
                inputInfoLabel.setText("A Player's name can't be more than 15 letters long!");
            }
            else {
                // create a label to display the name of player
                newGame.getPlayer1().setName(p1NameInput.getText().trim());
                newGame.getPlayer2().setName(p2NameInput.getText().trim());
                p1SceneNameLabel.setText(p1NameInput.getText());
                p1SceneNameLabel.setFont(Font.font("Arial", FontWeight.BOLD,40));

                // create a label to display the name of player2
                p2SceneNameLabel.setText(p2NameInput.getText());
                p2SceneNameLabel.setFont(Font.font("Arial", FontWeight.BOLD,40));

                primaryStage.setScene(p1Scene);
                primaryStage.centerOnScreen();

            }
        });

        btShowScores.setOnAction((ActionEvent event) -> scoreBoardStage.show());

        btCancel.setOnAction((ActionEvent event) ->{
            primaryStage.setScene(welcomeScene);
            primaryStage.centerOnScreen();
        });

        btP1QuitGame.setOnAction((ActionEvent event) ->{
            if(newGame.isGameOver()){
                gameOverStatsLabel.setText(newGame.getInfoMessage());
                primaryStage.setScene(gameOverScene);
            } else {
                newGame.setActivePlayer(newGame.getPlayer1());
                newGame.play("QUIT");
                if (newGame.getPlayer2().isStillIn()) {
                    primaryStage.setScene(p2Scene);
                } else {
                    gameOverStatsLabel.setText(newGame.getInfoMessage());
                    primaryStage.setScene(gameOverScene);
                }
            }
            primaryStage.centerOnScreen();
        });

        btP2QuitGame.setOnAction((ActionEvent event) ->{
            if(newGame.isGameOver()){
                gameOverStatsLabel.setText(newGame.getInfoMessage());
                primaryStage.setScene(gameOverScene);
            } else {
                newGame.setActivePlayer(newGame.getPlayer2());
                newGame.play("QUIT");
                if (newGame.getPlayer1().isStillIn()) {
                    primaryStage.setScene(p1Scene);
                } else {
                    gameOverStatsLabel.setText(newGame.getInfoMessage());
                    primaryStage.setScene(gameOverScene);
                }
            }
            primaryStage.centerOnScreen();
        });

        Label gameOverLabel = new Label("*** Game Over ***");
        gameOverLabel.setFont(Font.font("Arial", FontWeight.BOLD,46));

        gameOverStatsLabel = new Label();
        gameOverStatsLabel.setFont(Font.font("Arial", FontWeight.NORMAL,26));
        gameOverStatsLabel.setWrapText(true);
        gameOverStatsLabel.setTextAlignment(TextAlignment.CENTER);
        gameOverStatsLabel.setAlignment(Pos.CENTER);

        Button btShowOriginal = new Button("Show Original Map");
        btShowOriginal.setOnAction((ActionEvent event) -> {
            originalStage.show();
            originalStage.centerOnScreen();
        });


        Button gameOverExitBT = new Button("Exit Game");
        gameOverExitBT.setOnAction((ActionEvent event) -> primaryStage.close());

        VBox gameOverVRoot = new VBox(20,gameOverLabel, gameOverStatsLabel, btShowOriginal, gameOverExitBT);
        gameOverVRoot.setAlignment(Pos.CENTER);
        gameOverScene = new Scene( gameOverVRoot , 600 , 400);

        // setting-up a window to show original ships map after a player had won the game
        originalStage = new Stage();
        originalStage.setTitle("Battleships Game");
        originalStage.getIcons().add(new Image("https://bit.ly/2pAUQkY"));
        originalStage.setResizable(false);

        GridPane originalGrid = new GridPane();
        originalGrid.setPadding(new Insets(10,10,10,10));
        originalGrid.setVgap(1);
        originalGrid.setHgap(1);
        originalGrid.setAlignment(Pos.CENTER);

        Label originalLabel = new Label("Original Game Map");
        originalLabel.setFont(Font.font("Arial", FontWeight.BOLD,46));
        originalLabel.setTextFill(Color.BLUEVIOLET);

        Button btCloseOriginal = new Button("Close");
        btCloseOriginal.setOnAction((ActionEvent event) -> originalStage.close());

        VBox originalVRoot1 = new VBox(20,  originalLabel, originalGrid, btCloseOriginal);
        originalVRoot1.setAlignment(Pos.CENTER);

        // legend labels
        Label originalBATLabel = new Label("BAT: Battleship");
        originalBATLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        originalBATLabel.setTextFill(Color.BLUEVIOLET);
        originalBATLabel.setTextAlignment(TextAlignment.LEFT);

        Label originalCRULabel = new Label("CRU: Cruiser");
        originalCRULabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        originalCRULabel.setTextFill(Color.BLUEVIOLET);
        originalCRULabel.setTextAlignment(TextAlignment.LEFT);

        Label originalDESLabel = new Label("DES: Destroyer");
        originalDESLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        originalDESLabel.setTextFill(Color.BLUEVIOLET);
        originalDESLabel.setTextAlignment(TextAlignment.LEFT);

        Label originalSUBLabel = new Label("SUB: Submarine");
        originalSUBLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        originalSUBLabel.setTextFill(Color.BLUEVIOLET);
        originalSUBLabel.setTextAlignment(TextAlignment.LEFT);

        VBox originalVRoot2 = new VBox(20,  originalBATLabel, originalCRULabel, originalDESLabel, originalSUBLabel);
        originalVRoot2.setAlignment(Pos.CENTER_LEFT);

        HBox originalHRoot = new HBox(10, originalVRoot1, originalVRoot2);
        originalHRoot.setAlignment(Pos.CENTER);

        Scene originalScene = new Scene(originalHRoot, 1000, 920);
        originalStage.setScene(originalScene);

        buildPlayerGrid(newGame.getDeployedGrid(), originalGrid, originalStage);


        //Score Board Scene
        Scene scoreBoardScene = new Scene(tableview,400,500);
        scoreBoardStage = new Stage();
        scoreBoardStage.setScene(scoreBoardScene);
        scoreBoardStage.setTitle("Battleships Score Board");
        scoreBoardStage.getIcons().add(new Image("https://bit.ly/2pAUQkY"));
        scoreBoardStage.setResizable(false);

        primaryStage.show();
    }

    public static void main(String[] args) {

        Application.launch(args);

    }

}
