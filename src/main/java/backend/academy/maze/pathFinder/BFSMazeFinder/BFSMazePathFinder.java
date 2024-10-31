package backend.academy.maze.pathFinder.BFSMazeFinder;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.Path;
import backend.academy.maze.pathFinder.Node;
import backend.academy.maze.pathFinder.PathFinder;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;
import static backend.academy.maze.utils.PathUtils.processNeighbors;
import static backend.academy.maze.utils.PathUtils.reconstructPath;

/**
 * Реализация поиска пути с использованием алгоритма поиска в ширину (BFS).
 * <p>
 * Этот класс реализует интерфейс {@link PathFinder} и предоставляет метод для нахождения
 * кратчайшего пути в лабиринте от заданной начальной до конечной точки, используя метод BFS.
 * </p>
 */
public class BFSMazePathFinder implements PathFinder {

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

        // Инициализация массива посещенных клеток
        boolean[][] visited = new boolean[height][width];

        // Очередь для BFS
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start, null, 0, 0));
        visited[start.row()][start.col()] = true;

        // Основной цикл BFS
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            Coordinate currentCoord = currentNode.getCoordinate();

            // Если достигли конца, восстанавливаем путь
            if (currentCoord.equals(end)) {
                return new Path(reconstructPath(currentNode), currentNode.getGCost());
            }

            // Обработка соседних клеток
            processNeighbors(maze, currentNode, visited, queue);
        }

        // Если путь не найден
        return new Path(Collections.emptyList(), 0);
    }
}
