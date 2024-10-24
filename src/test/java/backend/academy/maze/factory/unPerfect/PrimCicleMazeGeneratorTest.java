package backend.academy.maze.factory.unPerfect;

import backend.academy.maze.Maze;
import backend.academy.maze.Cell;
import backend.academy.maze.factory.unPerfect.PrimCicleMazeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PrimCicleMazeGeneratorTest {

    private PrimCicleMazeGenerator primCicleMazeGenerator;

    @BeforeEach
    void setUp() {
        primCicleMazeGenerator = new PrimCicleMazeGenerator();
    }

    @Test
    void testGenerateMazeWithCycles() {
        // Генерируем лабиринт и проверяем наличие циклов
        int height = 7;
        int width = 7;
        Maze maze = primCicleMazeGenerator.generateMaze(height, width);

        boolean hasCycle = false;

        // Проверяем наличие циклов в лабиринте
        for (int row = 1; row < height - 1; row += 2) {
            for (int col = 1; col < width - 1; col += 2) {
                Cell current = maze.getCell(row, col);

                // Если соседние клетки являются проходами, это указывает на цикл
                if (current.type() == Cell.Type.PASSAGE) {
                    if (maze.getCell(row, col - 1).type() == Cell.Type.PASSAGE ||
                        maze.getCell(row, col + 1).type() == Cell.Type.PASSAGE ||
                        maze.getCell(row - 1, col).type() == Cell.Type.PASSAGE ||
                        maze.getCell(row + 1, col).type() == Cell.Type.PASSAGE) {
                        hasCycle = true;
                        break;
                    }
                }
            }
            if (hasCycle) break;
        }

        assertTrue(hasCycle, "Лабиринт должен содержать хотя бы один цикл.");
    }

    @Test
    void testGenerateMazeValidDimensions() {
        // Тестируем генерацию лабиринта с корректными размерами
        int height = 5;
        int width = 5;
        Maze maze = primCicleMazeGenerator.generateMaze(height, width);

        assertEquals(height, maze.getHeight(), "Высота лабиринта должна совпадать с заданной.");
        assertEquals(width, maze.getWidth(), "Ширина лабиринта должна совпадать с заданной.");
    }

    @Test
    void testGenerateMazeInvalidDimensions() {
        // Тестируем генерацию лабиринта с некорректными размерами
        int invalidHeight = 4;
        int invalidWidth = 6;

        assertThrows(IllegalArgumentException.class, () -> primCicleMazeGenerator.generateMaze(invalidHeight, invalidWidth),
            "Ожидается исключение при попытке создать лабиринт с четными размерами.");
    }
}
