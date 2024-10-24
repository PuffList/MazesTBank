package backend.academy.render;

import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Maze;
import backend.academy.maze_primitives.Render;
import java.util.List;

public class ConsoleRender implements Render {

    //Рендер лабиринта
    @Override
    public String render(Maze maze) {
        StringBuilder sb = new StringBuilder();

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell cell = maze.getCell(row, col);
                sb.append(cell.type() == Cell.Type.WALL ? '█' : '░');
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    //Рендер пути в лабиринте
    @Override
    public String render(Maze maze, List<Coordinate> path) {
        //Создаем копию лабиринта
        StringBuilder sb = new StringBuilder();
        char[][] renderedMaze = new char[maze.height()][maze.width()];

        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                Cell cell = maze.getCell(row, col);
                renderedMaze[row][col] = (cell.type() == Cell.Type.WALL) ? '█' : '░';
            }
        }

        //Помечаем путь звёздочками
        for (Coordinate coordinate : path) {
            renderedMaze[coordinate.row()][coordinate.col()] = '•';
        }

        //Формируем итоговый лабиринт с путём
        for (int row = 0; row < maze.height(); row++) {
            for (int col = 0; col < maze.width(); col++) {
                sb.append(renderedMaze[row][col]);
            }
            sb.append("\n");
        }

        return sb.toString();
    }
}
