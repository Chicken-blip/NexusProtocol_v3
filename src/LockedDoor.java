import java.util.Objects;

public class LockedDoor extends Interactable {
    int difficulty;
    int turnsSpent = 0;
    /*
     * Possible States:
     * 	- LOCKED
     * 	- UNLOCKED
     */

    public LockedDoor(String name, int id, int diff) {
        super(name, id);
        this.difficulty = diff;
        this.isTerminalInteractable = true;
        this.state = "LOCKED";
    }

    public String unlock() {
        turnsSpent ++;
        if (Objects.equals(this.state, "UNLOCKED")) {
            return "ERROR | Door already unlocked";
        } else if (turnsSpent == difficulty) {
            this.state = "UNLOCKED";
            return "ACCESS GRANTED | Door unlocked";
        } else {
            return "HACKING | " + (difficulty - turnsSpent) + " MORE ATTEMPTS REQUIRED";
        }
    }
}
