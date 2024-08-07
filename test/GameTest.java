import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {
    int[][] board = { { 8, 8, 8, 8 },
                      { 8, 8, 8, 8 },
                      { 8, 8, 8, 8 },
                      { 8, 8, 8, 8 } };

    GameBoard gb = new GameBoard();

    @Test
    public void testInitBoard() {
        GameBoard gameBoard = new GameBoard();
        int[][] board = gameBoard.getBoard();
        int nonZeroCount = 0;
        for (int[] row : board) {
            for (int cell : row) {
                if (cell != 0) {
                    nonZeroCount++;
                }
            }
        }
        assertEquals(2, nonZeroCount);
    }

}