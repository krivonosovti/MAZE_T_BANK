package backend.academy.maze.factory;

import backend.academy.maze.Maze;

/**
 * Интерфейс для генераторов лабиринтов.
 * <p>
 * Определяет метод для генерации лабиринта заданного размера.
 * </p>
 */
public interface MazeGenerator {

    /**
     * Генерирует лабиринт с заданной высотой и шириной.
     *
     * @param height высота лабиринта (должна быть нечетным числом)
     * @param width ширина лабиринта (должна быть нечетным числом)
     * @return сгенерированный лабиринт
     * @throws IllegalArgumentException если высота или ширина являются четными числами
     */
    Maze generateMaze(int height, int width);
}
