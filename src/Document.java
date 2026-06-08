public class Document extends Item {
    String contents;
    public Document(String name, int id) {
        super(name, id);
    }
    public void setContents(String txt) {
        contents = txt;
    }

}
