import java.util.ArrayList;

public class Submarine extends Ship {

    private int lives;
    private ArrayList<String> body;
    private final String type = "Submarine";
    private final int size = 1;


    public Submarine(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Submarine can only be assigned 1 cell!");
        }
    }

    public String toString(){
        return ("I'm a Submarine, and I'm located at "+super.toString());
    }

}
