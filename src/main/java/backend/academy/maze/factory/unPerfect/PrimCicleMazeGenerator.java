package backend.academy.maze.factory.unPerfect;

import backend.academy.maze.Cell;
import backend.academy.maze.Maze;
import backend.academy.maze.factory.Distribution;
import backend.academy.maze.factory.perfect.PrimGenerator.PrimMazeGenerator;
import java.security.SecureRandom;
import static backend.academy.maze.utils.MazeUtils.addRandomCycle;

/**
 * Генератор лабиринтов с использованием алгоритма Прима с добавлением случайных циклов.
 * Этот класс расширяет PrimMazeGenerator и добавляет функциональность для создания
 * случайных циклов в лабиринте.
 */
public class PrimCicleMazeGenerator extends PrimMazeGenerator {
    private final SecureRandom random = new SecureRandom();
    private final Distribution distribution = new Distribution();
    private static final int HUNDRED = 100;
    private static final int CYCLE_PROBABILITY = 10;

    /**
     * Генерирует лабиринт заданного размера с возможными случайными циклами.
     *
     * @param height высота лабиринта (должна быть нечетным числом)
     * @param width  ширина лабиринта (должна быть нечетным числом)
     * @return сгенерированный лабиринт с возможными циклами
     * @throws IllegalArgumentException если высота или ширина четные числа
     */
    @Override
    public Maze generateMaze(int height, int width) {
        Maze maze = super.generateMaze(height, width);
        Cell[][] grid = maze.getGrid();

        // Проходим по клеткам лабиринта и добавляем случайные циклы с вероятностью 10%
        for (int row = 1; row < height; row += 2) {
            for (int col = 1; col < width; col += 2) {
                if (random.nextInt(HUNDRED) < CYCLE_PROBABILITY) {
                    addRandomCycle(height, width, grid, random, distribution);
                }
            }
        }
        return maze;
    }
}
