import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Grid mainGrid;
    private ArrayList<String> availableCells;
    private Ship battleship, cruiser, distroyer, submarien;
    private Ship[] ships;

    public Game(){
        System.out.println("Getting first player's name.");
        player1 = new Player();
        System.out.println("Getting secondAseAs player's name.");
        player2 = new Player();

        activePlayer = player1;

        mainGrid = new Grid();
        ships = new Ship[4];

        availableCells = new ArrayList<>();
        availableCells.addAll(mainGrid.getCells());

        //randomly generate and add ships to the grid
        for (int i = 0; i < ships.length; i++) {
            double ran = Math.random();
            if(ran <0.5){
                //pick cells horizontally
                //........
            } else{
                //pick cells vertically
                //.......
            }

            //mainGrid.markShip();
        }

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
        /*Game thisGame = new Game();
        System.out.println("First player is: "+thisGame.player1.getName());
        System.out.println("Second player is: "+thisGame.player2.getName());
        System.out.println("Active player is: "+thisGame.activePlayer.getName());
        System.out.println(thisGame.availableCells);*/
        String[] s = {"A1", "B1", "C1"};
        Ship ship = new Cruiser(s);
        System.out.println(ship.toString());


    }

}
