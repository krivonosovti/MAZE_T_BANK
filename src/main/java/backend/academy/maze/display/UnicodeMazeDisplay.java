package backend.academy.maze.display;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;
import static backend.academy.GameLogic.MONEY_RATING;

public class UnicodeMazeDisplay implements MazeDisplay {
    private static final int MAZE_ZOOM = 3;
    private static final char WALL_CHAR = '█';
    private static final char PASSAGE_CHAR = ' ';
    private static final char WAY_CHAR = '.';
    private static final char FOREST_CHAR = '#';
    private static final char DESERT_CHAR = 'X';
    private static final char SWAMP_CHAR = '~';
    private static final char MONEY_CHAR = '$';

    public char selectSimbol(Cell cell) {
        char displayChar;
        //CHECKSTYLE:OFF
        switch (cell.weight()) {
            case 0:
                displayChar = WALL_CHAR;
                break;
            case 3:
                displayChar = PASSAGE_CHAR;
                break;
            case 4:
                displayChar = FOREST_CHAR;
                break;
            case 5:
                displayChar = DESERT_CHAR;
                break;
            case 6:
                displayChar = SWAMP_CHAR;
                break;
            case MONEY_RATING:
                displayChar = MONEY_CHAR;
                break;
            default:
                throw new IllegalArgumentException("Неверный вес ячейки: " + cell.weight());
        }
        //CHECKSTYLE:ON
        return displayChar;
    }

    @Override
    public char[] display(Maze maze) {
        int height = maze.getHeight();
        int width = maze.getWidth();
        char[] charMaze = new char[height * (width * MAZE_ZOOM + 1)];
        int index = 0;
        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                Cell cell = maze.getCell(row, col);
                char symbol = selectSimbol(cell);

                // Заполняем массив тремя символами для каждой ячейки
                charMaze[index++] = symbol;
                charMaze[index++] = symbol;
                charMaze[index++] = symbol;
            }
            // После завершения строки добавляем символ новой строки
            charMaze[index++] = '\n';
        }

        return charMaze;
    }

    @Override
    public char[] display(char[] mazePicture, int width, List<Coordinate> path) {
        char[] charMaze = mazePicture;
        int index = 0;

        // Изменяем стартовую точку
        changeCell(charMaze, width, path.getFirst(), 'A');

        // Изменяем точки пути
        for (int i = 1; i < path.size() - 1; i++) {
            changeCell(charMaze, width, path.get(i), WAY_CHAR);
        }

        // Изменяем конечную точку
        changeCell(charMaze, width, path.getLast(), 'B');

        return charMaze;
    }

    private void changeCell(char[] charMaze, int width, Coordinate coord, char centerChar) {
        int row = coord.row();
        int col = coord.col();
        int index = row * (width * MAZE_ZOOM + 1) + (col * MAZE_ZOOM) + 1;
        charMaze[index] = centerChar;
    }

    @SuppressWarnings("checkstyle:MultipleStringLiterals") @Override
    public  String displayMatrix(Maze maze) {
        Cell[][] grid = maze.getGrid();
        int height = maze.getHeight();
        int width = maze.getWidth();
        StringBuilder sb = new StringBuilder();

        // Создаем матрицу связности
        int[][] adjacencyMatrix = new int[height][width];

        for (int row = 0; row < height; row++) {
            for (int col = 0; col < width; col++) {
                if (grid[row][col].type() == Cell.Type.PASSAGE) {
                    adjacencyMatrix[row][col] = grid[row][col].weight();
                } else {
                    adjacencyMatrix[row][col] = 0;
                }
            }
        }

        sb.append("    ");
        for (int col = 0; col < width; col++) {
            sb.append(String.format("%3d ", col));
        }
        sb.append("\n");

        // Выводим матрицу с подписями строк
        for (int row = 0; row < height; row++) {
            sb.append(String.format("%2d| ", row));
            for (int col = 0; col < width; col++) {
                sb.append(String.format("%3d ", adjacencyMatrix[row][col]));
            }
            sb.append("\n"); // Переход на новую строку
        }

        return sb.toString();
    }
}
