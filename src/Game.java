//import java.io.IOException;
import java.sql.SQLOutput;


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

        System.out.println(deployedGrid.getTargetCells().toString());

        // give each player his own copy of similar target cells
        player1.setTargetCells(deployedGrid.getTargetCells());
        player2.setTargetCells(deployedGrid.getTargetCells());

        gameOver = false;
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


    public String gameStats(Player p){
        String s1 = p.getName()+" made "+p.shots()+(p.shots()==0?" shot.\n":" shots.\n");
        String s2 = p.shots()==0? "" : p.getName()+"'s aiming accuracy was "+
                (int)((float)p.getHitCounter()/(float)p.shots() * 100) + "%";
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

                System.out.println(activePlayer.getName()+", thank you for playing. Your stats are:\n"+
                        gameStats(activePlayer));
                infoMessage = activePlayer.getName()+", thank you for playing. Your stats are:\n"+
                gameStats(activePlayer);

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
                    infoMessage = "Congratulations "+activePlayer.getName()+", you made it :)\n"+gameStats(winner);

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
                System.out.println(gameStats(winner));
                infoMessage = "The winner is "+winner.getName()+".\n"+gameStats(winner);
            } else if (!player1.isStillIn() && !player2.isStillIn()) {
                infoMessage = "There is no winner!\n Both players quit the game early :(";
                System.out.println(infoMessage);
            }

            System.out.println("Below is the original battleships distribution map, have a look ;)");
            deployedGrid.plot();
        }
    }
}
