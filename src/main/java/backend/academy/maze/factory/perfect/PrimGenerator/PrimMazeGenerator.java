package backend.academy.maze.factory.perfect.PrimGenerator;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Edge;
import backend.academy.maze.Maze;
import backend.academy.maze.factory.Distribution;
import backend.academy.maze.factory.MazeGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static backend.academy.maze.utils.MazeUtils.addFrontierCells;

/**
 * Генератор лабиринтов с использованием алгоритма Прима.
 * Этот класс реализует интерфейс MazeGenerator и создает
 * лабиринт с использованием метода, основанного на случайном
 * выборе ребер из набора фронтов.
 */
public class PrimMazeGenerator implements MazeGenerator {
    private final Random random = new Random();
    private Distribution distribution = new Distribution();

    /**
     * Генерирует лабиринт заданного размера.
     *
     * @param height высота лабиринта (должна быть нечетным числом)
     * @param width  ширина лабиринта (должна быть нечетным числом)
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если высота или ширина четные числа
     */
    @Override
    public Maze generateMaze(int height, int width) {
        if (height % 2 == 0 || width % 2 == 0) {
            throw new IllegalArgumentException("Height and width must be odd numbers to create a proper maze.");
        }

        Cell[][] grid = new Cell[height][width];

        // Инициализация всех клеток как стен
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL, 0);
            }
        }

        // Выбираем случайную начальную клетку
        int startRow = random.nextInt(height / 2) * 2 + 1;
        int startCol = random.nextInt(width / 2) * 2 + 1;
        grid[startRow][startCol] = new Cell(startRow, startCol, Cell.Type.PASSAGE, distribution.generateWeight());

        // Список возможных ребер (фронт)
        List<Edge> frontiers = new ArrayList<>();
        addFrontierCells(startRow, startCol, height, width, frontiers, distribution);

        // Алгоритм Прима с добавлением циклов
        while (!frontiers.isEmpty()) {
            // Выбираем случайное ребро из фронта
            Edge edge = frontiers.remove(random.nextInt(frontiers.size()));
            Coordinate from = edge.getFrom();
            Coordinate to = edge.getTo();

            // Если целевая клетка непосещена, соединяем ее с текущей
            if (grid[to.row()][to.col()].type() == Cell.Type.WALL) {
                // Удаляем стену между клетками
                int wallRow = (from.row() + to.row()) / 2;
                int wallCol = (from.col() + to.col()) / 2;
                grid[wallRow][wallCol] = new Cell(wallRow, wallCol,
                    Cell.Type.PASSAGE, distribution.generateWeight());

                // Целевая клетка становится проходом
                grid[to.row()][to.col()] = new Cell(to.row(), to.col(),
                    Cell.Type.PASSAGE, distribution.generateWeight());

                // Добавляем новые соседние клетки к фронту
                addFrontierCells(to.row(), to.col(), height, width, frontiers, distribution);
            }
        }

        return new Maze(height, width, grid);
    }
}
