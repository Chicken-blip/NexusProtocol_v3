public class HidingSpot extends Interactable {
    int concealmentLvl, capacity;

    public HidingSpot(String name, int id, int concLvl, int cap) {
        super(name, id);
        this.concealmentLvl = concLvl;
        this.capacity = cap;
        this.isTerminalInteractable = false;
    }
}
