public class Distroyer extends Ship {

    private int lives;
    private String[] body;
    private final String type = "Distroyer";
    private final int size = 2;


    public Distroyer(String[] body){
        super(body);
        if(body.length!=size){
            throw new IllegalArgumentException("A Distroyer can only be assigned 3 cells!");
        }
    }

    public String toString(){
        return ("I'm a Distroyer, and I'm located at "+super.toString());
    }

}
