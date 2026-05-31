public class LightSwitch extends Interactable {
    boolean isOn;

    public LightSwitch(String name, int id) {
        super(name, id);
        this.isOn = false;
        this.isTerminalInteractable = true;
    }

    public String toggle() {
        this.isOn = !this.isOn;
        if (isOn) {
            return "SUCCESS | Light switch now on";
        } else {
            return "SUCCESS | Light switch now off";
        }
    }
}
