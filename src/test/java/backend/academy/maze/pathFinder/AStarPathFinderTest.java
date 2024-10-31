package backend.academy.maze.pathFinder;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.pathFinder.AStarFinder.AStarPathFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class AStarPathFinderTest {

    private AStarPathFinder pathFinder;
    private Maze maze;

    @BeforeEach
    public void setUp() {
        pathFinder = new AStarPathFinder();
        maze = createTestMaze();
    }

    @Test
    public void testFindPath_Successful() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        List<Coordinate> path = pathFinder.findPath(maze, start, end).getPath();

        assertNotNull(path, "Путь не должен быть null");
        assertEquals(9, path.size(), "Длина пути должна быть корректной");
        assertEquals(start, path.get(0), "Начальная точка должна совпадать");
        assertEquals(end, path.get(path.size() - 1), "Конечная точка должна совпадать");
    }

    @Test
    public void testFindPath_NoPath() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(4, 4);

        maze.getGrid()[1][0] = new Cell(1, 0, Cell.Type.WALL, 0);
        maze.getGrid()[1][1] = new Cell(1, 1, Cell.Type.WALL, 0);
        maze.getGrid()[0][1] = new Cell(0, 1, Cell.Type.WALL, 0);

        List<Coordinate> path = pathFinder.findPath(maze, start, end).getPath();

        assertTrue(path.isEmpty(), "Путь должен быть пустым, если его невозможно найти");
    }

    @Test
    public void testFindPath_StartEqualsEnd() {
        Coordinate start = new Coordinate(2, 2);
        Coordinate end = new Coordinate(2, 2);

        List<Coordinate> path = pathFinder.findPath(maze, start, end).getPath();

        assertEquals(1, path.size(), "Длина пути должна быть 1, если старт и конец совпадают");
        assertEquals(start, path.get(0), "Путь должен содержать только стартовую точку");
    }

    @Test
    public void testFindPath_EdgeCase() {
        Coordinate start = new Coordinate(0, 0);
        Coordinate end = new Coordinate(0, 4);

        List<Coordinate> path = pathFinder.findPath(maze, start, end).getPath();

        assertNotNull(path, "Путь не должен быть null");
        assertEquals(5, path.size(), "Путь должен быть вдоль края лабиринта");
        assertEquals(start, path.get(0), "Начальная точка должна совпадать");
        assertEquals(end, path.get(path.size() - 1), "Конечная точка должна совпадать");
    }


    private Maze createTestMaze() {
        int height = 5;
        int width = 5;
        Cell[][] grid = new Cell[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE, 3); // Пустая клетка с весом 1
            }
        }

        return new Maze(height, width, grid);
    }
}
