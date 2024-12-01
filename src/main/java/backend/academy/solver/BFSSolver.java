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

/**
 * Реализация алгоритма поиска пути с использованием поиска в ширину (BFS).
 */

public class BFSSolver implements Solver {

    /**
     * Ищет путь в лабиринте от начальной до конечной точки.
     *
     * @param maze лабиринт
     * @param start начальная точка
     * @param end конечная точка
     * @return список координат, представляющих путь
     */
    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {

        Queue<Coordinate> queue = new LinkedList<>();
        queue.add(start);
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        cameFrom.put(start, null);
        Set<Coordinate> visited = new HashSet<>();
        visited.add(start);

        while (!queue.isEmpty()) {
            Coordinate current = queue.poll();
            if (current.equals(end)) {
                return SolveUtils.reconstructPath(cameFrom, end);
            }
            for (Coordinate neighbor : SolveUtils.getNeighbors(maze, current)) {
                if (!visited.contains(neighbor)) {
                    queue.add(neighbor);
                    visited.add(neighbor);
                    cameFrom.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList();
    }
}
