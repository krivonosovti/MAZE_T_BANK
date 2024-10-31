package backend.academy.maze.utils;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.pathFinder.Node;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.PriorityQueue;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PathUtilsTest {

    private int height = 5;
    private int width = 5;
    private Maze maze;
    private int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Верх, низ, влево, вправо

    @BeforeEach
    void setUp() {
        maze = mock(Maze.class);
        when(maze.getHeight()).thenReturn(height);
        when(maze.getWidth()).thenReturn(width);

        Cell[][] grid = new Cell[height][width];
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE, 1); // все клетки - проходы
            }
        }
        when(maze.getGrid()).thenReturn(grid);
    }

    @Test
    void testInitializeCostArray() {
        int initialValue = Integer.MAX_VALUE;
        int[][] costArray = PathUtils.initializeCostArray(height, width, initialValue);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                assertEquals(initialValue, costArray[row][col],
                    "Every cell should be initialized to the initial value");
            }
        }
    }

    @Test
    void testProcessNeighbors() {
        int[][] gCost = PathUtils.initializeCostArray(height, width, Integer.MAX_VALUE);
        int[][] fCost = PathUtils.initializeCostArray(height, width, Integer.MAX_VALUE);
        PriorityQueue<Node> openSet = new PriorityQueue<>();
        Node startNode = new Node(new Coordinate(1, 1), null, 0, 0);
        gCost[1][1] = 0;

        PathUtils.processNeighbors(startNode, maze, gCost, fCost, openSet, directions, new Coordinate(4, 4));

        assertFalse(openSet.isEmpty(), "Open set should not be empty after processing neighbors");
        for (Node node : openSet) {
            assertTrue(gCost[node.getCoordinate().row()][node.getCoordinate().col()] < Integer.MAX_VALUE,
                "G-cost should be updated for reachable cells");
        }
    }

    @Test
    void testHeuristic() {
        Coordinate a = new Coordinate(0, 0);
        Coordinate b = new Coordinate(3, 4);

        int result = PathUtils.heuristic(a, b);
        assertEquals(7, result, "Manhattan distance should be correctly calculated");
    }

    @Test
    void testIsValid() {
        assertTrue(PathUtils.isValid(0, 0, height, width), "Top-left corner should be valid");
        assertFalse(PathUtils.isValid(-1, 0, height, width), "Negative row should be invalid");
        assertFalse(PathUtils.isValid(0, -1, height, width), "Negative column should be invalid");
        assertFalse(PathUtils.isValid(height, 0, height, width), "Row out of bounds should be invalid");
        assertFalse(PathUtils.isValid(0, width, height, width), "Column out of bounds should be invalid");
    }

    @Test
    void testGetNeighbors() {
        Coordinate coord = new Coordinate(2, 2);
        List<Coordinate> neighbors = PathUtils.getNeighbors(maze, coord);

        assertEquals(4, neighbors.size(), "A cell in the middle should have 4 neighbors");
        assertTrue(neighbors.contains(new Coordinate(1, 2)), "Neighbors should contain cell above");
        assertTrue(neighbors.contains(new Coordinate(3, 2)), "Neighbors should contain cell below");
        assertTrue(neighbors.contains(new Coordinate(2, 1)), "Neighbors should contain cell to the left");
        assertTrue(neighbors.contains(new Coordinate(2, 3)), "Neighbors should contain cell to the right");
    }
}
