package backend.academy.samples;

import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Maze;
import backend.academy.solver.BFSSolver;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class BFSSolverTest {

    @Test
    public void testBFSFindsPathInSimpleMaze() {
        Maze maze = new Maze(3, 3);
        maze.setCell(0, 0, Cell.Type.PASSAGE); // Начальная точка
        maze.setCell(4, 4, Cell.Type.PASSAGE); // Конечная точка
        maze.setCell(0, 1, Cell.Type.PASSAGE);
        maze.setCell(1, 1, Cell.Type.PASSAGE);
        maze.setCell(2, 1, Cell.Type.PASSAGE);
        maze.setCell(3, 1, Cell.Type.PASSAGE);
        maze.setCell(4, 1, Cell.Type.PASSAGE);
        maze.setCell(4, 2, Cell.Type.PASSAGE);
        maze.setCell(4, 3, Cell.Type.PASSAGE);
        BFSSolver solver = new BFSSolver();
        List<Coordinate> path = solver.solve(maze, new Coordinate(0, 0), new Coordinate(4, 4));
        assertFalse(path.isEmpty(), "Путь не должен быть пустым");
        assertEquals(new Coordinate(4, 4), path.get(path.size() - 1), "Конечная точка должна быть (4,4)");
    }
}
