package backend.academy.maze.utils;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.pathFinder.Node;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Утилитный класс PathUtils предоставляет статические методы для работы с путями в лабиринте,
 * включая инициализацию массивов стоимости, обработку соседей и восстановление пути.
 * Этот класс не может быть инстанцирован.
 */
public final class PathUtils {

    private PathUtils() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    /**
     * Инициализирует двумерный массив стоимости, заполняя его заданным начальным значением.
     *
     * @param height высота массива
     * @param width ширина массива
     * @param initialValue начальное значение для заполнения массива
     * @return двумерный массив стоимости
     */
    public static int[][] initializeCostArray(int height, int width, int initialValue) {
        int[][] costArray = new int[height][width];
        for (int[] row : costArray) {
            Arrays.fill(row, initialValue);
        }
        return costArray;
    }

    /**
     * Обрабатывает соседние клетки текущего узла, обновляя стоимости и добавляя их в очередь.
     *
     * @param current текущий узел
     * @param maze лабиринт, содержащий сетку клеток
     * @param gCost массив g-стоимости
     * @param fCost массив f-стоимости
     * @param openSet очередь для узлов, которые нужно обработать
     * @param directions массив направлений для проверки соседей
     * @param end координаты целевой клетки
     */
    public static void processNeighbors(
        Node current, Maze maze, int[][] gCost, int[][] fCost,
        PriorityQueue<Node> openSet, int[][] directions, Coordinate end) {
        int currentRow = current.getCoordinate().row();
        int currentCol = current.getCoordinate().col();
        Cell[][] grid = maze.getGrid();
        int height = maze.getHeight();
        int width = maze.getWidth();

        for (int[] direction : directions) {
            int newRow = currentRow + direction[0];
            int newCol = currentCol + direction[1];

            if (isValid(newRow, newCol, height, width)) {
                Cell neighborCell = grid[newRow][newCol];
                if (neighborCell.type() == Cell.Type.WALL) {
                    continue;  // Пропускаем стены
                }

                // Рассчитываем новую стоимость пути через эту клетку (gCost)
                int newGCost = current.getGCost() + neighborCell.weight();

                if (newGCost < gCost[newRow][newCol]) {
                    gCost[newRow][newCol] = newGCost;
                    int newFCost = newGCost + heuristic(new Coordinate(newRow, newCol), end);
                    fCost[newRow][newCol] = newFCost;

                    Node neighborNode = new Node(new Coordinate(newRow, newCol), current, newGCost, newFCost);
                    openSet.add(neighborNode);
                }
            }
        }
    }

    /**
     * Вычисляет эвристику Манхэттенского расстояния между двумя координатами.
     *
     * @param a первая координата
     * @param b вторая координата
     * @return эвристическая стоимость (Манхэттенское расстояние) между a и b
     */
    public static int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    /**
     * Проверяет, находятся ли заданные координаты в пределах лабиринта.
     *
     * @param row координата строки
     * @param col координата столбца
     * @param height высота лабиринта
     * @param width ширина лабиринта
     * @return true, если координаты действительны, иначе false
     */
    public static boolean isValid(int row, int col, int height, int width) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    /**
     * Восстанавливает путь из узлов, начиная с конечного узла.
     *
     * @param endNode конечный узел
     * @return список координат, представляющий восстановленный путь
     */
    public static List<Coordinate> reconstructPath(Node endNode) {
        List<Coordinate> path = new ArrayList<>();
        for (Node at = endNode; at != null; at = at.getParent()) {
            path.add(at.getCoordinate());
        }
        Collections.reverse(path);  // Путь был восстановлен с конца, нужно развернуть
        return path;
    }

    /**
     * Обрабатывает соседние клетки для текущего узла и добавляет их в очередь.
     *
     * @param maze лабиринт, содержащий сетку клеток
     * @param currentNode текущий узел
     * @param visited массив для отслеживания посещенных клеток
     * @param queue очередь для узлов, которые нужно обработать
     */
    public static void processNeighbors(Maze maze, Node currentNode, boolean[][] visited, Queue<Node> queue) {
        Coordinate currentCoord = currentNode.getCoordinate();

        for (Coordinate neighbor : getNeighbors(maze, currentCoord)) {
            int row = neighbor.row();
            int col = neighbor.col();

            if (!visited[row][col] && maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                visited[row][col] = true;

                // Добавляем соседа в очередь, увеличивая gCost на 1
                queue.add(new Node(neighbor, currentNode,
                    currentNode.getGCost() + 1, 0)); // Увеличиваем gCost на 1 для каждой клетки
            }
        }
    }

    /**
     * Получает список соседних клеток для заданной координаты.
     *
     * @param maze лабиринт, содержащий сетку клеток
     * @param coord координаты текущей клетки
     * @return список соседних координат
     */
    public static List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = coord.row();
        int col = coord.col();

        // Проверяем каждое из направлений (верх, низ, влево, вправо)
        if (row - 1 >= 0) {
            neighbors.add(new Coordinate(row - 1, col));
        }
        if (row + 1 < maze.getHeight()) {
            neighbors.add(new Coordinate(row + 1, col));
        }
        if (col - 1 >= 0) {
            neighbors.add(new Coordinate(row, col - 1));
        }
        if (col + 1 < maze.getWidth()) {
            neighbors.add(new Coordinate(row, col + 1));
        }

        return neighbors;
    }
}
