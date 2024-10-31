package backend.academy.maze;

import backend.academy.GameLogic;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.PrintStream;
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
        // Simulate valid inputs "3\n" and "3\n"
        scanner = new Scanner("3\n3\n");
        Maze maze = mock(Maze.class);
        when(maze.getHeight()).thenReturn(7);
        when(maze.getWidth()).thenReturn(7);

        Coordinate coordinate = gameLogic.inputCoordinate(scanner, printStream, maze, "Start (A)");
        assertEquals(new Coordinate(3, 3), coordinate, "Координаты должны быть (3, 3).");
    }

    @Test
    void testInputCoordinateInvalid() {
        // Simulate invalid inputs "8\n" and "8\n" followed by valid inputs "3\n" and "3\n"
        scanner = new Scanner("8\n8\n3\n3\n");
        Maze maze = mock(Maze.class);
        when(maze.getHeight()).thenReturn(7);
        when(maze.getWidth()).thenReturn(7);

        Coordinate coordinate = gameLogic.inputCoordinate(scanner, printStream, maze, "Start (A)");
        assertEquals(new Coordinate(3, 3), coordinate, "Координаты должны быть (3, 3) после повторного ввода.");
        verify(printStream, atLeastOnce()).println("Ошибка: координаты должны быть внутри лабиринта.");
    }

    @Test
    void testInputIntValidInput() {
        when(scanner.nextLine()).thenReturn("5");
        int result = gameLogic.inputInt(scanner, printStream);
        assertEquals(5, result, "Должно возвращать введенное значение 5.");
    }

    @Test
    void testInputIntInvalidInput() {
        // Simulate an invalid input "abc" followed by a valid input "7"
        when(scanner.nextLine()).thenReturn("abc", "7");
        int result = gameLogic.inputInt(scanner, printStream);
        assertEquals(7, result, "Должно возвращать 7 после повторного ввода.");
        verify(printStream, times(1)).println("Введите цифру.");
    }

    @Test
    void testIsNumericTrue() {
        assertTrue(gameLogic.isNumeric("123"), "Должно возвращать true для строки '123'.");
    }

    @Test
    void testIsNumericFalse() {
        assertFalse(gameLogic.isNumeric("abc"), "Должно возвращать false для строки 'abc'.");
        assertFalse(gameLogic.isNumeric("12.3"), "Должно возвращать false для строки '12.3'.");
        assertFalse(gameLogic.isNumeric(""), "Должно возвращать false для пустой строки.");
    }
}
