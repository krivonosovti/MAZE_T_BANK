package backend.academy.maze.factory;

import backend.academy.maze.factory.perfect.KuskalGenerator.KruskalMazeGenerator;
import backend.academy.maze.factory.perfect.PrimGenerator.PrimMazeGenerator;
import backend.academy.maze.factory.unPerfect.PrimCicleMazeGenerator;

public final class MazeGeneratorFactory {
    private MazeGeneratorFactory() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static MazeGenerator createGenerator(AlgorithmType type) {
        return switch (type) {
            case KRUSKAL -> new KruskalMazeGenerator();
            case PRIM -> new PrimMazeGenerator();
            case CICLE_PRIM -> new PrimCicleMazeGenerator();
        };
    }

    public enum AlgorithmType {
        KRUSKAL,
        PRIM,
        CICLE_PRIM
    }
}
