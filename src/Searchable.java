import java.util.ArrayList;
import java.util.List;

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
}
