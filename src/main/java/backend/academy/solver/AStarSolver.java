package backend.academy.solver;

import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Maze;
import backend.academy.maze_primitives.Solver;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class AStarSolver implements Solver {

    @Override
    public List<Coordinate> solve(Maze maze, Coordinate start, Coordinate end) {
        PriorityQueue<Node> pQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.fScore));
        pQueue.add(new Node(start, 0, heuristic(start, end)));
        // Словарь для отслеживания, откуда мы пришли в каждую клетку
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        Map<Coordinate, Integer> gScore = new HashMap<>(); // Хранения фактической стоимости пути (g)
        gScore.put(start, 0);

        while (!pQueue.isEmpty()) {
            Node currentNode = pQueue.poll();
            Coordinate current = currentNode.coordinate;

            // Если достигли конечной точки, восстанавливаем путь
            if (current.equals(end)) {
                return SolveUtils.reconstructPath(cameFrom, end);
            }

            // Проверяем соседей
            for (Coordinate neighbor : SolveUtils.getNeighbors(maze, current)) {
                int tentativeGScore = gScore.get(current) + 1;  // Расстояние от старта до соседа исследуемой клетки

                // Проверка, является ли новый путь более коротким
                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);  // Обновляем стоимость пути до соседа исследуемой клетки
                    int fScore = tentativeGScore + heuristic(neighbor, end);  // f(n) = g(n) + h(n)
                    pQueue.add(new Node(neighbor, tentativeGScore, fScore));
                }
            }
        }

        return Collections.emptyList(); // Если путь не найден, возвращаем пустой список
    }

    // Эвристика Манхэттена(hScope) - кротчайшее расстояние от точки A до B
    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    // Класс для узла с координатами и оценкой fScore
    private static class Node {
        Coordinate coordinate;
        int gScore;  // Стоимость пути до этого узла(конкретной клетки)
        int fScore;  // Общая оценка (f = g + h)

        Node(Coordinate coordinate, int gScore, int fScore) {
            this.coordinate = coordinate;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }
}
