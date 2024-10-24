package backend.academy.maze_primitives;

import java.util.List;

/*
    интерфейс для отрисовки лабиринта и найденного пути
 */
public interface Render {

    String render(Maze maze);

    String render(Maze maze, List<Coordinate> path);
}
