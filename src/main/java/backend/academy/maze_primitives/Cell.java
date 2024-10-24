package backend.academy.maze_primitives;

/*
    ячейка лабиринта, которая может быть либо стеной, либо проходом
 */
public record Cell(int row, int col, Type type) {

    public enum Type { WALL, PASSAGE }
}
