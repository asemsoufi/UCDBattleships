import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private String name;
    private boolean stillIn;
    private int hitCounter;
    private int missCounter;
    private Grid grid;
    private ArrayList<String> myTargetCells;

    public Player(){
        stillIn = true;   // this wil be false should the player decide to quit the game before it ends
        hitCounter = 0;
        missCounter = 0;
        grid = new Grid();
        myTargetCells = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTargetCells(ArrayList<String> targetCells){
        myTargetCells.addAll(targetCells);
    }

    public ArrayList<String> getMyTargetCells(){
        return myTargetCells;
    }

    public void notStillIn(){
        stillIn=false;
    }

    public boolean isStillIn(){
        return stillIn;
    }

    public Grid getGrid(){
        return grid;
    }

    public int getHitCounter() {
        return hitCounter;
    }

    public void incHitCounter() {
        hitCounter++;
    }

    public int getMissCounter() {
        return missCounter;
    }

    public void incMissCounter() {
        missCounter++;
    }

    public int shots(){
        return hitCounter+missCounter;
    }

}
