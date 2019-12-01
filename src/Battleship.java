import java.util.ArrayList;

public class Battleship extends Ship {


    private final int size = 4;

    /**
     * Defines how to construct a new ship of the type Battleship that consists of 4 cells
     * @param body the ArrayList used to determine ship size and location on the grid
     * @throws IllegalArgumentException : A Battleship can only be assigned 4 cells!
     */
    public Battleship(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Battleship can only be assigned 4 cells!");
        }
    }

    /**
     * This is mainly used for code testing purposes/terminal version,
     * and is not actually used in the GUI version of the game
     * <p>
     * e.g. I'm a Battleship, and I'm located at [C1, D1, E1, F1]
     *
     * @return a string describing it's a Battleship and the 4 cells that it's comprised of
     */
    public String toString(){
        return (super.toString());
    }

}
