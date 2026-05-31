public class Window extends Interactable {
    int visibility, risk;

    public Window(String name, int id, int visib, int risk) {
        super(name, id);
        this.visibility = visib;
        this.risk = risk;
        this.isTerminalInteractable = false;
    }
}
