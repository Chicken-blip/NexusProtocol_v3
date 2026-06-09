
import java.util.ArrayList;
import java.util.List;

public class Room {
    List<Interactable> roomObjects;
    List<Exit> roomExits;
    List<Item> roomItems;
    String name;
    String desc;
    Interactable dependent;
    ActionRequirement act_req;
    boolean visited = false;

    public Room(String name) {
        this.roomObjects = new ArrayList<>();
        this.roomExits = new ArrayList<>();
        this.name = name;
    }

    public void addExit(Exit e) {
        roomExits.add(e);
    }
    public void addInteractable(Interactable i) {
        roomObjects.add(i);
    }
    public void addItem(Item i) {
        roomItems.add(i);
    }
    public void setDependent(Interactable i) {
        this.dependent = i;
    }
    public void setActionRequirement(ActionRequirement act) {
        this.act_req = act;
    }
    public boolean canActInRoom(Player p) {
        switch (this.act_req) {
            case LIGHTS_ON:
                if (dependent instanceof LightSwitch sw) {
                    return sw.isOn;
                }
            case ALWAYS_ACTIONABLE:
                return true;
        }
        return false;
    }
    public String actionFail(Player player) {
        // FIXME: 5/30/2026 : Add more cases in the future, if needed
        switch (this.act_req) {
            case LIGHTS_ON:
                return "ACTION UNAVAILABLE | Room lights need to be turned on";
            default:
                return "ACTION UNAVAILABLE | Requirements unfulfilled.";
        }
    }
}
