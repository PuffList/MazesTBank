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

/**
 * Генератор лабиринта с использованием алгоритма Прима.
 */
public class PrimGenerator implements Generator {

    private static final Random RANDOM = new Random();

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
        List<Coordinate> walls = new ArrayList<>();
        Set<Coordinate> inMaze = new HashSet<>();
        int realHeight = height * 2 - 1;
        int realWidth = width * 2 - 1;
        int startRow = (RANDOM.nextInt(realHeight) / 2) * 2;
        int startCol = (RANDOM.nextInt(realWidth) / 2) * 2;
        inMaze.add(new Coordinate(startRow, startCol));
        addWallsAround(maze, startRow, startCol, walls);

        while (!walls.isEmpty()) {
            Coordinate wall = walls.remove(RANDOM.nextInt(walls.size()));
            List<Coordinate> neighbors = getNeighbors(maze, wall);
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
                maze.setCell(wall.row(), wall.col(), Cell.Type.PASSAGE);
                inMaze.add(outsideMaze);
                addWallsAround(maze, outsideMaze.row(), outsideMaze.col(), walls);
            }
        }

        return maze;
    }

    private void addWallsAround(Maze maze, int row, int col, List<Coordinate> walls) {

        if (row > 1 && maze.getCell(row - 1, col).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row - 1, col));
        }

        if (row < maze.height() - 2 && maze.getCell(row + 1, col).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row + 1, col));
        }

        if (col > 1 && maze.getCell(row, col - 1).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row, col - 1));
        }

        if (col < maze.width() - 2 && maze.getCell(row, col + 1).type() == Cell.Type.WALL) {
            walls.add(new Coordinate(row, col + 1));
        }
    }

    private List<Coordinate> getNeighbors(Maze maze, Coordinate wall) {
        List<Coordinate> neighbors = new ArrayList<>();

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
