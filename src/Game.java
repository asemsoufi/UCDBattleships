import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Grid mainGrid;
    private ArrayList<String> availableCells;
    private Ship battleship, cruiser, distroyer, submarien;
    private Ship[] ships;

    public Game(){
        player1 = new Player();
        player2 = new Player();

        mainGrid = new Grid();
        ships = new Ship[4];

        availableCells = new ArrayList<>();
        for(String s : mainGrid.getCells())
            availableCells.add(s);

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

    public static void main(String[] args) {

        System.out.println("Game is on..");

    }

}
