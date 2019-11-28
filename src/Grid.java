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

    // this method marks deployed ships on the map/grid, for testing purposes or at the end of the game,
    // but will be hidden from players until they had finished playing
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

    // mark a "HIT" string on the cell once a successful hit is made (terminal version)
    void markHit(int cellNr){
        if(cellNr>=0 && cellNr < cells.size()) {
            cells.set(cellNr, "HIT");
        }
    }

    // mark a miss by printing " X " string on the cell once a miss is made (terminal version)
    void markMiss(int cellNr){
        if(cellNr>=0 && cellNr < cells.size()) {
            cells.set(cellNr, "X");
        }
    }

    // returns all available cells on the grid
    public ArrayList<String> getCells() {
        return cells;
    }

    // returns all deployed cells on the grid (all cells that are part of a ship and are targets)
    public ArrayList<String> getTargetCells() {
        return targetCells;
    }

    // plot grid to the terminal
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

    public String[] getColumns() {
        return columns;
    }

    public String[] getRows() {
        return rows;
    }

}
