import java.util.ArrayList;
import java.util.List;

public abstract class Searchable extends Interactable {
    List<Item> contents = new ArrayList<Item>();
    public Searchable(String name, int id) {
        super(name, id);
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
}
