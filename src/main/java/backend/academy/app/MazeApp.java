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
        int generationChoice;

        while (true) {
            try {
                generationChoice = commandSource.getIntInput("Выберите алгоритм генерации (1 - Прима, 2 - DFS):");
                if (generationChoice == 1 || generationChoice == 2) {
                    break;
                } else {
                    commandSource.showMessageOrMaze("Некорректный выбор. Введите 1 или 2.");
                }
            } catch (Exception e) {
                throw new NoSuchElementException("Ошибка ввода. Попробуйте снова.");
            }
        }

        game.generator(generationChoice == 1 ? new PrimGenerator() : new DFSGenerator());
        int height, width;

        while (true) {
            try {
                height = commandSource.getIntInput("Введите высоту лабиринта:");
                width = commandSource.getIntInput("Введите ширину лабиринта:");
                if (height > 1 && width > 1) {
                    break;
                } else {
                    commandSource.showMessageOrMaze("Размеры лабиринта должны быть больше 1.");
                }
            } catch (Exception e) {
                commandSource.showMessageOrMaze("Ошибка ввода. Попробуйте снова.");
            }
        }

        game.generateMaze(height, width);
        commandSource.showMessageOrMaze(renderer.render(game.maze()));

        Coordinate start = null, end = null;
        while (true) {
            try {
                int startRow = commandSource.getIntInput("Введите начальную строку:");
                int startCol = commandSource.getIntInput("Введите начальный столбец:");
                int endRow = commandSource.getIntInput("Введите конечную строку:");
                int endCol = commandSource.getIntInput("Введите конечный столбец:");
                start = new Coordinate(startRow, startCol);
                end = new Coordinate(endRow, endCol);
                if (isValidCoordinate(start) && isValidCoordinate(end)) {
                    break;
                } else {
                    commandSource.showMessageOrMaze("Одна или обе из введённых точек некорректны. Попробуйте снова.");
                }
            } catch (Exception e) {
                commandSource.showMessageOrMaze("Ошибка ввода. Попробуйте снова.");
            }
        }

        game.setStartAndEnd(start, end);
        int solverChoice;

        while (true) {
            try {
                solverChoice = commandSource.getIntInput("Выберите алгоритм поиска пути (1 - BFS, 2 - A*):");
                if (solverChoice == 1 || solverChoice == 2) {
                    break;
                } else {
                    commandSource.showMessageOrMaze("Некорректный выбор. Введите 1 или 2.");
                }
            } catch (Exception e) {
                commandSource.showMessageOrMaze("Ошибка ввода. Попробуйте снова.");
            }
        }

        game.solver(solverChoice == 1 ? new BFSSolver() : new AStarSolver());
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
