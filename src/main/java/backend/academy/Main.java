package backend.academy;

import java.util.Scanner;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        GameLogic gameLogic = new GameLogic();
        gameLogic.game(System.out, scanner); // Передаем PrintStream для вывода
    }
}
