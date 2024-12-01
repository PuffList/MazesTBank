package backend.academy.app;

import backend.academy.maze_primitives.Render;
import backend.academy.render.ConsoleRender;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {

    public static void main(String[] args) {
        CommandSource commandSource = new ConsoleCommandSource();
        Render renderer = new ConsoleRender();
        MazeApp app = new MazeApp(commandSource, renderer);
        app.start();
    }
}
