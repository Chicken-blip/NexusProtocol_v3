public class Keycard extends Item {

    /* State Options:
    0 - General/All-Access
    Other - Number corresponds with specific door
     */
    String permissions;
    public Keycard(String name, int id, String perm) {
        super(name, id);
        permissions = perm;
    }
}
