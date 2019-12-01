import java.util.ArrayList;

public class Cruiser extends Ship {


    private final int size = 3;

    /**
     * Defines how to construct a new ship of the type Cruiser that consists of 3 cells
     * @param body the ArrayList used to determine ship size and location on the grid
     * @throws IllegalArgumentException : A Cruiser can only be assigned 3 cells!
     */
    public Cruiser(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Cruiser can only be assigned 3 cells!");
        }
    }

    /**
     * This is mainly used for code testing purposes/terminal version,
     * and is not actually used in the GUI version of the game
     * <p>
     * e.g. I'm a Cruiser, and I'm located at [C1, D1, E1]
     *
     * @return a string describing it's a Cruiser and the 3 cells that it's comprised of
     */
    public String toString(){
        return (super.toString());
    }

}
