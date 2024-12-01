package backend.academy.app;

import backend.academy.generator.DFSGenerator;
import backend.academy.generator.PrimGenerator;
import backend.academy.maze_primitives.Cell;
import backend.academy.maze_primitives.Coordinate;
import backend.academy.maze_primitives.Render;
import backend.academy.solver.AStarSolver;
import backend.academy.solver.BFSSolver;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Главный класс приложения для работы с лабиринтом.
 * Отвечает за взаимодействие между пользователем и логикой генерации и поиска пути.
 */
public class MazeApp {

    private static final String ERROR_INPUT = "Ошибка ввода. Попробуйте снова.";
    private static final String INVALID_CHOICE = "Некорректный выбор. Введите 1 или 2.";
    private final CommandSource commandSource;
    private final Render renderer;
    private final MazeGame game;

    /**
     * Конструктор класса MazeApp.
     *
     * @param commandSource источник команд пользователя
     * @param renderer рендер для вывода лабиринта и пути
     */
    public MazeApp(CommandSource commandSource, Render renderer) {
        this.commandSource = commandSource;
        this.renderer = renderer;
        this.game = new MazeGame();
    }

    /**
     * Основной метод запуска приложения.
     * Осуществляет взаимодействие с пользователем, генерирует лабиринт, находит путь и отображает результат.
     */
    public void start() {
        commandSource.showMessageOrMaze("Добро пожаловать в MazeApp!");
        chooseGenerationAlgorithm();
        enterMazeDimensions();
        commandSource.showMessageOrMaze(renderer.render(game.maze()));
        enterStartAndEndPoints();
        chooseSolverAlgorithm();
        findAndDisplayPath();
    }

    private void chooseGenerationAlgorithm() {
        while (true) {
            try {
                int generationChoice = commandSource.getIntInput("Выберите алгоритм генерации (1 - Прима, 2 - DFS):");
                if (generationChoice == 1 || generationChoice == 2) {
                    game.generator(generationChoice == 1 ? new PrimGenerator() : new DFSGenerator());
                    return;
                } else {
                    commandSource.showMessageOrMaze(INVALID_CHOICE);
                }
            } catch (Exception e) {
                throw new NoSuchElementException(ERROR_INPUT);
            }
        }
    }

    private void enterMazeDimensions() {
        while (true) {
            try {
                int height = commandSource.getIntInput("Введите высоту лабиринта:");
                int width = commandSource.getIntInput("Введите ширину лабиринта:");
                if (height > 1 && width > 1) {
                    game.generateMaze(height, width);
                    return;
                } else {
                    commandSource.showMessageOrMaze("Размеры лабиринта должны быть больше 1.");
                }
            } catch (Exception e) {
                commandSource.showMessageOrMaze(ERROR_INPUT);
            }
        }
    }

    private void enterStartAndEndPoints() {
        while (true) {
            try {
                int startRow = commandSource.getIntInput("Введите начальную строку:");
                int startCol = commandSource.getIntInput("Введите начальный столбец:");
                int endRow = commandSource.getIntInput("Введите конечную строку:");
                int endCol = commandSource.getIntInput("Введите конечный столбец:");
                Coordinate start = new Coordinate(startRow, startCol);
                Coordinate end = new Coordinate(endRow, endCol);
                if (isValidCoordinate(start) && isValidCoordinate(end)) {
                    game.setStartAndEnd(start, end);
                    return;
                } else {
                    commandSource.showMessageOrMaze("Одна или обе из введённых точек некорректны. Попробуйте снова.");
                }
            } catch (Exception e) {
                commandSource.showMessageOrMaze(ERROR_INPUT);
            }
        }
    }

    private void chooseSolverAlgorithm() {
        while (true) {
            try {
                int solverChoice = commandSource.getIntInput("Выберите алгоритм поиска пути (1 - BFS, 2 - A*):");
                if (solverChoice == 1 || solverChoice == 2) {
                    game.solver(solverChoice == 1 ? new BFSSolver() : new AStarSolver());
                    return;
                } else {
                    commandSource.showMessageOrMaze(INVALID_CHOICE);
                }
            } catch (Exception e) {
                commandSource.showMessageOrMaze(ERROR_INPUT);
            }
        }
    }

    private void findAndDisplayPath() {
        List<Coordinate> path = game.findPath();
        if (path.isEmpty()) {
            commandSource.showMessageOrMaze("Путь не найден.");
        } else {
            commandSource.showMessageOrMaze("Найденный путь:");
            commandSource.showMessageOrMaze(renderer.render(game.maze(), path));
        }
    }
    private boolean isValidCoordinate(Coordinate coord) {
        int row = coord.row();
        int col = coord.col();
        return row >= 0 && row < game.maze().height()
            && col >= 0 && col < game.maze().width()
            && game.maze().getCell(row, col).type() == Cell.Type.PASSAGE;
    }

}
