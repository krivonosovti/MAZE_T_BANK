package backend.academy.maze.display;

import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import java.util.List;
import static backend.academy.GameLogic.MONEY_RATING;

/**
 * Класс UnicodeMazeDisplay реализует интерфейс MazeDisplay и предоставляет
 * методы для визуализации лабиринта с использованием символов Unicode.
 * Он определяет, как отображать различные типы ячеек лабиринта в виде
 * символов, а также как визуализировать путь через лабиринт.
 */
public class UnicodeMazeDisplay implements MazeDisplay {
    private static final int MAZE_ZOOM = 3; // Увеличение размера отображаемого лабиринта
    private static final char WALL_CHAR = '█'; // Символ для стен
    private static final char PASSAGE_CHAR = ' '; // Символ для проходов
    private static final char WAY_CHAR = '.'; // Символ для пути
    private static final char FOREST_CHAR = '#'; // Символ для леса
    private static final char DESERT_CHAR = 'X'; // Символ для пустыни
    private static final char SWAMP_CHAR = '~'; // Символ для болота
    private static final char MONEY_CHAR = '$'; // Символ для денег

    /**
     * Выбирает символ для отображения в зависимости от веса ячейки.
     *
     * @param cell ячейка лабиринта, для которой необходимо выбрать символ.
     * @return символ, представляющий ячейку.
     * @throws IllegalArgumentException если вес ячейки не соответствует ни одному из ожидаемых значений.
     */
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

    /**
     * Отображает лабиринт в виде массива символов.
     *
     * @param maze объект лабиринта, который необходимо отобразить.
     * @return массив символов, представляющий визуализацию лабиринта.
     */
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

    /**
     * Отображает лабиринт с учетом заданного пути.
     *
     * @param mazePicture массив символов, представляющий текущее состояние лабиринта.
     * @param width ширина лабиринта.
     * @param path список координат, представляющих путь через лабиринт.
     * @return обновленный массив символов с отмеченным путем.
     */
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

    /**
     * Изменяет символ в заданной ячейке лабиринта на указанный символ.
     *
     * @param charMaze массив символов, представляющий лабиринт.
     * @param width ширина лабиринта.
     * @param coord координаты ячейки, которую необходимо изменить.
     * @param centerChar символ, на который нужно изменить ячейку.
     */
    private void changeCell(char[] charMaze, int width, Coordinate coord, char centerChar) {
        int row = coord.row();
        int col = coord.col();
        int index = row * (width * MAZE_ZOOM + 1) + (col * MAZE_ZOOM) + 1;
        charMaze[index] = centerChar;
    }

    /**
     * Отображает матрицу связности лабиринта в виде строки.
     *
     * @param maze объект лабиринта, для которого необходимо создать матрицу связности.
     * @return строковое представление матрицы связности.
     */
    @SuppressWarnings("checkstyle:MultipleStringLiterals")
    @Override
    public String displayMatrix(Maze maze) {
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
