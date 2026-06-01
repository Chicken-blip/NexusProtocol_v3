public abstract class Item {
    String name;
    int id;
    String desc;
    public Item(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setDesc(String d) {
        this.desc = d;
    }

    public String getDesc() {
        return this.desc;
    }
}
