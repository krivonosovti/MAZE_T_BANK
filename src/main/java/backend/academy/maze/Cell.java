package backend.academy.maze;

public record Cell(int row, int col, Type type, int weight) {
    public enum Type { WALL, PASSAGE }
}

