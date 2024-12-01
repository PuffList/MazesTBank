package backend.academy.samples;

import backend.academy.app.CommandSource;
import backend.academy.app.ConsoleCommandSource;
import backend.academy.app.MazeApp;
import backend.academy.render.ConsoleRender;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.NoSuchElementException;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class MazeAppTest {

    @Test
    public void testHandleInvalidInput() {
        String input = "abc\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        CommandSource commandSource = new ConsoleCommandSource();
        MazeApp app = new MazeApp(commandSource, new ConsoleRender());
        assertThrows(NoSuchElementException.class, app::start,
            "Ожидалось выбрасывание NoSuchElementException при некорректном вводе");
    }
}
