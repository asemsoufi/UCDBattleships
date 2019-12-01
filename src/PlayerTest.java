import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private final Player p = new Player();

    Game g = new Game();

    @Test
    void testSetName() {
        try {
            String longName = "abcdefg hijklmnop qrstuv wxyz";
            p.setName(longName);
            fail("Invalid player name! A name can't be longer than 15 letters!");
        } catch (IllegalArgumentException ignored){}

        try {
            String emptyName = "    ";
            p.setName(emptyName);
            fail("Invalid player name! A name can't be empty!");
        } catch (IllegalArgumentException ignored){}

    }

    @Test
    void testSettargetCells(){
        // try to set target cells (supposed to be 30 cells long) to 100 cells!
        ArrayList<String> wrongSizeList = new ArrayList<>(g.getLookupGrid().getCells());
        try{
            p.setTargetCells(wrongSizeList);
            assertEquals(30, p.getMyTargetCells().size());
        } catch (IllegalArgumentException ignored){}
    }
}