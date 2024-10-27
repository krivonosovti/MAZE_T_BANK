package backend.academy.maze.pathFinder.BFSMazeFinder;

//import backend.academy.maze.Cell;
//import backend.academy.maze.Coordinate;
//import backend.academy.maze.Maze;
//import backend.academy.maze.Path;
//import backend.academy.maze.pathFinder.Node;
//import backend.academy.maze.pathFinder.PathFinder;
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Queue;
//
//public class BFSMazePathFinder implements PathFinder {
//    @Override
//    public Path findPath(Maze maze, Coordinate start, Coordinate end) {
//        int height = maze.getHeight();
//        int width = maze.getWidth();
//
//        // Инициализация массива посещенных клеток
//        boolean[][] visited = initializeVisitedArray(height, width);
//
//        // Очередь для BFS
//        Queue<Node> queue = new LinkedList<>();
//        queue.add(new Node(start, null, 0, 0));
//        visited[start.row()][start.col()] = true;
//
//        // Основной цикл BFS
//        while (!queue.isEmpty()) {
//            Node currentNode = queue.poll();
//            Coordinate currentCoord = currentNode.getCoordinate();
//
//            // Если достигли конца, восстанавливаем путь
//            if (currentCoord.equals(end)) {
//                return new Path(reconstructPath(currentNode), currentNode.getGCost());
//            }
//
//            // Обработка соседних клеток
//            processNeighbors(maze, currentNode, visited, queue);
//        }
//
//        // Если путь не найден
//        return new Path(Collections.emptyList(), 0);
//    }
//
//    // Инициализация массива посещенных клеток
//    private boolean[][] initializeVisitedArray(int height, int width) {
//        return new boolean[height][width];  // Все ячейки изначально false (не посещены)
//    }
//
//    // Обработка соседних клеток
//    private void processNeighbors(Maze maze, Node currentNode, boolean[][] visited, Queue<Node> queue) {
//        Coordinate currentCoord = currentNode.getCoordinate();
//
//        for (Coordinate neighbor : getNeighbors(maze, currentCoord)) {
//            int row = neighbor.row();
//            int col = neighbor.col();
//
//            if (!visited[row][col] && maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
//                visited[row][col] = true;
//
//                // Добавляем соседей в очередь
//                int gCost = currentNode.getGCost() + maze.getCell(row, col).weight();
//                queue.add(new Node(neighbor, currentNode, gCost, 0)); // fCost не используется в BFS
//            }
//        }
//    }
//
//    // Получение списка соседей для клетки
//    private List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
//        List<Coordinate> neighbors = new ArrayList<>();
//        int row = coord.row();
//        int col = coord.col();
//
//        // Проверяем каждое из направлений (верх, низ, влево, вправо)
//        if (row - 1 >= 0) {
//            neighbors.add(new Coordinate(row - 1, col));
//        }
//        if (row + 1 < maze.getHeight()) {
//            neighbors.add(new Coordinate(row + 1, col));
//        }
//        if (col - 1 >= 0) {
//            neighbors.add(new Coordinate(row, col - 1));
//        }
//        if (col + 1 < maze.getWidth()) {
//            neighbors.add(new Coordinate(row, col + 1));
//        }
//
//        return neighbors;
//    }
//
//    // Восстановление пути из узлов
//    private List<Coordinate> reconstructPath(Node endNode) {
//        List<Coordinate> path = new ArrayList<>();
//        Node current = endNode;
//
//        while (current != null) {
//            path.add(current.getCoordinate());
//            current = current.getParent();
//        }
//
//        Collections.reverse(path);  // Путь восстанавливается с конца, поэтому разворачиваем его
//        return path;
//    }
//}
import backend.academy.maze.Cell;
import backend.academy.maze.Coordinate;
import backend.academy.maze.Maze;
import backend.academy.maze.Path;
import backend.academy.maze.pathFinder.Node;
import backend.academy.maze.pathFinder.PathFinder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BFSMazePathFinder implements PathFinder {
    @Override
    public Path findPath(Maze maze, Coordinate start, Coordinate end) {
        int height = maze.getHeight();
        int width = maze.getWidth();

        // Инициализация массива посещенных клеток
        boolean[][] visited = new boolean[height][width];

        // Очередь для BFS
        Queue<Node> queue = new LinkedList<>();
        queue.add(new Node(start, null, 0, 0));
        visited[start.row()][start.col()] = true;

        // Основной цикл BFS
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();
            Coordinate currentCoord = currentNode.getCoordinate();

            // Если достигли конца, восстанавливаем путь
            if (currentCoord.equals(end)) {
                return new Path(reconstructPath(currentNode), currentNode.getGCost());
            }

            // Обработка соседних клеток
            processNeighbors(maze, currentNode, visited, queue);
        }

        // Если путь не найден
        return new Path(Collections.emptyList(), 0);
    }

    // Обработка соседних клеток
    private void processNeighbors(Maze maze, Node currentNode, boolean[][] visited, Queue<Node> queue) {
        Coordinate currentCoord = currentNode.getCoordinate();

        for (Coordinate neighbor : getNeighbors(maze, currentCoord)) {
            int row = neighbor.row();
            int col = neighbor.col();

            if (!visited[row][col] && maze.getCell(row, col).type() == Cell.Type.PASSAGE) {
                visited[row][col] = true;

                // Добавляем соседа в очередь, увеличивая gCost на 1
                queue.add(new Node(neighbor, currentNode, currentNode.getGCost() + 1, 0)); // Увеличиваем gCost на 1 для каждой клетки
            }
        }
    }

    // Получение списка соседей для клетки
    private List<Coordinate> getNeighbors(Maze maze, Coordinate coord) {
        List<Coordinate> neighbors = new ArrayList<>();
        int row = coord.row();
        int col = coord.col();

        // Проверяем каждое из направлений (верх, низ, влево, вправо)
        if (row - 1 >= 0) {
            neighbors.add(new Coordinate(row - 1, col));
        }
        if (row + 1 < maze.getHeight()) {
            neighbors.add(new Coordinate(row + 1, col));
        }
        if (col - 1 >= 0) {
            neighbors.add(new Coordinate(row, col - 1));
        }
        if (col + 1 < maze.getWidth()) {
            neighbors.add(new Coordinate(row, col + 1));
        }

        return neighbors;
    }

    // Восстановление пути из узлов
    private List<Coordinate> reconstructPath(Node endNode) {
        List<Coordinate> path = new ArrayList<>();
        Node current = endNode;

        while (current != null) {
            path.add(current.getCoordinate());
            current = current.getParent();
        }

        Collections.reverse(path); // Путь восстанавливается с конца, поэтому разворачиваем его
        return path;
    }
}
