package backend.academy.generator;

import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Generator;
import backend.academy.maze_primitives.Maze;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class PrimGenerator implements Generator {

    private static final Random RANDOM = new Random();

    @Override
    public Maze generate(int height, int width) {
        Maze maze = new Maze(height, width);
        List<Coordinate> walls = new ArrayList<>();
        Set<Coordinate> inMaze = new HashSet<>();  // Множество для отслеживания клеток, которые уже в лабиринте
        int realHeight = height * 2 - 1;
        int realWidth = width * 2 - 1;
        // Выбор случайной стартовой клетки (только четные клетки могут быть проходами)
        int startRow = (RANDOM.nextInt(realHeight) / 2) * 2;
        int startCol = (RANDOM.nextInt(realWidth) / 2) * 2;
        inMaze.add(new Coordinate(startRow, startCol));
        addWallsAround(maze, startRow, startCol, walls); // Определяем координаты стен вокруг стартовой клетки

        while (!walls.isEmpty()) {
            Coordinate wall = walls.remove(RANDOM.nextInt(walls.size()));
            List<Coordinate> neighbors = getNeighbors(maze, wall); // Получаем соседние клетки по обе стороны от стены
            // Устанавливаем флаги
            Coordinate insideMaze = null;
            Coordinate outsideMaze = null;

            if (inMaze.contains(neighbors.get(0)) && !inMaze.contains(neighbors.get(1))) {
                insideMaze = neighbors.get(0);
                outsideMaze = neighbors.get(1);
            } else if (inMaze.contains(neighbors.get(1)) && !inMaze.contains(neighbors.get(0))) {
                insideMaze = neighbors.get(1);
                outsideMaze = neighbors.get(0);
            }

            if (insideMaze != null && outsideMaze != null) {
                maze.setCell(wall.row(), wall.col(), Cell.Type.PASSAGE);  // Превращаем стену в проход
                inMaze.add(outsideMaze);  // Добавляем внешний проход в множество, тк он теперь внутренний
                addWallsAround(maze, outsideMaze.row(), outsideMaze.col(), walls);
            }
        }

        return maze;
    }

    private void addWallsAround(Maze maze, int row, int col, List<Coordinate> walls) {

        if (row > 1 && maze.getCell(row - 1, col).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row - 1, col));  // Стена сверху
        }

        if (row < maze.height() - 2 && maze.getCell(row + 1, col).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row + 1, col));  // Стена снизу
        }

        if (col > 1 && maze.getCell(row, col - 1).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row, col - 1));  // Стена слева
        }

        if (col < maze.width() - 2 && maze.getCell(row, col + 1).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row, col + 1));  // Стена справа
        }
    }

    private List<Coordinate> getNeighbors(Maze maze, Coordinate wall) {
        List<Coordinate> neighbors = new ArrayList<>();

        // Проверяем соседей
        if (wall.row() > 0 && maze.getCell(wall.row() - 1, wall.col()).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(wall.row() - 1, wall.col()));
        }
        if (wall.row() < maze.height() - 1 && maze.getCell(wall.row() + 1, wall.col()).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(wall.row() + 1, wall.col()));
        }
        if (wall.col() > 0 && maze.getCell(wall.row(), wall.col() - 1).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(wall.row(), wall.col() - 1));
        }
        if (wall.col() < maze.width() - 1 && maze.getCell(wall.row(), wall.col() + 1).type() == Cell.Type.PASSAGE) {
            neighbors.add(new Coordinate(wall.row(), wall.col() + 1));
        }

        return neighbors;
    }
}
