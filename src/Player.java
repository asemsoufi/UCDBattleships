import java.util.Scanner;

public class Player {

    private String name="";
    private boolean myTurn = false;
    private int hitCounter;
    private int missCounter;
    Grid grid;

    public Player(){
        while(this.name.equals("")) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter Player's name: ");
            this.name = input.nextLine().toUpperCase();
        }
        grid = new Grid();
    }

    public String getName() {
        return this.name;
    }

    public boolean isMyTurn(){
        return myTurn;
    }
    public void makeMyTurn(){
        this.myTurn = true;
    }

    public int getHitCounter() {
        return hitCounter;
    }

    public void setHitCounter(int hitCounter) {
        this.hitCounter = hitCounter;
    }

    public int getMissCounter() {
        return missCounter;
    }

    public void setMissCounter(int missCounter) {
        this.missCounter = missCounter;
    }

    public static void main(String[] args) {
        Player me = new Player();
        System.out.println(me.getName());

        me.grid.plot();
    }
}
