import java.util.ArrayList;

public class Submarine extends Ship {


    private final int size = 1;

    /**
     * Defines how to construct a new ship of the type Submarine that consists of only 1 cell
     * @param body the ArrayList used to determine ship size and location on the grid
     * @throws IllegalArgumentException : A Cruiser can only be assigned 2 cells!
     */
    public Submarine(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Submarine can only be assigned 1 cell!");
        }
    }

    /**
     * This is mainly used for code testing purposes/terminal version,
     * and is not actually used in the GUI version of the game
     * <p>
     * e.g. I'm a Submarine, and I'm located at [C1]
     *
     * @return a string describing it's a Submarine and the 1 cell that it's comprised of
     */
    public String toString(){
        return (super.toString());
    }

}
