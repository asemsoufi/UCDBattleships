import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;


public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Player winner;
    private Grid lookupGrid;
    private Grid deployedGrid;
    private boolean gameOver;
    private boolean isAHit;
    private String infoMessage;



    public Game(){

        player1 = new Player();
        player2 = new Player();

        activePlayer = player1;

        infoMessage = "Please click a cell from the grid below to make a shot!";

        lookupGrid = new Grid();

        deployedGrid = new Grid();

        deployedGrid.deployShips();

        //System.out.println(deployedGrid.getTargetCells().toString());

        // give each player his own copy of similar target cells
        player1.setTargetCells(deployedGrid.getTargetCells());
        player2.setTargetCells(deployedGrid.getTargetCells());

        gameOver = false;
    }

    public int winnerScore(){
        return (int)((float)winner.getHitCounter()/(float)winner.shots() * 100);
    }

    public void switchPlayer(){
        if (activePlayer==player1 && player2.isStillIn()){
            activePlayer = player2;
        }
        else if(activePlayer==player2 && player1.isStillIn()) {
            activePlayer = player1;
        }
        System.out.println();
    }

    public boolean itIsAHit(){
        return isAHit;
    }


    public String gameStats(){
        String s1 = winner.getName()+" made "+winner.shots()+(winner.shots()==0?" shot.\n":" shots.\n");
        String s2 = winner.shots()==0? "" : winner.getName()+"'s aiming accuracy was "+ winnerScore() + "%";
        return s1+s2;
    }

    public Player getPlayer1() {
        return player1;
    }


    public Player getPlayer2() {
        return player2;
    }


    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player p) {
        this.activePlayer = p;
    }


    public Grid getLookupGrid() {
        return lookupGrid;
    }

    public Grid getDeployedGrid() {
        return deployedGrid;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String getInfoMessage(){
        return infoMessage;
    }

    public void play(String currentGuess){

        if (!gameOver && (player1.isStillIn() || player2.isStillIn())) {

            activePlayer.getGrid().plot();
            System.out.println();

            if (currentGuess.equals("QUIT")){

                activePlayer.notStillIn();
                switchPlayer();

            } else if ((activePlayer.getMyTargetCells().contains(currentGuess)) && !gameOver){
                isAHit = true;
                activePlayer.incHitCounter();
                activePlayer.getGrid().markHit(lookupGrid.getCells().indexOf(currentGuess));
                activePlayer.getGrid().plot();
                activePlayer.getMyTargetCells().remove(currentGuess);
                if (activePlayer.getMyTargetCells().size() == 0){
                    winner = activePlayer;

                    System.out.println("Congratulations "+activePlayer.getName()+", you made it :)");
                    infoMessage = "Congratulations "+activePlayer.getName()+", you made it :)\n"+gameStats();

                    gameOver=true;
                } else {
                    activePlayer.getGrid().plot();

                    System.out.println("Wow, it's a hit, well done! Keep playing.");
                    infoMessage = "Wow, it's a hit, well done! Keep playing.";
                }

            } else {
                isAHit = false;
                System.out.println("Sorry "+activePlayer.getName()+", you missed!");
                infoMessage = "Sorry "+activePlayer.getName()+", you missed!";

                activePlayer.incMissCounter();
                activePlayer.getGrid().markMiss(lookupGrid.getCells().indexOf(currentGuess));
                System.out.println();
                switchPlayer();
            }
        }

        if(gameOver || (!player1.isStillIn() && !player2.isStillIn())) {
            System.out.println("Game Over!");
            if (winner==player1 || winner==player2) {
                System.out.println("And The winner is " + winner.getName());
                System.out.println("Below is your game summary:");
                System.out.println(gameStats());
                infoMessage = "The winner is "+winner.getName()+".\n"+gameStats();
                try{
                    Connection con = GameDB.dbCon();
                    Statement stmt = GameDB.dbCon().createStatement();
                    String createScoresTable = "CREATE TABLE IF NOT EXISTS battleshipdb."+
                            "scores(game_id INT(11) NOT NULL,player_name VARCHAR(45) NOT NULL,"+
                    "aiming_accuracy INT(11) NOT NULL, date"+
                            " DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,"+
                    "PRIMARY KEY (game_id))";
                    stmt.executeUpdate(createScoresTable);

                    PreparedStatement insertWinnerData = con.prepareStatement("INSERT INTO battleshipdb.scores (player_name, aiming_accuracy) VALUES (?,?)");
                    insertWinnerData.setString(1, winner.getName());
                    insertWinnerData.setInt(2,winnerScore());
                    insertWinnerData.executeUpdate();

                    infoMessage = "The winner is "+winner.getName()+".\n"+gameStats()+"\n"+
                    "This score has been recorded.";
                } catch(Exception ex) {
                    System.out.println("Something went wrong with the database!");
                    ex.printStackTrace();
                }

            } else if (!player1.isStillIn() && !player2.isStillIn()) {
                infoMessage = "There is no winner!\n Both players quit the game early :(";
                System.out.println(infoMessage);
            }

            System.out.println("Below is the original battleships distribution map, have a look ;)");
            deployedGrid.plot();
        }
    }
}
