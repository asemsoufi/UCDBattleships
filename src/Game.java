import java.io.IOException;


public class Game {

    private Player player1;
    private Player player2;
    private Player activePlayer;
    private Player winner;
    private Grid lookupGrid;
    private Grid deployedGrid;
    private boolean gameOver = false;


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
    }

    public void switchPlayer(){
        activePlayer = (activePlayer==player1? player2:player1);
        System.out.println();
        //clearConsole();
    }

    public boolean isAHit(String playerGuess){
        return (activePlayer.getMyTargetCells().contains(playerGuess));
    }

    public void showGrid(){
        deployedGrid.plot();
    }

    public void play(){
        // implement here
        while (!gameOver && (player1.isStillIn() || player2.isStillIn())) {
            System.out.println("Ok "+activePlayer.getName()+", it's your turn. Good luck :)");
            activePlayer.getGrid().plot();
            System.out.println();
            String currentInput = activePlayer.makeGuess();
            //System.out.println("Your guess is: "+currentInput);
            int hitCell = lookupGrid.getCells().indexOf(currentInput);
            //System.out.println("Your input index is: "+hitCell);
            /*if (currentInput.equals("quit".toUpperCase())){
                System.out.println(activePlayer.getName()+", thank you for playing, below is your final scoreboard.");
                activePlayer.getGrid().plot();
                activePlayer.notStillIn();
                switchPlayer();
                continue;
            }*/
            while(isAHit(currentInput) && !gameOver){
                System.out.println("Wow, "+currentInput+" is a hit, well done! Keep playing "+
                        activePlayer.getName()+"..");
                activePlayer.incHitCounter();
                //System.out.println("You made "+activePlayer.getHitCounter()+" hits so far.");
                //System.out.println("I will mark your hit at index: "+hitCell);
                activePlayer.getGrid().markHit(hitCell);
                activePlayer.getGrid().plot();
                if (activePlayer.getHitCounter() == deployedGrid.getTargetCells().size()){
                    winner = activePlayer;
                    gameOver=true;
                    break;
                }
                currentInput = activePlayer.makeGuess();
                hitCell = lookupGrid.getCells().indexOf(currentInput);
            }
            System.out.println("Sorry "+activePlayer.getName()+", you missed!");
            activePlayer.incMissCounter();
            activePlayer.getGrid().markMiss(hitCell);
            switchPlayer();
        }
        //gameOver=true;
    }

    /*public void clearConsole()
    {
        try
        {
            Runtime.getRuntime().exec("cls");
        }
        catch (final Exception e)
        {
            //  Handle any exceptions.
        }
    }*/
}
