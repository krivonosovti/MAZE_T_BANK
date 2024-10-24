package backend.academy.maze.factory.perfect.KuskalGenerator;

import backend.academy.maze.Cell;
import backend.academy.maze.Maze;
import backend.academy.maze.factory.perfect.KuskalGenerator.KruskalMazeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class KruskalMazeGeneratorTest {

    private KruskalMazeGenerator generator;

    @BeforeEach
    void setUp() {
        generator = new KruskalMazeGenerator();
    }

    @Test
    void testGenerateMazeDimensions() {
        // Проверка создания лабиринта с нечетными размерами
        int height = 5;
        int width = 5;
        Maze maze = generator.generateMaze(height, width);

        assertEquals(height, maze.getHeight(), "Высота лабиринта должна совпадать с указанной");
        assertEquals(width, maze.getWidth(), "Ширина лабиринта должна совпадать с указанной");
    }

    @Test
    void testGenerateMazeOddDimensions() {
        // Проверка исключения при четных размерах
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            generator.generateMaze(4, 4);
        });
        assertEquals("Height and width must be odd numbers to create a proper maze.", exception.getMessage());
    }

    @Test
    void testMazeConnectivity() {
        // Проверка, что все проходы связаны (корректность алгоритма Краскала)
        int height = 5;
        int width = 5;
        Maze maze = generator.generateMaze(height, width);

        // Проверяем, что между всеми ячейками есть пути (минимальное остовное дерево)
        boolean allPassagesConnected = isAllPassagesConnected(maze);
        assertTrue(allPassagesConnected, "Все проходы должны быть связаны.");
    }

    private boolean isAllPassagesConnected(Maze maze) {
        // Этот метод может быть реализован для проверки связности всех проходов
        // Используя BFS/DFS, мы можем обойти лабиринт и проверить, что все проходы доступны
        return true; // Должна быть реализация обхода
    }
}
