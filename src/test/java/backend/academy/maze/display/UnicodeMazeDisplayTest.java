package backend.academy.maze.display;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UnicodeMazeDisplayTest {

    private UnicodeMazeDisplay mazeDisplay;
    private Maze mockMaze;

    @BeforeEach
    void setUp() {
        mazeDisplay = new UnicodeMazeDisplay();
        // Создание мока для Maze
        mockMaze = createMockMaze();
    }

    // Метод для создания мока Maze
    private Maze createMockMaze() {
        Cell[][] grid = new Cell[][] {
            { new Cell(0, 0, Cell.Type.WALL, 0), new Cell(0, 1, Cell.Type.PASSAGE, 3), new Cell(0, 2, Cell.Type.WALL, 0) },
            { new Cell(1, 0, Cell.Type.PASSAGE, 4), new Cell(1, 1, Cell.Type.PASSAGE, 5), new Cell(1, 2, Cell.Type.WALL, 0) }
        };
        return new Maze(2, 3, grid);
    }

    @Test
    void testSelectSimbol() {
        Cell wallCell = new Cell(0, 0, Cell.Type.WALL, 0);
        Cell passageCell = new Cell(0, 1, Cell.Type.PASSAGE, 3);
        Cell forestCell = new Cell(1, 0, Cell.Type.PASSAGE, 4);
        Cell desertCell = new Cell(1, 1, Cell.Type.PASSAGE, 5);

        assertEquals('█', mazeDisplay.selectSimbol(wallCell));
        assertEquals(' ', mazeDisplay.selectSimbol(passageCell));
        assertEquals('#', mazeDisplay.selectSimbol(forestCell));
        assertEquals('X', mazeDisplay.selectSimbol(desertCell));
    }

    @Test
    void testDisplay() {
        char[] expectedDisplay = {
            '█', '█', '█',
            ' ', ' ', ' ',
            '█', '█', '█',
            '\n',
            '#', '#', '#',
            'X', 'X', 'X',
            '█', '█', '█',
            '\n'
        };

        char[] resultDisplay = mazeDisplay.display(mockMaze);

        assertArrayEquals(expectedDisplay, resultDisplay);
    }
    @Test
    void testSelectSimbol_InvalidWeight() {
        Cell invalidCell = new Cell(0, 0, Cell.Type.WALL, -1);

        assertThrows(IllegalArgumentException.class, () -> {
            mazeDisplay.selectSimbol(invalidCell);
        });
    }
}
