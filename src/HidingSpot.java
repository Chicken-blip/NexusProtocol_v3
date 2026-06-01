public class HidingSpot extends Interactable {
    int concealmentLvl;

    public HidingSpot(String name, int id, int concLvl) {
        super(name, id);
        this.concealmentLvl = concLvl;
        this.isTerminalInteractable = false;
    }

    public String hide(Player p) {
        if (p.status == PlayerStatus.HIDING) {
            //Already hiding
            return "ERROR | Cass is already hiding.";
        } else {
            p.setStatus(PlayerStatus.HIDING);
            p.location = this;
            return "ACTION SUCCESS | Cass is now hiding behind " + this.name + ".";
        }
    }
}
