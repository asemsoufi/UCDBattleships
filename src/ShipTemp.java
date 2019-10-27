/*public class ShipTemp {

    enum Type {BATTLESHIP, CRUISER, DISTROYER, SUBMARINE}
    private Type t;
    private int size;
    private int lives;
    private String[] body;
    private boolean distroyed = false;

    public Ship(String[] body){
        this.body = body;
        switch (body.length){
            case 4:
                this.t = Type.BATTLESHIP;
                break;
            case 3:
                this.t = Type.CRUISER;
                break;
            case 2:
                this.t = Type.DISTROYER;
                break;
            default:
                this.t = Type.SUBMARINE;
        }
    }

    public String[] getBody(){
        return body;
    }

    public int getSize() {
        return size;
    }

    public Type getType(){
        return t;
    }

    public String toString(){
        String bodyString = "[";
        for(int i = 0; i<body.length; i++){
            bodyString+= i!=(body.length-1)? (body[i]+", ") : (body[i]+"]");
        }
        return ("I'm a " + t +", and I'm located at " + bodyString);
    }

    public void markDistroyed(){
        this.distroyed = true;
    }

    public static void main(String[] args) {
        String[] s = ["A1", "B1", "C1"];
        Ship ship = new Ship(new String[]);
        System.out.println(ship.toString());
    }
}*/
