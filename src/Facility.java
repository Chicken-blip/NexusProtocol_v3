import java.util.ArrayList;
import java.util.List;

public class Facility {
    Game main;
    List<Room> rooms;

    public Facility(Game game, int mapType) {
        main = game;
        this.rooms = new ArrayList<>();
        //Map 0: 3-Room test map
        Room roomA, roomB, roomC, roomD, roomE, roomF, roomG, roomH;
        Interactable a;
        Exit b;
        Item c;
        switch (mapType) {
            case 0 -> {
                roomA = new Room("Cell_A7");
                roomA.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomB = new Room("Corridor_A");
                roomB.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomC = new Room("Evaluation_Lab");
                roomC.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);


                a = new LockedDoor("Reinforced Door", main.generateID(), 1);
                roomA.addInteractable(a);
                a = new VentCover("Air Vent", main.generateID(), 2);
                roomA.addInteractable(a);
                a = new WaterDispenser("Water Dispenser", main.generateID(), 3);
                roomA.addInteractable(a);
                a = new LightSwitch("Light Switch A7", main.generateID());
                roomA.setDependent(a);
                roomA.addInteractable(a);


                b = new Exit("North Exit");
                b.setTarget(roomB);
                b.setDependent(roomA.roomObjects.getFirst()); //Reinforced_Door must be unlocked
                b.setDirection(Direction.NORTH);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDesc("The reinforced door leads to the corridor.");
                roomA.addExit(b);

                rooms.add(roomA);

                a = new SecurityCamera("Security Camera", main.generateID());
                roomB.addInteractable(a);
                a = new Keypad("Keypad A1", main.generateID());
                roomB.addInteractable(a);
                a = new VentCover("Air Vent", main.generateID(), 4);
                roomB.addInteractable(a);
                a = new HidingSpot("Emergency Closet", main.generateID(), 8);
                roomB.addInteractable(a);

                b = new Exit("South Exit");
                b.setTarget(roomA);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.SOUTH);
                b.setDesc("The cell door is open.");
                roomB.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomC);
                b.setDependent(roomB.roomObjects.get(1)); //Keypad_A1 must be bypassed
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.EAST);
                b.setDesc("A keypad-secured door to the medical lab.");
                roomB.addExit(b);

                rooms.add(roomB);

                a = new LockedDoor("Drug Cabinet", main.generateID(), 2);
                roomC.addInteractable(a);
                a = new Window("Observation Window", main.generateID(), 5, 3);
                roomC.addInteractable(a);
                a = new EmergencyKit("Medical Supplies", main.generateID(), 2, new String[] {"STIMULANT", "SEDATIVE"});
                roomC.addInteractable(a);
                a = new Keypad("Biometric Terminal", main.generateID());
                roomC.addInteractable(a);

                b = new Exit("West Exit");
                b.setTarget(roomB);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.WEST);
                b.setDesc("The corridor door.");
                roomC.addExit(b);

                rooms.add(roomC);
            }
            case 1 -> {
                //TODO: Facility needs testing, but should work generally
                roomA = new Room("Cell_M4");
                roomA.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomB = new Room("Corridor_M");
                roomB.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomC = new Room("Utility_Access");
                roomC.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomD = new Room("Logistics Bay");
                roomD.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomE = new Room("Loading Bay");
                roomE.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomF = new Room("Med-Lab");
                roomF.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomG = new Room("Atrium");
                roomG.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomH = new Room("Security Office");
                roomH.setActionRequirement(ActionRequirement.LIGHTS_ON);

                a = new LockedDoor("Reinforced Door", main.generateID(), 3);
                roomA.addInteractable(a);
                a = new WaterDispenser("Water Dispenser", main.generateID(), 3);
                roomA.addInteractable(a);
                a = new Bed("Cot", main.generateID(), 35, 2);
                roomA.addInteractable(a);
                a = new LightSwitch("Light Switch M4", main.generateID());
                roomA.setDependent(a);
                roomA.addInteractable(a);

                b = new Exit("East Exit");
                b.setTarget(roomB);
                b.setDependent(roomA.roomObjects.getFirst()); //Reinforced_Door must be unlocked
                b.setDirection(Direction.EAST);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDesc("The reinforced door leads to the corridor.");
                roomA.addExit(b);

                rooms.add(roomA);

                a = new Keypad("Keypad M1", main.generateID());
                roomB.addInteractable(a);
                a = new SecurityCamera("Security Camera", main.generateID());
                roomB.addInteractable(a);
                a = new SecurityCamera("Security Camera", main.generateID());
                roomB.addInteractable(a);
                a = new HidingSpot("Emergency Closet", main.generateID(), 5);
                roomB.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 3);
                roomB.addInteractable(a);
                //Also add Trash Bin in this room
                a = new OpenSearchable("Trash Bin", main.generateID());
                roomB.addInteractable(a);

                b = new Exit("West Exit");
                b.setTarget(roomA);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.WEST);
                b.setDesc("The cell door is open.");
                roomB.addExit(b);

                b = new Exit("North Exit");
                b.setTarget(roomD);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.NORTH);
                b.setDesc("The exit leads to the Logistics Bay.");
                roomB.addExit(b);

                b = new Exit("South Exit");
                b.setTarget(roomC);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.SOUTH);
                b.setDesc("The exit leads to Utility Access.");
                roomB.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomB);
                b.setDependent(roomA.roomObjects.getFirst()); //Keypad must be bypassed
                b.setDirection(Direction.EAST);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDesc("The keypad-locked exit leads to the med-lab.");
                roomB.addExit(b);

                rooms.add(roomB);

                a = new HidingSpot("Pipe Junction Box", main.generateID(), 2);
                roomC.addInteractable(a);
                a = new Searchable("Storage Shelves", main.generateID());
                roomC.addInteractable(a);
                a = new Searchable("Maintenance Log Cabinet", main.generateID());
                c = new Document("Repair Logs", main.generateID());
                ((Searchable) a).addItem(c);
                a = new LightSwitch("Light Switch", main.generateID());
                roomC.setDependent(a);
                roomC.addInteractable(a);

                b = new Exit("North Exit");
                b.setTarget(roomB);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to Corridor M.");
                b.setDirection(Direction.NORTH);
                roomC.addExit(b);

                rooms.add(roomC);

                a = new LockedDoor("Roll-up Door", main.generateID(), 2);
                roomD.addInteractable(a);
                a = new Searchable("Shipping Container", main.generateID());
                c = new Tool("Crowbar", main.generateID());
                ((Searchable) a).addItem(c);
                roomD.addInteractable(a);
                a = new Searchable("Shipping Manifests", main.generateID());
                c = new Document("Manifests", main.generateID());
                ((Searchable) a).addItem(c);
                roomD.addInteractable(a);
                a = new Searchable("Tool Cabinet", main.generateID());
                c = new Tool("Wire Cutters", main.generateID());
                ((Searchable) a).addItem(c);
                c = new Tool("Flashlight", main.generateID());
                ((Searchable) a).addItem(c);
                roomD.addInteractable(a);

                b = new Exit("North Exit");
                b.setTarget(roomE);
                b.setDependent(roomD.roomObjects.getFirst()); //Roll-up door must be unlocked
                b.setDirection(Direction.NORTH);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDesc("The roll-up door leads to the med-lab.");
                roomD.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomG);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to atrium.");
                b.setDirection(Direction.EAST);
                roomD.addExit(b);

                b = new Exit("South Exit");
                b.setTarget(roomB);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to Corridor M.");
                b.setDirection(Direction.SOUTH);
                roomD.addExit(b);

                rooms.add(roomD);

                a = new HidingSpot("Truck", main.generateID(), 3);
                roomE.addInteractable(a);
                a = new SecurityCamera("Security Camera", main.generateID());
                roomE.addInteractable(a);
                a = new Searchable("Shipping Box", main.generateID());
                c = new Tool("Emergency Flare", main.generateID());
                ((Searchable) a).addItem(c);
                roomE.addInteractable(a);

                b = new Exit("South Exit");
                b.setTarget(roomD);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to the Logistics Bay.");
                b.setDirection(Direction.SOUTH);
                roomE.addExit(b);

                rooms.add(roomE);

                a = new VentCover("Vent Cover", main.generateID(), 3);
                roomF.addInteractable(a);
                a = new EmergencyKit("Medical Supplies", main.generateID(), 2, new String[] {"SEDATIVE", "SEDATIVE", "BANDAGES"});
                roomF.addInteractable(a);
                a = new Searchable("Drug Cabinet", main.generateID());
                roomF.addInteractable(a);
                a = new OpenSearchable("Examination Table", main.generateID());
                roomF.addInteractable(a);

                b = new Exit("West Exit");
                b.setTarget(roomB);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to Corridor M.");
                b.setDirection(Direction.WEST);
                roomF.addExit(b);

                b = new Exit("North Exit");
                b.setTarget(roomG);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to the atrium.");
                b.setDirection(Direction.NORTH);
                roomF.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomH);
                b.setRequirement(ExitRequirement.HAS_KEYCARD);
                b.setDesc("The exit leads to the security office.");
                b.setCard_perm("Security");
                b.setDirection(Direction.EAST);
                roomF.addExit(b);

                rooms.add(roomF);

                a = new WaterDispenser("Water Dispenser", main.generateID(), 7);
                roomG.addInteractable(a);
                a = new SecurityCamera("Patrol Drone", main.generateID());
                roomG.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 5);
                roomG.addInteractable(a);
                a = new OpenSearchable("Bench", main.generateID());
                c = new Document("Facility Brochure", main.generateID());
                ((OpenSearchable) a).addItem(c);
                c = new Keycard("Employee Badge", main.generateID(), "Security");
                ((OpenSearchable) a).addItem(c);
                roomG.addInteractable(a);
                //Add Searchable and Items later

                b = new Exit("South Exit");
                b.setTarget(roomF);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to the med-lab.");
                b.setDirection(Direction.SOUTH);
                roomG.addExit(b);

                b = new Exit("West Exit");
                b.setTarget(roomD);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit leads to the Logistics Bay.");
                b.setDirection(Direction.WEST);
                roomG.addExit(b);

                rooms.add(roomG);

                a = new LightSwitch("Security Office Light Switch", main.generateID());
                roomH.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 3);
                roomH.addInteractable(a);
                a = new Searchable("Filing Cabinet", main.generateID());
                roomH.addInteractable(a);
                a = new Searchable("Shift Log Binder", main.generateID());
                roomH.addInteractable(a);
                a = new OpenSearchable("Guard Desk", main.generateID());
                c = new Keycard("Master Keycard", main.generateID(), "Master");
                ((OpenSearchable) a).addItem(c);
                roomH.addInteractable(a);

                b = new Exit("West Exit");
                b.setTarget(roomF);
                b.setRequirement(ExitRequirement.HAS_KEYCARD);
                b.setDesc("The exit leads to the med-lab.");
                b.setCard_perm("Security");
                b.setDirection(Direction.WEST);
                roomH.addExit(b);

                rooms.add(roomH);
            }
            default -> {
                rooms.add(new Room("Invalid map number!"));
            }
        }
    }
}
