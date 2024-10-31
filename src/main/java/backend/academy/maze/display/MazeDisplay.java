package backend.academy.maze.display;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;

/**
 * Интерфейс MazeDisplay предоставляет методы для отображения лабиринта.
 * Он определяет, как лабиринт может быть визуализирован в разных форматах.
 *
 * Методы интерфейса:
 *
 * - display(Maze maze):
 *   Возвращает массив символов, представляющий визуализацию заданного лабиринта.
 *
 *
 * - displayMatrix(Maze maze):
 *   Возвращает строковое представление матрицы лабиринта.
 */
public interface MazeDisplay {
    char[] display(Maze maze);

    char[] display(char[] mazePicture, int width, List<Coordinate> path);

    String displayMatrix(Maze maze);
}
