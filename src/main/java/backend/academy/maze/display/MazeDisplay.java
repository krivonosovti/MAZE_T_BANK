package backend.academy.maze.display;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;

public interface MazeDisplay {
    char[] display(Maze maze);

    char[] display(char[] mazePicture, int width, List<Coordinate> path);

    String displayMatrix(Maze maze);
}
