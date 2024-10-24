package backend.academy.maze.factory.perfect.PrimGenerator;

import backend.academy.maze.Maze;
import backend.academy.maze.Cell;
import backend.academy.maze.factory.perfect.PrimGenerator.PrimMazeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimMazeGeneratorTest {

    private PrimMazeGenerator primMazeGenerator;

    @BeforeEach
    void setUp() {
        primMazeGenerator = new PrimMazeGenerator();
    }

    @Test
    void testGenerateMazeValidDimensions() {
        // Тестируем генерацию лабиринта с корректными размерами
        int height = 5;
        int width = 5;
        Maze maze = primMazeGenerator.generateMaze(height, width);

        assertEquals(height, maze.getHeight(), "Высота лабиринта должна совпадать с заданной.");
        assertEquals(width, maze.getWidth(), "Ширина лабиринта должна совпадать с заданной.");
    }

    @Test
    void testGenerateMazeInvalidDimensions() {
        // Тестируем генерацию лабиринта с некорректными размерами
        int invalidHeight = 4;
        int invalidWidth = 6;

        assertThrows(IllegalArgumentException.class, () -> primMazeGenerator.generateMaze(invalidHeight, invalidWidth),
            "Ожидается исключение при попытке создать лабиринт с четными размерами.");
    }

    @Test
    void testMazeHasPassagesAndWalls() {
        // Тестируем, что лабиринт содержит и проходы, и стены
        int height = 7;
        int width = 7;
        Maze maze = primMazeGenerator.generateMaze(height, width);

        boolean hasPassage = false;
        boolean hasWall = false;

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.type() == Cell.Type.PASSAGE) {
                    hasPassage = true;
                } else if (cell.type() == Cell.Type.WALL) {
                    hasWall = true;
                }
            }
        }

        assertTrue(hasPassage, "Лабиринт должен содержать хотя бы один проход.");
        assertTrue(hasWall, "Лабиринт должен содержать хотя бы одну стену.");
    }

    @Test
    void testMazeRandomWeights() {
        // Тестируем, что в лабиринте случайные веса у проходов
        int height = 7;
        int width = 7;
        Maze maze = primMazeGenerator.generateMaze(height, width);

        int passageCount = 0;
        int distinctWeights = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = maze.getCell(row, col);
                if (cell.type() == Cell.Type.PASSAGE) {
                    passageCount++;
                    if (cell.weight() > 1) {
                        distinctWeights++;
                    }
                }
            }
        }

        assertTrue(passageCount > 0, "В лабиринте должны быть проходы.");
        assertTrue(distinctWeights > 0, "Проходы должны иметь различные веса.");
    }
}
