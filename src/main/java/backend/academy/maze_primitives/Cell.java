package backend.academy.maze_primitives;

/**
 * Класс, представляющий ячейку лабиринта.
 *
 * @param row строка ячейки
 * @param col столбец ячейки
 * @param type тип ячейки (проход или стена)
 */
public record Cell(int row, int col, Type type) {

    /**
     * Типы ячеек в лабиринте.
     */
    public enum Type { WALL, PASSAGE }
}
