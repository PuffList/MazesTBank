package backend.academy.app;

import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Generator;
import backend.academy.maze_primitives.Maze;
import backend.academy.maze_primitives.Solver;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Класс, отвечающий за логику генерации лабиринта и поиска пути.
 */
public class MazeGame {

    @Setter
    private Generator generator;
    @Setter
    private Solver solver;
    @Getter
    private Maze maze;
    private Coordinate start;
    private Coordinate end;

    /**
     * Генерирует лабиринт заданной высоты и ширины.
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @throws IllegalStateException если генератор не установлен
     */
    public void generateMaze(int height, int width) {
        if (generator == null) {
            throw new IllegalStateException("Генератор не установлен.");
        }

        maze = generator.generate(height, width);
    }

    /**
     * Устанавливает начальную и конечную точки лабиринта.
     *
     * @param start начальная точка
     * @param end конечная точка
     */
    public void setStartAndEnd(Coordinate start, Coordinate end) {
        this.start = start;
        this.end = end;
    }

    /**
     * Ищет путь в лабиринте от начальной до конечной точки.
     *
     * @return список координат, представляющих путь
     * @throws IllegalStateException если данные для поиска пути не установлены
     */
    public List<Coordinate> findPath() {
        if (maze == null || solver == null || start == null || end == null) {
            throw new IllegalStateException("Недостаточно данных для поиска пути.");
        }
        return solver.solve(maze, start, end);
    }
}
