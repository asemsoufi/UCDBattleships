import java.util.ArrayList;

public class Grid {

    private final String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private final String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private ArrayList<String> cells;
    private ArrayList<String> availableCells;
    private ArrayList<String> targetCells;
    private final String[][] noOfShips = {{"Battleship","2"},{"Cruiser","3"},
            {"Destroyer","4"},{"Submarine","5"}};
    private ArrayList<Ship> ships;

    /**
     * Constructs a grid object, basically a list of 100 strings representing 100 cells in a 10x10 grids board
     * i.e. [A1, B1, ...., J10]
     * It them makes these cells available for the random selection process of the cells that will be used to deploy ships
     * Ships will then be added to a list to be referred to when needed
     */
    public Grid(){
        cells = new ArrayList<>();
        for (int i = 0; i < rows.length; i++){
            for (int j = 0; j < columns.length; j++){
                cells.add(columns[j]+rows[i]);
            }
        }
        availableCells = new ArrayList<>();
        availableCells.addAll(getCells());
        targetCells = new ArrayList<>();
        ships = new ArrayList<>();
    }

    /**
     * Deploys ships by randomly selecting from available cells and assigning them to the ships on the game menu
     * <p>
     * This random process addresses cells locations and orientation.
     * At the end of the process all deployed ships will be marked on the original grids board to be displayed when appropriate
     * Also assigned cells will be added to the target cells list (a total of 30 cells representing all ships)
     */
    public void deployShips(){

        //randomly generate and add ships to the grid
        for (String[] aShip : noOfShips) {
            switch(aShip[0].toUpperCase()){
                case "BATTLESHIP":
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        double rand = Math.random();
                        if(rand >0.5){
                            // create and place Battleship horizontally
                            Ship bat = new Battleship(getCellsHorizontal(4));
                            ships.add(bat);

                        } else{
                            // create and place Battleship vertically
                            Ship bat = new Battleship(getCellsVertical(4));
                            ships.add(bat);

                        }
                    }
                    break;
                case "CRUISER":
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        double rand = Math.random();
                        if(rand >0.5){
                            // create and place Cruiser horizontally
                            Ship cru = new Cruiser(getCellsHorizontal(3));
                            ships.add(cru);

                        } else{
                            // create and place Cruiser vertically
                            Ship cru = new Cruiser(getCellsVertical(3));
                            ships.add(cru);

                        }
                    }
                    break;
                case "DESTROYER":
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        double rand = Math.random();
                        if(rand >0.5){
                            // create and place Destroyer horizontally
                            Ship des = new Destroyer(getCellsHorizontal(2));
                            ships.add(des);

                        } else{
                            // create and place Destroyer vertically
                            Ship des = new Destroyer(getCellsVertical(2));
                            ships.add(des);

                        }
                    }
                    break;
                default:
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        // create and place Submarine, no orientation applies here
                        Ship sub = new Submarine(getCellsVertical(1));
                        ships.add(sub);

                    }
            }
        }
        // mark ships on the main grid. This is for testing purposes only, and will be hidden from the players.
        for(Ship ship : ships){
            markShip(ship);
        }
    }

    /**
     * This method will be called when the random process of selecting cells decides to assign cells horizontally
     * @param numberOfCells is used to determine how many cells need to be assigned
     * @return the line of cells selected horizontally to the calling method to use it in deploying current ship
     */
    private ArrayList<String> getCellsHorizontal(int numberOfCells){
        // make sure a (valid & available) number of cells is passed to the method
        checkNumOfCells(numberOfCells);
        boolean success = false;    // this will be true once a ship has been deployed successfully
        ArrayList<String> tempHorArray = new ArrayList<>();
        // until successful, keep trying to find a sequence of cells on the grid to deploy next ship
        while(!success) {
            // trying to get cells horizontally
            // pick a random row (1-10)
            int rowNumber = (int) (Math.random() * (9)) + 1;
            // pick a random column (A-J), but also considering length of ship
            // (i.e. number of cells needed horizontally)
            int columnIndex = (int) (Math.random() * (9 - numberOfCells)) + 1;
            for(int j = columnIndex; j< (columnIndex + numberOfCells); j++){
                String columnLetter = getColumns()[j];
                tempHorArray.add(columnLetter + rowNumber);
            }
            // success, if picked cells are all available and match the size of selected ship
            if (availableCells.containsAll(tempHorArray) && tempHorArray.size()==numberOfCells){
                success = true;
                // Success, I picked required cells on the grid
            } else {
                // failed this time, will try again..
                // drop previously selected cells and start over clean
                tempHorArray.clear();
            }
        }
        // remove cells assigned to the new ship from available cells list,
        // so that it can't be accidentally re-assigned to another ship
        availableCells.removeAll(tempHorArray);
        // a new ship was added successfully to the grid and needs to be marked as possible target
        targetCells.addAll(tempHorArray);
        return tempHorArray;
    }

    /**
     * This method will be called when the random process of selecting cells decides to assign cells vertically
     * @param numberOfCells is used to determine how many cells need to be assigned
     * @return the line of cells selected vertically to the calling method to use it in deploying current ship
     */
    private ArrayList<String> getCellsVertical(int numberOfCells){
        // make sure a (valid & available) number of cells is passed to the method
        checkNumOfCells(numberOfCells);
        boolean success = false;
        ArrayList<String> tempVerArray = new ArrayList<>();
        while(!success) {
            // trying to get cells vertically
            // pick a random column (A-J)
            int columnIndex = (int) (Math.random() * 9);
            String columnLetter = getColumns()[columnIndex];
            // pick a random row (1-10), but also considering length of ship
            // (i.e. number of cells needed vertically)
            int rowNumber = (int) (Math.random() * (10 - numberOfCells)) + 1;
            for(int i = rowNumber; i< rowNumber + numberOfCells; i++){
                tempVerArray.add(columnLetter + i);
                //System.out.println("Adding cell "+(columnLetter + i)+" vertically to the grid.");
            }
            // success, if picked cells are all available and match the size of selected ship
            if (availableCells.containsAll(tempVerArray) && tempVerArray.size()==numberOfCells){
                success = true;
            } else {
                // failed this time, will try again..
                // drop previously selected cells and start over clean
                tempVerArray.clear();
            }
        }
        // remove cells assigned to the new ship,
        // so that it can't be accidentally re-assigned to another ship
        availableCells.removeAll(tempVerArray);
        // a new ship was added successfully to the grid and needs to be marked as possible target
        targetCells.addAll(tempVerArray);
        return tempVerArray;
    }

    // a method to make sure a (valid & available) number of cells is still available in the grid,
    // and more ships can be deployed
    private void checkNumOfCells(int numberOfCells) {
        if(numberOfCells<1 || numberOfCells>4 || availableCells.size()<numberOfCells) {
            throw new IllegalArgumentException("The grid can't assign this number of cells.");
        }
    }

    /**
     * Marks deployed ships on the map/grid, for testing purposes or at the end of the game,
     * but will be hidden from players until they had finished/quit playing
     * @param ship the ship object to be marked
     */

    private void markShip(Ship ship){
        for(String shipCell:ship.getBody()){
            if (ship instanceof Battleship) {
                cells.set(cells.indexOf(shipCell), "BAT");
            } else if (ship instanceof Cruiser) {
                cells.set(cells.indexOf(shipCell), "CRU");
            } else if (ship instanceof Destroyer) {
                cells.set(cells.indexOf(shipCell), "DES");
            } else if (ship instanceof Submarine) {
                cells.set(cells.indexOf(shipCell), "SUB");
            }
        }
    }

    /**
     * Marks a "HIT" string on the cell once a successful hit is made (terminal version)
     * @param cellNr is the reference of the cell that needs to be marked as a hit
     */
    void markHit(int cellNr){
        if(cellNr>=0 && cellNr < cells.size()) {
            cells.set(cellNr, "HIT");
        }
    }

    /**
     * Marks a Miss by printing " X " string on the cell once a miss is made (terminal version)
     * @param cellNr is the reference of the cell that needs to be marked as a miss
     */
    void markMiss(int cellNr){
        if(cellNr>=0 && cellNr < cells.size()) {
            cells.set(cellNr, "X");
        }
    }

    /**
     * Returns all available cells on the grid
     * @return a list of all 100 cell references
     */
    public ArrayList<String> getCells() {
        return cells;
    }

    /**
     * Returns all deployed cells on the grid (all cells that are part of a ship and are possible targets)
     * @return a list of all 30 target cells references
     */
    public ArrayList<String> getTargetCells() {
        return targetCells;
    }

    /**
     * Plots a grids board to the terminal (terminal version of the game)
     */
    public void plot(){
        int counter = 0;
        System.out.println("     A     B     C     D     E     F     G     H     I     J");
        System.out.println("   -----------------------------------------------------------");
        for(int j = 0; j<rows.length; j++){
            if(j!=9){
                System.out.print(rows[j]+" |");
            } else{
                System.out.print(rows[j]+"|");
            }
            for(int i = 0; i<10; i++){
                switch (cells.get(counter)){
                    case "HIT":
                        System.out.print(" HIT |");
                        break;
                    case "X":
                        System.out.print("  X  |");
                        break;
                    case "BAT":
                        System.out.print(" BAT |");
                        break;
                    case "CRU":
                        System.out.print(" CRU |");
                        break;
                    case "DES":
                        System.out.print(" DES |");
                        break;
                    case "SUB":
                        System.out.print(" SUB |");
                        break;
                    default:
                        System.out.print("     |");
                }
                counter+=1;
            }
            System.out.println();
            System.out.println("   -----------------------------------------------------------");
        }


    }

    /**
     * Returnsa list of all columns headings, e.g. ["A", "B", ..., "J"]
     * @return a lis t of 10 letters represented as strings
     */
    public String[] getColumns() {
        return columns;
    }

    /**
     * Returnsa list of all rows headings, e.g. ["1", "2", ..., "10"]
     * @return a lis t of 10 numbers represented as strings
     */
    public String[] getRows() {
        return rows;
    }

    /**
     * Returns all ships that were deployed
     * @return a list of all deployed ships, should return 14 ship objects
     */
    public ArrayList<Ship> getShips() {
        return ships;
    }
}
