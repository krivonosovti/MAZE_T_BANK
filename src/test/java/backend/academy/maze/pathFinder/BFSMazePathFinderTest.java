package backend.academy.maze.pathFinder;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.pathFinder.BFSMazeFinder.BFSMazePathFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BFSMazePathFinderTest {
    private BFSMazePathFinder pathFinder;

    @BeforeEach
    void setUp() {
        pathFinder = new BFSMazePathFinder();
    }

    @Test
    void testFindPath_SimpleMaze() {
        Maze maze = createSimpleMaze();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        List<Coordinate> path = pathFinder.findPath(maze, start, end);

        assertEquals(5, path.size());
        assertTrue(path.contains(new Coordinate(0, 0)));
        assertTrue(path.contains(new Coordinate(1, 0)));
        assertTrue(path.contains(new Coordinate(1, 1)));
        assertTrue(path.contains(new Coordinate(2, 1)));
        assertTrue(path.contains(new Coordinate(2, 2)));
    }

    @Test
    void testFindPath_NoPath() {
        Maze maze = createMazeWithoutPath();
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(2, 2);

        List<Coordinate> path = pathFinder.findPath(maze, start, end);

        assertTrue(path.isEmpty());
    }

    @Test
    void testFindPath_StartEqualsEnd() {
        Maze maze = createSimpleMaze();
        Coordinate startAndEnd = new Coordinate(1, 1);

        List<Coordinate> path = pathFinder.findPath(maze, startAndEnd, startAndEnd);

        assertEquals(1, path.size());
        assertTrue(path.contains(startAndEnd));
    }

    private Maze createSimpleMaze() {
        Cell[][] cells = {
            {new Cell(0, 0, Cell.Type.PASSAGE, 1), new Cell(0, 1, Cell.Type.WALL, 1), new Cell(0, 2, Cell.Type.PASSAGE, 1)},
            {new Cell(1, 0, Cell.Type.PASSAGE, 1), new Cell(1, 1, Cell.Type.PASSAGE, 1), new Cell(1, 2, Cell.Type.WALL, 1)},
            {new Cell(2, 0, Cell.Type.WALL, 1), new Cell(2, 1, Cell.Type.PASSAGE, 1), new Cell(2, 2, Cell.Type.PASSAGE, 1)}
        };
        return new Maze(cells.length, cells[0].length, cells); // Используем высоту и ширину из массива
    }

    private Maze createMazeWithoutPath() {
        Cell[][] cells = {
            {new Cell(0, 0, Cell.Type.WALL, 1), new Cell(0, 1, Cell.Type.WALL, 1), new Cell(0, 2, Cell.Type.WALL, 1)},
            {new Cell(1, 0, Cell.Type.WALL, 1), new Cell(1, 1, Cell.Type.WALL, 1), new Cell(1, 2, Cell.Type.WALL, 1)},
            {new Cell(2, 0, Cell.Type.WALL, 1), new Cell(2, 1, Cell.Type.WALL, 1), new Cell(2, 2, Cell.Type.WALL, 1)}
        };
        return new Maze(cells.length, cells[0].length, cells); // Используем высоту и ширину из массива
    }
}
