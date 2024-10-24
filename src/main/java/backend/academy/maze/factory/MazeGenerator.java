package backend.academy.maze.factory;

import backend.academy.maze.Maze;

public interface MazeGenerator {
    Maze generateMaze(int height, int width);
}

