package backend.academy.maze;

import java.util.Objects;

public class Edge {
    private final Coordinate from;
    private final Coordinate to;
    private final int weight;

    public Edge(Coordinate from, Coordinate to, int weight) {
        this.from = from;
        this.to = to;
        this.weight = weight;
    }

    public Coordinate getFrom() {
        return from;
    }

    public Coordinate getTo() {
        return to;
    }

    public int getWeight() {
        return weight;
    }

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

    @Override
    public int hashCode() {
        return Objects.hash(from, to, weight);
    }

    @Override
    public String toString() {
        return "Edge{"
            + "from=" + from
            + ", to=" + to
            + ", weight=" + weight
            + '}';
    }
}
