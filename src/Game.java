import java.util.ArrayList;

public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Grid mainGrid;
    private ArrayList<String> availableCells;
    private ArrayList<String> targetCells;
    //private Ship battleship, cruiser, distroyer, submarien;
    private ArrayList<Ship> ships;
    private final String[][] noOfShips = {{"Battleship","2"},{"Cruiser","3"},
            {"Distroyer","4"},{"Submarine","5"}};


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


    }

    public void deployShips(){
        //ArrayList<String> tempList = new ArrayList<>();
        //randomly generate and add ships to the grid
        for (String[] aShip : noOfShips) {
            switch(aShip[0].toUpperCase()){
                case "BATTLESHIP":
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        int rand = (int) (Math.random());
                        if(rand <0.5){
                            // create and place Battleship horizontally
                            Ship bat = new Battleship(getCellsHorizontal(4));
                            ships.add(bat);
                            //mainGrid.markShip(bat);
                        } else{
                            // create and place Battleship vertically
                            Ship bat = new Battleship(getCellsHorizontal(4));
                            ships.add(bat);
                            //mainGrid.markShip(bat);
                        }
                    }
                    break;
                case "CRUISER":
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        int rand = (int) (Math.random());
                        if(rand <0.5){
                            // create and place Cruiser horizontally
                            Ship cru = new Cruiser(getCellsHorizontal(3));
                            ships.add(cru);
                            //mainGrid.markShip(cru);
                        } else{
                            // create and place Cruiser vertically
                            Ship cru = new Cruiser(getCellsHorizontal(3));
                            ships.add(cru);
                            //mainGrid.markShip(cru);
                        }
                    }
                    break;
                case "DISTROYER":
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        int rand = (int) (Math.random());
                        if(rand <0.5){
                            // create and place Distroyer horizontally
                            Ship dis = new Distroyer(getCellsHorizontal(2));
                            ships.add(dis);
                            //mainGrid.markShip(dis);
                        } else{
                            // create and place Distroyer vertically
                            Ship dis = new Distroyer(getCellsHorizontal(2));
                            ships.add(dis);
                            //mainGrid.markShip(dis);
                        }
                    }
                    break;
                default:
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        // create and place Submarine, no orientation applies here
                        Ship sub = new Submarine(getCellsHorizontal(1));
                        ships.add(sub);
                        //mainGrid.markShip(sub);
                    }
            }
        }
        // mark ships on the main grid
        for(Ship ship : ships){
            mainGrid.markShip(ship);
        }
    }

    public ArrayList<String> getCellsHorizontal(int numberOfCells){
        //System.out.println("method getCellsHorizontal started");
        checkNumOfCells(numberOfCells);
        boolean success = false;
        ArrayList<String> tempHorArray = new ArrayList<>();
        while(!success) {
            //System.out.println("trying to get cells horizontally");
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
                //System.out.println("Success, here are the cells I picked "+tempHorArray.toString());
            } else {
                //System.out.println("failed this time, will try again..");
                tempHorArray.clear();
            }
        }
        availableCells.removeAll(tempHorArray);
        targetCells.addAll(tempHorArray);
        return tempHorArray;
    }

    public ArrayList<String> getCellsVertical(int numberOfCells){
        //System.out.println("method getCellsVertical started");
        checkNumOfCells(numberOfCells);
        boolean success = false;
        ArrayList<String> tempVerArray = new ArrayList<>();
        while(!success) {
            //System.out.println("trying to get cells vertically");
            // pick a random column (A-J)
            int columnIndex = (int) (Math.random() * 9);
            String columnLetter = mainGrid.getColumns()[columnIndex];
            //System.out.println("I picked column "+columnLetter);
            // pick a random row (1-10), but considering length of ship(i.e. number of cells needed vertically)
            int rowNumber = (int) (Math.random() * (10 - numberOfCells)) + 1;
            for(int i = rowNumber; i< rowNumber + numberOfCells; i++){
                tempVerArray.add(columnLetter + i);
                //System.out.println("Adding cell "+(columnLetter + i)+" vertically to the grid.");
            }
            if (availableCells.containsAll(tempVerArray) && tempVerArray.size()==numberOfCells){
                success = true;
                //System.out.println("Success, here are the cells I picked "+tempVerArray.toString());
            } else {
                //System.out.println("failed this time, will try again..");
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

        thisGame.deployShips();

        thisGame.mainGrid.plot();

    }

}
