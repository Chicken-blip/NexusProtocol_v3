import java.util.Objects;

public class VentCover extends Interactable {
    int difficulty;
    int turnsSpent = 0;
    /*
     *  Possible States:
     * 	- CLOSED
     * 	- OPEN
     */

    public VentCover(String name, int id, int diff) {
        super(name, id);
        this.difficulty = diff;
        this.isTerminalInteractable = false;
        this.state = "CLOSED";
    }

    public String open() {
        turnsSpent++;
        if (Objects.equals(this.state, "OPEN")) {
            return "ERROR | Vent already opened";
        } else if (turnsSpent == difficulty) {
            this.state = "OPEN";
            return "ACCESS GRANTED | Vent opened";
        } else {
            return "HACKING | " + (difficulty - turnsSpent) + " MORE ATTEMPTS REQUIRED";
        }
    }
}
