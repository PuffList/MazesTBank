package backend.academy.maze_primitives;

import java.util.List;

/**
 * Интерфейс для алгоритмов поиска пути в лабиринте.
 * Определяет метод для нахождения пути.
 */
public interface Solver {

    List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end);
}
