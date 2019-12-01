import java.util.ArrayList;

public class Destroyer extends Ship {


    private final int size = 2;

    /**
     * Defines how to construct a new ship of the type Destroyer that consists of 2 cells
     * @param body the ArrayList used to determine ship size and location on the grid
     * @throws IllegalArgumentException : A Cruiser can only be assigned 2 cells!
     */
    public Destroyer(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Destroyer can only be assigned 2 cells!");
        }
    }

    /**
     * This is mainly used for code testing purposes/terminal version,
     * and is not actually used in the GUI version of the game
     * <p>
     * e.g. I'm a Destroyer, and I'm located at [C1, D1]
     *
     * @return a string describing it's a Destroyer and the 2 cells that it's comprised of
     */
    public String toString(){
        return (super.toString());
    }

}
