package backend.academy.maze_primitives;

import java.util.List;

/*
    интерфейс для поиска пути в лабиринте
 */
public interface Solver {

    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
