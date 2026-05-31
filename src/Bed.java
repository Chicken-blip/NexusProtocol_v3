public class Bed extends Interactable {
    int restEfficiency, turnCost;
    boolean inUse;

    public Bed(String name, int id, int restEff, int tnCost) {
        super(name, id);
        this.restEfficiency = restEff;
        this.turnCost = tnCost;
        this.isTerminalInteractable = false;
    }
}
