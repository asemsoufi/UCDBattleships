import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GridTest {

    private Grid g = new Grid();


    @Test
    void testGetCells() {
        assertEquals(100, g.getCells().size());
    }

    @Test
    void testGetTargetCells() {
        g.deployShips();

        assertEquals(30, g.getTargetCells().size());
    }

    @Test
    void testDeployShips() {
        g.deployShips();

        int numberOFShips = 0;
        for(Ship s : g.getShips()){
            numberOFShips++;
        }

        assertEquals(14, numberOFShips);
    }
}