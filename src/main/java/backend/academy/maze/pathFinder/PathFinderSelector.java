package backend.academy.maze.pathFinder;

import backend.academy.maze.pathFinder.AStarFinder.AStarPathFinder;
import backend.academy.maze.pathFinder.BFSMazeFinder.BFSMazePathFinder;

public final class PathFinderSelector {
    private PathFinderSelector() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static PathFinder finderSelector(PathFinderSelector.AlgorithmType type) {
        return switch (type) {
            case ASTAR -> new AStarPathFinder();
            case BFS -> new BFSMazePathFinder();
        };
    }

    public enum AlgorithmType {
        ASTAR,
        BFS
    }
}

