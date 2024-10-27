package backend.academy.maze.pathFinder.AStarFinder;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.Path;
import backend.academy.maze.pathFinder.Node;
import backend.academy.maze.pathFinder.PathFinder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStarPathFinder implements PathFinder {

    @Override
    public Path findPath(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.getHeight();
        int width = maze.getWidth();
        Cell[][] grid = maze.getGrid();

        // Инициализируем gCost и fCost массивы
        int[][] gCost = initializeCostArray(height, width, Integer.MAX_VALUE);
        int[][] fCost = initializeCostArray(height, width, Integer.MAX_VALUE);

        gCost[start.row()][start.col()] = 0;
        fCost[start.row()][start.col()] = heuristic(start, end);

        PriorityQueue<Node> openSet = new PriorityQueue<>();
        openSet.add(new Node(start, null, 0, fCost[start.row()][start.col()]));

        // Направления движения: вверх, вниз, влево, вправо
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Основной цикл поиска
        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinate currentCoord = current.getCoordinate();

            if (currentCoord.equals(end)) {
                return new Path(reconstructPath(current), current.getGCost());
            }

            // Обработка соседних клеток
            processNeighbors(current, maze, gCost, fCost, openSet, directions, end);
        }

        return new Path(Collections.emptyList(), 0);  // Если путь не найден
    }

    // Инициализация массивов стоимости
    private int[][] initializeCostArray(int height, int width, int initialValue) {
        int[][] costArray = new int[height][width];
        for (int[] row : costArray) {
            Arrays.fill(row, initialValue);
        }
        return costArray;
    }

    // Обработка соседних клеток
    private void processNeighbors(Node current, Maze maze, int[][] gCost, int[][] fCost,
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

    // Эвристическая функция Манхэттенского расстояния
    private int heuristic(Coordinate a, Coordinate b) {
        return Math.abs(a.row() - b.row()) + Math.abs(a.col() - b.col());
    }

    // Проверка, что координаты находятся в пределах лабиринта
    private boolean isValid(int row, int col, int height, int width) {
        return row >= 0 && row < height && col >= 0 && col < width;
    }

    // Восстановление пути из узлов
    private List<Coordinate> reconstructPath(Node endNode) {
        List<Coordinate> path = new ArrayList<>();
        for (Node at = endNode; at != null; at = at.getParent()) {
            path.add(at.getCoordinate());
        }
        Collections.reverse(path);  // Путь был восстановлен с конца, нужно развернуть
        return path;
    }
}
