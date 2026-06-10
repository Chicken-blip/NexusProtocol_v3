import java.util.ArrayList;
import java.util.List;

public class Facility {
    Game main;
    List<Room> rooms;

    public Facility(Game game, int mapType) {
        main = game;
        this.rooms = new ArrayList<>();
        //Map 0: 3-Room test map
        Room roomA, roomB, roomC, roomD, roomE, roomF, roomG, roomH, roomI, roomJ, roomK, roomL, roomM, roomN, roomO, roomP;
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
                roomI = new Room("Vents");
                roomI.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomJ = new Room("Vents");
                roomJ.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomK = new Room("Vents");
                roomK.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomL = new Room("Vents");
                roomL.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);

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
                roomI.addInteractable(a);
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

                b = new Exit("Vent Entrance");
                b.setTarget(roomI);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomI.roomObjects.getFirst());
                b.setDesc("The exit leads to the vents above this room.");
                roomB.addExit(b);

                rooms.add(roomB);

                a = new HidingSpot("Pipe Junction Box", main.generateID(), 2);
                roomC.addInteractable(a);
                a = new Searchable("Storage Shelves", main.generateID());
                roomC.addInteractable(a);
                a = new Searchable("Maintenance Log Cabinet", main.generateID());
                c = new Document("Repair Logs", main.generateID());
                //Add Repair Log contents
                ((Document) c).setContents("NEXCORP - IRONHOLD FACILITY MAINTENANCE SYSTEM\n\n" +
                        "Work Order #438 – Opened 10/15\n" +
                        "Location: Cell M4, Light Fixture\n" +
                        "Issue: Flickering, intermittent failure\n" +
                        "Status: Assigned to J. Chen\n\n" +
                        "10/16 – Chen: Replaced bulb. No change.\n" +
                        "10/18 – Chen: Replaced ballast. Flickering reduced but not eliminated.\n" +
                        "10/25 – Supervisor note: Close order. Budget constraints." +
                        "\n\n\n" +
                        "Work Order #441 – Opened 11/2\n" +
                        "Location: Cell M4, Light Fixture (repeat)\n" +
                        "Issue: Complete failure\n" +
                        "Status: Assigned to T. Miller\n\n" +
                        "11/3 – Miller: Found loose wiring behind panel. Retightened. Operational.\n" +
                        "11/4 – Miller: Patient reports light still flickering. Recommend full panel replacement.\n" +
                        "11/5 – Supervisor note: Request denied. Monitor only." +
                        "\n\n\n" +
                        "Work Order #447 – Opened 11/9\n" +
                        "Location: Corridor M, Camera M1\n" +
                        "Issue: Intermittent signal loss, 3-5 minute durations\n" +
                        "Status: Assigned to J. Chen\n\n" +
                        "11/10 – Chen: No hardware fault found. Possible electromagnetic interference from nearby panel.\n" +
                        "11/11 – Chen: Moved camera 2 feet. Signal stable for 48 hours.\n" +
                        "11/13 – Open again. Same issue." +
                        "\n\n\n" +
                        "Work Order #452 – Opened 11/14\n" +
                        "Location: Utility Access, Steam Pipe Junction 9B\n" +
                        "Issue: Pressure fluctuation, audible hissing\n" +
                        "Status: Assigned to T. Miller\n\n" +
                        "11/15 – Miller: Tightened valve assembly. Pressure normalized.\n" +
                        "11/16 – Miller: Recurrence. Gasket likely failing. Replacement ordered.\n" +
                        "11/18 – Gasket arrived. Scheduled for 11/20.\n" +
                        "\n\n\n" +
                        "Work Order #458 – Opened 11/19\n" +
                        "Location: Logistics Bay, Roll-Up Door\n" +
                        "Issue: Slow operation, grinding noise\n" +
                        "Status: Assigned to J. Chen\n\n" +
                        "11/20 – Chen: Lubricated track. Improved but not resolved.\n" +
                        "11/21 – Chen: Motor assembly showing wear. Replacement recommended.\n" +
                        "11/22 – Supervisor note: Not a priority. Mark \"monitor only.\"");
                ((Searchable) a).addItem(c);
                roomC.addInteractable(a);
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
                //Add Manifests contents
                ((Document) c).setContents("NEXCORP SHIPPING MANIFEST – CONFIDENTIAL\n" +
                        "Document No.: NX-8842-01\n" +
                        "Destination: Ironhold Facility, Sublevel 3\n" +
                        "Shipment Date: 11/1\n\n" +
                        "Containers (4 total):\n" +
                        "Container A: 12x Medical Grade Sedatives (Schedule B-IV)\n" +
                        "Container B: 8x Industrial Solvent (Class 3 Flammable)\n" +
                        "Container C: 2x Biometric Terminal Replacement Units\n" +
                        "Container D: 20x Standard Issue Keycards (Engineering Access only)\n\n" +
                        "Received by: Dr. Grant, Med-Lab\n" +
                        "Notes: Container D short by 2 units. Discrepancy reported.\n\n\n" +
                        "NEXCORP SHIPPING MANIFEST – CONFIDENTIAL\n" +
                        "Document No.: NX-8842-07\n" +
                        "Destination: Ironhold Facility, Sublevel 3\n" +
                        "Shipment Date: 11/8\n\n" +
                        "Containers (3 total):\n" +
                        "Container A: 4x Security Camera Replacement Units\n" +
                        "Container B: 500ft Electrical Wiring (14 gauge)\n" +
                        "Container C: 1x Industrial Crowbar\n\n" +
                        "Received by: J. Chen, Logistics\n" +
                        "Notes: Container G opened during transit. Contents intact.\n\n\n" +
                        "NEXCORP SHIPPING MANIFEST – CONFIDENTIAL\n" +
                        "Document No.: NX-8842-12\n" +
                        "Destination: Ironhold Facility, Sublevel 3\n" +
                        "Shipment Date: 11/15\n\n" +
                        "Containers (2 total):\n" +
                        "Container A: 6x Emergency Flares (Expiration: 01/2027)\n" +
                        "Container B: 3x Vehicle Maintenance Kits (Loading Bay Truck)\n\n" +
                        "Received by: J. Chen, Logistics\n" +
                        "Notes: Truck maintenance scheduled for 11/22. Kits stored in truck cab.");
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
                roomJ.addInteractable(a);
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

                b = new Exit("Vent Entrance");
                b.setTarget(roomJ);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomJ.roomObjects.getFirst());
                b.setDesc("The exit leads to the vents above this room.");
                roomF.addExit(b);

                rooms.add(roomF);

                a = new WaterDispenser("Water Dispenser", main.generateID(), 7);
                roomG.addInteractable(a);
                a = new SecurityCamera("Patrol Drone", main.generateID());
                roomG.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 5);
                roomG.addInteractable(a);
                roomK.addInteractable(a);
                a = new OpenSearchable("Bench", main.generateID());
                c = new Document("Facility Brochure", main.generateID());
                //Add Facility Brochure contents
                ((Document) c).setContents("WELCOME TO NEXCORP'S IRONHOLD'\n\n" +
                        "Your safety is our priority. Excellence is our standard.\n\n" +
                        "NexCorp's Ironhold Facility' is a state-of-the-art research and containment facility dedicated to advancing human potential. Since opening in 2047, we have maintained an unbroken record of operational security.\n\n" +
                        "Facility Guidelines:\n" +
                        "All employees must wear visible keycards at all times\n" +
                        "Unauthorized access to Security Office (Level 3) is strictly prohibited\n" +
                        "Medical staff must log all controlled substance usage in Med-Lab\n" +
                        "Maintenance requests should be submitted through the digital system\n\n" +
                        "Emergency Procedures:\n" +
                        "In case of evacuation, proceed to Loading Bay\n" +
                        "In case of containment breach, follow red floor markings\n" +
                        "In case of fire, use extinguishers located in Corridor M and Logistics Bay\n\n" +
                        "Thank you for your commitment to NexCorp's mission. — Human Resources Department, Ironhold Facility");
                ((OpenSearchable) a).addItem(c);
                c = new Keycard("Employee Badge", main.generateID(), "Security");
                ((OpenSearchable) a).addItem(c);
                roomG.addInteractable(a);

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

                b = new Exit("Vent Entrance");
                b.setTarget(roomK);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomK.roomObjects.getFirst());
                b.setDesc("The exit leads to the vents above this room.");
                roomG.addExit(b);

                rooms.add(roomG);

                a = new LightSwitch("Security Office Light Switch", main.generateID());
                roomH.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 3);
                roomH.addInteractable(a);
                roomL.addInteractable(a);
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

                b = new Exit("Vent Entrance");
                b.setTarget(roomL);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomL.roomObjects.getFirst());
                b.setDesc("The exit leads to the vents above this room.");
                roomH.addExit(b);

                rooms.add(roomH);

                //Add Vent Room exits
                b = new Exit("Vent Exit");
                b.setTarget(roomB);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomI.roomObjects.getFirst());
                b.setDesc("The exit leads down to Corridor M.");
                b.setDirection(Direction.DOWN);
                roomI.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomJ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.EAST);
                roomI.addExit(b);

                b = new Exit("Vent Exit");
                b.setTarget(roomF);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomJ.roomObjects.getFirst());
                b.setDesc("The exit leads down to the med-lab.");
                b.setDirection(Direction.DOWN);
                roomJ.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomI);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.WEST);
                roomJ.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomK);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.NORTH);
                roomJ.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomL);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.EAST);
                roomJ.addExit(b);

                b = new Exit("Vent Exit");
                b.setTarget(roomG);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomK.roomObjects.getFirst());
                b.setDesc("The exit leads down to the atrium.");
                b.setDirection(Direction.DOWN);
                roomK.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomJ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.SOUTH);
                roomK.addExit(b);

                b = new Exit("Vent Exit");
                b.setTarget(roomH);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomL.roomObjects.getFirst());
                b.setDesc("The exit leads down to the Security Office.");
                b.setDirection(Direction.DOWN);
                roomL.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomJ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.WEST);
                roomL.addExit(b);

            }
            default -> {
                rooms.add(new Room("Invalid map number!"));
            }
        }
    }
}
