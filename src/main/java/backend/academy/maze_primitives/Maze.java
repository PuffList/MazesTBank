package backend.academy.maze_primitives;

import lombok.Getter;

/*
    класс, представляющий лабиринт (состоит из ячеек типа Cell)
 */
public final class Maze {

    @Getter
    private final int height;
    @Getter
    private final int width;
    private final Cell[][] grid;

    public Maze(int height, int width) {
        this.height = height * 2 - 1;
        this.width = width * 2 - 1;
        this.grid = new Cell[this.height][this.width];

        for (int row = 0; row < this.height; row++) {
            for (int col = 0; col < this.width; col++) {
                if (row % 2 == 0 && col % 2 == 0) {
                    grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE);
                } else {
                    grid[row][col] = new Cell(row, col, Cell.Type.WALL);
                }
            }
        }
    }

    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    public void setCell(int row, int col, Cell.Type type) {
        grid[row][col] = new Cell(row, col, type);
    }
}
