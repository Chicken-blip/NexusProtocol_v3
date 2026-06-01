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
        switch (mapType) {
            case 0:
                roomA = new Room("Cell_A7");
                roomA.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomB = new Room("Corridor_A");
                roomB.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomC = new Room("Evaluation_Lab");
                roomC.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);


                Interactable a = new LockedDoor("Reinforced Door", main.generateID(), 1);
                roomA.addInteractable(a);
                a = new VentCover("Air Vent", main.generateID(), 2);
                roomA.addInteractable(a);
                a = new WaterDispenser("Water Dispenser", main.generateID(), 3);
                roomA.addInteractable(a);
                a = new LightSwitch("Light Switch A7", main.generateID());
                roomA.setDependent(a);
                roomA.addInteractable(a);


                Exit b = new Exit("North Exit");
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
            case 1:
                //TODO: Complete 1st Facility Construction
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
            default:
                rooms.add(new Room("Invalid map number!"));
        }
    }
}
