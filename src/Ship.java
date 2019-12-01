import java.util.ArrayList;

public abstract class Ship {

    //private int lives;
    private ArrayList<String> body;

    /**
     * Defines how to construct a new Ship for subclasses to use
     * @param body the ArrayList used to determine ship size and location on the grid
     */
    public Ship(ArrayList<String> body){
        if(body.size()<1 || body.size()>4){
            throw new IllegalArgumentException("Number of cells can only be between 1-4!");
        }
        this.body = body;
    }

    /**
     * Returns the a Ship's body, which is basically the set of cells that comprise the body of that ship
     * @return the a Ship's ArrayList used to determine its size and location on the grid
     */
    public ArrayList<String> getBody(){
        return body;
    }

    /**
     * Returns the length of a ship
     * @return an int between 1 and 4 inclusive
     */
    public int getLength() {
        return getBody().size();
    }

    /**
     * This is mainly used for code testing purposes/terminal version,
     * and is not actually used in the GUI version of the game
     * <p>
     * e.g. I'm a Destroyer, and I'm located at [C1, D1]
     *
     * @return a string describing the type of the ship and the cells that it's comprised of
     */
    public String toString(){
        String bodyString = "[";
        for(int i = 0; i<getLength(); i++){
            bodyString+= i!=(getLength()-1)? (getBody().get(i)+", ") : (getBody().get(i)+"]");
        }
        return "I'm a "+getClass().getSimpleName()+", and I'm located at "+bodyString;
    }
}
