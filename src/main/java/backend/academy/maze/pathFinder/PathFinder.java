package backend.academy.maze.pathFinder;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.Path;

public interface PathFinder {
    Path findPath(Maze maze, Coordinate start, Coordinate end);
}
