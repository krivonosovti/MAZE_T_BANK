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

public class GameLogic {

    private static final Logger LOGGER = Logger.getLogger(GameLogic.class.getName());

    public static final int MONEY_RATING = 1;
    public static final int PATH_FINDER_AMOUNT = 2;
    public static final int MAZE_GENERSTOR_AMOUNT = 3;
    public static final int MIN_MAZE_SIZE = 3;
    public static final int MAX_MAZE_SIZE = 1000;

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

    public MazeGenerator selectMazeGenerator(Scanner scanner, PrintStream printStream) {
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

    public PathFinder selectPathFinder(Scanner scanner, PrintStream printStream) {
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

    public int inputDimension(Scanner scanner, PrintStream printStream, String dimensionName) {
        int dimension = -1;
        while (dimension % 2 == 0 || dimension < MIN_MAZE_SIZE || dimension > MAX_MAZE_SIZE) {
            printStream.printf("Введите %s (нечетное число больше 3 и меньше %d): ", dimensionName, MAX_MAZE_SIZE);
            dimension = readInt(scanner, printStream, MAX_MAZE_SIZE);
        }
        return dimension;
    }
    public Coordinate inputCoordinate(Scanner scanner, PrintStream printStream, Maze maze, String pointName) {
        printStream.printf("Введите координаты %s (формат: x '/n' y): ", pointName);
        int y = inputInt(scanner, printStream);
        int x = inputInt(scanner, printStream);
        while (x < 0 || x >= maze.getHeight() || y < 0 || y >= maze.getWidth()) {
            printStream.println("Ошибка: координаты должны быть внутри лабиринта.");
            printStream.printf("Введите координаты %s: ", pointName);
            y = inputInt(scanner, printStream);
            x = inputInt(scanner, printStream);
        }
        return new Coordinate(x, y);
    }
    private int inputInt(Scanner scanner, PrintStream printStream) {
        String a;
        int x;
        while (true) {
            a = scanner.nextLine();
            if (isNumeric(a)) {
                x = Integer.parseInt(a);
                return x;
            }
            printStream.println("Ошибка: Введите цифру.");
        }
    }

    private Boolean isNumeric(String x) {
        try {
            Integer.parseInt(x);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private int readInt(Scanner scanner, PrintStream printStream, int maxValue) {
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
