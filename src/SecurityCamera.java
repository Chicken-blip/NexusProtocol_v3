import java.util.Objects;

public class SecurityCamera extends Interactable {
    boolean isActive;
    int difficulty;
    int turnsSpent = 0;
    /*Possible States:
        - INACTIVE
        - ACTIVE
        - ALERTED
    */
    public SecurityCamera(String name, int id) {
        super(name, id);
        this.isActive = true;
        this.state = "ACTIVE";
        this.isTerminalInteractable = true;
    }

    public String enable() {
        if (this.isActive) {
            return "ERROR | Camera already enabled";
        } else {
            this.isActive = true;
            this.state = "ACTIVE";
            turnsSpent = 0;
            return "ACCESS GRANTED | Camera enabled";
        }
    }
    public String disable() {
        if (!this.isActive) {
            return "ERROR | Camera already disabled";
        } else if (Objects.equals(this.state, "ALERTED")) {
            return "REQUEST DENIED | Must resolve security alert first";
        } else {
            this.isActive = false;
            this.state = "INACTIVE";
            return "ACCESS GRANTED | Camera disabled";
        }
    }
    public String reset() {
        turnsSpent ++;
        if (!Objects.equals(this.state, "ALERTED")) {
            turnsSpent --;
            return "ERROR | No security alert to resolve";
        } else if (this.turnsSpent < this.difficulty) {
            return "HACKING | " + (difficulty - turnsSpent) + " MORE ATTEMPTS REQUIRED";
        } else {
            this.state = "ACTIVE";
            return "ACCESS GRANTED | Security alert resolved";
        }
    }
}
