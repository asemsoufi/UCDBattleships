import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class DestroyerTest {

    private Grid g = new Grid();
    ArrayList<String> subList = new ArrayList<>(g.getCells().subList(0,2));

    @Test
    void testToString() {
        Destroyer destroyer = new Destroyer(subList);
        assertEquals("I'm a Destroyer, and I'm located at [A1, B1]", destroyer.toString());
    }
}