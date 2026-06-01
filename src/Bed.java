public class Bed extends Interactable {
    int restEfficiency, turnCost;
    boolean inUse;
    int turnCount = 0;

    public Bed(String name, int id, int restEff, int tnCost) {
        super(name, id);
        this.restEfficiency = restEff;
        this.turnCost = tnCost;
        this.isTerminalInteractable = false;
    }

    public String rest(Player p) {
        //TODO: Build function
        if (!inUse) {
            this.inUse = true;
            this.turnCount = this.turnCost;
            p.setStatus(PlayerStatus.RESTING);
            p.bed = this;
            return "ACTIOM SUCCESS | Cass is now sleeping";
        } else {
            return "ERROR | Cass is already sleeping";
        }
    }
}
