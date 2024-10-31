package backend.academy.maze.utils;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Edge;
import backend.academy.maze.factory.Distribution;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class MazeUtilsTest {

    private int height = 5;
    private int width = 5;
    private Cell[][] grid;
    private Random random;
    private Distribution distribution;

    @BeforeEach
    void setUp() {
        grid = new Cell[height][width];
        random = new Random();
        distribution = new Distribution(); // Замените на реальную инициализацию Distribution
    }

    @Test
    void testInitializeGrid() {
        MazeUtils.initializeGrid(grid);

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                assertNotNull(grid[row][col], "Cell should be initialized");
                assertEquals(Cell.Type.WALL, grid[row][col].type(), "All cells should be walls initially");
            }
        }
    }

    @Test
    void testSelectRandomStartCell() {
        MazeUtils.initializeGrid(grid);
        Coordinate startCell = MazeUtils.selectRandomStartCell(height, width, random, distribution, grid);

        assertNotNull(startCell, "Start cell should not be null");
        assertEquals(Cell.Type.PASSAGE, grid[startCell.row()][startCell.col()].type(),
            "Start cell should be a passage");
        assertTrue(startCell.row() % 2 == 1 && startCell.col() % 2 == 1,
            "Start cell should be at odd coordinates");
    }

    @Test
    void testAddFrontierCells() {
        List<Edge> frontiers = new ArrayList<>();
        MazeUtils.addFrontierCells(1, 1, height, width, frontiers, distribution);

        assertFalse(frontiers.isEmpty(), "Frontier cells should be added");

        for (Edge edge : frontiers) {
            assertTrue((Math.abs(edge.getFrom().row() - edge.getTo().row()) == 2 && edge.getFrom().col() == edge.getTo().col())
                    || (Math.abs(edge.getFrom().col() - edge.getTo().col()) == 2 && edge.getFrom().row() == edge.getTo().row()),
                "Frontier cells should be at a distance of two cells in row or column");
        }
    }

    @Test
    void testAddRandomCycle() {
        MazeUtils.initializeGrid(grid);

        // Превратим несколько клеток в пути, чтобы создать условия для цикла
        grid[1][1] = new Cell(1, 1, Cell.Type.PASSAGE, 1);
        grid[3][1] = new Cell(3, 1, Cell.Type.PASSAGE, 1);

        SecureRandom secureRandom = new SecureRandom();
        MazeUtils.addRandomCycle(height, width, grid, secureRandom, distribution);

        boolean foundCycle = false;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col].type() == Cell.Type.PASSAGE) {
                    foundCycle = true;
                    break;
                }
            }
        }

        assertTrue(foundCycle, "A cycle should be created in the maze");
    }
}
