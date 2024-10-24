package backend.academy.samples;

import backend.academy.app.MazeApp;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CoordinateInputTest {

    @Test
    public void testInvalidStartEndPoints() {
        String input = "1\n3\n3\n-1 -1\n10 10\n0 0\n2 2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        MazeApp app = new MazeApp();
        assertDoesNotThrow(() -> app.startGame(), "Программа не должна выбрасывать исключения при вводе некорректных координат");
    }
}
