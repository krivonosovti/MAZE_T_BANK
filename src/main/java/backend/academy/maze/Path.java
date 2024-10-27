package backend.academy.maze;

import java.util.List;

public class Path {
    private List<Coordinate> path;
    private Integer cost;

    public Path(List<Coordinate> path, Integer cost) {
        this.path = path;
        this.cost = cost;
    }

    public List<Coordinate> getPath() {
        return path;
    }

    public void setPath(List<Coordinate> path) {
        this.path = path;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }
}
