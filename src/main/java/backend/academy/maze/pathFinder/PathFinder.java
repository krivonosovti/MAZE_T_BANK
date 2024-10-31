package backend.academy.maze.pathFinder;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.Path;

/**
 * Интерфейс для поиска пути в лабиринте.
 * <p>
 * Этот интерфейс определяет метод для поиска пути между двумя координатами в заданном лабиринте.
 * Реализации этого интерфейса должны предоставлять конкретные алгоритмы поиска пути.
 * </p>
 */
public interface PathFinder {

    /**
     * Находит путь в лабиринте от начальной координаты до конечной.
     *
     * @param maze лабиринт, в котором будет осуществляться поиск пути
     * @param start координаты начальной точки
     * @param end координаты конечной точки
     * @return объект {@link Path}, представляющий найденный путь.
     *         Если путь не найден, возвращается пустой объект Path.
     */
    Path findPath(Maze maze, Coordinate start, Coordinate end);
}
