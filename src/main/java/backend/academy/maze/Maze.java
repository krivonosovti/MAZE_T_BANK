package backend.academy.maze;

/**
 * Класс Maze представляет собой лабиринт, состоящий из ячеек.
 * Он содержит информацию о размере лабиринта (высоте и ширине)
 * и структуре его ячеек в виде двумерного массива.
 */
public final class Maze {
    private final int height; // Высота лабиринта
    private final int width;  // Ширина лабиринта
    private final Cell[][] grid; // Двумерный массив ячеек лабиринта

    /**
     * Конструктор для создания нового лабиринта с заданными размерами и ячейками.
     *
     * @param height высота лабиринта.
     * @param width ширина лабиринта.
     * @param grid двумерный массив ячеек, представляющий структуру лабиринта.
     */
    public Maze(int height, int width, Cell[][] grid) {
        this.height = height;
        this.width = width;
        this.grid = grid;
    }

    /**
     * Возвращает ячейку лабиринта по заданным координатам.
     *
     * @param row номер строки ячейки.
     * @param col номер столбца ячейки.
     * @return ячейка, находящаяся в указанных координатах.
     */
    public Cell getCell(int row, int col) {
        return grid[row][col];
    }

    /**
     * Возвращает высоту лабиринта.
     *
     * @return высота лабиринта.
     */
    public int getHeight() {
        return height;
    }

    /**
     * Возвращает ширину лабиринта.
     *
     * @return ширина лабиринта.
     */
    public int getWidth() {
        return width;
    }

    /**
     * Возвращает двумерный массив ячеек, представляющий структуру лабиринта.
     *
     * @return двумерный массив ячеек.
     */
    public Cell[][] getGrid() {
        return grid;
    }
}

