import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameTest {

    Game game = new Game();

    @Test
    void testSetActivePlayer() {
        game.getPlayer1().setName("Player A");
        game.getPlayer2().setName("Player B");

        game.setActivePlayer(game.getPlayer2());
        assertEquals("Player B", game.getActivePlayer().getName());
    }

    @Test
    void testSwitchPlayer() {
        game.getPlayer1().setName("Asem");
        game.getPlayer2().setName("Mosab");
        game.setActivePlayer(game.getPlayer2());

        game.switchPlayer();

        assertEquals("Asem", game.getActivePlayer().getName());
    }

    @Test
    void testGetLookupGrid() {
        assertEquals(100, game.getLookupGrid().getCells().size());
    }

    @Test
    void testGetDeployedGrid() {
        assertEquals(30, game.getDeployedGrid().getTargetCells().size());

        assertEquals(game.getPlayer1().getMyTargetCells(), game.getDeployedGrid().getTargetCells());

        assertEquals(game.getPlayer2().getMyTargetCells(), game.getDeployedGrid().getTargetCells());
    }
}