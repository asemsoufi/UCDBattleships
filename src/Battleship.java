public class Battleship extends Ship {

    private int lives;
    private String[] body;
    private final String type = "Battleship";
    private final int size = 4;


    public Battleship(String[] body){
        super(body);
        if(body.length!=size){
            throw new IllegalArgumentException("A Battleship can only be assigned 3 cells!");
        }
    }

    public String toString(){
        return ("I'm a Battleship, and I'm located at "+super.toString());
    }

}
