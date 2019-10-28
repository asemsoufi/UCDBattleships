import java.util.ArrayList;

public class Cruiser extends Ship {

    private int lives;
    private ArrayList<String> body;
    private final String type = "Cruiser";
    private final int size = 3;


    public Cruiser(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Cruiser can only be assigned 3 cells!");
        }
    }

    public String toString(){
        return ("I'm a Cruiser, and I'm located at "+super.toString());
    }

}
