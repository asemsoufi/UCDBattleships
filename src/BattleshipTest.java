import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class BattleshipTest {

    private Grid g = new Grid();
    ArrayList<String> subList = new ArrayList<>(g.getCells().subList(0,4));

    @Test
    void testToString() {
        Battleship bs = new Battleship(subList);
        assertEquals("I'm a Battleship, and I'm located at [A1, B1, C1, D1]", bs.toString());
    }
}