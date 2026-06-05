import java.security.Key;
import java.util.Objects;

public class Exit {
    String name;
    String desc;
    Direction direction;
    Interactable dependent;
    Room targetRoom;
    ExitRequirement requirement;

    public Exit(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
    public String getDesc() {
        return this.desc;
    }

    public void setDependent(Interactable i) {
        this.dependent = i;
    }

    public void setDirection(Direction direct) {
        this.direction = direct;
    }
    public Direction getDirection() {
        return this.direction;
    }

    public void setTarget(Room target) {
        this.targetRoom = target;
    }
    public Room getTarget() {
        return this.targetRoom;
    }

    public void setRequirement(ExitRequirement req) {
        this.requirement = req;
    }
    public boolean canUseExit(Player p) {
        switch (this.requirement) {
            case UNLOCKED:
                if (dependent instanceof LockedDoor door) {
                    return Objects.equals(door.state, "UNLOCKED");
                } else if (dependent instanceof Keypad pad) {
                    return pad.isSolved;
                }
                break;
            case HAS_KEYCARD:
                break;
            case VENT_OPEN:
                if (dependent instanceof VentCover vent) {
                    return Objects.equals(vent.state, "OPEN");
                }
                break;
            case POWER_ON:
                break;
            case ALWAYS_OPEN:
                return true;
        }
        return false;
    }
    public String useFailure(Player player) {
        if (!player.currentRoom.canActInRoom(player)) {
            return player.currentRoom.actionFail(player);
        }
        switch (this.requirement) {
            case UNLOCKED:
                if (dependent instanceof LockedDoor) {
                    return "ACCESS DENIED | Door must be unlocked";
                } else if (dependent instanceof Keypad) {
                    return "ACCESS DENIED | Keypad must be bypassed";
                }
            case VENT_OPEN:
                return "ACCESS DENIED | Vent is not open";
            default:
                return "ACCESS DENIED | Requirements unfulfilled";
        }
    }
}
