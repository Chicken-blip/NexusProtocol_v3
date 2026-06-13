/* Terminal Types:
1 - Data Terminal:
Containers of encrypted Documents. Must be decrypted, then downloaded, to have them added to the Document View
2 - Security Terminal:
Control for security cameras. Only allows for resetting - cannot disable them remotely.
3 - Medical Terminal:
Library of patient records. Look at records for specific patients, given their ID. Can also access their diagnosed medication, apparently.
4 - Prisoner Management Terminal:
Check cells within a specific block. Unlock doors and check the status of cells (or lockdown all cells entirely)
5 - Maintenance Terminal:
Control facility utilities and view full facility stats. Toggle power, water, and more for a specific room.
 */

public class Terminal extends Interactable {
    public Terminal(String name, int id) {
        super(name, id);
    }
}
