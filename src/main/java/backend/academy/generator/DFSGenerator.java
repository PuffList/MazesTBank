package backend.academy.generator;

import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Generator;
import backend.academy.maze_primitives.Maze;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

/**
 * Генератор лабиринта с использованием алгоритма поиска в глубину (DFS).
 */
public class DFSGenerator implements Generator {

    private static final Random RANDOM = new Random();
    private static final int STEP_SIZE = 2;
    Set<Coordinate> visited = new HashSet<>();

    /**
     * Генерирует лабиринт заданной высоты и ширины.
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return сгенерированный лабиринт
     */
    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        Stack<Coordinate> stack = new Stack<>();
        int realHeight = height * 2 - 1;
        int realWidth = width * 2 - 1;
        int startRow = (RANDOM.nextInt(realHeight) / 2) * 2;
        int startCol = (RANDOM.nextInt(realWidth) / 2) * 2;
        stack.add(new Coordinate(startRow, startCol));
        visited.add(new Coordinate(startRow, startCol));

        while (!stack.isEmpty()) {
            Coordinate current = stack.pop();
            addNeighborsTostack(maze, current, stack);
        }

        return maze;
    }

    private void addNeighborsTostack(Maze maze, Coordinate current, Stack<Coordinate> stack) {
        int row = current.row();
        int col = current.col();
        int[][] directions = {{-STEP_SIZE, 0}, {STEP_SIZE, 0}, {0, -STEP_SIZE}, {0, STEP_SIZE}};
        shuffleArray(directions);

        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            Coordinate neighbor = new Coordinate(newRow, newCol);
            if (isInBounds(newRow, newCol, maze)) {
                int wallRow = (row + newRow) / 2;
                int wallCol = (col + newCol) / 2;
                if (!visited.contains(neighbor) && maze.getCell(wallRow, wallCol).type() == Cell.Type.WALL) {
                    maze.setCell(wallRow, wallCol, Cell.Type.PASSAGE);
                    stack.add(neighbor);
                    visited.add(neighbor);
                }
            }
        }
    }

    private boolean isInBounds(int row, int col, Maze maze) {
        return row >= 0 && row < maze.height() && col >= 0 && col < maze.width();
    }

    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = RANDOM.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
