import java.util.HashMap;
import java.util.Map;

public class Guard {
    Room rm;
    boolean alerted = false;
    Map<Integer, Room> pattern;
    int turn;
    public Guard() {
        rm = null;
        pattern = new HashMap<>();
        turn = 0;
    }
}
