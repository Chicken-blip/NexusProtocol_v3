public class LightSwitch extends Interactable {
    boolean isOn;
    /* State Options:
    1 - ON
    2 - OFF
     */

    public LightSwitch(String name, int id) {
        super(name, id);
        this.isOn = false;
        this.isTerminalInteractable = true;
        this.state = "OFF";
    }

    public String toggle() {
        this.isOn = !this.isOn;
        if (isOn) {
            this.state = "ON";
            return "SUCCESS | Light switch now on";
        } else {
            this.state = "OFF";
            return "SUCCESS | Light switch now off";
        }
    }
}
