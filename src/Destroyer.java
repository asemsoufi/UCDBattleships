import java.util.ArrayList;

public class Destroyer extends Ship {


    private final int size = 2;


    public Destroyer(ArrayList<String> body){
        super(body);
        if(body.size()!=size){
            throw new IllegalArgumentException("A Destroyer can only be assigned 2 cells!");
        }
    }

    public String toString(){
        return (super.toString());
    }

}
