public class Ship {

    private int lives;
    private String[] body;


    public Ship(String[] body){
        if(body.length<1 || body.length>4){
            throw new IllegalArgumentException("Number of cells can only be between 1-4!");
        }
        this.body = body;
        this.lives = body.length;
    }

    public String[] getBody(){
        return body;
    }

    public int getLength() {
        return getBody().length;
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
            bodyString+= i!=(getLength()-1)? (getBody()[i]+", ") : (getBody()[i]+"]");
        }
        return (bodyString);
    }

    public boolean isDistroyed(){
        return getLives()==0;
    }


}
