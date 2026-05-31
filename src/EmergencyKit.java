public class EmergencyKit extends Interactable {
    String[] contents;
    int usesRemaining;

    public EmergencyKit(String name, int id, int uses, String[] contents) {
        super(name, id);
        this.usesRemaining = uses;
        this.isTerminalInteractable = false;
        this.contents = contents;
    }

    public boolean canUse() {
        return usesRemaining > 0;
    }

    public String[][] use(Player p) {
        String[][] compilation = new String[contents.length][3];
        String[] item;
        for (int i = 0; i < contents.length; i++) {
            switch (contents[i]) {
                case "STIMULANT":
                    item = new String[3];
                    item[0] = "Stimulant Used:";
                    if (p.stress > 25) {
                        p.stress -= 25;
                        item[1] = "Reduced stress by 25";
                    } else {
                        p.stress = 0;
                        item[1] = "Reduced stress to 0";
                    }
                    if (p.heartRate > 150) {
                        p.heartRate = 180;
                        item[2] = "Increased heart rate to 180";
                    } else {
                        p.heartRate += 30;
                        item[2] = "Increased heart rate by 30";
                    }
                    compilation[i] = item;
                    break;
                case "SEDATIVE":
                    item = new String[3];
                    item[0] = "Sedative Used:";
                    if (p.stress > 85) {
                        p.stress = 100;
                        item[1] = "Increased stress to 100";
                    } else {
                        p.stress += 15;
                        item[1] = "Increased stress by 15";
                    }
                    if (p.heartRate > 100) {
                        p.heartRate -= 40;
                        item[2] = "Reduced heart rate by 40";
                    } else {
                        p.heartRate = 0;
                        item[2] = "Reduced heart rate to 60";
                    }
                    compilation[i] = item;
                    break;
                case "BANDAGE":
                    item = new String[3];
                    item[0] = "Bandage Used:";
                    if (p.stress > 10) {
                        p.stress -= 10;
                        item[1] = "Reduced stress by 10";
                    } else {
                        p.stress = 0;
                        item[1] = "Reduced stress to 10";
                    }
                    if (p.status == PlayerStatus.INJURED) {
                        item[2] = "Stopped bleeding";
                    } else {
                        item[2] = "";
                    }
                    compilation[i] = item;
                    break;
            }
        }
        return compilation;
    }
}
