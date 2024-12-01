package backend.academy.maze_primitives;

/**
 * Интерфейс для генераторов лабиринта.
 * Определяет метод генерации лабиринта.
 */
public interface Generator {

    Maze generate(int height, int width);
}
