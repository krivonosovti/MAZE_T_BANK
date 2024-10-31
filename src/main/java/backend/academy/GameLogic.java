package backend.academy;

import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.Path;
import backend.academy.maze.display.MazeDisplay;
import backend.academy.maze.display.UnicodeMazeDisplay;
import backend.academy.maze.factory.MazeGenerator;
import backend.academy.maze.factory.MazeGeneratorFactory;
import backend.academy.maze.pathFinder.PathFinder;
import backend.academy.maze.pathFinder.PathFinderSelector;
import java.io.PrintStream;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Класс GameLogic реализует логику игры, связанную с генерацией лабиринта и поиском пути.
 * Он предоставляет методы для выбора алгоритма генерации лабиринта и поиска пути,
 * а также для получения пользовательского ввода и отображения результатов.
 */
public class GameLogic {

    private static final Logger LOGGER = Logger.getLogger(GameLogic.class.getName());

    /** Константа для оценки стоимости */
    public static final int MONEY_RATING = 1;
    /** Константа для количества доступных алгоритмов поиска пути */
    public static final int PATH_FINDER_AMOUNT = 2;
    /** Константа для количества доступных генераторов лабиринтов */
    public static final int MAZE_GENERSTOR_AMOUNT = 3;
    /** Минимальный размер лабиринта */
    public static final int MIN_MAZE_SIZE = 3;
    /** Максимальный размер лабиринта */
    public static final int MAX_MAZE_SIZE = 1000;

    /**
     * Основной метод игры, который управляет процессом генерации лабиринта,
     * выбором алгоритма поиска пути и выводом результатов.
     *
     * @param printStream поток вывода для отображения информации
     * @param scanner объект для чтения пользовательского ввода
     */
    public void game(PrintStream printStream, Scanner scanner) {
        MazeDisplay display = new UnicodeMazeDisplay();

        try {
            MazeGenerator mazeGenerator = selectMazeGenerator(scanner, printStream);
            int height = inputDimension(scanner, printStream, "height");
            int width = inputDimension(scanner, printStream, "width");
            Maze maze = mazeGenerator.generateMaze(height, width);
            printStream.println("Сгенерированный лабиринт:");
            char[] mazePicture = display.display(maze);
            printStream.println(mazePicture);
            PathFinder pathFinder = selectPathFinder(scanner, printStream);
            printStream.print("Хотите вывести матрицу смежности? (y/n): ");
            String showMatrix = scanner.next().toLowerCase();
            if (showMatrix.equals("y") || showMatrix.equals("да")) {
                printStream.println("Матрица смежности:");
                printStream.println(display.displayMatrix(maze));
            }
            Coordinate start = inputCoordinate(scanner, printStream, maze, "Start (A)");
            printStream.println("Start (A) = (" + start.col() + ", " + start.row() + ")");
            Coordinate end = inputCoordinate(scanner, printStream, maze, "Finish (B)");
            printStream.println("Finish (B) = (" + end.col() + ", " + end.row() + ")");
            Path path = pathFinder.findPath(maze, start, end);
            if (!path.getPath().isEmpty()) {
                printStream.println("Путь найден:");
                printStream.println(display.display(mazePicture, width, path.getPath()));
                printStream.println("Итоговая стоимость пути: " + path.getCost());
            } else {
                printStream.println("Путь не найден.");
            }
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Ошибка при выполнении игры", e);
            printStream.println("Произошла ошибка: " + e.getMessage());
        }
    }

    /**
     * Метод для выбора алгоритма генерации лабиринта.
     *
     * @param scanner объект для чтения пользовательского ввода
     * @param printStream поток вывода для отображения информации
     * @return выбранный генератор лабиринта
     */
    protected MazeGenerator selectMazeGenerator(Scanner scanner, PrintStream printStream) {
        printStream.println("Выберите алгоритм генерации лабиринта: ");
        printStream.println("1. Kruskal");
        printStream.println("2. Prim");
        printStream.println("3. Cicle Prim");

        int choice = readInt(scanner, printStream, MAZE_GENERSTOR_AMOUNT);

        switch (choice) {
            case 1:
                return MazeGeneratorFactory.createGenerator(MazeGeneratorFactory.AlgorithmType.KRUSKAL);
            case 2:
                return MazeGeneratorFactory.createGenerator(MazeGeneratorFactory.AlgorithmType.PRIM);
            case MAZE_GENERSTOR_AMOUNT:
                return MazeGeneratorFactory.createGenerator(MazeGeneratorFactory.AlgorithmType.CICLE_PRIM);
            default:
                throw new IllegalArgumentException("Некорректный выбор алгоритма.");
        }
    }

    /**
     * Метод для выбора алгоритма поиска пути.
     *
     * @param scanner объект для чтения пользовательского ввода
     * @param printStream поток вывода для отображения информации
     * @return выбранный алгоритм поиска пути
     */
    protected PathFinder selectPathFinder(Scanner scanner, PrintStream printStream) {
        printStream.println("Выберите алгоритм поиска пути: ");
        printStream.println("1. BFS");
        printStream.println("2. A*");

        int choice = readInt(scanner, printStream, PATH_FINDER_AMOUNT);

        switch (choice) {
            case 1:
                return PathFinderSelector.finderSelector(PathFinderSelector.AlgorithmType.BFS);
            case PATH_FINDER_AMOUNT:
                return PathFinderSelector.finderSelector(PathFinderSelector.AlgorithmType.ASTAR);
            default:
                throw new IllegalArgumentException("Некорректный выбор алгоритма поиска пути.");
        }
    }

    /**
     * Метод для ввода размерности лабиринта от пользователя.
     *
     * @param scanner объект для чтения пользовательского ввода
     * @param printStream поток вывода для отображения информации
     * @param dimensionName название размерности (высота или ширина)
     * @return введенная размерность
     */
    protected int inputDimension(Scanner scanner, PrintStream printStream, String dimensionName) {
        int dimension = -1;
        while (dimension % 2 == 0 || dimension < MIN_MAZE_SIZE || dimension > MAX_MAZE_SIZE) {
            printStream.printf("Введите %s (нечетное число больше 3 и меньше %d): ", dimensionName, MAX_MAZE_SIZE);
            dimension = readInt(scanner, printStream, MAX_MAZE_SIZE);
        }
        return dimension;
    }

    /**
     * Метод для ввода координат точки в лабиринте.
     *
     * @param scanner объект для чтения пользовательского ввода
     * @param printStream поток вывода для отображения информации
     * @param maze лабиринт, в котором будут проверяться координаты
     * @param pointName название точки (например, "Start" или "Finish")
     * @return объект Coordinate с введенными координатами
     */
    public Coordinate inputCoordinate(Scanner scanner, PrintStream printStream, Maze maze, String pointName) {
        printStream.println("Введите точку " + pointName + " (формат: x '/n' y): ");
        int y = inputInt(scanner, printStream);
        int x = inputInt(scanner, printStream);
        while (x < 0 || x >= maze.getHeight() || y < 0 || y >= maze.getWidth()) {
            printStream.println("Ошибка: координаты должны быть внутри лабиринта.");
            printStream.println("Введите координаты " + pointName + ": ");
            y = inputInt(scanner, printStream);
            x = inputInt(scanner, printStream);
        }
        return new Coordinate(x, y);
    }

    /**
     * Метод для ввода целого числа от пользователя.
     *
     * @param scanner объект для чтения пользовательского ввода
     * @param printStream поток вывода для отображения информации
     * @return введенное целое число
     */
    public int inputInt(Scanner scanner, PrintStream printStream) {
        String a;
        int x;
        while (true) {
            a = scanner.nextLine();
            if (!isNumeric(a)) {
                printStream.println("Введите цифру.");
            } else {
                x = Integer.parseInt(a);
                return x;
            }
        }
    }

    /**
     * Метод для проверки, является ли строка числом.
     *
     * @param x строка для проверки
     * @return true, если строка является числом, иначе false
     */
    public Boolean isNumeric(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Метод для чтения целого числа с проверкой на допустимые значения.
     *
     * @param scanner объект для чтения пользовательского ввода
     * @param printStream поток вывода для отображения информации
     * @param maxValue максимальное допустимое значение
     * @return введенное целое число
     */
    protected int readInt(Scanner scanner, PrintStream printStream, int maxValue) {
        int value = -1;
        while (true) {
            try {
                value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0 && value <= maxValue) {
                    return value;
                } else {
                    printStream.println("Ошибка: Введите число в пределах допустимых значений.");
                }
            } catch (NumberFormatException e) {
                printStream.println("Ошибка: некорректный ввод. Введите число.");
            }
        }
    }
}
