package backend.academy.solver;

import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Maze;
import backend.academy.maze_primitives.Solver;
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
        // Словарь для отслеживания, откуда мы пришли в каждую клетку
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        cameFrom.put(start, null);  // Стартовая точка
        Set<Coordinate> visited = new HashSet<>(); // Множество для хранения посещённых клеток
        visited.add(start);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();

            // Если достигли конечной точки, восстанавливаем путь
            if (current.equals(end)) {
                return SolveUtils.reconstructPath(cameFrom, end);
            }

            // Проверяем соседей
            for (Coordinate neighbor : SolveUtils.getNeighbors(maze, current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList(); // Если путь не найден, возвращаем пустой список
    }
}
