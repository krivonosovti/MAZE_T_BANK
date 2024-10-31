package backend.academy.maze.pathFinder;

import backend.academy.maze.Coordinate;

/**
 * Представляет узел в процессе поиска пути.
 * <p>
 * Каждый узел содержит координаты клетки, информацию о родительском узле, а также
 * значения gCost и fCost, которые используются для определения стоимости пути.
 * </p>
 */
public class Node implements Comparable<Node> {
    private final Coordinate coordinate;
    private final Node parent;
    private final int gCost;
    private final int fCost;

    /**
     * Конструктор для создания узла.
     *
     * @param coordinate координаты узла в лабиринте
     * @param parent родительский узел, от которого был достигнут данный узел
     * @param gCost стоимость пути от начальной точки до этого узла
     * @param fCost общая стоимость пути (gCost + эвристическая стоимость)
     */
    public Node(Coordinate coordinate, Node parent, int gCost, int fCost) {
        this.coordinate = coordinate;
        this.parent = parent;
        this.gCost = gCost;
        this.fCost = fCost;
    }

    /**
     * Получает координаты узла.
     *
     * @return координаты узла
     */
    public Coordinate getCoordinate() {
        return coordinate;
    }

    /**
     * Получает родительский узел.
     *
     * @return родительский узел
     */
    public Node getParent() {
        return parent;
    }

    /**
     * Получает gCost узла.
     *
     * @return gCost узла
     */
    public int getGCost() {
        return gCost;
    }

    /**
     * Получает fCost узла.
     *
     * @return fCost узла
     */
    public int getFCost() {
        return fCost;
    }

    /**
     * Сравнивает текущий узел с другим узлом по значению fCost.
     *
     * @param other другой узел для сравнения
     * @return отрицательное число, ноль или положительное число, если fCost этого узла меньше,
     *         равно или больше, соответственно, fCost другого узла
     */
    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.fCost, other.fCost);
    }

    /**
     * Сравнивает текущий узел с другим объектом.
     *
     * @param obj объект для сравнения
     * @return {@code true}, если объекты равны, иначе {@code false}
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Node node = (Node) obj;
        return coordinate.equals(node.coordinate);
    }

    /**
     * Возвращает хэш-код узла.
     *
     * @return хэш-код узла
     */
    @Override
    public int hashCode() {
        return coordinate.hashCode();
    }
}
