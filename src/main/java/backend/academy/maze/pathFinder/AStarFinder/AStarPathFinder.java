package backend.academy.maze.pathFinder.AStarFinder;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.Path;
import backend.academy.maze.pathFinder.Node;
import backend.academy.maze.pathFinder.PathFinder;
import java.util.Collections;
import java.util.PriorityQueue;
import static backend.academy.maze.utils.PathUtils.heuristic;
import static backend.academy.maze.utils.PathUtils.initializeCostArray;
import static backend.academy.maze.utils.PathUtils.processNeighbors;
import static backend.academy.maze.utils.PathUtils.reconstructPath;

/**
 * Реализация поиска пути с использованием алгоритма A*.
 * <p>
 * Этот класс реализует интерфейс {@link PathFinder} и предоставляет метод для нахождения
 * кратчайшего пути в лабиринте от заданной начальной до конечной точки с использованием
 * эвристики для оценки стоимости пути.
 * </p>
 */
public class AStarPathFinder implements PathFinder {

    /**
     * Находит путь от начальной координаты до конечной в заданном лабиринте.
     *
     * @param maze  лабиринт, в котором будет осуществляться поиск пути
     * @param start начальная координата (точка входа)
     * @param end   конечная координата (точка выхода)
     * @return объект {@link Path}, представляющий найденный путь или пустой путь, если путь не найден
     */
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
}
