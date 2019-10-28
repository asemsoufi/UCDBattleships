import java.util.ArrayList;

public class Distroyer extends Ship {

    private int lives;
    private ArrayList<String> body;
    private final String type = "Distroyer";
    private final int size = 2;


    public Distroyer(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Distroyer can only be assigned 2 cells!");
        }
    }

    public String toString(){
        return ("I'm a Distroyer, and I'm located at "+super.toString());
    }

}
