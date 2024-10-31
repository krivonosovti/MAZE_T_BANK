package backend.academy.maze.factory.perfect.KuskalGenerator;

import backend.academy.maze.Coordinate;
import java.util.HashMap;
import java.util.Map;

/**
 * Реализация структуры данных "Объединение-Поиск" (Union-Find).
 * Используется для управления множествами клеток при генерации лабиринтов,
 * позволяя эффективно объединять множества и находить их корни.
 */
public class UnionFind {
    private final Map<Coordinate, Coordinate> parent = new HashMap<>();
    private final Map<Coordinate, Integer> rank = new HashMap<>();

    /**
     * Инициализирует новое множество, где элемент сам себе родитель.
     *
     * @param coord координата, представляющая новый элемент множества
     */
    public void makeSet(Coordinate coord) {
        parent.put(coord, coord); // Каждый элемент сам себе родитель
        rank.put(coord, 0);       // Начальный ранг равен 0
    }

    /**
     * Находит корень множества, к которому принадлежит данный элемент.
     * Использует сжатие пути для оптимизации.
     *
     * @param coord координата, для которой нужно найти корень
     * @return координата корня множества
     */
    public Coordinate find(Coordinate coord) {
        if (!parent.get(coord).equals(coord)) {
            parent.put(coord, find(parent.get(coord))); // Сжатие пути
        }
        return parent.get(coord);
    }

    /**
     * Объединяет два множества, к которым принадлежат два элемента.
     * Объединение выполняется по рангу для оптимизации.
     *
     * @param coord1 первая координата
     * @param coord2 вторая координата
     */
    public void union(Coordinate coord1, Coordinate coord2) {
        Coordinate root1 = find(coord1);
        Coordinate root2 = find(coord2);

        if (!root1.equals(root2)) {
            int rank1 = rank.get(root1);
            int rank2 = rank.get(root2);

            // Объединение по рангу
            if (rank1 > rank2) {
                parent.put(root2, root1);
            } else if (rank1 < rank2) {
                parent.put(root1, root2);
            } else {
                parent.put(root2, root1);
                rank.put(root1, rank1 + 1);
            }
        }
    }
}
