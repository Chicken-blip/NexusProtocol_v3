import java.util.ArrayList;
import java.util.List;

public class Player {
    Room currentRoom;
    PlayerView currentView;
    int stress;
    int heartRate;
    PlayerStatus status;
    List<Item> inventory;
    public int injuries;
    int exhaustedCnter;
    HidingSpot location = null;
    Bed bed = null;
    Tool equipped = null;

    public Player() {
        status = PlayerStatus.PANICKING;
        stress = 80;
        this.heartRate = setHeartRate(150, 180);
        this.inventory = new ArrayList<>();
    }

    private int setHeartRate(int min, int max) {
        return (int)(Math.random() * (max - min + 1)) + min;
    }

    public String stressDesc() {
        if (stress <= 20) {
            return "CALM";
        } else if (stress <= 40) {
            return "LOW";
        } else if (stress <= 60) {
            return "MODERATE";
        } else if (stress <= 80) {
            return "HIGH";
        } else {
            return "CRITICAL";
        }
    }

    public void setStatus(PlayerStatus sts) {
        this.status = sts;
    }

    public void setStatus(int val) {
        switch (val) {
        default -> {
            if (this.exhaustedCnter >= 3) {
                this.status = PlayerStatus.EXHAUSTED;
            } else if (this.stress >= 60) {
                this.status = PlayerStatus.PANICKING;
            } else {
                this.status = PlayerStatus.CALM;
            }
            }
        }
    }

    public void setStatus() {
        if (status == PlayerStatus.RESTING) {
            if (bed.turnCount > bed.turnCost) {
                //Completed sleeping
                this.bed.turnCount = 0;
                this.bed.inUse = false;
                this.bed = null;
            }
        }
        if (!(status == PlayerStatus.HIDING || this.bed != null)) {
            this.location = null;
            if (this.exhaustedCnter >= 3) {
                this.status = PlayerStatus.EXHAUSTED;
            } else if (this.stress >= 60) {
                this.status = PlayerStatus.PANICKING;
            } else {
                this.status = PlayerStatus.CALM;
            }
        }
    }

    public String getStatus() {
        return switch (status) {
            case CALM -> "CALM";
            case PANICKING -> "PANICKING";
            case EXHAUSTED -> "EXHAUSTED";
            case HIDING -> "HIDING";
            case RESTING -> "RESTING";
            case INJURED -> "INJURED";
        };
    }

    public boolean canAct() {
        return switch (status) {
            case PANICKING, RESTING, EXHAUSTED -> false;
            default -> true;
        };
    }

    public boolean isSleeping() {
        return this.status == PlayerStatus.RESTING;
    }

    public boolean isHiding() {
        return this.status == PlayerStatus.HIDING;
    }

    public String actFail() {
        return switch (status) {
            case PANICKING -> "ACTION UNAVAILABLE | Cass's stress is too high";
            case EXHAUSTED -> "ACTION UNAVAILABLE | Cass's heart rate has been too high";
            case RESTING -> "ACTION UNAVAILABLE | Cass is currently resting";
            default -> "ACTION UNAVAILABLE | Check Cass's vitals";
        };
    }

    public void addToInv(Item i) {
        this.inventory.add(i);
    }
    public Item getFromInv(int id) {
        for (Item i : inventory) {
            if (i.id == id) {
                return i;
            }
        }
        return null;
    }

    public String equip(Item i) {
        if (this.equipped == null) {
            this.equipped = (Tool) i;
            return "ACTION SUCCESS | " + i.name + " equipped";
        } else {
            return "ERROR | Cass already has " + this.equipped.name + " equipped";
        }
    }
    public String unequip() {
        Item i = this.equipped;
        this.equipped = null;
        return "ACTION SUCCESS | " + i.name + " unequipped and returned to inventory";
    }
}
