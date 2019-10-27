import java.util.Scanner;

public class Player {

    private String name="";
    private boolean myTurn = false;  // this will be used to continue playing if a hit is made, or switch turns
    private int hitCounter=0;
    private int missCounter=0;
    private Grid grid;

    public Player(){
        while(this.name.equals("")) {
            Scanner input = new Scanner(System.in);
            System.out.print("Enter a name: ");
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

    public void incHitCounter(int hitCounter) {
        this.hitCounter +=1;
    }

    public int getMissCounter() {
        return missCounter;
    }

    public void incMissCounter(int missCounter) {
        this.missCounter += 1;
    }

    public static void main(String[] args) {
        Player me = new Player();
        System.out.println(me.getName());

        me.grid.plot();
    }
}
