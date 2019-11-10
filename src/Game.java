import java.io.IOException;
import java.sql.SQLOutput;


public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Player winner;
    private Grid lookupGrid;
    private Grid deployedGrid;
    private boolean gameOver;


    public Game(){

        System.out.println("Getting first player's name.");
        player1 = new Player();
        System.out.println("Getting second player's name.");
        player2 = new Player();

        activePlayer = player1;

        lookupGrid = new Grid();

        deployedGrid = new Grid();
        //System.out.println(deployedGrid.getCells().toString());
        //showGrid();

        //System.out.println("deploying ships...");

        deployedGrid.deployShips();
        //System.out.println(deployedGrid.getCells().toString());
        //showGrid();
        System.out.println(deployedGrid.getTargetCells().toString());

        // give each player his own copy of target cells
        player1.setTargetCells(deployedGrid.getTargetCells());
        player2.setTargetCells(deployedGrid.getTargetCells());

        gameOver = false;
    }

    private void switchPlayer(){
        if (activePlayer==player1 && player2.isStillIn()){
            activePlayer = player2;
        }
        else if(activePlayer==player2 && player1.isStillIn()) {
            activePlayer = player1;
        }
        System.out.println();
    }

    private boolean isAHit(String playerGuess){
        return (activePlayer.getMyTargetCells().contains(playerGuess));
    }

    public void showGrid(){
        deployedGrid.plot();
    }

    public void printStats(Player p){
        System.out.println("You made "+activePlayer.shots()+(activePlayer.shots()!=1? " shots.":" shot."));
        if(p.shots()!=0){
            System.out.println("Your aiming accuracy was "+
                    (float)p.getHitCounter()/((float)p.getHitCounter()+
                            (float)p.getMissCounter()) * 100 + "%");
        }
        System.out.println();
    }

    public void play(){
        // implement here
        while (!gameOver && (player1.isStillIn() || player2.isStillIn())) {
            System.out.println("Ok "+activePlayer.getName()+", make a guess? ");
            activePlayer.getGrid().plot();
            System.out.println();
            String currentInput = activePlayer.makeGuess();
            System.out.println("You entered: "+currentInput);

            int hitCellIndex;

            if (currentInput.equals("QUIT")){
                System.out.println(activePlayer.getName()+", thank you for playing, below is your summary:");
                printStats(activePlayer);

                activePlayer.notStillIn();
                switchPlayer();
                continue;
            } else {
                hitCellIndex = lookupGrid.getCells().indexOf(currentInput);
            }
            if (isAHit(currentInput) && !gameOver){
                activePlayer.incHitCounter();
                activePlayer.getGrid().markHit(hitCellIndex);
                activePlayer.getGrid().plot();
                if (activePlayer.getHitCounter() == deployedGrid.getTargetCells().size()){
                    winner = activePlayer;
                    System.out.println("Congratulations "+activePlayer.getName()+", you made it :)");
                    gameOver=true;
                    break;
                } else {
                    activePlayer.getGrid().plot();
                    System.out.println("Wow, it's a hit, well done! Keep playing ");
                }

            } else {
                System.out.println("Sorry "+activePlayer.getName()+", you missed!");
                activePlayer.incMissCounter();
                activePlayer.getGrid().markMiss(hitCellIndex);
                switchPlayer();
            }
        }

        System.out.println("Game Over!");
        if(winner!=null){
            System.out.println("And The winner is "+winner.getName());
            System.out.println("Below is your game summary:");
            printStats(winner);
        } else {
            System.out.println("There is no winner! Both players quit the game early :(");
        }

        System.out.println("Below is the original battleships distribution map, have a look ;)");
        deployedGrid.plot();
    }

    /*public static void main(String[] args) {
        //Game thisGame = new Game();

        System.out.println("Game is on..");
        Game game = new Game();
        game.play();
    }*/

}
