import java.nio.charset.StandardCharsets;
import java.util.*;

public class Game {
    Scanner scanner;
    int turn;
    int maxTurns;
    ConsoleColor txt_color;
    Player player;
    Facility facility;
    boolean gameRunning;
    ArrayList<Integer> IDs = new ArrayList<>();
    Map<Integer, String> logs = new LinkedHashMap<>();

    public Game(int maxTurns) {
        this.txt_color = ConsoleColor.BLACK;
        this.IDs.add(0);
        this.turn = 0;
        this.maxTurns = maxTurns;
        this.facility = new Facility(this, 0);
        this.player = new Player();
        gameRunning = false;
    }

    public void println(String text) {
        String addition = "\u001B[0m";
        switch (this.txt_color) {
            case BLACK:
                addition = "\u001B[30m";
                break;
            case GREEN:
                addition = "\u001B[32m";
                break;
            case BLUE:
                addition = "\u001B[34m";
                break;
            case CYAN:
                addition = "\u001B[36m";
                break;
            default:
                break;
        }
        System.out.println(addition + text);
    }
    public void print(String text) {
        String addition = "\u001B[0m";
        switch (this.txt_color) {
            case BLACK:
                addition = "\u001B[30m";
                break;
            case GREEN:
                addition = "\u001B[32m";
                break;
            case BLUE:
                addition = "\u001B[34m";
                break;
            case CYAN:
                addition = "\u001B[36m";
                break;
            default:
                break;
        }
        System.out.print(addition + text);
    }

    public int generateID() {
        int code = 0;
        while (this.IDs.contains(code)) {
            code = (int) (Math.random() * 10000);
        }
        this.IDs.add(code);
        return code;
    }


    public void runGame() {
        scanner = new Scanner(System.in);
        this.player.currentView = PlayerView.DEFAULT_VIEW;
        this.player.currentRoom = this.facility.rooms.getFirst();
        gameRunning = true;
    }

    public Interactable find_object(int id) {
        for (Room r : facility.rooms) {
            for (Interactable obj : r.roomObjects) {
                if (obj.id == id) {
                    return obj;
                }
            }
        }
        return null;
    }

    public String encode(String val) {
        byte[] bytes = val.getBytes(StandardCharsets.UTF_8);
        return Base64.getEncoder().withoutPadding().encodeToString(bytes);
    }

    public String decode(String val) {
        byte[] data = Base64.getDecoder().decode(val);
        return new String(data, StandardCharsets.UTF_8);
    }

    public void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public void TerminalInterface() {
        //TODO: To be completed
        println("> NEXUS PROTOCOL v1.07");
        println("> CONNECTION ESTABLISHED");
        println(">");
        println("> Enter a command:");
        //This is where all the commands are
        while (player.currentView == PlayerView.TERMINAL_VIEW) {
            print("> ");
            String input = scanner.nextLine();
            if (input.contains(" ")) {
                String id;
                //Argument-command input
                switch (input.substring(0, input.indexOf(" ")).toLowerCase()) {
                    case "inspect":
                        id = input.substring(input.indexOf(" "));
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            if (!this.IDs.contains(id_num)) {
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            println("> NAME: " + obj.name);
                            println("> DESC: " + obj.desc);
                            println("> STATE: " + obj.state);
                            if (obj instanceof Keypad o) {
                                println("> ENCRYPTION: [" + encode(String.valueOf(o.correctCode)) + "]");
                            }
                            if (obj instanceof EmergencyKit kit) {
                                println("> USES: " + kit.usesRemaining);
                            }
                            if (obj instanceof WaterDispenser disp) {
                                println("> USES: " + disp.usesRemaining);
                            }
                            if (obj instanceof Bed bed) {
                                println("> TURNS FOR USE: " + bed.turnCost);
                            }
                            if (obj instanceof Window wnd) {
                                println("> VISIBILITY: " + wnd.visibility);
                                println("> RISK: " + wnd.risk);
                            }
                            println(">");
                        } catch (Exception e) {
                            println("> INVALID OBJECT ID | You must put in a valid object ID");
                        }
                        useTurn("DEFAULT");
                        break;
                    case "unlock":
                        id = input.substring(input.indexOf(" "));
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            if (!this.IDs.contains(id_num)) {
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            if (obj instanceof LockedDoor door) {
                                println("> " + door.unlock());
                            } else {
                                println("> UNAPPLICABLE OBJECT | This command must reference a door");
                                println("> ");
                            }
                        } catch (Exception e) {
                            println("> INVALID OBJECT ID | You must put in a valid object ID");
                        }
                        useTurn("DEFAULT");
                        break;
                    case "disable":
                        id = input.substring(input.indexOf(" "));
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            if (!this.IDs.contains(id_num)) {
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            if (obj instanceof SecurityCamera camera) {
                                println("> " + camera.disable());
                            } else {
                                println("> UNAPPLICABLE OBJECT | This command must reference a security camera");
                                println("> ");
                            }
                        } catch (Exception e) {
                            println("> INVALID OBJECT ID | You must put in a valid object ID");
                        }
                        useTurn("DEFAULT");
                        break;
                    case "enable":
                        id = input.substring(input.indexOf(" "));
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            if (!this.IDs.contains(id_num)) {
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            if (obj instanceof SecurityCamera camera) {
                                println("> " + camera.enable());
                            } else {
                                println("> UNAPPLICABLE OBJECT | This command must reference a security camera");
                                println("> ");
                            }
                        } catch (Exception e) {
                            println("> INVALID OBJECT ID | You must put in a valid object ID");
                        }
                        useTurn("DEFAULT");
                        break;
                    case "toggle":
                        id = input.substring(input.indexOf(" "));
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            if (!this.IDs.contains(id_num)) {
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            if (obj instanceof LightSwitch swtch) {
                                println("> " + swtch.toggle());
                            } else {
                                println("> UNAPPLICABLE OBJECT | This command must reference a light switch");
                                println("> ");
                            }
                        } catch (Exception e) {
                            println("> INVALID OBJECT ID | You must put in a valid object ID");
                        }
                        useTurn("DEFAULT");
                        break;
                    case "bypass":
                        String code;
                        Boolean success_A = false, success_B = false;
                        String remainder = input.substring(input.indexOf(" ")).strip();
                        if (!remainder.contains(" ")) {
                            println("> INVALID COMMAND SYNTAX | Requires a code to bypass");
                            break;
                        } else {
                            id = remainder.substring(0, remainder.indexOf(" "));
                            code = remainder.substring(remainder.indexOf(" "));
                        }
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            success_A = true;
                            int code_num = Integer.parseInt(code.strip());
                            success_B = true;
                            if (!this.IDs.contains(id_num)) {
                                success_A = false;
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            if (obj instanceof Keypad pad) {
                                println("> " + pad.bypass(code_num));
                            } else {
                                println("> UNAPPLICABLE OBJECT | This command must reference a keypad");
                                println("> ");
                            }
                        } catch (Exception e) {
                            try {
                                int id_num = Integer.parseInt(code);
                                success_B = true;
                            } catch (Exception ef) {
                            }

                        } finally {
                            if (!success_A) {
                                println("> INVALID OBJECT ID | You must put in a valid object ID");
                            } else if (!success_B) {
                                println("> INVALID CODE | The code must be an integer");
                            }
                        }
                        useTurn("DEFAULT");
                        break;
                    case "decrypt":
                        code = input.substring(input.indexOf(" "), input.length()).strip();
                        println("> " + decode(code));
                        break;
                    case "reset":
                        id = input.substring(input.indexOf(" "));
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            if (!this.IDs.contains(id_num)) {
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            if (obj instanceof SecurityCamera camera) {
                                println("> " + camera.reset());
                                println("> ");
                            } else {
                                println("> UNAPPLICABLE OBJECT | This command must reference a security camera");
                                println("> ");
                            }
                        } catch (Exception e) {
                            println("> INVALID OBJECT ID | You must put in a valid object ID");
                        }
                        useTurn("DEFAULT");
                        break;
                    case "cass-go":
                        String[] directions = {"north", "south", "east", "west"};
                        String dir = input.substring(input.indexOf(" ")).strip().toLowerCase();
                        if (Arrays.asList(directions).contains(dir)) {
                            Direction d = switch (dir) {
                                case "north" -> Direction.NORTH;
                                case "south" -> Direction.SOUTH;
                                case "east" -> Direction.EAST;
                                case "west" -> Direction.WEST;
                                default -> null;
                            };
                            boolean exit_found = false;
                            Room original = player.currentRoom;
                            //Stop hiding
                            if (player.status == PlayerStatus.HIDING) {
                                player.setStatus(PlayerStatus.CALM);
                                player.setStatus();
                                player.location = null;
                                println("> NOTE: Cass is no longer hiding.");
                            }
                            for (Exit e : player.currentRoom.roomExits) {
                                if (e.direction == d && e.canUseExit(player) && player.currentRoom.canActInRoom(player) && player.canAct()) {
                                    player.currentRoom = e.targetRoom;
                                    exit_found = true;
                                } else if (e.direction == d) {
                                    if (!e.canUseExit(player)) {
                                        println("> " + e.useFailure(this.player));
                                    } else if (!player.currentRoom.canActInRoom(player)) {
                                        println("> " + player.currentRoom.actionFail(player));
                                    } else if (!player.canAct()) {
                                        println("> " + player.actFail());
                                    }
                                    exit_found = true;
                                }
                            }
                            if (!exit_found) {
                                println("> ERROR | No available exit in that direction");
                                useTurn("INVALID_ROOM_CHANGE");
                            } else if (player.currentRoom != original) {
                                this.player.setStatus(0);
                                println("> ACCESS GRANTED | Changing rooms...");
                                useTurn("CHANGE_OF_ROOMS");
                            } else {
                                useTurn("INACCESSIBLE_EXIT");
                            }
                        } else {
                            println("> INVALID DIRECTION | The direction must be one of the following: north, south, east, west");
                            useTurn("DEFAULT");
                        }
                        break;
                    case "cass-use":
                        if (!this.player.currentRoom.canActInRoom(player)) {
                            println("> " + this.player.currentRoom.actionFail(player));
                        } else if (this.player.isSleeping()) {
                            println("> " + this.player.actFail());
                        } else {
                            //Stop hiding
                            if (player.status == PlayerStatus.HIDING) {
                                player.setStatus(PlayerStatus.CALM);
                                player.setStatus();
                                player.location = null;
                                println("> NOTE: Cass is no longer hiding.");
                            }
                            id = input.substring(input.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!this.IDs.contains(id_num)) {
                                    id_num /= 0;
                                }
                                Interactable obj = find_object(id_num);
                                boolean recovered;
                                switch (obj) {
                                    case EmergencyKit kit -> {
                                        recovered = kit.canUse();
                                        if (recovered) {
                                            println("> ACTION SUCCESS | Used " + obj.name);
                                            String[][] list = kit.use(player);
                                            for (String[] i : list) {
                                                for (String x : i) {
                                                    println("> " + x);
                                                }
                                            }
                                        } else {
                                            println("> EMERGENCY KIT UNUSABLE | This emergency kit has no more uses remaining.");
                                        }
                                    }
                                    case Bed bed -> {
                                        println("> " + bed.rest(this.player));
                                    }
                                    case WaterDispenser water -> {
                                        recovered = water.canUse();
                                        if (recovered) {
                                            println("> ACTION SUCCESS | Used " + obj.name);
                                            String[] list = water.use(player);
                                            for (String i : list) {
                                                println("> " + i);
                                            }
                                        } else {
                                            println("> WATER DISPENSER UNUSABLE | This water dispenser has no more uses remaining.");
                                        }
                                    }
                                    case Window wnd -> wnd.peek();
                                    case null, default -> {
                                        println("> UNAPPLICABLE OBJECT | This command must reference an object Cass can use");
                                        println("> ");
                                    }
                                }
                            } catch (Exception e) {
                                println("> INVALID OBJECT ID | You must put in a valid object ID");
                            }
                        }
                        useTurn("DEFAULT");
                        break;
                    case "cass-hide":
                        id = input.substring(input.indexOf(" "));
                        try {
                            int id_num = Integer.parseInt(id.strip());
                            if (!this.IDs.contains(id_num)) {
                                id_num /= 0;
                            }
                            Interactable obj = find_object(id_num);
                            if (obj instanceof HidingSpot hs) {
                                println("> " + hs.hide(this.player));
                            } else {
                                println("> UNAPPLICABLE OBJECT | This command must reference a hiding spot");
                                println("> ");
                            }
                        } catch (Exception e) {
                            println("> INVALID OBJECT ID | You must put in a valid object ID");
                        }
                        useTurn("DEFAULT");
                        break;
                    default:
                        println("> INVALID COMMAND | Type in 'help' to view a list of commands.");
                        useTurn("DEFAULT");
                }
            } else {
                //No-argument command input
                switch(input.toLowerCase()) {
                    case "scan":
                        println("> Room: " + player.currentRoom.name);
                        println("> ");

                        println("> OBJECTS: ");
                        for (Interactable x : player.currentRoom.roomObjects) {
                            println("> ID: " + x.id);
                        }

                        println(">");
                        println("> EXITS: ");
                        for (Exit x : player.currentRoom.roomExits) {
                            println("> NAME: " + x.name);
                            println("> DIRECTION: " + x.direction);
                            println("> DESTINATION: " + x.targetRoom.name);
                            println("> ");
                        }
                        useTurn("DEFAULT");
                        break;
                    case "quit":
                        println("> EXITING TERMINAL VIEW");
                        player.currentView = PlayerView.DEFAULT_VIEW;
                        clearScreen();
                        useTurn("DEFAULT");
                        break;
                    case "look", "inspect", "unlock", "disable", "toggle", "bypass", "decrypt", "reset", "cass-use",
                         "cass-hide", "cass-go":
                        println("> INCORRECT COMMAND SYNTAX | This command requires at least 1 argument.");
                        useTurn("DEFAULT");
                        break;
                    case "enable":
                        println("> INCORRECT COMMAND SYNTAX | This command requires at least 1 argument. ");
                        useTurn("DEFAULT");
                        break;
                    case "cass":
                        println("> INVALID COMMAND | Must be followed by dash and desired action. (cass-[action])");
                        useTurn("DEFAULT");
                        break;
                    case "time":
                        println("> TURNS REMAINING: " + (this.maxTurns - this.turn));
                        break;
                    case "help":
                        println("> ");
                        println("> HELP | List all commands and their descriptions");
                        println("> QUIT | Exit Terminal View");
                        println("> TIME | Lists the number of remaining turns");
                        println("> SCAN | Lists the room, the objects inside (only IDs) and exits of said room");
                        println(">");
                        println("> INSPECT [OBJECT ID] | Lists the name, description, and current state of an object");
                        println("> UNLOCK [OBJECT ID] | Unlocks a locked door if it is locked");
                        println("> DISABLE [OBJECT ID] | Disables a security camera if it is enabled");
                        println("> ENABLE [OBJECT ID] | Enables a security camera if it is disabled");
                        println("> TOGGLE [OBJECT ID] | Toggles a light switch (i.e: turns off the light if it is on, and vice versa)");
                        println("> BYPASS [OBJECT ID] [ENTERED CODE] | Attempts to bypass a keypad with the entered code");
                        println("> DECRYPT [ENTERED CODE] | Decrypts an encrypted code from a Keypad");
                        println("> RESET [OBJECT ID] | Resets a security camera, removing any active alerts");
                        println("> ");
                        println("> CASS-GO [DIRECTION] | Move Cass to the connected location in the given direction");
                        println("> CASS-USE [OBJECT ID] | Instruct Cass to use the object according to its functionality");
                        println("> CASS-HIDE [OBJECT ID] | Instruct Cass to hide using a HidingSpot");
                        println("> ");
                        useTurn("DEFAULT");
                        break;
                    default:
                        println("> INVALID COMMAND | Type in 'help' to view a list of commands.");
                        useTurn("DEFAULT");
                }
            }
            println(">");

        }
    }

    public void guardMvmtSystem() {
        //TODO: Navigate guards on regular patrol, if implemented
    }

    public void CommsInterface() {
        println("----- COMMS RECORD -----");
        println("");
        for (Integer index : logs.keySet()) {
            println("[Turn " + index + "] CASS: " + logs.get(index));
        }
        println("");
        println("-- END OF COMMS RECORD --");
        player.currentView = PlayerView.DEFAULT_VIEW;
    }

    public void VitalsInterface() {
        println("--- VITALS INTERFACE ---");
        println("Stress: " + player.stress + " (" +  player.stressDesc() + ")");
        println("Heart Rate: " + player.heartRate + " BPM");
        println("Status: " + player.getStatus());
        println("Turns Remaining: " + (this.maxTurns - this.turn) + " / " + this.maxTurns);
        println("------------------------");
        player.currentView = PlayerView.DEFAULT_VIEW;
    }

    //TODO: Add Documents Interface/View
    /*
    public void DocumentsInterface() {
    }
     */

    public void updateLog(Object src) {
        //TODO: Implement Observer method in Interactables to return state changes
        String[] filler = {};
        if (player.isSleeping()) {
            return;
        }
        if (src == "CHANGE_OF_ROOMS") {
            filler = new String[] {"Okay, new room, new struggle.", "Changing rooms...", "Hoping this is the way out."};
        } else if (src == "INVALID_ROOM_CHANGE") {
            filler = new String[] {"There's no exit there!", "There's nowhere to go!", "Are you dumb? I can't go that way!"};
        } else if (src == "INACCESSIBLE_EXIT") {
            filler = new String[] {"I'd love to go that way, but I can't.", "I can't access that exit."};
        } else if (src == "DEFAULT" && Math.random() > 0.9) {
            filler = new String[] {"Kinda freaking out here...", "If anyone's listening, I'd appreciate their help.", "Will I see my family again...?", "Hey, is this thing working?"};
        }
        if (filler.length > 0) {
            int index = (int) (Math.random() * filler.length);
            String selected = filler[index];
            logs.put(this.turn, selected);
        }
    }

    public void useTurn(Object source) {
        if (player.bed != null) {
            player.bed.turnCount++;
            player.stress -= (int) (Math.random() * player.bed.restEfficiency) + 10;
            if (player.stress < 0) {
                player.stress = 0;
            }
        }
        updateLog(source);
        player.setStatus();
        this.turn ++;
    }

    public void gameLoop() {
        while (this.gameRunning && this.turn < this.maxTurns) {
            switch (player.currentView) {
                case DEFAULT_VIEW:
                    System.out.println();
                    this.txt_color = ConsoleColor.WHITE;
                    println("1. Terminal View");
                    println("2. Vitals View");
                    println("3. Comms View");
                    print("Select a number to change viewpoint (0 to exit): ");
                    String input = scanner.nextLine();
                    try {
                        int view = Integer.parseInt(input);
                        if (view > 3 || view < 0) {
                            view /= 0;
                        }
                        switch (view) {
                            case 0:
                                print("Quitting game....");
                                System.exit(0);
                            case 1:
                                player.currentView = PlayerView.TERMINAL_VIEW;
                                break;
                            case 2:
                                player.currentView = PlayerView.VITALS_VIEW;
                                break;
                            case 3:
                                player.currentView = PlayerView.COMMS_VIEW;
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input! Type in a number between 1 and 3.");
                    }
                    useTurn("DEFAULT");
                    continue;
                case TERMINAL_VIEW:
                    System.out.println();
                    this.txt_color = ConsoleColor.GREEN;
                    this.TerminalInterface();
                    useTurn("DEFAULT");
                    continue;
                case COMMS_VIEW:
                    System.out.println();
                    this.txt_color = ConsoleColor.CYAN;
                    this.CommsInterface();
                    useTurn("DEFAULT");
                    continue;
                case VITALS_VIEW:
                    System.out.println();
                    this.txt_color = ConsoleColor.DEFAULT;
                    this.VitalsInterface();
                    useTurn("DEFAULT");
                    continue;
                default:
                    clearScreen();
                    System.out.println();
                    System.exit(1);
            }
        }
    }
}
