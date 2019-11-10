import java.util.ArrayList;

public class Battleship extends Ship {


    private final int size = 4;


    public Battleship(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Battleship can only be assigned 4 cells!");
        }
    }

    public String toString(){
        return (super.toString());
    }

}
