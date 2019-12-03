import java.util.ArrayList;


public class Player {

    private String name;
    private boolean stillIn;
    private int hitCounter;
    private int missCounter;
    private Grid grid;
    private ArrayList<String> myTargetCells;

    /**
     * Constructs a new Player object
     * <p>
     * it initiates the Player's variables as: still playing, no hits or misses made yet,
     * gives him a grids board of his own to shoot at, and his copy of the target cells of deployed ships
     * @param name pass the name of the player to constructor
    */
    public Player(String name){
        this.setName(name);
        stillIn = true;
        hitCounter = 0;
        missCounter = 0;
        grid = new Grid();
        myTargetCells = new ArrayList<>();
    }

    /**
     * Returns the name of the player
     * @return name of this player
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of a player
     * @param name a string representing the player's name, it can't be empty or longer than 15 characters long
     */
    public void setName(String name) {
        if(name.trim().isEmpty() || name.trim().length()>15){
            throw new IllegalArgumentException("Invalid player name! A name can't be empty or longer than 15 letters!");
        }
        else {
            this.name = name;
        }
    }

    /**
     * Adds target cells to the list of the player to be checked while playing for hits made
     * @param targetCells an ArrayList of all target cells randomly selected by the game for deployed ships
     */
    public void setTargetCells(ArrayList<String> targetCells){
        if(targetCells.size()!=30){
            throw  new IllegalArgumentException("Invalid targetCells list!");
        } else{
            myTargetCells.addAll(targetCells);
        }
    }

    /**
     * Returns a list of all target cells for player to check if he hit a target or not
     * @return an ArrayList of all target cells deployed by the game
     */
    public ArrayList<String> getMyTargetCells(){
        return myTargetCells;
    }

    /**
     * Sets the status (stillIn variable) of the player, whether he is still playing (true) or quit the game (false)
     */
    public void notStillIn(){
        stillIn=false;
    }

    /**
     * returns the current status of the player, whether he is still playing (true) or quit the game (false)
     * @return either true or false
     */
    public boolean isStillIn(){
        return stillIn;
    }

    /**
     * Returns the dedicated grids board created for the player
     * @return the grid object of current player
     */
    public Grid getGrid(){
        return grid;
    }

    /**
     * Returns the number of successful hits made so far
     * @return number of hits made
     */
    public int getHitCounter() {
        return hitCounter;
    }

    /**
     * Increases the number of hits by one every time the player hits a target
     */
    public void incHitCounter() {
        hitCounter++;
    }

    /**
     * Returns the number of failed shots made so far
     * @return number of misses made
     */
    public int getMissCounter() {
        return missCounter;
    }

    /**
     * Increases the number of misses by one every time the player misses to hit a target
     */
    public void incMissCounter() {
        missCounter++;
    }

    /**
     * This is the total number of shots made so far regardless of whether successful or not
     * @return the integer resulting from adding number of hits and number of misses
     */
    public int shots(){
        return hitCounter+missCounter;
    }

}
