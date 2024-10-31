package backend.academy.maze;

import java.util.Objects;

/**
 * Класс Edge представляет собой ребро в графе, соединяющее две координаты.
 * Он содержит информацию о начальной и конечной координатах, а также о весе ребра,
 * который может использоваться для алгоритмов, связанных с графами, таких как
 * поиск кратчайшего пути.
 */
public class Edge {
    private final Coordinate from; // Начальная координата
    private final Coordinate to; // Конечная координата
    private final int weight; // Вес ребра

    /**
     * Конструктор для создания ребра с заданными координатами и весом.
     *
     * @param from начальная координата ребра.
     * @param to конечная координата ребра.
     * @param weight вес ребра.
     */
    public Edge(Coordinate from, Coordinate to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    /**
     * Возвращает начальную координату ребра.
     *
     * @return начальная координата.
     */
    public Coordinate getFrom() {
        return from;
    }

    /**
     * Возвращает конечную координату ребра.
     *
     * @return конечная координата.
     */
    public Coordinate getTo() {
        return to;
    }

    /**
     * Возвращает вес ребра.
     *
     * @return вес ребра.
     */
    public int getWeight() {
        return weight;
    }
    /**
     * Переопределяет метод equals для сравнения объектов класса Edge.
     * Два объекта считаются равными, если они ссылаются на один и тот же объект
     * или если у них одинаковые значения начальной и конечной координат,
     * а также одинаковый вес.
     *
     * @param o объект, с которым производится сравнение.
     * @return true, если объекты равны, иначе false.
     */

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Edge edge)) {
            return false;
        }
        return weight == edge.weight
            && Objects.equals(from, edge.from)
            && Objects.equals(to, edge.to);
    }

    /**
     * Переопределяет метод hashCode для вычисления хеш-кода объекта Edge.
     * Хеш-код рассчитывается на основе начальной и конечной координат,
     * а также веса ребра. Это позволяет корректно использовать объекты
     * класса Edge в коллекциях, таких как HashMap или HashSet.
     *
     * @return хеш-код объекта Edge.
     */
    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    /**
     * Переопределяет метод toString для получения строкового представления
     * объекта Edge. Возвращает строку, содержащую информацию о начальной
     * и конечной координатах, а также весе ребра.
     *
     * @return строковое представление объекта Edge.
     */
    @Override
    public String toString() {
        return "Edge{"
            + "from=" + from
            + ", to=" + to
            + ", weight=" + weight
            + '}';
    }
}
