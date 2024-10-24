package backend.academy.maze.factory.perfect.PrimGenerator;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Edge;
import backend.academy.maze.Maze;
import backend.academy.maze.factory.Distribution;
import backend.academy.maze.factory.MazeGenerator;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;


public class PrimMazeGenerator implements MazeGenerator {
    private final Distribution distribution = new Distribution(); // Добавляем генератор среды

    private final SecureRandom random = new SecureRandom();

    public Cell[][] initializeGrid(int height, int width) {
        Cell[][] grid = new Cell[height][width];

        // Инициализация всех клеток как стен
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                grid[row][col] = new Cell(row, col, Cell.Type.WALL, 0);
            }
        }

        return grid;
    }


    @Override
    public Maze generateMaze(int height, int width) {
        if (height % 2 == 0 || width % 2 == 0) {
            throw new IllegalArgumentException("Height and width must be odd numbers to create a proper maze.");
        }

        Cell[][] grid = initializeGrid(height, width);

        int startRow = random.nextInt(height / 2) * 2 + 1;
        int startCol = random.nextInt(width / 2) * 2 + 1;
        grid[startRow][startCol] = new Cell(startRow, startCol, Cell.Type.PASSAGE,  distribution.generateWeight());

        List<Edge> frontiers = new ArrayList<>();

        addFrontierCells(startRow, startCol, height, width, frontiers);

        // Алгоритм Прима
        while (!frontiers.isEmpty()) {
            Edge edge = frontiers.remove(random.nextInt(frontiers.size()));

            Coordinate from = edge.getFrom();
            Coordinate to = edge.getTo();

            if (grid[to.row()][to.col()].type() == Cell.Type.WALL) {
                int wallRow = (from.row() + to.row()) / 2;
                int wallCol = (from.col() + to.col()) / 2;
                grid[wallRow][wallCol] = new Cell(wallRow, wallCol,
                    Cell.Type.PASSAGE, distribution.generateWeight());

                grid[to.row()][to.col()] = new Cell(to.row(), to.col(),
                    Cell.Type.PASSAGE, distribution.generateWeight());

                addFrontierCells(to.row(), to.col(), height, width, frontiers);
            }
        }

        return new Maze(height, width, grid);
    }

    private void addFrontierCells(int row, int col, int height, int width, List<Edge> frontiers) {
        if (col - 2 >= 0) {
            frontiers.add(new Edge(new Coordinate(row, col),
                new Coordinate(row, col - 2), distribution.generateWeight()));
        }
        if (col + 2 < height) {
            frontiers.add(new Edge(new Coordinate(row, col),
                new Coordinate(row, col + 2), distribution.generateWeight()));
        }
        if (row - 2 >= 0) {
            frontiers.add(new Edge(new Coordinate(row, col),
                new Coordinate(row - 2, col),  distribution.generateWeight()));
        }
        if (row + 2 < width) {
            frontiers.add(new Edge(new Coordinate(row, col),
                new Coordinate(row + 2, col), distribution.generateWeight()));
        }
    }
}
