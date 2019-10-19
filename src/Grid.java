import java.util.ArrayList;

public class Grid {

    private String[] columns = {"A", "B", "C", "D", "E", "F", "G", "H", "I", "J"};
    private String[] rows = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    private ArrayList<String> cells;

    public Grid(){
        cells = new ArrayList<>();
        for (int i = 0; i < columns.length; i++){
            for (int j = 0; j < rows.length; j++){
                cells.add(columns[i]+rows[j]);
            }
        }
    }

    public void markShip(Ship ship){
        for(String shipCell:ship.getBody()){
            switch (ship.getType()){
                case BATTLESHIP:
                    cells.set(cells.indexOf(shipCell), "BAT");
                case CRUISER:
                    cells.set(cells.indexOf(shipCell), "CRU");
                case DISTROYER:
                    cells.set(cells.indexOf(shipCell), "DIS");
                default:
                    cells.set(cells.indexOf(shipCell), "SUB");
            }
        }
    }

    public void markHit(int cellNr){
        if(cellNr>=0 && cellNr <= 99) {
            cells.set(cellNr, "HIT");
        }
    }

    public void markMiss(int cellNr){
        if(cellNr>=0 && cellNr <= 99) {
            cells.set(cellNr, "X");
        }
    }

    public ArrayList<String> getCells() {
        return cells;
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

    public static void main(String[] args) {
        Grid g = new Grid();
        g.markHit(10);
        g.markHit(16);
        g.markMiss(40);
        g.markHit(55);
        g.markHit(62);
        g.markMiss(70);
        g.markHit(99);

        g.plot();
    }

}
