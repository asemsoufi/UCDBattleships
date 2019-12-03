import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SubmarineTest {

    private Grid g = new Grid();
    ArrayList<String> subList = new ArrayList<>(g.getCells().subList(0,1));

    @Test
    void testToString() {
        Submarine submarine = new Submarine(subList);
        assertEquals("I'm a Submarine, and I'm located at [A1]", submarine.toString());
    }

}