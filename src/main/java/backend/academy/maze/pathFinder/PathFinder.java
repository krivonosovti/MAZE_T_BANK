package backend.academy.maze.pathFinder;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;

public interface PathFinder {
    List<Coordinate> findPath(Maze maze, Coordinate start, Coordinate end);
}
