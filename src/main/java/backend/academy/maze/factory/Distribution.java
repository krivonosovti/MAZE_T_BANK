package backend.academy.maze.factory;

import java.util.Random;
import static backend.academy.GameLogic.MONEY_RATING;

public class Distribution {
    private static final int FIRST = 100;
    private static final int SECOND = 80;
    private static final int THIRD = 90;
    private static final int PASSAGE_RATING = 3;
    private static final int FORE = 4;
    private final Random random = new Random();

    public int generateWeight() {
        int rand = random.nextInt(FIRST);

        if (rand < SECOND) {
            return PASSAGE_RATING;
        } else if (rand < THIRD) {
            return MONEY_RATING;
        } else {
            return random.nextInt(2) + FORE;
        }
    }
}

