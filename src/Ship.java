import java.util.ArrayList;

public abstract class Ship {

    //private int lives;
    private ArrayList<String> body;


    public Ship(ArrayList<String> body){
        if(body.size()<1 || body.size()>4){
            throw new IllegalArgumentException("Number of cells can only be between 1-4!");
        }
        this.body = body;
    }

    public ArrayList<String> getBody(){
        return body;
    }

    public int getLength() {
        return getBody().size();
    }


    public String toString(){
        String bodyString = "[";
        for(int i = 0; i<getLength(); i++){
            bodyString+= i!=(getLength()-1)? (getBody().get(i)+", ") : (getBody().get(i)+"]");
        }
        return "I'm a "+getClass().getSimpleName()+", and I'm located at "+bodyString;
    }
}
