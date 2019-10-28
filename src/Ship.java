import java.util.ArrayList;

public class Ship {

    private int lives;
    private ArrayList<String> body;


    public Ship(ArrayList<String> body){
        if(body.size()<1 || body.size()>4){
            throw new IllegalArgumentException("Number of cells can only be between 1-4!");
        }
        this.body = body;
        this.lives = body.size();
    }

    public ArrayList<String> getBody(){
        return body;
    }

    public int getLength() {
        return getBody().size();
    }

    public int getLives() {
        return lives;
    }

    public void reduceLives(){
        if(lives != 0){
            lives-=1;
        }
    }

    public String toString(){
        String bodyString = "[";
        for(int i = 0; i<getLength(); i++){
            bodyString+= i!=(getLength()-1)? (getBody().get(i)+", ") : (getBody().get(i)+"]");
        }
        return (bodyString);
    }

    public boolean isDistroyed(){
        return getLives()==0;
    }


}
