package backend.academy.app;

import backend.academy.generator.DFSGenerator;
import backend.academy.generator.PrimGenerator;
import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Generator;
import backend.academy.maze_primitives.Maze;
import backend.academy.maze_primitives.Render;
import backend.academy.maze_primitives.Solver;
import backend.academy.render.ConsoleRender;
import backend.academy.solver.AStarSolver;
import backend.academy.solver.BFSSolver;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class MazeApp {

    private static final PrintStream OUT = System.out;
    private Generator generator;
    private Solver solver;
    private Maze maze;
    private Coordinate start;
    private Coordinate end;
    private Render render;

    public void startGame() {
        Scanner scanner = new Scanner(System.in);
        this.render = new ConsoleRender();
        chooseMazeGenerationAlgorithm(scanner); // Выбор алгоритма генерации
        generateMaze(scanner); // Генерация лабиринта
        inputStartAndEndPoints(scanner); // Ввод начальной и конечной точек
        choosePathFindingAlgorithm(scanner); // Выбор алгоритма поиска пути
        findPath(); // Поиск пути
        scanner.close();
    }

    // Метод для выбора алгоритма генерации лабиринта
    private void chooseMazeGenerationAlgorithm(Scanner scanner) {
        boolean flag = true;

        while (flag) {
            OUT.println("Выберите алгоритм для генерации лабиринта:");
            OUT.println("1. Алгоритм Прима");
            OUT.println("2. Алгоритм DFS (поиск в глубину)");

            try {
                int generationChoice = scanner.nextInt();

                switch (generationChoice) {
                    case 1:
                        this.generator = new PrimGenerator();
                        flag = false;
                        break;
                    case 2:
                        this.generator = new DFSGenerator();
                        flag = false;
                        break;
                    default:
                        OUT.println("Некорректный выбор, попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                OUT.println("Ошибка ввода, пожалуйста, введите число.");
                scanner.next(); // Очищаем некорректный ввод
            }
        }
    }

    // Метод для генерации лабиринта
    private void generateMaze(Scanner scanner) {
        while (true) {
            OUT.println("Введите размеры лабиринта (высота и ширина):");
            try {
                int height = scanner.nextInt();
                int width = scanner.nextInt();

                if (height > 1 && width > 1) {
                    this.maze = this.generator.generate(height, width);
                    OUT.println("Сгенерированный лабиринт:");
                    OUT.println(render.render(maze));
                    return;
                } else {
                    OUT.println("Размеры лабиринта должны быть больше одного.");
                }
            } catch (InputMismatchException e) {
                OUT.println("Ошибка ввода, введите два целых числа.");
                scanner.next();
            }
        }
    }

    // Метод для ввода начальной и конечной точек
    private void inputStartAndEndPoints(Scanner scanner) {
        boolean validCoordinates = false;

        while (!validCoordinates) {
            try {
                OUT.println("Введите координаты начальной точки (строка и столбец):");
                int startRow = scanner.nextInt();
                int startCol = scanner.nextInt();
                this.start = new Coordinate(startRow, startCol);

                OUT.println("Введите координаты конечной точки (строка и столбец):");
                int endRow = scanner.nextInt();
                int endCol = scanner.nextInt();
                this.end = new Coordinate(endRow, endCol);

                if (isValidCoordinate(start) && isValidCoordinate(end)) {
                    validCoordinates = true;
                } else {
                    OUT.println("Одна или обе из введённых точек являются стенами или выходят за пределы лабиринта. Попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                OUT.println("Ошибка ввода, пожалуйста, введите числа в пределах лабиринта.");
                scanner.next();
            }
        }
    }

    // Проверка, что координаты находятся в пределах лабиринта и не являются стенами
    private boolean isValidCoordinate(Coordinate coord) {
        int row = coord.row();
        int col = coord.col();
        return row >= 0 && row < maze.height() && col >= 0 && col < maze.width() && maze.getCell(row, col).type() == Cell.Type.PASSAGE;
    }

    // Метод для выбора алгоритма поиска пути
    private void choosePathFindingAlgorithm(Scanner scanner) {
        while (true) {
            OUT.println("Выберите алгоритм для поиска пути:");
            OUT.println("1. Поиск в ширину (BFS)");
            OUT.println("2. Поиск A-Star");

            try {
                int searchChoice = scanner.nextInt();

                switch (searchChoice) {
                    case 1:
                        this.solver = new BFSSolver();
                        return;
                    case 2:
                        this.solver = new AStarSolver();
                        return;
                    default:
                        OUT.println("Некорректный ввод, попробуйте снова.");
                }
            } catch (InputMismatchException e) {
                OUT.println("Ошибка ввода, пожалуйста, введите число.");
                scanner.next();
            }
        }
    }

    // Метод для поиска пути и его отображения
    private void findPath() {
        List<Coordinate> path = this.solver.solve(this.maze, this.start, this.end);

        if (path.isEmpty()) {
            OUT.println("Путь не найден.");
        } else {
            OUT.println("Найденный путь:");
            OUT.println(render.render(maze, path));
        }
    }
}
