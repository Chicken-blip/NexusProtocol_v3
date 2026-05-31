import java.util.Base64;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public Game game;

    public static void main(String[] args) {
        Game home = new Game(99);
        home.runGame();
        home.gameLoop();
    }
}