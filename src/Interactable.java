public abstract class Interactable {
    String name;
    int id;
    String desc;
    String state;
    boolean isTerminalInteractable;

    public Interactable(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    private void useFailed() {

    }
}
