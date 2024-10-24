package backend.academy.maze.factory.perfect.KuskalGenerator;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Edge;
import backend.academy.maze.Maze;
import backend.academy.maze.factory.Distribution;
import backend.academy.maze.factory.MazeGenerator;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KruskalMazeGenerator implements MazeGenerator {
    private SecureRandom secureRandom = new SecureRandom();

    private final Distribution distribution = new Distribution(); // Добавляем генератор среды
    SecureRandom random = new SecureRandom();

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

        UnionFind unionFind = new UnionFind();
        List<Edge> edges = new ArrayList<>();

        // Добавляем все клетки лабиринта, которые будут проходами
        for (int row = 1; row < height; row += 2) {
            for (int col = 1; col < width; col += 2) {
                Coordinate coord = new Coordinate(row, col);
                unionFind.makeSet(coord);
                grid[row][col] = new Cell(row, col, Cell.Type.PASSAGE, distribution.generateWeight());

                // Добавляем ребра между соседними клетками
                if (row + 2 < height) {
                    edges.add(new Edge(coord, new Coordinate(row + 2, col),
                        distribution.generateWeight())); // Вертикальные ребра
                }
                if (col + 2 < width) {
                    edges.add(new Edge(coord, new Coordinate(row, col + 2),
                        distribution.generateWeight())); // Горизонтальные ребра
                }
            }
        }

        Collections.shuffle(edges, secureRandom);

        for (Edge edge : edges) {
            Coordinate from = edge.getFrom();
            Coordinate to = edge.getTo();

            if (unionFind.find(from).equals(unionFind.find(to))) {
                continue; // Пропускаем, если уже в одном множестве
            }

            // Удаляем стену между клетками
            int wallRow = (from.row() + to.row()) / 2;
            int wallCol = (from.col() + to.col()) / 2;
            grid[wallRow][wallCol] = new Cell(wallRow, wallCol, Cell.Type.PASSAGE, distribution.generateWeight());
            // Объединяем множества
            unionFind.union(from, to);
        }

        return new Maze(height, width, grid);
    }

}

