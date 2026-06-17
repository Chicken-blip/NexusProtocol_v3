import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Facility {
    Game main;
    List<Room> rooms;

    public Facility(Game game, int mapType) {
        main = game;
        this.rooms = new ArrayList<>();
        //Map 0: 3-Room test map
        Room roomA, roomB, roomC, roomD, roomE, roomF, roomG, roomH, roomI, roomJ, roomK, roomL, roomM, roomN, roomO, roomP, roomQ, roomR, roomS, roomT;
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
                roomA = new Room("Cell M4");
                roomA.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomB = new Room("Corridor M");
                roomB.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomC = new Room("Utility Access");
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
                ((Document) c).setContents("""
                        NEXCORP - IRONHOLD FACILITY MAINTENANCE SYSTEM
                        
                        Work Order #438 – Opened 10/15
                        Location: Cell M4, Light Fixture
                        Issue: Flickering, intermittent failure
                        Status: Assigned to J. Chen
                        
                        10/16 – Chen: Replaced bulb. No change.
                        10/18 – Chen: Replaced ballast. Flickering reduced but not eliminated.
                        10/25 – Supervisor note: Close order. Budget constraints.\
                        
                        
                        
                        Work Order #441 – Opened 11/2
                        Location: Cell M4, Light Fixture (repeat)
                        Issue: Complete failure
                        Status: Assigned to T. Miller
                        
                        11/3 – Miller: Found loose wiring behind panel. Retightened. Operational.
                        11/4 – Miller: Patient reports light still flickering. Recommend full panel replacement.
                        11/5 – Supervisor note: Request denied. Monitor only.\
                        
                        
                        
                        Work Order #447 – Opened 11/9
                        Location: Corridor M, Camera M1
                        Issue: Intermittent signal loss, 3-5 minute durations
                        Status: Assigned to J. Chen
                        
                        11/10 – Chen: No hardware fault found. Possible electromagnetic interference from nearby panel.
                        11/11 – Chen: Moved camera 2 feet. Signal stable for 48 hours.
                        11/13 – Open again. Same issue.\
                        
                        
                        
                        Work Order #452 – Opened 11/14
                        Location: Utility Access, Steam Pipe Junction 9B
                        Issue: Pressure fluctuation, audible hissing
                        Status: Assigned to T. Miller
                        
                        11/15 – Miller: Tightened valve assembly. Pressure normalized.
                        11/16 – Miller: Recurrence. Gasket likely failing. Replacement ordered.
                        11/18 – Gasket arrived. Scheduled for 11/20.
                        
                        
                        
                        Work Order #458 – Opened 11/19
                        Location: Logistics Bay, Roll-Up Door
                        Issue: Slow operation, grinding noise
                        Status: Assigned to J. Chen
                        
                        11/20 – Chen: Lubricated track. Improved but not resolved.
                        11/21 – Chen: Motor assembly showing wear. Replacement recommended.
                        11/22 – Supervisor note: Not a priority. Mark "monitor only.\"""");
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
                ((Document) c).setContents("""
                        NEXCORP SHIPPING MANIFEST – CONFIDENTIAL
                        Document No.: NX-8842-01
                        Destination: Ironhold Facility, Sublevel 3
                        Shipment Date: 11/1
                        
                        Containers (4 total):
                        Container A: 12x Medical Grade Sedatives (Schedule B-IV)
                        Container B: 8x Industrial Solvent (Class 3 Flammable)
                        Container C: 2x Biometric Terminal Replacement Units
                        Container D: 20x Standard Issue Keycards (Engineering Access only)
                        
                        Received by: Dr. Grant, Med-Lab
                        Notes: Container D short by 2 units. Discrepancy reported.
                        
                        
                        NEXCORP SHIPPING MANIFEST – CONFIDENTIAL
                        Document No.: NX-8842-07
                        Destination: Ironhold Facility, Sublevel 3
                        Shipment Date: 11/8
                        
                        Containers (3 total):
                        Container A: 4x Security Camera Replacement Units
                        Container B: 500ft Electrical Wiring (14 gauge)
                        Container C: 1x Industrial Crowbar
                        
                        Received by: J. Chen, Logistics
                        Notes: Container G opened during transit. Contents intact.
                        
                        
                        NEXCORP SHIPPING MANIFEST – CONFIDENTIAL
                        Document No.: NX-8842-12
                        Destination: Ironhold Facility, Sublevel 3
                        Shipment Date: 11/15
                        
                        Containers (2 total):
                        Container A: 6x Emergency Flares (Expiration: 01/20XX)
                        Container B: 3x Vehicle Maintenance Kits (Loading Bay Truck)
                        
                        Received by: J. Chen, Logistics
                        Notes: Truck maintenance scheduled for 11/22. Kits stored in truck cab.""");
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
                ((Document) c).setContents("""
                        WELCOME TO NEXCORP'S IRONHOLD FACILITY
                        
                        Your safety is our priority. Excellence is our standard.
                        
                        'NexCorp's Ironhold Facility' is a state-of-the-art research and containment facility dedicated to advancing human potential. Since opening in 2047, we have maintained an unbroken record of operational security.
                        
                        Facility Guidelines:
                        All employees must wear visible keycards at all times
                        Unauthorized access to Security Office (Level 3) is strictly prohibited
                        Medical staff must log all controlled substance usage in Med-Lab
                        Maintenance requests should be submitted through the digital system
                        
                        Emergency Procedures:
                        In case of evacuation, proceed to Loading Bay
                        In case of containment breach, follow red floor markings
                        In case of fire, use extinguishers located in Corridor M and Logistics Bay
                        
                        Thank you for your commitment to NexCorp's mission. — Human Resources Department, Ironhold Facility""");
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
            case 2 -> {
                roomA = new Room("Cell G3");
                roomA.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomB = new Room("Cell G4");
                roomB.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomC = new Room("Cell G7");
                roomC.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomD = new Room("Corridor G");
                roomD.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomE = new Room("Security Checkpoint");
                roomE.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomF = new Room("Main Atrium");
                roomF.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomG = new Room("Employee Lounge");
                roomG.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomH = new Room("General Lab A");
                roomH.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomI = new Room("General Lab B");
                roomI.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomJ = new Room("Genetics Lab");
                roomJ.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomK = new Room("Neural Research Lab");
                roomK.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomL = new Room("Testing Chamber");
                roomL.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomM = new Room("Generator Room");
                roomM.setActionRequirement(ActionRequirement.LIGHTS_ON);
                roomN = new Room("Maintenance Junction");
                roomN.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomO = new Room("Loading Bay");
                roomO.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomP = new Room("Vents");
                roomP.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomQ = new Room("Vents");
                roomQ.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomR = new Room("Vents");
                roomR.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomS = new Room("Vents");
                roomS.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);
                roomT = new Room("Vents");
                roomT.setActionRequirement(ActionRequirement.ALWAYS_ACTIONABLE);

                a = new LockedDoor("Reinforced Door", main.generateID(), 5);
                roomA.addInteractable(a);
                roomD.addInteractable(a);
                a = new Bed("Cot", main.generateID(), 15, 3);
                roomA.addInteractable(a);

                b = new Exit("South Exit");
                b.setTarget(roomD);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.SOUTH);
                b.setDependent(roomA.roomObjects.getFirst());
                b.setDesc("The reinforced door leads to the corridor.");
                roomA.addExit(b);

                rooms.add(roomA);

                a = new LockedDoor("Reinforced Door", main.generateID(), 5);
                roomB.addInteractable(a);
                roomD.addInteractable(a);
                a = new Bed("Cot", main.generateID(), 15, 3);
                roomB.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 2);
                roomB.addInteractable(a);
                roomP.addInteractable(a);

                b = new Exit("Vent Entrance");
                b.setTarget(roomP);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomB.roomObjects.getLast());
                b.setDesc("The exit leads to the vents above this room.");
                roomB.addExit(b);

                b = new Exit("West Exit");
                b.setTarget(roomD);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.WEST);
                b.setDependent(roomB.roomObjects.getFirst());
                b.setDesc("The reinforced door leads to the corridor.");
                roomB.addExit(b);

                rooms.add(roomB);

                a = new LockedDoor("Reinforced Door", main.generateID(), 5);
                roomC.addInteractable(a);
                roomD.addInteractable(a);
                a = new Bed("Cot", main.generateID(), 15, 3);
                roomC.addInteractable(a);

                b = new Exit("East Exit");
                b.setTarget(roomD);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.EAST);
                b.setDependent(roomC.roomObjects.getFirst());
                b.setDesc("The reinforced door leads to the corridor.");
                roomC.addExit(b);

                rooms.add(roomC);

                a = new LightSwitch("Light Switch", main.generateID());
                roomD.setDependent(a);
                roomD.addInteractable(a);
                a = new SecurityCamera("Security Camera", main.generateID());
                roomD.addInteractable(a);
                a = new HidingSpot("Emergency Closet", main.generateID(), 5);
                roomD.addInteractable(a);
                a = new OpenSearchable("Trash Bin", main.generateID());
                roomD.addInteractable(a);

                b = new Exit("North Exit");
                b.setTarget(roomA);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.NORTH);
                b.setDependent(roomA.roomObjects.getFirst());
                b.setDesc("The reinforced door leads to a cell.");
                roomD.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomB);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.EAST);
                b.setDependent(roomB.roomObjects.getFirst());
                b.setDesc("The reinforced door leads to a cell.");
                roomD.addExit(b);

                b = new Exit("West Exit");
                b.setTarget(roomC);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.WEST);
                b.setDependent(roomC.roomObjects.getFirst());
                b.setDesc("The reinforced door leads to a cell.");
                roomD.addExit(b);

                b = new Exit("South Exit");
                b.setTarget(roomE);
                b.setRequirement(ExitRequirement.HAS_KEYCARD);
                b.setDesc("The exit leads to the security checkpoint.");
                b.setCard_perm("Security");
                b.setDirection(Direction.SOUTH);
                roomD.addExit(b);

                rooms.add(roomD);

                a = new SecurityCamera("Security Camera", main.generateID());
                roomE.addInteractable(a);
                a = new OpenSearchable("Guard Desk", main.generateID());
                c = new Document("Shift Log", main.generateID());
                ((Document) c).setContents("""
                        NEXCORP – OBELISK FACILITY SECURITY SHIFT LOG — Binder ID: SEC-OB-20XX-11
                        
                        11/1 (Friday) – Supervisor: H. Cross
                        0600: Shift change. All systems nominal.
                        1200: Dr. Patel requests after-hours access to East Wing. Approved.
                        1800: Routine patrol. No incidents.
                        2200: Light flickering reported in North Wing, Cell C. Maintenance notified.
                        
                        11/2 (Saturday) – Supervisor: M. Webb
                        0600: Cross out. Webb covering.
                        1000: Generator test scheduled for 1400. Backup power confirmed operational.
                        1400: Generator test completed. Fuel levels at 78%.
                        1800: Cell C light repair completed by J. Ortiz.
                        
                        11/3 (Sunday) – Supervisor: H. Cross
                        0600: Webb relieved. Cross returns.
                        0800: Inmate 882 (Cass) transferred from Ironhold Facility. Placed in Cell C.
                        1200: Inmate 882 refuses medical evaluation. Note added to file.
                        2200: Night patrol reports unusual noise in Maintenance Wing. Investigation pending.
                        
                        11/4 (Monday) – Supervisor: H. Cross
                        0600: Maintenance Wing noise identified as loose steam pipe. Repair scheduled.
                        1400: Dr. Patel logs 4 hours in Research Lab. No further details.
                        2200: All secure.
                        
                        11/5 (Tuesday) – Supervisor: M. Webb
                        0600: Webb taking shift.
                        0900: Weekly system check. Camera E2 offline for 2 hours during maintenance.
                        1600: Ortiz requests fuel delivery for generator. Approved.
                        2300: Fuel delivered. Receipt logged.
                        
                        11/6 (Wednesday) – Supervisor: H. Cross
                        0600: Cross returns.
                        0800: Inmate 882 requests water. Dispenser refilled.
                        1400: Security drill. All personnel participate. No breaches.
                        2200: All secure.
                        
                        11/7 (Thursday) – Supervisor: H. Cross
                        0600: Shift change.
                        1000: Maintenance log updated. Generator status: OPERATIONAL.
                        1800: Dr. Patel observed entering Maintenance Wing. No authorization on file.
                        2200: Patel questioned. Claims "routine check." Warning issued.
                        
                        11/8 (Friday) – Supervisor: M. Webb
                        0600: Webb covering.
                        1200: Fuel level reading: 74%. No delivery scheduled.
                        2200: All secure.
                        """);
                ((OpenSearchable) a).addItem(c);
                roomE.addInteractable(a);
                //a = new Terminal("Security Terminal", main.generateID(), 2);
                //roomE.addInteractable(a);

                b = new Exit("North Exit");
                b.setTarget(roomD);
                b.setRequirement(ExitRequirement.HAS_KEYCARD);
                b.setDesc("The exit leads to the cell block corridor.");
                b.setCard_perm("Security");
                b.setDirection(Direction.NORTH);
                roomE.addExit(b);

                b = new Exit("South Exit");
                b.setTarget(roomF);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.SOUTH);
                b.setDesc("The door leads to the main atrium.");
                roomE.addExit(b);

                rooms.add(roomE);

                a = new WaterDispenser("Water Dispenser", main.generateID(), 3);
                roomF.addInteractable(a);
                a = new OpenSearchable("Information Kiosk", main.generateID());
                c = new Document("Facility Brochure", main.generateID());
                ((Document) c).setContents("""
                        WELCOME TO NEXCORP'S OBELISK FACILITY
                        
                        Since 2049, we have served as NexCorp's premier research and containment center.
                        Our commitment to safety, security, and scientific advancement remains unwavering.
                        
                        Facility Overview:
                        North Wing: Containment and Observation (RESTRICTED ACCESS)
                        East Wing: Medical and Research
                        West Wing: Logistics and Maintenance (RESTRICTED ACCESS)
                        Central Hub: Administration and Security (RESTRICTED ACCESS)
                        
                        Employee Guidelines:
                        - Keycards must be visible at all times
                        - Unauthorized access to Central Hub, North Wing, and West Wing is prohibited
                        - All medical procedures must be logged in the Med-Lab system
                        - Maintenance requests should be submitted via the digital work order system
                        
                        Emergency Procedures:
                        Evacuation: Proceed to West Wing Loading Bay
                        Containment Breach: Follow orange floor markings to designated shelters
                        Fire: Extinguishers located in every wing
                        Power Failure: Emergency lighting activates immediately. Backup generator in Maintenance Wing.
                        
                        Thank you for your commitment to NexCorp's mission. — Human Resources, Obelisk Facility
                        """);
                ((Searchable) a).addItem(c);
                roomF.addInteractable(a);
                a = new OpenSearchable("Bench", main.generateID());
                c = new Keycard("Employee Badge", main.generateID(), "Security");
                ((OpenSearchable) a).addItem(c);
                //Keypad for Main Atrium and General Lab A
                a = new Keypad("Keypad", main.generateID());
                roomF.addInteractable(a);
                roomH.addInteractable(a);

                b = new Exit("South Exit");
                b.setTarget(roomH);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.SOUTH);
                b.setDependent(roomF.roomObjects.getLast());
                b.setDesc("The keypad-locked exit leads to a lab.");
                roomF.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomG);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.EAST);
                b.setDesc("The door leads to the employee lounge.");
                roomF.addExit(b);

                b = new Exit("West Exit");
                b.setTarget(roomN);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.WEST);
                b.setDesc("The door leads to a junction.");
                roomF.addExit(b);

                b = new Exit("North Exit");
                b.setTarget(roomE);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.NORTH);
                b.setDesc("The door leads to the security checkpoint.");
                roomF.addExit(b);

                rooms.add(roomF);

                a = new WaterDispenser("Water Dispenser", main.generateID(), 7);
                roomG.addInteractable(a);
                a = new Searchable("Lockers", main.generateID());
                c = new Document("Generator Operation Manual", main.generateID());
                ((Document) c).setContents("""
                        NEXCORP – OBELISK FACILITY
                        GENERATOR OPERATIONS MANUAL — REV 4.2
                        Last Updated: 10/15
                        
                        Unit: NexCorp G7 Backup Generator
                        Output: 750kW
                        Status: OPERATIONAL
                        Fuel Capacity: 200L
                        Last Recorded Fuel Level: 74%
                        
                        Normal Operation:
                        - Unit activates automatically during power loss
                        - Fuel delivery required every 14 days (200L capacity)
                        - Monthly inspection: Check fuel lines, coolant, and mounting bolts
                        - Service records maintained in Maintenance Log
                        
                        Safety Systems:
                        - Automatic shutdown on fuel line pressure drop below 20 PSI
                        - Thermal cutoff at 210°F
                        - Manual emergency stop: Red button on control panel
                        - Fuel line access: Rear panel, requires 14mm wrench
                        
                        WARNING:
                        - DO NOT operate with fuel line pressure below 20 PSI
                        - DO NOT block ventilation intake
                        - Fuel line is pressurized. Use extreme caution when servicing.
                        - In event of fuel leak, evacuate area immediately and contact Maintenance.
                        
                        Maintenance Procedures:
                        - Fuel System: Access through rear panel. Inspect fuel line for cracks or wear. Ensure pressure gauge reads 40-50 PSI during operation.
                        - Cooling System: Coolant valve located on side panel. Drain and replace coolant every 6 months. Operating temperature should remain below 180°F.
                        - Electrical System: Control panel houses temperature cutoff relay, voltage regulator, and logic board. Do not bypass thermal safety mechanisms.
                        - Ventilation: Intake grille on top. Keep clear of debris. Restricted airflow will cause temperature rise and potential shutdown.
                        
                        Troubleshooting Common Issues:
                        - Low fuel pressure: Check fuel line for leaks. Severed lines will cause immediate shutdown.
                        - Overheating: Verify coolant valve is fully closed. Open valve will drain coolant and trigger thermal shutdown.
                        - Excessive vibration: Check mounting bolts. Loose bolts may indicate damage.
                        - Sudden shutdown: Verify fuel line integrity and thermal cutoff status.
                        
                        Document ID: GEN-MAN-004
                        Distribution: Maintenance Staff Only
                        """);
                roomG.addInteractable(a);
                //a = new Terminal("Admin Terminal", main.generateID(), 1);
                //roomG.addInteractable(a);
                a = new OpenSearchable("Table", main.generateID());
                c = new Document("Employee Schedule", main.generateID());
                ((Document) c).setContents("""
                        NEXCORP – OBELISK FACILITY EMPLOYEE WEEKLY SCHEDULE
                        
                        H. Cross - Security Supervisor
                        SHIFT: 0600 - 1800
                        DAYS: M, W, F, Su
                        
                        M. Webb - Security Officer
                        SHIFT: 0600 - 1800
                        DAYS: Tu, Th, Sa
                        
                        J. Ortiz - Maintenance Technician
                        SHIFT: 0800 - 2000
                        DAYS: M, Tu, W, F
                        
                        S. Patel - Lead Researcher
                        SHIFT: 1000 - 2200
                        DAYS: M, Tu, Th, F
                        
                        K. Reyas - Medical Staff
                        SHIFT: 0800 - 2000
                        DAYS: W, Th, F, Sa
                        
                        T. Aoki - Logistics Coordinator
                        SHIFT: 0800 - 2000
                        DAYS: M, W, F, Su
                        
                        D. Mercer - Junior Researcher
                        SHIFT: 0900 - 1700
                        DAYS: M, Tu, W, Th, F
                        
                        L. Park - Security Officer
                        SHIFT: 2200 - 0600
                        DAYS: M, Tu, W, Th, F, Sa, Su
                        """);
                ((OpenSearchable) a).addItem(c);
                roomG.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 4);
                roomG.addInteractable(a);
                roomQ.addInteractable(a);

                b = new Exit("Vent Entrance");
                b.setTarget(roomQ);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomG.roomObjects.getLast());
                b.setDesc("The exit leads to the vents above this room.");
                roomG.addExit(b);

                b = new Exit("West Exit");
                b.setTarget(roomF);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.WEST);
                b.setDesc("The door leads to the main atrium.");
                roomG.addExit(b);

                rooms.add(roomG);

                a = new Searchable("Chemical Storage Cabinet", main.generateID());
                roomH.addInteractable(a);
                //a = new Terminal("Research Terminal", main.generateID(), 1);
                //roomH.addInteractable(a);

                b = new Exit("North Exit");
                b.setTarget(roomF);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDirection(Direction.NORTH);
                b.setDependent(roomF.roomObjects.getLast());
                b.setDesc("The keypad-locked exit leads the main atrium.");
                roomH.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomI);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.EAST);
                b.setDesc("The door leads to another lab.");
                roomH.addExit(b);

                rooms.add(roomH);

                a = new Searchable("Tool Cabinet", main.generateID());
                c = new Tool("Wire Cutters", main.generateID());
                ((Searchable) a).addItem(c);
                c = new Keycard("Maintenance Keycard", main.generateID(), "Maintenance");
                ((Searchable) a).addItem(c);
                roomI.addInteractable(a);
                //a = new Terminal("Research Terminal", main.generateID(), 1);
                //roomI.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 5);
                roomI.addInteractable(a);
                roomR.addInteractable(a);

                b = new Exit("Vent Entrance");
                b.setTarget(roomR);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomI.roomObjects.getLast());
                b.setDesc("The exit leads to the vents above this room.");
                roomI.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomJ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.EAST);
                b.setDesc("The door leads to another lab.");
                roomI.addExit(b);

                b = new Exit("West Exit");
                b.setTarget(roomH);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.WEST);
                b.setDesc("The door leads to another lab.");
                roomI.addExit(b);

                rooms.add(roomI);

                //a = new Terminal("Genetics Terminal", main.generateID(), 1);
                //roomJ.addInteractable(a);
                a = new OpenSearchable("Storage Shelf", main.generateID());
                roomJ.addInteractable(a);
                a = new Searchable("Medical Refrigerator", main.generateID());
                c = new Document("Genetics Report", main.generateID());
                ((Document) c).setContents("""
                        NEXCORP – OBELISK FACILITY
                        CONFIDENTIAL — RESEARCH DIVISION Document ID: GEN-882-01
                        
                        Subject: 882 (CASS)
                        Preliminary Genetic Analysis
                        Date: 11/4
                        Analyst: Dr. S. Patel
                        
                        Subject 882 presents with markers consistent with enhanced cognitive and physical resilience. Baseline stress tolerance exceeds population average by 34%. Heart rate recovery time is notably accelerated.
                        
                        Observations:
                        - Elevated adrenaline response in simulation.
                        - Increased problem-solving speed under pressure.
                        - No sign of genetic modification. Naturally occurring traits.
                        
                        Recommendation: Subject 882 should remain under observation. Possible transfer to Bastion Facility considered but rejected due to "behavioral instability" noted in previous reports.
                        
                        Addendum (11/6): Subject exhibits unusual social withdrawal. Refuses verbal communication with staff. Suggest psychological evaluation.
                        
                        — Dr. S. Patel, Lead Researcher, Obelisk Facility
                        """);
                ((Searchable) a).addItem(c);
                roomJ.addInteractable(a);

                b = new Exit("West Exit");
                b.setTarget(roomI);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.WEST);
                b.setDesc("The door leads to another lab.");
                roomJ.addExit(b);

                b = new Exit("North Exit");
                b.setTarget(roomK);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.NORTH);
                b.setDesc("The door leads to another lab.");
                roomJ.addExit(b);

                rooms.add(roomJ);

                a = new LightSwitch("Light Switch", main.generateID());
                roomK.setDependent(a);
                roomK.addInteractable(a);
                //a = new Terminal("Computer Terminal", main.generateID(), 1);
                //roomK.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 10);
                roomK.addInteractable(a);
                roomS.addInteractable(a);

                b = new Exit("Vent Entrance");
                b.setTarget(roomS);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomK.roomObjects.getLast());
                b.setDesc("The exit leads to the vents above this room.");
                roomK.addExit(b);

                b = new Exit("South Exit");
                b.setTarget(roomJ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.SOUTH);
                b.setDesc("The door leads to another lab.");
                roomK.addExit(b);

                b = new Exit("North Exit");
                b.setTarget(roomL);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.NORTH);
                b.setDesc("The door leads to...something?");
                roomK.addExit(b);

                rooms.add(roomK);

                //RoomL - Testing Chamber - TBD
                b = new Exit("South Exit");
                b.setTarget(roomK);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.SOUTH);
                b.setDesc("The door leads to a lab");
                roomL.addExit(b);

                rooms.add(roomL);

                a = new LightSwitch("Light Switch", main.generateID());
                roomM.addInteractable(a);
                a = new OpenSearchable("Tool Bench", main.generateID());
                c = new Tool("Multitool", main.generateID());
                ((OpenSearchable) a).addItem(c);
                roomM.addInteractable(a);

                b = new Exit("South Exit");
                b.setTarget(roomN);
                b.setRequirement(ExitRequirement.HAS_KEYCARD);
                b.setCard_perm("Maintenance");
                b.setDirection(Direction.SOUTH);
                b.setDesc("The door leads to a junction.");
                roomM.addExit(b);

                rooms.add(roomM);

                a = new Searchable("Electrical Box", main.generateID());
                roomN.addInteractable(a);
                a = new OpenSearchable("Storage Shelf", main.generateID());
                c = new Document("Maintenance Log", main.generateID());
                ((Document) c).setContents("""
                        NEXCORP – OBELISK FACILITY
                        MAINTENANCE LOG — 20XX
                        Supervisor: J. Ortiz
                        
                        10/15 – Work Order OB-447
                        Location: North Wing, Corridor G
                        Issue: Light fixture flickering
                        Action: Replaced bulb. No change.
                        Notes: Likely wiring issue. Scheduled for 10/18.
                        
                        10/18 – Work Order OB-447 (follow-up)
                        Location: North Wing, Corridor G
                        Issue: Persistent flickering
                        Action: Replaced ballast. Flickering reduced.
                        Notes: Monitor for recurrence.
                        
                        10/22 – Work Order OB-451
                        Location: East Wing, Genetics Lab
                        Issue: Medical refrigerator temperature fluctuation
                        Action: Checked seals. Stabilized.
                        Notes: Recommend replacement in Q1 20XY.
                        
                        10/28 – Work Order OB-458
                        Location: West Wing, Loading Bay
                        Issue: Roll-up door grinding noise
                        Action: Lubricated track. Improved.
                        Notes: Motor assembly showing wear. Replacement ordered.
                        
                        11/1 – Work Order OB-462
                        Location: Central Hub, Security Checkpoint
                        Issue: Camera signal loss
                        Action: No hardware fault found. Possible EMI.
                        Notes: Moved camera 2 feet. Stable for 48 hours.
                        
                        11/3 – Work Order OB-466
                        Location: Maintenance Wing, Generator Room
                        Issue: Fuel gauge reading inaccurate
                        Action: Calibrated sensor. Reading normalized.
                        Notes: Fuel levels at 78%. Next delivery scheduled 11/15.
                        
                        11/5 – Work Order OB-469
                        Location: Maintenance Wing, Maintenance Junction
                        Issue: Audible hissing, pressure fluctuation
                        Action: Tightened valve assembly. Pressure normalized.
                        Notes: Gasket failing. Replacement ordered for 11/12.
                        
                        11/7 – Work Order OB-472
                        Location: Maintenance Wing, Generator Room
                        Issue: Unusual vibration during operation
                        Action: Secured mounting bolts. Vibration reduced.
                        Notes: Checked fuel line for debris. Clear. Recommend full inspection before next delivery.
                        """);
                ((OpenSearchable) a).addItem(c);
                roomN.addInteractable(a);
                a = new LockedDoor("Roll-up Door", main.generateID(), 5);
                roomN.addInteractable(a);
                roomO.addInteractable(a);
                a = new VentCover("Vent Cover", main.generateID(), 5);
                roomN.addInteractable(a);
                roomT.addInteractable(a);

                b = new Exit("Vent Entrance");
                b.setTarget(roomT);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDirection(Direction.UP);
                b.setDependent(roomN.roomObjects.getLast());
                b.setDesc("The exit leads to the vents above this room.");
                roomN.addExit(b);

                b = new Exit("North Exit");
                b.setTarget(roomM);
                b.setRequirement(ExitRequirement.HAS_KEYCARD);
                b.setCard_perm("Maintenance");
                b.setDirection(Direction.NORTH);
                b.setDesc("The door leads to the generator.");
                roomN.addExit(b);

                b = new Exit("East Exit");
                b.setTarget(roomF);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDirection(Direction.EAST);
                b.setDesc("The door leads to the main atrium.");
                roomN.addExit(b);

                b = new Exit("West Exit");
                b.setTarget(roomO);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDependent(roomO.roomObjects.getFirst());
                b.setDirection(Direction.WEST);
                b.setDesc("The roll-up door leads to the loading bay.");
                roomN.addExit(b);

                rooms.add(roomN);

                a = new Searchable("Cargo Container", main.generateID());
                c = new Document("Shipping Manifest", main.generateID());
                ((Document) c).setContents("""
                        NEXCORP SHIPPING MANIFEST - CONFIDENTIAL
                        Document No.: OB-8842-01
                        Destination: Obelisk Facility, Loading Bay
                        Shipment Date: 11/1
                        
                        Containers (5 total):
                        Container A: 8x Medical Grade Sedatives (Schedule IV)
                        Container B: 6x Industrial Solvent (Class 3 Flammable)
                        Container C: 4x Biometric Terminal Replacement Units
                        Container D: 20x Standard Issue Keycards
                        Container E: 12x Emergency Flares (Expiration: 01/20XY)
                        
                        Received by: T. Aoki
                        Notes: Container D short by 3 units. Discrepancy reported to Security.
                        
                        
                        NEXCORP SHIPPING MANIFEST - CONFIDENTIAL
                        Document No.: OB-8842-04
                        Destination: Obelisk Facility, Loading Bay
                        Shipment Date: 11/8
                        
                        Containers (4 total):
                        Container A: 4x Security Camera Replacement Units
                        Container B: 500ft Electrical Wiring (14 gauge)
                        Container C: 2x Industrial Crowbars
                        Container D: 3x Vehicle Maintenance Kits
                        
                        Received by: T. Aoki
                        Notes: Container C opened during transit. Contents intact.
                        
                        
                        NEXCORP SHIPPING MANIFEST - CONFIDENTIAL
                        Document No.: OB-8842-07
                        Destination: Obelisk Facility, Loading Bay
                        Shipment Date: 11/15 (scheduled)
                        
                        Containers:
                        Container A: 200L Diesel Fuel (Generator)
                        Container B: 10x Gasket Replacement Kits
                        Container C: 2x Motor Assembly (for Roll-up Door)
                        
                        To be received by: T. Aoki
                        Notes: Fuel delivery marked urgent.
                        """);
                ((Searchable) a).addItem(c);
                roomO.addInteractable(a);
                a = new HidingSpot("Truck", main.generateID(), 5);
                roomO.addInteractable(a);
                //a = new Terminal("Loading Terminal", main.generateID(), 1);
                //roomO.addInteractable(a);

                b = new Exit("East Exit");
                b.setTarget(roomN);
                b.setRequirement(ExitRequirement.UNLOCKED);
                b.setDependent(roomO.roomObjects.getFirst());
                b.setDirection(Direction.EAST);
                b.setDesc("The roll-up door leads to a junction.");
                roomO.addExit(b);

                rooms.add(roomO);

                //NOTE: Vent Connections should be complete
                b = new Exit("Vent Exit");
                b.setTarget(roomB);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomP.roomObjects.getFirst());
                b.setDesc("The exit leads down to Corridor M.");
                b.setDirection(Direction.DOWN);
                roomP.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomQ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.SOUTH);
                roomP.addExit(b);

                rooms.add(roomP);

                b = new Exit("Vent Exit");
                b.setTarget(roomG);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomQ.roomObjects.getFirst());
                b.setDesc("The exit leads down to Corridor M.");
                b.setDirection(Direction.DOWN);
                roomQ.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomP);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.NORTH);
                roomQ.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomR);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.SOUTH);
                roomQ.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomS);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.EAST);
                roomQ.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomT);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.WEST);
                roomQ.addExit(b);

                rooms.add(roomQ);

                b = new Exit("Vent Exit");
                b.setTarget(roomI);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomR.roomObjects.getFirst());
                b.setDesc("The exit leads down to Corridor M.");
                b.setDirection(Direction.DOWN);
                roomR.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomQ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.NORTH);
                roomR.addExit(b);

                rooms.add(roomR);

                b = new Exit("Vent Exit");
                b.setTarget(roomK);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomS.roomObjects.getFirst());
                b.setDesc("The exit leads down to Corridor M.");
                b.setDirection(Direction.DOWN);
                roomS.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomQ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.WEST);
                roomS.addExit(b);

                rooms.add(roomS);

                b = new Exit("Vent Exit");
                b.setTarget(roomN);
                b.setRequirement(ExitRequirement.VENT_OPEN);
                b.setDependent(roomT.roomObjects.getFirst());
                b.setDesc("The exit leads down to Corridor M.");
                b.setDirection(Direction.DOWN);
                roomT.addExit(b);
                b = new Exit("Vent Connection");
                b.setTarget(roomQ);
                b.setRequirement(ExitRequirement.ALWAYS_OPEN);
                b.setDesc("The exit continues down the vents.");
                b.setDirection(Direction.EAST);
                roomT.addExit(b);

                rooms.add(roomT);
            }
            default -> rooms.add(new Room("Invalid map number!"));
        }
    }
}
