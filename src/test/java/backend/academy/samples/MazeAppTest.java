package backend.academy.samples;

import backend.academy.app.MazeApp;
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
        MazeApp app = new MazeApp();
        assertThrows(NoSuchElementException.class, () -> app.startGame(), "Ожидалось выбрасывание InputMismatchException при некорректном вводе");
    }
}
