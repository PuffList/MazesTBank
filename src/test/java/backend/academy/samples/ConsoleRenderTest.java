package backend.academy.samples;

import backend.academy.maze_primitives.Maze;
import backend.academy.render.ConsoleRender;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleRenderTest {

    @Test
    public void testRenderMaze() {
        Maze maze = new Maze(3, 3);
        ConsoleRender render = new ConsoleRender();
        String result = render.render(maze);
        String expected =
            "░█░█░\n" +
            "█████\n" +
            "░█░█░\n" +
            "█████\n" +
            "░█░█░\n";
        assertEquals(expected, result, "Отображение лабиринта должно соответствовать ожидаемому результату");
    }
}
