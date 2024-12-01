package backend.academy.samples;

import backend.academy.app.CommandSource;
import backend.academy.app.ConsoleCommandSource;
import backend.academy.app.MazeApp;
import backend.academy.render.ConsoleRender;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class CoordinateInputTest {

    @Test
    public void testInvalidStartEndPoints() {
        String input = "1\n3\n3\n-1\n-1\n10\n10\n0\n0\n2\n2\n2\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        CommandSource commandSource = new ConsoleCommandSource();
        MazeApp app = new MazeApp(commandSource, new ConsoleRender());
        assertDoesNotThrow(app::start,
            "Программа не должна выбрасывать исключения при вводе некорректных координат");
        System.setIn(System.in);
    }
}
