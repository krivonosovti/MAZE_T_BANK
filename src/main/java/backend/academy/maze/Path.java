package backend.academy.maze;

import java.util.List;

/**
 * Класс Path представляет собой путь в лабиринте, состоящий из списка координат
 * и связанного с ним значения стоимости. Этот класс используется для хранения
 * информации о найденном пути и его стоимости.
 */
public class Path {
    private List<Coordinate> path; // Список координат, представляющий путь
    private Integer cost; // Стоимость пути

    /**
     * Конструктор для создания нового объекта Path с заданным списком координат и стоимостью.
     *
     * @param path список координат, представляющий путь.
     * @param cost стоимость пути.
     */
    public Path(List<Coordinate> path, Integer cost) {
        this.path = path;
        this.cost = cost;
    }

    /**
     * Возвращает список координат, представляющий путь.
     *
     * @return список координат.
     */
    public List<Coordinate> getPath() {
        return path;
    }

    /**
     * Устанавливает новый список координат для пути.
     *
     * @param path новый список координат.
     */
    public void setPath(List<Coordinate> path) {
        this.path = path;
    }

    /**
     * Возвращает стоимость пути.
     *
     * @return стоимость пути.
     */
    public Integer getCost() {
        return cost;
    }
}
