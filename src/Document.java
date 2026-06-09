public class Document extends Item {
    String contents;
    boolean read;
    public Document(String name, int id) {
        super(name, id);
        read = false;
    }
    public void setContents(String txt) {
        contents = txt;
    }

}
