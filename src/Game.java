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


    /**
     * Constructs a new Game and all required non-GUI objects
     */
    public Game(){

        player1 = new Player();
        player2 = new Player();

        activePlayer = player1;

        infoMessage = "";

        lookupGrid = new Grid();

        deployedGrid = new Grid();

        deployedGrid.deployShips();

        //System.out.println(deployedGrid.getTargetCells().toString()); //used for testing

        // give each player his own copy of identical target cells
        player1.setTargetCells(deployedGrid.getTargetCells());
        player2.setTargetCells(deployedGrid.getTargetCells());

        gameOver = false;
    }

    /**
     * Calculates and returns the winner's score
     * @return winner's score calculated as the ration of the number of hits to the number of shots as a percentage
     */
    public int winnerScore(){
        return (int)((float)winner.getHitCounter()/(float)winner.shots() * 100);
    }

    /**
     * Switches turns between the two players as long as both are playing
     */
    public void switchPlayer(){
        if (activePlayer==player1 && player2.isStillIn()){
            activePlayer = player2;
        }
        else if(activePlayer==player2 && player1.isStillIn()) {
            activePlayer = player1;
        }
        System.out.println();
    }

    /**
     * Returns true if player's selection/click was a successful hit, otherwise returns false
     * @return true if a hit, otherwise return false
     */
    public boolean itIsAHit(){
        return isAHit;
    }

    /**
     * Used to deliver basic game statistics about the game to the winner and his performance
     * @return a string showing number of shots made by the winner and his score
     */
    public String gameStats(){
        String s1 = winner.getName()+" made "+winner.shots()+(winner.shots()==0?" shot.\n":" shots.\n");
        String s2 = winner.shots()==0? "" : winner.getName()+"'s aiming accuracy was "+ winnerScore() + "%";
        return s1+s2;
    }

    /**
     * Returns first player
     * @return a Player1
     */
    public Player getPlayer1() {
        return player1;
    }

    /**
     * Returns second player
     * @return a Player2
     */
    public Player getPlayer2() {
        return player2;
    }

    /**
     * Returns active player, that is the player who currently has the turn to play
     * @return activePlayer
     */
    public Player getActivePlayer() {
        return activePlayer;
    }

    /**
     * Sets the active player when there is a need to swithch turns
     * @param p will be set as the active player
     */
    public void setActivePlayer(Player p) {
        this.activePlayer = p;
    }

    /**
     * Returns an empty grids board cells to be used to assign buttons references in the GUI interface,
     * and to use a selected cell reference to see if it matches any of the assigned cells to mark a hit
     * @return an ArrayList of all 100 generic cells references, i.e. [A1,..., J10]
     */
    public Grid getLookupGrid() {
        return lookupGrid;
    }

    /**
     * Returns deployed grids board to be used to assign buttons references in the GUI interface of original game map,
     * which shows in a visual way how ships were deployed by the game with string abbreviations representing ships,
     * and it will only be shown upon completing the game or quitting it by both players
     * @return an ArrayList of all 100 cells references, including deployed ones shown as abbreviated strings of 3 letters,
     * e.g. [A1, DES, DES, DES, E1, SUB,.. etc]
     */
    public Grid getDeployedGrid() {
        return deployedGrid;
    }

    /**
     * Marks the end of the game upon beig won by one of the two players, or quited by both before completing it
     * @return true if game is over, otherwise return false and the game will continue
     */
    public boolean isGameOver() {
        return gameOver;
    }

    /**
     * Returns information message to be used by the GUI interface at the end of the game to display useful info
     * @return a string concatenating game statistics and a confirmation if a score has been recorded in the database
     */
    public String getInfoMessage(){
        return infoMessage;
    }

    /**
     * Evaluates active player's selection/click to see if he made a hit, missed, or quit the game,
     * and then switches turns or marks game as over as needed
     * @param currentGuess is a string representation of the player's selection/click
     */
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
                infoMessage = "There is no winner!\nBoth players quit the game early :(";
                System.out.println(infoMessage);
            }

            System.out.println("Below is the original battleships distribution map, have a look ;)");
            deployedGrid.plot();
        }
    }
}
