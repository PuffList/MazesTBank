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

/**
 * Реализация алгоритма поиска пути A*.
 */
public class AStarSolver implements Solver {

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
        PriorityQueue<Node> pQueue = new PriorityQueue<>(Comparator.comparingInt(node -> node.fScore));
        pQueue.add(new Node(start, 0, heuristic(start, end)));
        Map<Coordinate, Coordinate> cameFrom = new HashMap<>();
        Map<Coordinate, Integer> gScore = new HashMap<>();
        gScore.put(start, 0);

        while (!pQueue.isEmpty()) {
            Node currentNode = pQueue.poll();
            Coordinate current = currentNode.coordinate;


            if (current.equals(end)) {
                return SolveUtils.reconstructPath(cameFrom, end);
            }


            for (Coordinate neighbor : SolveUtils.getNeighbors(maze, current)) {
                int tentativeGScore = gScore.get(current) + 1;

                if (tentativeGScore < gScore.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    cameFrom.put(neighbor, current);
                    gScore.put(neighbor, tentativeGScore);
                    int fScore = tentativeGScore + heuristic(neighbor, end);
                    pQueue.add(new Node(neighbor, tentativeGScore, fScore));
                }
            }
        }

        return Collections.emptyList();
    }

    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    private static class Node {
        Coordinate coordinate;
        int gScore;
        int fScore;

        Node(Coordinate coordinate, int gScore, int fScore) {
            this.coordinate = coordinate;
            this.gScore = gScore;
            this.fScore = fScore;
        }
    }
}
