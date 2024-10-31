package backend.academy.maze.pathFinder;

import backend.academy.maze.pathFinder.AStarFinder.AStarPathFinder;
import backend.academy.maze.pathFinder.BFSMazeFinder.BFSMazePathFinder;

/**
 * Селектор для выбора алгоритма поиска пути.
 * <p>
 * Этот класс предоставляет статический метод для выбора алгоритма поиска пути
 * из доступных реализаций, таких как A* и BFS.
 * </p>
 */
public final class PathFinderSelector {

    // Закрытый конструктор для предотвращения создания экземпляров этого класса
    private PathFinderSelector() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Выбирает реализацию алгоритма поиска пути в зависимости от указанного типа.
     *
     * @param type тип алгоритма поиска пути, который нужно использовать
     * @return реализация интерфейса {@link PathFinder} в соответствии с заданным типом
     */
    public static PathFinder finderSelector(PathFinderSelector.AlgorithmType type) {
        return switch (type) {
            case ASTAR -> new AStarPathFinder();
            case BFS -> new BFSMazePathFinder();
        };
    }

    /**
     * Перечисление для определения доступных типов алгоритмов поиска пути.
     */
    public enum AlgorithmType {
        ASTAR,  // Алгоритм A*
        BFS      // Алгоритм поиска в ширину (BFS)
    }
}
