import java.util.Objects;

public class Exit {
    String name;
    String desc;
    Direction direction;
    Interactable dependent;
    Room targetRoom;
    ExitRequirement requirement;
    String card_perm;

    public Exit(String name) {
        this.name = name;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setDependent(Interactable i) {
        this.dependent = i;
    }

    public void setDirection(Direction direct) {
        this.direction = direct;
    }

    public void setTarget(Room target) {
        this.targetRoom = target;
    }
    public void setRequirement(ExitRequirement req) {
        this.requirement = req;
    }
    public void setCard_perm(String p) {
        this.card_perm = p;
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
                for (Item i : p.inventory) {
                    if (i instanceof Keycard) {
                        if (Objects.equals(((Keycard) i).permissions, this.card_perm) || Objects.equals(((Keycard) i).permissions, "Master")) {
                            return true;
                        }
                    }
                }
                return false;
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
            case HAS_KEYCARD:
                return "ACCESS DENIED | Required keycard not held";
            default:
                return "ACCESS DENIED | Requirements unfulfilled";
        }
    }
}
