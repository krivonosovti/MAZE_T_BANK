package backend.academy.maze.factory;

import backend.academy.maze.factory.perfect.KuskalGenerator.KruskalMazeGenerator;
import backend.academy.maze.factory.perfect.PrimGenerator.PrimMazeGenerator;
import backend.academy.maze.factory.unPerfect.PrimCicleMazeGenerator;

/**
 * Фабрика генераторов лабиринтов, предоставляющая методы для создания
 * экземпляров различных генераторов лабиринтов на основе выбранного алгоритма.
 * <p>
 * Данный класс является утилитным и не должен быть инстанциирован.
 * </p>
 */
public final class MazeGeneratorFactory {

    private MazeGeneratorFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    /**
     * Создает генератор лабиринта в зависимости от заданного типа алгоритма.
     *
     * @param type тип алгоритма генерации лабиринта
     * @return экземпляр генератора лабиринта, соответствующий заданному типу
     * @throws IllegalArgumentException если передан неизвестный тип алгоритма
     */
    public static MazeGenerator createGenerator(AlgorithmType type) {
        return switch (type) {
            case KRUSKAL -> new KruskalMazeGenerator();
            case PRIM -> new PrimMazeGenerator();
            case CICLE_PRIM -> new PrimCicleMazeGenerator();
        };
    }

    /**
     * Перечисление, представляющее доступные типы алгоритмов генерации лабиринтов.
     */
    public enum AlgorithmType {
        KRUSKAL,
        PRIM,
        CICLE_PRIM
    }
}
