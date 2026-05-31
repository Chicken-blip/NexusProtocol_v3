public class WaterDispenser extends Interactable{
    int usesRemaining;

    public WaterDispenser(String name, int id, int uses) {
        super(name, id);
        this.usesRemaining = uses;
        this.isTerminalInteractable = false;
    }

    public boolean canUse() {
        return this.usesRemaining > 0;
    }

    public String[] use(Player p) {
        this.usesRemaining --;
        String[] results = new String[2];
        int val_str = (int) (Math.random() * 10) + 15;
        if (p.stress - val_str < 0) {
            p.stress = 0;
            results[0] = "Reduced stress to 0";
        } else {
            p.stress -= val_str;
            results[0] = "Reduced stress by " + val_str;
        }
        int val_bpm = (int) (Math.random() * 5) + 10;
        if (p.heartRate - val_bpm < 60) {
            p.heartRate = 60;
            results[1] = "Reduced heart rate to 60";
        } else {
            p.heartRate -= val_bpm;
            results[1] = "Reduced heart rate by " + val_bpm;
        }
        return results;
    }
}
