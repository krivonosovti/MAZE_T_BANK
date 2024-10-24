package backend.academy.maze.pathFinder;

import backend.academy.maze.Coordinate;

public class Node implements Comparable<Node> {
    private final Coordinate coordinate;
    private final Node parent;
    private final int gCost;
    private final int fCost;

    public Node(Coordinate coordinate, Node parent, int gCost, int fCost) {
        this.coordinate = coordinate;
        this.parent = parent;
        this.gCost = gCost;
        this.fCost = fCost;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public Node getParent() {
        return parent;
    }

    public int getGCost() {
        return gCost;
    }

    public int getFCost() {
        return fCost;
    }

    @Override
    public int compareTo(Node other) {
        return Integer.compare(this.fCost, other.fCost);
    }

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

    @Override
    public int hashCode() {
        return coordinate.hashCode();
    }
}
