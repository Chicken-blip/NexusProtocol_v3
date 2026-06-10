import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Searchable extends Interactable {
    /* State Options:
    1 - OPEN
    2 - CLOSED
     */
    List<Item> contents = new ArrayList<Item>();
    public Searchable(String name, int id) {
        super(name, id);
        state = "CLOSED";
    }

    public void addItem(Item i) {
        contents.add(i);
    }

    public Item getItem(int id) {
        for (Item i : contents) {
            if (id == i.id) {
                return i;
            }
        }
        return null;
    }

    public List<Item> getContents() {
        return contents;
    }
    public String open() {
        if (Objects.equals(this.state, "OPEN")) {
            return "ERROR | Container is already open";
        } else {
            this.state = "OPEN";
            return "ACTION SUCCESS | The container is now open";
        }
    }
    public String close() {
        if (Objects.equals(this.state, "CLOSED")) {
            return "ERROR | Container is already closed";
        } else {
            this.state = "CLOSED";
            return "ACTION SUCCESS | The container is now closed";
        }
    }
}
