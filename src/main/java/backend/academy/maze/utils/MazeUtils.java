package backend.academy.maze.utils;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Edge;
import backend.academy.maze.factory.Distribution;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Утилитный класс MazeUtils предоставляет статические методы для работы с лабиринтом,
 * включая инициализацию сетки, выбор случайных ячеек и добавление ребер к границам.
 * Этот класс не может быть инстанцирован.
 */
public final class MazeUtils {

    private MazeUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Инициализирует сетку ячеек, устанавливая все ячейки как стены.
     *
     * @param grid двумерный массив ячеек, представляющий лабиринт
     */
    public static void initializeGrid(Cell[][] grid) {
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL, 0);
            }
        }
    }

    /**
     * Выбирает случайную стартовую ячейку из лабиринта и устанавливает ее как проход.
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @param random объект для генерации случайных чисел
     * @param distribution объект для генерации веса ячейки
     * @param grid двумерный массив ячеек, представляющий лабиринт
     * @return координаты выбранной стартовой ячейки
     */
    public static Coordinate selectRandomStartCell(int height, int width, Random random,
        Distribution distribution, Cell[][] grid) {
        int startRow = random.nextInt(height / 2) * 2 + 1;
        int startCol = random.nextInt(width / 2) * 2 + 1;
        grid[startRow][startCol] = new Cell(startRow, startCol, Cell.Type.PASSAGE, distribution.generateWeight());
        return new Coordinate(startRow, startCol);
    }

    /**
     * Добавляет ячейки границы к списку, основываясь на координатах.
     *
     * @param row координата строки текущей ячейки
     * @param col координата столбца текущей ячейки
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @param frontiers список, в который добавляются ячейки границы
     * @param distribution объект для генерации веса границы
     */
    public static void addFrontierCells(int row, int col, int height, int width, List<Edge> frontiers,
        Distribution distribution) {
        if (col - 2 >= 0) {
            frontiers.add(new Edge(new Coordinate(row, col), new Coordinate(row, col - 2),
                distribution.generateWeight()));
        }
        if (col + 2 < width) {
            frontiers.add(new Edge(new Coordinate(row, col), new Coordinate(row, col + 2),
                distribution.generateWeight()));
        }
        if (row - 2 >= 0) {
            frontiers.add(new Edge(new Coordinate(row, col), new Coordinate(row - 2, col),
                distribution.generateWeight()));
        }
        if (row + 2 < height) {
            frontiers.add(new Edge(new Coordinate(row, col), new Coordinate(row + 2, col),
                distribution.generateWeight()));
        }
    }

    /**
     * Добавляет случайный цикл в лабиринт, соединяя проходы.
     *
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @param grid двумерный массив ячеек, представляющий лабиринт
     * @param random объект для генерации случайных чисел
     * @param distribution объект для генерации веса ячейки
     */
    public static void addRandomCycle(int height, int width, Cell[][] grid, SecureRandom random,
        Distribution distribution) {
        while (true) {
            // Выбираем случайную точку лабиринта
            int row = random.nextInt(height / 2) * 2 + 1;
            int col = random.nextInt(width / 2) * 2 + 1;

            // Проверяем соседние клетки, если есть возможность создать цикл
            List<Coordinate> neighbors = new ArrayList<>();
            if (row - 2 > 0 && grid[row - 2][col].type() == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(row - 2, col));
            }
            if (row + 2 < height && grid[row + 2][col].type() == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(row + 2, col));
            }
            if (col - 2 > 0 && grid[row][col - 2].type() == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(row, col - 2));
            }
            if (col + 2 < width && grid[row][col + 2].type() == Cell.Type.PASSAGE) {
                neighbors.add(new Coordinate(row, col + 2));
            }

            if (!neighbors.isEmpty()) {
                // Выбираем случайного соседа и соединяем
                Coordinate neighbor = neighbors.get(random.nextInt(neighbors.size()));
                int wallRow = (row + neighbor.row()) / 2;
                int wallCol = (col + neighbor.col()) / 2;
                grid[wallRow][wallCol] = new Cell(wallRow, wallCol,
                    Cell.Type.PASSAGE, distribution.generateWeight());
                break; // Завершаем цикл, как только добавили новый путь
            }
        }
    }
}
