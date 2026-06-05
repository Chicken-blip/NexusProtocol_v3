public class Cabinet extends Searchable {
    boolean open = false;
    public Cabinet(String n, int id) {
        super(n, id);
    }
    public String open() {
        if (!open) {
            this.open = true;
            return "SUCCESS | Cabinet now open";
        } else {
            return "ERROR | Cabinet already open";
        }
    }
    public String close() {
        if (open) {
            this.open = false;
            return "SUCCESS | Cabinet now closed";
        } else {
            return "ERROR | Cabinet already closed";
        }
    }
}
