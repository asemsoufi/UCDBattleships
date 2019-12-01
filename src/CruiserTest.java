import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class CruiserTest {

    private Grid g = new Grid();
    ArrayList<String> subList = new ArrayList<>(g.getCells().subList(0,3));

    @Test
    void testToString() {
        Cruiser cruiser = new Cruiser(subList);
        assertEquals("I'm a Cruiser, and I'm located at [A1, B1, C1]", cruiser.toString());
    }
}