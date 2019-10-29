
public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Grid grid;


    public Game(){
        System.out.println("Getting first player's name.");
        player1 = new Player();
        System.out.println("Getting second player's name.");
        player2 = new Player();

        activePlayer = player1;

        grid = new Grid();

        grid.deployShips();
    }

    public void switchPlayer(){
        if(activePlayer == player1){
            activePlayer = player2;
        } else {
            activePlayer = player2;
        }
    }

    public void getGrid(){
        grid.plot();
    }

    public void play(){
        // implement here
    }
}
