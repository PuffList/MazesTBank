package backend.academy.solver;

import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Maze;
import backend.academy.maze_primitives.Solver;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class BFSSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>(); // Словарь для отслеживания, откуда мы пришли в каждую клетку
        cameFrom.put(start, null);  // Стартовая точка
        Set<Coordinate> visited = new HashSet<>(); // Множество для хранения посещённых клеток
        visited.add(start);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            // Если достигли конечной точки, восстанавливаем путь
            if (current.equals(end)) {
                return reconstructPath(cameFrom, end);
            }

            // Проверяем соседей
            for (Coordinate neighbor : getNeighbors(maze, current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList(); // Если путь не найден, возвращаем пустой список
    }

    // Восстанавливаем путь по словарю cameFrom
    private List<Coordinate> reconstructPath(Map<Coordinate, Coordinate> cameFrom, Coordinate end) {
        List<Coordinate> path = new ArrayList<>();
        Coordinate current = end;

        while (current != null) {
            path.add(current);
            current = cameFrom.get(current);
        }

        Collections.reverse(path); // Реверсируем список, чтобы путь шёл от начала к концу
        return path;
    }

    // Возвращаем список соседей для текущей клетки
    private List<Coordinate> getNeighbors(Maze maze, Coordinate current) {
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
