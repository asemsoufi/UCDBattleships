import java.util.ArrayList;

public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Grid mainGrid;


    public Game(){
        System.out.println("Getting first player's name.");
        player1 = new Player();
        System.out.println("Getting second player's name.");
        player2 = new Player();

        activePlayer = player1;

        mainGrid = new Grid();

        mainGrid.deployShips();
    }

    public void switchPlayer(){
        if(activePlayer == player1){
            activePlayer = player2;
        } else {
            activePlayer = player2;
        }
    }

    public void play(){
        // implement here
    }

    public static void main(String[] args) {

        System.out.println("Game is on..");
        Game thisGame = new Game();
        //System.out.println("First player is: "+thisGame.player1.getName());
        //System.out.println("Second player is: "+thisGame.player2.getName());
       //System.out.println("Active player is: "+thisGame.activePlayer.getName());

        thisGame.mainGrid.plot();

    }

}
