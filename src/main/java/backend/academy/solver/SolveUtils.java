package backend.academy.solver;

import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Maze;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import lombok.experimental.UtilityClass;

/**
 * Утилитный класс для вспомогательных методов, используемых в алгоритмах поиска пути.
 */
@UtilityClass
public class SolveUtils {

    /**
     * Восстанавливает путь от конечной точки до начальной, используя карту переходов.
     *
     * @param cameFrom карта переходов (где ключ — текущая точка, значение — предыдущая точка)
     * @param end конечная точка
     * @return список координат, представляющих путь от начальной до конечной точки
     */
    public static List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;

        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }

        Collections.reverse(path);
        return path;
    }

    /**
     * Получает список соседних клеток, которые являются проходами.
     *
     * @param maze лабиринт
     * @param current текущая клетка
     * @return список соседних клеток
     */
    public static List<Coordinate> getNeighbors(Maze maze, Coordinate current) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = current.row();
        int col = current.col();

        if (row > 0 && maze.getCell(row - 1, col).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row - 1, col));
        }

        if (row < maze.height() - 1 && maze.getCell(row + 1, col).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row + 1, col));
        }

        if (col > 0 && maze.getCell(row, col - 1).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row, col - 1));
        }

        if (col < maze.width() - 1 && maze.getCell(row, col + 1).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(row, col + 1));
        }

        return neighbors;
    }
}
