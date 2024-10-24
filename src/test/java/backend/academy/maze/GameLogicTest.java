package backend.academy.maze;


import backend.academy.GameLogic;
import backend.academy.maze.factory.MazeGenerator;
import backend.academy.maze.factory.MazeGeneratorFactory;
import backend.academy.maze.pathFinder.PathFinder;
import backend.academy.maze.pathFinder.PathFinderSelector;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
import java.io.ByteArrayOutputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
class GameLogicTest {
    private GameLogic gameLogic;
    private PrintStream printStream;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        gameLogic = new GameLogic();
        printStream = mock(PrintStream.class);
        scanner = mock(Scanner.class);
    }

    @Test
    void testInputCoordinateValid() {
        scanner = new Scanner("3 3\n");
        // Создаем мока лабиринта с размерами 7x7
        Maze maze = mock(Maze.class);
        when(maze.getHeight()).thenReturn(7);
        when(maze.getWidth()).thenReturn(7);

        Coordinate coordinate = gameLogic.inputCoordinate(scanner, printStream, maze, "Start (A)");
        assertEquals(new Coordinate(3, 3), coordinate, "Координаты должны быть (3, 3).");
    }

    @Test
    void testInputCoordinateInvalid() {
        scanner = new Scanner("8 8\n3 3");
        // Создаем мока лабиринта с размерами 7x7
        Maze maze = mock(Maze.class);
        when(maze.getHeight()).thenReturn(7);
        when(maze.getWidth()).thenReturn(7);

        Coordinate coordinate = gameLogic.inputCoordinate(scanner, printStream, maze, "Start (A)");
        assertNotNull(coordinate, "Координаты должны быть (3, 3).");
    }
    @Test
    void testInputDimension_ValidInput() {
        when(scanner.nextLine()).thenReturn("5");
        int dimension = gameLogic.inputDimension(scanner, printStream, "height");
        assertEquals(5, dimension);
    }


    @Test
    void testInputCoordinate_ValidInput() {
        Maze maze = mock(Maze.class);
        when(maze.getHeight()).thenReturn(5);
        when(maze.getWidth()).thenReturn(5);
        when(scanner.nextInt()).thenReturn(2).thenReturn(2);

        Coordinate coordinate = gameLogic.inputCoordinate(scanner, printStream, maze, "Start (A)");
        assertEquals(new Coordinate(2, 2), coordinate);
    }

    @Test
    void testInputCoordinate_InvalidInput() {
        Maze maze = mock(Maze.class);
        when(maze.getHeight()).thenReturn(5);
        when(maze.getWidth()).thenReturn(5);
        when(scanner.nextInt()).thenReturn(6).thenReturn(2).thenReturn(2);

        Coordinate coordinate = gameLogic.inputCoordinate(scanner, printStream, maze, "Start (A)");
        assertEquals(new Coordinate(2, 2), coordinate);
        verify(printStream).println("Ошибка: координаты должны быть внутри лабиринта.");
    }
}
