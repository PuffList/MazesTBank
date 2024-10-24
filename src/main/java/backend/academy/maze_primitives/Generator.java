package backend.academy.maze_primitives;

/*
    интерфейс для генерации лабиринтов
 */
public interface Generator {

    Maze generate(int height, int width);
}
