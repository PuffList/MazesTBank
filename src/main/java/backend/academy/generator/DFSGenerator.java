package backend.academy.generator;

import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Generator;
import backend.academy.maze_primitives.Maze;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class DFSGenerator implements Generator {

    private static final Random random = new Random();
    Set<Coordinate> visited = new HashSet<>(); // Множество для отслеживания посещённых клеток

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        Stack<Coordinate> stack = new Stack<>();
        int realHeight = height * 2 - 1;
        int realWidth = width * 2 - 1;
        int startRow = (random.nextInt(realHeight) / 2) * 2; // Выбор случайной стартовой клетки (только четные клетки могут быть проходами)
        int startCol = (random.nextInt(realWidth) / 2) * 2;
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
        int[][] directions = {{-2, 0}, {2, 0}, {0, -2}, {0, 2}}; // Перемешиваем порядок направлений для случайного выбора
        shuffleArray(directions);

        // Для каждого направления проверяем соседа
        for (int[] direction : directions) {
            int newRow = row + direction[0];
            int newCol = col + direction[1];
            Coordinate neighbor = new Coordinate(newRow, newCol);
            // Проверяем, что сосед находится в пределах лабиринта
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

    // Метод для перемешивания массива направлений
    private void shuffleArray(int[][] array) {
        for (int i = array.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);
            int[] temp = array[index];
            array[index] = array[i];
            array[i] = temp;
        }
    }
}
