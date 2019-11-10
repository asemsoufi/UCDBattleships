import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private String name="";
    private boolean stillIn = true;  // this wil be false should the player decide to quit the game before it ends
    private int hitCounter=0;
    private int missCounter=0;
    private Grid grid;
    private ArrayList<String> myTargetCells;

    public Player(){
        while(this.name.isEmpty() || this.name.trim().isEmpty()) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter a name: ");
            this.name = input.nextLine().toUpperCase();
        }
        grid = new Grid();
        myTargetCells = new ArrayList<>();
    }

    public String getName() {
        return this.name;
    }

    public String makeGuess(){
        String userGuess = "";
        while(!(grid.getCells().contains(userGuess.toUpperCase())) && !userGuess.equals("quit")) {
            Scanner input = new Scanner(System.in);
            System.out.print("Make a guess? ");
            userGuess = input.nextLine().toUpperCase();
        }
        return userGuess;
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
        this.hitCounter +=1;
    }

    public int getMissCounter() {
        return missCounter;
    }

    public void incMissCounter() {
        this.missCounter += 1;
    }

    public static void main(String[] args) {
        Player me = new Player();
        System.out.println(me.getName());

        me.grid.plot();
    }
}
