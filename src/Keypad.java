public class Keypad extends Interactable {
    int correctCode;
    boolean isSolved;

    public Keypad(String name, int id) {
        super(name, id);
        this.correctCode = generateCode(4);
        this.isSolved = false;
        this.isTerminalInteractable = true;
    }


    public int generateCode(int len) {
        int code = 0;
        int min = (int) Math.pow(10, len - 1);
        int max = (int) Math.pow(10, len) - 1;
        code = (int) (Math.random() * (max - min + 1));
        return min + code;
    }

    public void setCode(int len) {
        this.correctCode = generateCode(len);
    }


    public String bypass(int code_num) {
        if (this.isSolved) {
            return "ERROR | Keypad already bypassed";
        } else if (code_num == this.correctCode) {
            this.isSolved = true;
            return "ACCESS GRANTED | Keypad bypassed";
        } else {
            return "ACCESS DENIED | Incorrect code entered";
        }
    }
}
