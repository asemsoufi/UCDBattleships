public class Submarine extends Ship {

    private int lives;
    private String[] body;
    private final String type = "Submarine";
    private final int size = 1;


    public Submarine(String[] body){
        super(body);
        if(body.length!=size){
            throw new IllegalArgumentException("A Submarine can only be assigned 3 cells!");
        }
    }

    public String toString(){
        return ("I'm a Submarine, and I'm located at "+super.toString());
    }

}
