import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Grid mainGrid;
    private ArrayList<String> availableCells;
    private ArrayList<String> targetCells;
    private Ship battleship, cruiser, distroyer, submarien;
    private ArrayList<Ship> ships;
    private final int noOfShips = 10;  // 1 bat, 2 cruz, 3 dist, 4 sub

    public Game(){
        System.out.println("Getting first player's name.");
        player1 = new Player();
        System.out.println("Getting secondAseAs player's name.");
        player2 = new Player();

        activePlayer = player1;

        mainGrid = new Grid();
        ships = new ArrayList<>();

        availableCells = new ArrayList<>();
        availableCells.addAll(mainGrid.getCells());

        targetCells = new ArrayList<>();

        //randomly generate and add ships to the grid
        for (int i = 0; i < noOfShips; i++) {
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

    public ArrayList<String> getCellsHorizontal(int numberOfCells){
        System.out.println("method getCellsHorizontal started");
        checkNumOfCells(numberOfCells);
        boolean success = false;
        ArrayList<String> tempHorArray = new ArrayList<>();
        while(!success) {
            System.out.println("trying to get cells horizontally");
            // pick a random row (1-10)
            int rowNumber = (int) (Math.random() * (9)) + 1;
            // pick a random column (A-J), but considering length of ship(i.e. number of cells needed horizontally)
            int columnIndex = (int) (Math.random() * (9 - numberOfCells)) + 1;
            for(int j = columnIndex; j< (columnIndex + numberOfCells); j++){
                String columnLetter = mainGrid.getColumns()[j];
                tempHorArray.add(columnLetter + rowNumber);
            }
            if (availableCells.containsAll(tempHorArray) && tempHorArray.size()==numberOfCells){
                success = true;
                System.out.println("Success, here are the cells I picked "+tempHorArray.toString());
            } else {
                System.out.println("failed this time, will try again..");
                tempHorArray.clear();
            }
        }
        availableCells.removeAll(tempHorArray);
        targetCells.addAll(tempHorArray);
        return tempHorArray;
    }

    public ArrayList<String> getCellsVertical(int numberOfCells){
        System.out.println("method getCellsVertical started");
        checkNumOfCells(numberOfCells);
        boolean success = false;
        ArrayList<String> tempVerArray = new ArrayList<>();
        while(!success) {
            System.out.println("trying to get cells vertically");
            // pick a random column (A-J)
            int columnIndex = (int) (Math.random() * 9);
            String columnLetter = mainGrid.getColumns()[columnIndex];
            System.out.println("I picked column "+columnLetter);
            // pick a random row (1-10), but considering length of ship(i.e. number of cells needed vertically)
            int rowNumber = (int) (Math.random() * (10 - numberOfCells)) + 1;
            for(int i = rowNumber; i< rowNumber + numberOfCells; i++){
                tempVerArray.add(columnLetter + i);
                System.out.println("Adding cell "+(columnLetter + i)+" vertically to the grid.");
            }
            if (availableCells.containsAll(tempVerArray) && tempVerArray.size()==numberOfCells){
                success = true;
                System.out.println("Success, here are the cells I picked "+tempVerArray.toString());
            } else {
                System.out.println("failed this time, will try again..");
                tempVerArray.clear();
            }
        }
        availableCells.removeAll(tempVerArray);
        targetCells.addAll(tempVerArray);
        return tempVerArray;
    }

    private void checkNumOfCells(int numberOfCells) {
        if(numberOfCells<1 || numberOfCells>4 || availableCells.size()<numberOfCells) {
            throw new IllegalArgumentException("The grid can't assign this number of cells.");
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
        Game thisGame = new Game();
        System.out.println("First player is: "+thisGame.player1.getName());
        System.out.println("Second player is: "+thisGame.player2.getName());
        System.out.println("Active player is: "+thisGame.activePlayer.getName());
        System.out.println(thisGame.availableCells.size());

        Ship ship = new Cruiser(thisGame.getCellsHorizontal(3));
        System.out.println(ship.toString());
        System.out.println();

        Ship ship1 = new Cruiser(thisGame.getCellsHorizontal(3));
        System.out.println(ship1.toString());

        ArrayList<String> s2 = thisGame.getCellsHorizontal(4);
        Ship bat = new Battleship(s2);
        System.out.println(bat.toString());

        Ship ship4 = new Distroyer(thisGame.getCellsVertical(2));
        System.out.println(ship4.toString());

        Ship ship5 = new Distroyer(thisGame.getCellsHorizontal(2));
        System.out.println(ship5.toString());

        Ship ship3 = new Distroyer(thisGame.getCellsHorizontal(2));
        System.out.println(ship3.toString());

        Ship ship6 = new Battleship(thisGame.getCellsVertical(4));
        System.out.println(ship6.toString());

        Ship ship7 = new Cruiser(thisGame.getCellsVertical(3));
        System.out.println(ship7.toString());

        System.out.println("Remaining cells:");
        System.out.println(thisGame.availableCells.size());
        System.out.println("Assigned cells:");
        System.out.println(thisGame.targetCells);

        System.out.println("Grid cells:");
        System.out.println(thisGame.mainGrid.getCells().toString());
        System.out.println(thisGame.mainGrid.getCells().size());

        thisGame.mainGrid.markShip(ship);
        thisGame.mainGrid.markShip(ship1);
        thisGame.mainGrid.markShip(bat);
        thisGame.mainGrid.markShip(ship3);
        thisGame.mainGrid.markShip(ship4);
        thisGame.mainGrid.markShip(ship5);
        thisGame.mainGrid.markShip(ship6);
        thisGame.mainGrid.markShip(ship7);

        thisGame.mainGrid.plot();

    }

}
