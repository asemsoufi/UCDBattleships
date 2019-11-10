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
        //ArrayList<String> tempList = new ArrayList<>();
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
                            //mainGrid.markShip(bat);
                        } else{
                            // create and place Battleship vertically
                            Ship bat = new Battleship(getCellsVertical(4));
                            ships.add(bat);
                            //mainGrid.markShip(bat);
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
                            //mainGrid.markShip(cru);
                        } else{
                            // create and place Cruiser vertically
                            Ship cru = new Cruiser(getCellsVertical(3));
                            ships.add(cru);
                            //mainGrid.markShip(cru);
                        }
                    }
                    break;
                case "DISTROYER":
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        double rand = Math.random();
                        if(rand >0.5){
                            // create and place Destroyer horizontally
                            Ship dis = new Destroyer(getCellsHorizontal(2));
                            ships.add(dis);
                            //mainGrid.markShip(dis);
                        } else{
                            // create and place Destroyer vertically
                            Ship dis = new Destroyer(getCellsVertical(2));
                            ships.add(dis);
                            //mainGrid.markShip(dis);
                        }
                    }
                    break;
                default:
                    for(int i = 1; i<=Integer.parseInt(aShip[1]); i++){
                        // create and place Submarine, no orientation applies here
                        Ship sub = new Submarine(getCellsVertical(1));
                        ships.add(sub);
                        //mainGrid.markShip(sub);
                    }
            }
        }
        // mark ships on the main grid. This is for testing purposes only, and will be hidden from the player.
        for(Ship ship : ships){
            markShip(ship);
        }
    }

    private ArrayList<String> getCellsHorizontal(int numberOfCells){
        //System.out.println("method getCellsHorizontal started");
        checkNumOfCells(numberOfCells);
        boolean success = false;
        ArrayList<String> tempHorArray = new ArrayList<>();
        while(!success) {
            //System.out.println("trying to get cells horizontally");
            // pick a random row (1-10)
            int rowNumber = (int) (Math.random() * (9)) + 1;
            // pick a random column (A-J), but considering length of ship
            // (i.e. number of cells needed horizontally)
            int columnIndex = (int) (Math.random() * (9 - numberOfCells)) + 1;
            for(int j = columnIndex; j< (columnIndex + numberOfCells); j++){
                String columnLetter = getColumns()[j];
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
        // remove cells assigned to the new ship, so that it can't be accidentally re-assigned to another ship
        availableCells.removeAll(tempHorArray);
        // a new ship was added successfully to the grid and needs to be marked as possible target
        targetCells.addAll(tempHorArray);
        return tempHorArray;
    }

    private ArrayList<String> getCellsVertical(int numberOfCells){
        //System.out.println("method getCellsVertical started");
        checkNumOfCells(numberOfCells);
        boolean success = false;
        ArrayList<String> tempVerArray = new ArrayList<>();
        while(!success) {
            //System.out.println("trying to get cells vertically");
            // pick a random column (A-J)
            int columnIndex = (int) (Math.random() * 9);
            String columnLetter = getColumns()[columnIndex];
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
                //clear previous selection and start over again
                tempVerArray.clear();
            }
        }
        // remove cells assigned to the new ship, so that it can't be accidentally re-assigned to another ship
        availableCells.removeAll(tempVerArray);
        // a new ship was added successfully to the grid and needs to be marked as possible target
        targetCells.addAll(tempVerArray);
        return tempVerArray;
    }

    private void checkNumOfCells(int numberOfCells) {
        if(numberOfCells<1 || numberOfCells>4 || availableCells.size()<numberOfCells) {
            throw new IllegalArgumentException("The grid can't assign this number of cells.");
        }
    }

    private void markShip(Ship ship){
        for(String shipCell:ship.getBody()){
            if (ship instanceof Battleship) {
                cells.set(cells.indexOf(shipCell), "BAT");
            } else if (ship instanceof Cruiser) {
                cells.set(cells.indexOf(shipCell), "CRU");
            } else if (ship instanceof Destroyer) {
                cells.set(cells.indexOf(shipCell), "DIS");
            } else if (ship instanceof Submarine) {
                cells.set(cells.indexOf(shipCell), "SUB");
            }
        }
    }

    void markHit(int cellNr){
        if(cellNr>=0 && cellNr < cells.size()) {
            cells.set(cellNr, "HIT");
        }
    }

    void markMiss(int cellNr){
        if(cellNr>=0 && cellNr < cells.size()) {
            cells.set(cellNr, "X");
        }
    }

    public ArrayList<String> getCells() {
        return cells;
    }

    public ArrayList<String> getTargetCells() {
        return targetCells;
    }

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
                    case "DIS":
                        System.out.print(" DIS |");
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

    public static void main(String[] args) {
        Grid g = new Grid();
        System.out.println(g.getCells().toString());
        /*g.markHit(10);
        g.markHit(16);
        g.markMiss(40);
        g.markHit(55);
        g.markHit(62);
        g.markMiss(70);
        g.markHit(99);

        /*Ship s1; //= new Battleship(new String[]{"A1", "B1", "C1", "D1"});
        System.out.println(s1.toString());
        g.markShip(s1);

        Ship s2; //= new Cruiser(new String[]{"H8", "H9", "H10"});
        System.out.println(s2.toString());
        g.markShip(s2);

        System.out.println(g.cells.toString());
        System.out.println(g.cells.get(99));
        System.out.println(g.cells.size());
        System.out.println(g.cells.indexOf("B1"));

        g.plot();*/
    }

}
