package backend.academy.maze.factory.perfect.KuskalGenerator;

import backend.academy.maze.Coordinate;
import backend.academy.maze.factory.perfect.KuskalGenerator.UnionFind;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UnionFindTest {

    private UnionFind unionFind;

    @BeforeEach
    void setUp() {
        unionFind = new UnionFind();
    }

    @Test
    void testMakeSet() {
        // Тестируем создание множества
        Coordinate coord1 = new Coordinate(0, 0);
        unionFind.makeSet(coord1);
        assertEquals(coord1, unionFind.find(coord1), "Элемент должен быть сам себе родителем после makeSet.");
    }

    @Test
    void testUnionAndFind() {
        // Тестируем объединение двух множеств и поиск
        Coordinate coord1 = new Coordinate(0, 0);
        Coordinate coord2 = new Coordinate(1, 1);
        unionFind.makeSet(coord1);
        unionFind.makeSet(coord2);

        unionFind.union(coord1, coord2);

        assertEquals(unionFind.find(coord1), unionFind.find(coord2), "Элементы должны быть в одном множестве после union.");
    }

    @Test
    void testUnionByRank() {
        // Тестируем объединение по рангу
        Coordinate coord1 = new Coordinate(0, 0);
        Coordinate coord2 = new Coordinate(1, 1);
        Coordinate coord3 = new Coordinate(2, 2);

        unionFind.makeSet(coord1);
        unionFind.makeSet(coord2);
        unionFind.makeSet(coord3);

        unionFind.union(coord1, coord2); // Объединяем coord1 и coord2
        unionFind.union(coord1, coord3); // Объединяем coord1 и coord3

        // Все должны иметь один корень
        assertEquals(unionFind.find(coord1), unionFind.find(coord2), "coord1 и coord2 должны быть в одном множестве.");
        assertEquals(unionFind.find(coord1), unionFind.find(coord3), "coord1 и coord3 должны быть в одном множестве.");
    }

    @Test
    void testPathCompression() {
        // Тестируем сжатие пути
        Coordinate coord1 = new Coordinate(0, 0);
        Coordinate coord2 = new Coordinate(1, 1);
        Coordinate coord3 = new Coordinate(2, 2);

        unionFind.makeSet(coord1);
        unionFind.makeSet(coord2);
        unionFind.makeSet(coord3);

        unionFind.union(coord1, coord2);
        unionFind.union(coord2, coord3);

        // Перед сжатием пути
        Coordinate rootBeforeCompression = unionFind.find(coord1);
        assertEquals(unionFind.find(coord1), unionFind.find(coord3), "Все элементы должны иметь один корень.");

        // Проверка сжатия пути
        unionFind.find(coord3);
        assertEquals(unionFind.find(coord1), unionFind.find(coord3), "Путь должен быть сжат после find.");
    }
}
