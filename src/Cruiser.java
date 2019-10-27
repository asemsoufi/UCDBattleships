public class Cruiser extends Ship {

    private int lives;
    private String[] body;
    private final String type = "Cruiser";
    private final int size = 3;


    public Cruiser(String[] body){
        super(body);
        if(body.length!=size){
            throw new IllegalArgumentException("A Cruiser can only be assigned 3 cells!");
        }
    }

    public String toString(){
        return ("I'm a Cruiser, and I'm located at "+super.toString());
    }

}
