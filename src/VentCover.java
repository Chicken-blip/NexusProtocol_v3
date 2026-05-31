public class VentCover extends Interactable {
    int difficulty;

    public VentCover(String name, int id, int diff) {
        super(name, id);
        this.difficulty = diff;
        this.isTerminalInteractable = false;
    }
}
