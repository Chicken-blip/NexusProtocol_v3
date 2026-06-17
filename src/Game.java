import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class Game extends JFrame {
    Scanner scanner;
    int turn;
    int maxTurns;
    ConsoleColor txt_color;
    Player player;
    Facility facility;
    boolean gameRunning;
    ArrayList<Integer> IDs = new ArrayList<>();
    ArrayList<Document> docs = new ArrayList<>();
    Map<Integer, String> logs = new LinkedHashMap<>();
    List<Guard> guards = new ArrayList<>();
    Room cameraAlertRoom = null;

    //JFrame Design
    JPanel cards = new JPanel(new CardLayout());
    JComboBox choices;
    JTextArea commsPage;
    List<JTextField> stats = new ArrayList<>();
    JComboBox docs_list;

    int cameraCountdown = 3;

    public Game(int maxTurns) {
        this.txt_color = ConsoleColor.BLACK;
        this.IDs.add(0);
        this.turn = 0;
        this.maxTurns = maxTurns;
        this.facility = new Facility(this, 2);
        guardMvmtSystem(2);
        this.player = new Player();
        gameRunning = false;
    }

    public void term_println(String text, JTextArea target) {
        target.setForeground(Color.WHITE);
        switch (this.txt_color) {
            case BLACK -> {
                target.setForeground(Color.BLACK);
            }
            case RED -> {
                target.setForeground(Color.RED);
            }
            case GREEN -> {
                target.setForeground(Color.GREEN);
            }
            case BLUE -> {
                target.setForeground(Color.BLUE);
            }
            case CYAN -> {
                target.setForeground(Color.CYAN);
            }
            case PURPLE -> {
                target.setForeground(Color.MAGENTA);
            }
        }
        target.append("\n" + text);
    }
    public void term_print(String text, JTextArea target) {
        target.setForeground(Color.WHITE);
        switch (this.txt_color) {
            case BLACK -> {
                target.setForeground(Color.BLACK);
            }
            case RED -> {
                target.setForeground(Color.RED);
            }
            case GREEN -> {
                target.setForeground(Color.GREEN);
            }
            case BLUE -> {
                target.setForeground(Color.BLUE);
            }
            case CYAN -> {
                target.setForeground(Color.CYAN);
            }
            case PURPLE -> {
                target.setForeground(Color.MAGENTA);
            }
        }
        target.append(text);
    }


    public int generateID() {
        int code = 0;
        while (this.IDs.contains(code)) {
            code = (int) (Math.random() * 10000);
        }
        this.IDs.add(code);
        return code;
    }


    public void setupFrame() {
        this.setPreferredSize(new Dimension(900, 550));

        cards.setPreferredSize(new Dimension(600, 350));

        JPanel menuPanel = getMenuJPanel();
        JPanel termPanel = getTermJPanel();
        JPanel commsPanel = getCommsJPanel();
        JPanel vitalPanel = getVitalJPanel();
        JPanel docPanel = getDocPanel();

        cards.add(menuPanel, "Menu View");
        cards.add(termPanel, "Terminal View");
        cards.add(commsPanel, "Comms View");
        cards.add(vitalPanel, "Vitals View");
        cards.add(docPanel, "Document View");

        this.add(cards, BorderLayout.CENTER);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(150, 150, 150)
                                .addComponent(cards, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(150, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addComponent(cards, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(100, Short.MAX_VALUE))
        );

        pack();

        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    private JPanel getDocPanel() {
        JPanel docPanel = new JPanel();

        JTextArea doc_content = new JTextArea(5, 20);
        JScrollPane scrl = new JScrollPane();
        scrl.setViewportView(doc_content);
        docs_list = new JComboBox();
        if (getDocs() != null) {
            docs_list.setModel(new DefaultComboBoxModel(getDocs()));
        }
        JLabel docs_lbl = new JLabel("Document");
        JButton btn = new JButton("View Doc");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                for (Document d : docs) {
                    if (d.name == docs_list.getSelectedItem()) {
                        doc_content.setText(d.contents);
                    }
                }
            }
        });
        JButton ext = new JButton("Exit View");
        ext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchView("Menu View");
            }
        });

        GroupLayout documentViewLayout = new GroupLayout(docPanel);
        docPanel.setLayout(documentViewLayout);
        documentViewLayout.setHorizontalGroup(
                documentViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, documentViewLayout.createSequentialGroup()
                                .addContainerGap(100, Short.MAX_VALUE)
                                .addComponent(scrl, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                .addGap(100, 100, 100))
                        .addGroup(documentViewLayout.createSequentialGroup()
                                .addGroup(documentViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(documentViewLayout.createSequentialGroup()
                                                .addGap(150, 150, 150)
                                                .addComponent(docs_lbl, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(docs_list, GroupLayout.PREFERRED_SIZE, 98, GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(btn))
                                        .addGroup(documentViewLayout.createSequentialGroup()
                                                .addGap(261, 261, 261)
                                                .addComponent(ext)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        documentViewLayout.setVerticalGroup(
                documentViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(documentViewLayout.createSequentialGroup()
                                .addGap(24, 24, 24)
                                .addGroup(documentViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(docs_list, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(docs_lbl)
                                        .addComponent(btn))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(scrl, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(ext)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        return docPanel;
    }

    private JPanel getVitalJPanel() {
        JPanel vitalPanel = new JPanel();
        JLabel hrt_lbl, str_lbl, sts_lbl, trn_lbl;
        JTextField hrt, str, sts, trn;

        str_lbl = new JLabel("Stress:");
        hrt_lbl = new JLabel("Heart Rate:");
        sts_lbl = new JLabel("Status:");
        trn_lbl = new JLabel("Turns Remaining:");

        str = new JTextField();
        str.setEditable(false);
        str.setPreferredSize(new Dimension(100, 25));

        hrt = new JTextField();
        hrt.setEditable(false);
        hrt.setPreferredSize(new Dimension(100, 25));

        sts = new JTextField();
        sts.setEditable(false);
        sts.setPreferredSize(new Dimension(100, 25));

        trn = new JTextField();
        trn.setEditable(false);
        trn.setPreferredSize(new Dimension(100, 25));

        stats.add(str);
        stats.add(hrt);
        stats.add(sts);
        stats.add(trn);

        JLabel jLabel6 = new JLabel();
        jLabel6.setFont(new Font("Segoe UI", 0, 36)); // NOI18N
        jLabel6.setHorizontalAlignment(SwingConstants.CENTER);
        jLabel6.setText("VITALS VIEW");
        jLabel6.setHorizontalTextPosition(SwingConstants.CENTER);

        JButton btn = new JButton("Exit View");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchView("Menu View");
            }
        });

        GroupLayout vitalsViewLayout = new GroupLayout(vitalPanel);
        vitalPanel.setLayout(vitalsViewLayout);
        vitalsViewLayout.setHorizontalGroup(
                vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(vitalsViewLayout.createSequentialGroup()
                                .addContainerGap(100, Short.MAX_VALUE)
                                .addGroup(vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(GroupLayout.Alignment.TRAILING, vitalsViewLayout.createSequentialGroup()
                                                .addGroup(vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                                        .addGroup(vitalsViewLayout.createSequentialGroup()
                                                                .addComponent(sts_lbl, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(sts, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(vitalsViewLayout.createSequentialGroup()
                                                                .addComponent(trn_lbl, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(trn, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(vitalsViewLayout.createSequentialGroup()
                                                                .addComponent(str_lbl, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(str, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE))
                                                        .addGroup(vitalsViewLayout.createSequentialGroup()
                                                                .addComponent(hrt_lbl, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                                .addGap(18, 18, 18)
                                                                .addComponent(hrt, GroupLayout.PREFERRED_SIZE, 174, GroupLayout.PREFERRED_SIZE)))
                                                .addGap(150, 150, 150))
                                        .addGroup(GroupLayout.Alignment.TRAILING, vitalsViewLayout.createSequentialGroup()
                                                .addComponent(jLabel6, GroupLayout.PREFERRED_SIZE, 400, GroupLayout.PREFERRED_SIZE)
                                                .addGap(100, 100, 100))
                                        .addGroup(GroupLayout.Alignment.TRAILING, vitalsViewLayout.createSequentialGroup()
                                                .addComponent(btn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                                .addGap(250, 250, 250))))
        );
        vitalsViewLayout.setVerticalGroup(
                vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(GroupLayout.Alignment.TRAILING, vitalsViewLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, 68, Short.MAX_VALUE)
                                .addGap(29, 29, 29)
                                .addGroup(vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(str_lbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(str, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(hrt_lbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(hrt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(sts_lbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(sts, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(trn_lbl, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE)
                                        .addComponent(trn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addGap(32, 32, 32)
                                .addComponent(btn)
                                .addContainerGap())
        );
        return vitalPanel;
    }

    private JPanel getCommsJPanel() {
        JPanel commsPanel = new JPanel();

        JLabel lbl = new JLabel();
        lbl.setFont(new Font("Segoe UI", 0, 36)); // NOI18N
        lbl.setHorizontalAlignment(SwingConstants.CENTER);
        lbl.setText("COMMS RECORD");

        commsPage = new JTextArea();
        JScrollPane scrl = new JScrollPane();
        commsPage.setColumns(20);
        commsPage.setRows(5);
        commsPage.setText(getCommLogs());
        commsPage.setEditable(false);
        scrl.setViewportView(commsPage);

        JButton btn = new JButton();
        btn.setText("Exit View");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switchView("Menu View");
            }
        });

        GroupLayout vitalsViewLayout = new GroupLayout(commsPanel);
        commsPanel.setLayout(vitalsViewLayout);
        vitalsViewLayout.setHorizontalGroup(
                vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(vitalsViewLayout.createSequentialGroup()
                                .addGap(100, 100, 100)
                                .addGroup(vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                                        .addComponent(lbl, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                        .addComponent(scrl))
                                .addContainerGap(100, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, vitalsViewLayout.createSequentialGroup()
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btn, GroupLayout.PREFERRED_SIZE, 100, GroupLayout.PREFERRED_SIZE)
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        vitalsViewLayout.setVerticalGroup(
                vitalsViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(vitalsViewLayout.createSequentialGroup()
                                .addComponent(lbl)
                                .addGap(18, 18, 18)
                                .addComponent(scrl, GroupLayout.PREFERRED_SIZE, 219, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn)
                                .addGap(0, 24, Short.MAX_VALUE))
        );
        return commsPanel;
    }

    private JPanel getTermJPanel() {
        JPanel termPanel = new JPanel();
        JScrollPane scrl = new JScrollPane();
        JTextArea txt = new JTextArea();
        txt.setBackground(Color.BLACK);
        txt.setForeground(Color.GREEN);
        txt.setText("> NEXUS PROTOCOL v1.07\n> CONNECTION ESTABLISHED\n> ");
        txt.setEditable(false);
        txt.setColumns(20);
        txt.setRows(5);
        scrl.setViewportView(txt);

        JTextField cmdLn = new JTextField("");
        cmdLn.setToolTipText("");

        JButton btn = new JButton("ENTER COMMAND");
        btn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String cmd = (String) cmdLn.getText();
                txt.append("\n> " + cmd);
                //TODO: Terminal parser here
                if (cmd.contains(" ")) {
                    String id;
                    //Argument-command input
                    switch (cmd.substring(0, cmd.indexOf(" ")).toLowerCase()) {
                        case "inspect" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num) && player.getFromInv(id_num) == null) {
                                    throw new ArithmeticException();
                                }
                                if (player.getFromInv(id_num) != null) {
                                    Item i = player.getFromInv(id_num);
                                    term_println("> NAME: " + i.name, txt);
                                    term_println("> IN INVENTORY", txt);
                                    switch (i) {
                                        case Document document -> term_println("> ITEM TYPE: DOCUMENT", txt);
                                        case Tool tool -> term_println("> ITEM TYPE: TOOL", txt);
                                        case Keycard keycard -> {
                                            term_println("> ITEM TYPE: KEYCARD", txt);
                                            term_println("> ALLOWANCE: " + keycard.permissions.toUpperCase(), txt);
                                        }
                                        default -> {
                                        }
                                    }
                                } else {
                                    Interactable obj = find_object(id_num);
                                    term_println("> NAME: " + obj.name, txt);
                                    term_println("> STATE: " + obj.state, txt);
                                    if (obj instanceof Keypad o) {
                                        term_println("> ENCRYPTION: [" + encode(String.valueOf(o.correctCode)) + "]", txt);
                                    }
                                    if (obj instanceof EmergencyKit kit) {
                                        term_println("> USES: " + kit.usesRemaining, txt);
                                    }
                                    if (obj instanceof WaterDispenser disp) {
                                        term_println("> USES: " + disp.usesRemaining, txt);
                                    }
                                    if (obj instanceof Bed bed) {
                                        term_println("> TURNS FOR USE: " + bed.turnCost, txt);
                                    }
                                    if (obj instanceof Window wnd) {
                                        term_println("> VISIBILITY: " + wnd.visibility, txt);
                                        term_println("> RISK: " + wnd.risk, txt);
                                    }
                                    term_println(">", txt);
                                }
                            } catch (Exception ef) {
                                term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "unlock" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    throw new ArithmeticException();
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof LockedDoor door) {
                                   term_println("> " + door.unlock(), txt);
                                } else if (obj instanceof VentCover vent) {
                                   term_println("> " + vent.open(), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a door", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "disable" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    throw new ArithmeticException();
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof SecurityCamera camera) {
                                   term_println("> " + camera.disable(), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a security camera", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "enable" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    id_num /= 0;
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof SecurityCamera camera) {
                                   term_println("> " + camera.enable(), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a security camera", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "toggle" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    throw new ArithmeticException();
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof LightSwitch swtch) {
                                   term_println("> " + swtch.toggle(), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a light switch", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "bypass" -> {
                            String code;
                            Boolean success_A = false, success_B = false;
                            String remainder = cmd.substring(cmd.indexOf(" ")).strip();
                            if (!remainder.contains(" ")) {
                               term_println("> INVALID COMMAND SYNTAX | Requires a code to bypass", txt);
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
                                if (!IDs.contains(id_num)) {
                                    success_A = false;
                                    id_num /= 0;
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof Keypad pad) {
                                   term_println("> " + pad.bypass(code_num), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a keypad", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                                try {
                                    int id_num = Integer.parseInt(code);
                                    success_B = true;
                                } catch (Exception eff) {
                                }

                            } finally {
                                if (!success_A) {
                                   term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                                } else if (!success_B) {
                                   term_println("> INVALID CODE | The code must be an integer", txt);
                                }
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "decrypt" -> {
                            String code;
                            code = cmd.substring(cmd.indexOf(" "), cmd.length()).strip();
                           term_println("> " + decode(code), txt);
                        }
                        case "access" -> {}
                        case "open" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    id_num /= 0;
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof Searchable && !(obj instanceof OpenSearchable)) {
                                   term_println("> " + ((Searchable) obj).open(), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a closed container", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "close" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    throw new ArithmeticException();
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof Searchable && !(obj instanceof OpenSearchable)) {
                                   term_println("> " + ((Searchable) obj).close(), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a closable container", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "reset" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    throw new ArithmeticException();
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof SecurityCamera camera) {
                                   term_println("> " + camera.reset(), txt);
                                   term_println("> ", txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a security camera", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "cass-go" -> {
                            String[] directions = {"north", "south", "east", "west", "up", "down"};
                            String dir = cmd.substring(cmd.indexOf(" ")).strip().toLowerCase();
                            if (Arrays.asList(directions).contains(dir)) {
                                Direction d = switch (dir) {
                                    case "north" -> Direction.NORTH;
                                    case "south" -> Direction.SOUTH;
                                    case "east" -> Direction.EAST;
                                    case "west" -> Direction.WEST;
                                    case "up" -> Direction.UP;
                                    case "down" -> Direction.DOWN;
                                    default -> null;
                                };
                                boolean exit_found = false;
                                Room original = player.currentRoom;
                                //Stop hiding
                                if (player.status == PlayerStatus.HIDING) {
                                    player.setStatus(PlayerStatus.CALM);
                                    player.setStatus();
                                    player.location = null;
                                   term_println("> NOTE: Cass is no longer hiding.", txt);
                                }
                                for (Exit ex : player.currentRoom.roomExits) {
                                    if (ex.direction == d && ex.canUseExit(player) && player.currentRoom.canActInRoom(player) && player.canAct()) {
                                        player.currentRoom = ex.targetRoom;
                                        exit_found = true;
                                    } else if (ex.direction == d) {
                                        if (!ex.canUseExit(player)) {
                                           term_println("> " + ex.useFailure(player), txt);
                                        } else if (!player.currentRoom.canActInRoom(player)) {
                                           term_println("> " + player.currentRoom.actionFail(player), txt);
                                        } else if (!player.canAct()) {
                                           term_println("> " + player.actFail(), txt);
                                        }
                                        exit_found = true;
                                    }
                                }
                                if (!exit_found) {
                                   term_println("> ERROR | No available exit in that direction", txt);
                                    useTurn("INVALID_ROOM_CHANGE", txt);
                                } else if (player.currentRoom != original) {
                                    player.setStatus(0);
                                   term_println("> ACCESS GRANTED | Changing rooms...", txt);
                                    useTurn("CHANGE_OF_ROOMS", txt);
                                } else {
                                    useTurn("INACCESSIBLE_EXIT", txt);
                                }
                            } else {
                               term_println("> INVALID DIRECTION | The direction must be one of the following: north, south, east, west", txt);
                                useTurn("DEFAULT", txt);
                            }
                        }
                        case "cass-use" -> {
                            if (!player.currentRoom.canActInRoom(player)) {
                               term_println("> " + player.currentRoom.actionFail(player), txt);
                            } else if (player.isSleeping()) {
                               term_println("> " + player.actFail(), txt);
                            } else {
                                //Stop hiding
                                if (player.status == PlayerStatus.HIDING) {
                                    player.setStatus(PlayerStatus.CALM);
                                    player.setStatus();
                                    player.location = null;
                                   term_println("> NOTE: Cass is no longer hiding.", txt);
                                }
                                id = cmd.substring(cmd.indexOf(" "));
                                try {
                                    int id_num = Integer.parseInt(id.strip());
                                    if (!IDs.contains(id_num)) {
                                        throw new ArithmeticException();
                                    }
                                    Interactable obj = find_object(id_num);
                                    boolean recovered;
                                    switch (obj) {
                                        case EmergencyKit kit -> {
                                            recovered = kit.canUse();
                                            if (recovered) {
                                               term_println("> ACTION SUCCESS | Used " + obj.name, txt);
                                                String[][] list = kit.use(player);
                                                for (String[] i : list) {
                                                    for (String x : i) {
                                                       term_println("> " + x, txt);
                                                    }
                                                }
                                            } else {
                                               term_println("> EMERGENCY KIT UNUSABLE | This emergency kit has no more uses remaining.", txt);
                                            }
                                        }
                                        case Bed bed -> {
                                           term_println("> " + bed.rest(player), txt);
                                        }
                                        case WaterDispenser water -> {
                                            recovered = water.canUse();
                                            if (recovered) {
                                               term_println("> ACTION SUCCESS | Used " + obj.name, txt);
                                                String[] list = water.use(player);
                                                for (String i : list) {
                                                   term_println("> " + i, txt);
                                                }
                                            } else {
                                               term_println("> WATER DISPENSER UNUSABLE | This water dispenser has no more uses remaining.", txt);
                                            }
                                        }
                                        case Window wnd -> wnd.peek();
                                        case null, default -> {
                                           term_println("> UNAPPLICABLE OBJECT | This command must reference an object Cass can use", txt);
                                           term_println("> ", txt);
                                        }
                                    }
                                } catch (Exception ef) {
                                   term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                                }
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "cass-hide" -> {
                            id = cmd.substring(cmd.indexOf(" "));
                            try {
                                int id_num = Integer.parseInt(id.strip());
                                if (!IDs.contains(id_num)) {
                                    throw new ArithmeticException();
                                }
                                Interactable obj = find_object(id_num);
                                if (obj instanceof HidingSpot hs) {
                                   term_println("> " + hs.hide(player), txt);
                                } else {
                                   term_println("> UNAPPLICABLE OBJECT | This command must reference a hiding spot", txt);
                                   term_println("> ", txt);
                                }
                            } catch (Exception ef) {
                               term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "cass-read" -> {
                            //FIXME: In-progress - still needs to be tested
                            if (!player.currentRoom.canActInRoom(player)) {
                                term_println("> " + player.currentRoom.actionFail(player), txt);
                            } else if (player.isSleeping()) {
                                term_println("> " + player.actFail(), txt);
                            } else {
                                //Stop hiding
                                if (player.status == PlayerStatus.HIDING) {
                                    player.setStatus(PlayerStatus.CALM);
                                    player.setStatus();
                                    player.location = null;
                                    term_println("> NOTE: Cass is no longer hiding.", txt);
                                }
                                id = cmd.substring(cmd.indexOf(" "));
                                try {
                                    int id_num = Integer.parseInt(id.strip());
                                    if (!IDs.contains(id_num)) {
                                        throw new ArithmeticException();
                                    }
                                    Item obj = player.getFromInv(id_num);
                                    if (obj instanceof Document d) {
                                        if (d.read) {
                                            term_println("> ERROR | Document has already been read", txt);
                                        } else {
                                            docs.add(d);
                                            d.read = true;
                                            term_println("> ACTION SUCCESS | Document info has been stored - check in Documents View", txt);
                                        }
                                    } else {
                                        term_println("> UNAPPLICABLE OBJECT | This command must reference a document", txt);
                                        term_println("> ", txt);
                                    }
                                } catch (Exception ef) {
                                    term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                                }
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "cass-search" -> {
                            //FIXME: In-progress - still needs to be tested
                            if (!player.currentRoom.canActInRoom(player)) {
                                term_println("> " + player.currentRoom.actionFail(player), txt);
                            } else if (player.isSleeping()) {
                                term_println("> " + player.actFail(), txt);
                            } else {
                                //Stop hiding
                                if (player.status == PlayerStatus.HIDING) {
                                    player.setStatus(PlayerStatus.CALM);
                                    player.setStatus();
                                    player.location = null;
                                    term_println("> NOTE: Cass is no longer hiding.", txt);
                                }
                                id = cmd.substring(cmd.indexOf(" "));
                                try {
                                    int id_num = Integer.parseInt(id.strip());
                                    if (!IDs.contains(id_num)) {
                                        throw new ArithmeticException();
                                    }
                                    Interactable obj = find_object(id_num);
                                    if (obj instanceof Searchable s) {
                                        if (s.contents.isEmpty()) {
                                            term_println("> ERROR | " + s.name + " is empty", txt);
                                        } else {
                                            term_println(">\n> " + s.name.toUpperCase() + " CONTENTS: ", txt);
                                            for (Item i : s.contents) {
                                                term_println("> ID: " + i.id, txt);
                                            }
                                            term_println(">", txt);
                                        }
                                    } else {
                                        term_println("> UNAPPLICABLE OBJECT | This command must reference a searchable object", txt);
                                        term_println("> ", txt);
                                    }
                                } catch (Exception ef) {
                                    term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                                }
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "cass-take" -> {
                            //FIXME: In-progress - still needs to be tested
                            if (!player.currentRoom.canActInRoom(player)) {
                                term_println("> " + player.currentRoom.actionFail(player), txt);
                            } else if (player.isSleeping()) {
                                term_println("> " + player.actFail(), txt);
                            } else {
                                //Stop hiding
                                if (player.status == PlayerStatus.HIDING) {
                                    player.setStatus(PlayerStatus.CALM);
                                    player.setStatus();
                                    player.location = null;
                                    term_println("> NOTE: Cass is no longer hiding.", txt);
                                }
                                String rem;
                                int con_id = 0, itm_id = 0;
                                Boolean success_A = false, success_B = false;
                                String remainder = cmd.substring(cmd.indexOf(" ")).strip();
                                if (!remainder.contains(" ")) {
                                    term_println("> INVALID COMMAND SYNTAX | Requires both the ID of the container and the ID of the item to take", txt);
                                    break;
                                } else {
                                    id = remainder.substring(0, remainder.indexOf(" "));
                                    rem = remainder.substring(remainder.indexOf(" "));
                                }
                                try {
                                    con_id = Integer.parseInt(id.strip());
                                } catch (Exception ef) {
                                    term_println("> INVALID OBJECT ID | You must put in a valid object id", txt);
                                }
                                try {
                                    itm_id = Integer.parseInt(rem.strip());
                                } catch (Exception ef) {
                                    term_println(" INVALID OBJECT ID | You must put in a valid object id", txt);
                                }
                                if (!IDs.contains(con_id)) {
                                    term_println("> INVALID OBJECT ID | You must put in a valid object id", txt);
                                }
                                Interactable obj = find_object(con_id);
                                if (obj == null) {
                                    term_println("> INVALID OBJECT ID | You must put in a valid object id", txt);
                                } else if (obj instanceof Searchable s) {
                                    if (((Searchable) obj).getItem(itm_id) == null) {
                                        term_println("> ERROR | The item doesn't exist in this object", txt);
                                    } else {
                                        player.addToInv(((Searchable) obj).getItem(itm_id));
                                        term_println("> ACTION SUCCESS | Item added to inventory", txt);
                                    }
                                } else {
                                    term_println("> UNAPPLICABLE OBJECT | This command must reference a searchable object", txt);
                                    term_println("> ", txt);
                                }
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "cass-equip" -> {
                            if (!player.currentRoom.canActInRoom(player)) {
                                term_println("> " + player.currentRoom.actionFail(player), txt);
                            } else if (player.isSleeping()) {
                                term_println("> " + player.actFail(), txt);
                            } else {
                                //Stop hiding
                                if (player.status == PlayerStatus.HIDING) {
                                    player.setStatus(PlayerStatus.CALM);
                                    player.setStatus();
                                    player.location = null;
                                    term_println("> NOTE: Cass is no longer hiding.", txt);
                                }
                                id = cmd.substring(cmd.indexOf(" "));
                                try {
                                    int id_num = Integer.parseInt(id.strip());
                                    if (!IDs.contains(id_num) && player.getFromInv(id_num) == null) {
                                        throw new ArithmeticException();
                                    }
                                    if (player.getFromInv(id_num) != null) {
                                        Item i = player.getFromInv(id_num);
                                        term_println("> " + player.equip(i), txt);
                                    } else {
                                        Interactable obj = find_object(id_num);
                                        term_println("> NAME: " + obj.name, txt);
                                        term_println("> STATE: " + obj.state, txt);
                                        if (obj instanceof Keypad o) {
                                            term_println("> ENCRYPTION: [" + encode(String.valueOf(o.correctCode)) + "]", txt);
                                        }
                                        if (obj instanceof EmergencyKit kit) {
                                            term_println("> USES: " + kit.usesRemaining, txt);
                                        }
                                        if (obj instanceof WaterDispenser disp) {
                                            term_println("> USES: " + disp.usesRemaining, txt);
                                        }
                                        if (obj instanceof Bed bed) {
                                            term_println("> TURNS FOR USE: " + bed.turnCost, txt);
                                        }
                                        if (obj instanceof Window wnd) {
                                            term_println("> VISIBILITY: " + wnd.visibility, txt);
                                            term_println("> RISK: " + wnd.risk, txt);
                                        }
                                        term_println(">", txt);
                                    }
                                } catch (Exception ef) {
                                    term_println("> INVALID OBJECT ID | You must put in a valid object ID", txt);
                                }
                            }
                        }
                        case "cass-interact" -> {}
                        default -> {
                            term_println("> INVALID COMMAND | Type in 'help' to view a list of commands.", txt);
                            useTurn("DEFAULT", txt);
                        }
                    }
                } else {
                    //No-argument command input
                    switch (cmd.toLowerCase()) {
                        case "inventory" -> {
                            if (player.inventory.isEmpty()) {
                               term_println("> ERROR | The inventory is empty", txt);
                            } else {
                               term_println("> INVENTORY CONTENTS:", txt);
                                for (Item i : player.inventory) {
                                   term_println("> ID: " + i.id, txt);
                                }
                            }
                            term_println("> ", txt);
                            useTurn("DEFAULT", txt);
                        }
                        case "cass-unequip" -> {
                            term_println("> " + player.unequip(), txt);
                        }
                        case "scan" -> {
                           term_println("> Room: " + player.currentRoom.name, txt);
                           term_println("> ", txt);

                           term_println("> OBJECTS: ", txt);
                            for (Interactable x : player.currentRoom.roomObjects) {
                               term_println("> ID: " + x.id, txt);
                            }

                           term_println(">", txt);
                           term_println("> EXITS: ", txt);
                            for (Exit x : player.currentRoom.roomExits) {
                               term_println("> NAME: " + x.name, txt);
                               term_println("> DIRECTION: " + x.direction, txt);
                               term_println("> DESTINATION: " + x.targetRoom.name, txt);
                               term_println("> ", txt);
                            }
                            useTurn("DEFAULT", txt);
                        }
                        case "quit" -> {
                           term_println("> EXITING TERMINAL VIEW", txt);
                           switchView("Menu View");
                           useTurn("DEFAULT", txt);
                        }
                        case "look", "inspect", "enable", "unlock", "disable", "toggle", "bypass", "decrypt", "reset", "cass-use",
                             "cass-hide", "cass-go", "cass-search", "cass-take", "cass-read", "cass-equip", "cass-interact" -> {
                           term_println("> INCORRECT COMMAND SYNTAX | This command requires at least 1 argument.", txt);
                            useTurn("DEFAULT", txt);
                        }
                        case "cass" -> {
                           term_println("> INVALID COMMAND | Must be followed by dash and desired action. (cass-[action])", txt);
                            useTurn("DEFAULT", txt);
                        }
                        case "time" ->term_println("> TURNS REMAINING: " + (maxTurns - turn), txt);
                        case "help" -> {
                           term_println("> ", txt);
                           term_println("> HELP | List all commands and their descriptions", txt);
                           term_println("> QUIT | Exit Terminal View", txt);
                           term_println("> TIME | Lists the number of remaining turns", txt);
                           term_println("> SCAN | Lists the room, the objects inside (only IDs) and exits of said room", txt);
                           term_println(">", txt);
                           term_println("> INSPECT [OBJECT ID] | Lists the name, description, and current state of an object", txt);
                           term_println("> UNLOCK [OBJECT ID] | Unlocks a locked door if it is locked", txt);
                           term_println("> DISABLE [OBJECT ID] | Disables a security camera if it is enabled", txt);
                           term_println("> ENABLE [OBJECT ID] | Enables a security camera if it is disabled", txt);
                           term_println("> TOGGLE [OBJECT ID] | Toggles a light switch (i.e: turns off the light if it is on, and vice versa)", txt);
                           term_println("> BYPASS [OBJECT ID] [ENTERED CODE] | Attempts to bypass a keypad with the entered code", txt);
                           term_println("> DECRYPT [ENTERED CODE] | Decrypts an encrypted code from a Keypad", txt);
                           term_println("> RESET [OBJECT ID] | Resets a security camera, removing any active alerts", txt);
                           term_println("> OPEN [OBJECT ID] | Open a closed container", txt);
                           term_println("> CLOSE [OBJECT ID] | CLose an open container, if applicable", txt);
                           term_println("> ", txt);
                           term_println("> CASS-GO [DIRECTION] | Move Cass to the connected location in the given direction", txt);
                           term_println("> CASS-USE [OBJECT ID] | Instruct Cass to use the object according to its functionality", txt);
                           term_println("> CASS-HIDE [OBJECT ID] | Instruct Cass to hide using a HidingSpot", txt);
                           term_println("> CASS-SEARCH [OBJECT ID] | Instruct Cass to look through a container", txt);
                           term_println("> CASS-TAKE [OBJECT_A ID] [OBJECT_B ID] | Instruct Cass to get Object_B, an item, from Object_A, a container", txt);
                           term_println("> CASS-READ [DOCUMENT ID] | Instruct Cass to read the Document, sending its contents to Document View", txt);
                           term_println("> ", txt);
                           useTurn("DEFAULT", txt);
                        }
                        default -> {
                           term_println("> INVALID COMMAND | Type in 'help' to view a list of commands.", txt);
                            useTurn("DEFAULT", txt);
                        }
                    }
                }
                cmdLn.setText("");
                txt.setCaretPosition(txt.getText().length());
                term_println(">", txt);
            }
        });

        GroupLayout terminalViewLayout = new GroupLayout(termPanel);
        termPanel.setLayout(terminalViewLayout);
        terminalViewLayout.setHorizontalGroup(
                terminalViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(terminalViewLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addGroup(terminalViewLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(scrl, GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE)
                                        .addComponent(cmdLn))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(GroupLayout.Alignment.TRAILING, terminalViewLayout.createSequentialGroup()
                                .addContainerGap(243, Short.MAX_VALUE)
                                .addComponent(btn)
                                .addGap(241, 241, 241))
        );
        terminalViewLayout.setVerticalGroup(
                terminalViewLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(terminalViewLayout.createSequentialGroup()
                                .addGap(17, 17, 17)
                                .addComponent(scrl, GroupLayout.PREFERRED_SIZE, 152, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(cmdLn, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn)
                                .addContainerGap(100, Short.MAX_VALUE))
        );
        return termPanel;
    }
    private JPanel getMenuJPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setPreferredSize(new Dimension(600, 350));

        JTextArea txt = new JTextArea("1. Terminal View\n" +
                "2. Vitals View\n" +
                "3. Comms View\n" +
                "4. Document View\n\n" +
                "Select a view to switch to");
        txt.setEditable(false);
        txt.setColumns(20);
        txt.setRows(5);
        txt.setPreferredSize(new Dimension(250, 100));

        JScrollPane scrlA = new JScrollPane();
        scrlA.setViewportView(txt);

        choices = new JComboBox(new String[] {"Terminal View", "Vitals View", "Comms View", "Document View", "Quit"});

        JButton btnA = new JButton("Switch to View");

        btnA.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selection = (String) choices.getSelectedItem();
                switch (selection) {
                    case "Terminal View" -> {
                        txt_color = ConsoleColor.GREEN;
                        switchView(selection);
                    }
                    case "Comms View" -> {
                        commsPage.setText(getCommLogs());
                        switchView(selection);
                    }
                    case "Vitals View" -> {
                        stats.get(0).setText(String.valueOf(player.stress) + " " + player.stressDesc());
                        stats.get(1).setText(player.heartRate + " BPM");
                        stats.get(2).setText(player.getStatus());
                        stats.get(3).setText(turn + "/" + maxTurns);
                        switchView(selection);
                    }
                    case "Document View" -> {
                        if (getDocs() == null) {
                            JOptionPane.showMessageDialog(null, "You don't have any documents to view!", "ERROR", JOptionPane.ERROR_MESSAGE);
                        } else {
                            docs_list.setModel(new DefaultComboBoxModel(getDocs()));
                            switchView(selection);
                        }
                    }
                    case "Quit" -> {
                        term_println("Game over!",txt );
                        System.exit(0);
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + selection);
                }
            }
        });
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(900, 550));

        cards.setPreferredSize(new Dimension(600, 350));
        cards.setLayout(new CardLayout());

        GroupLayout menuPanelLayout = new GroupLayout(menuPanel);
        menuPanel.setLayout(menuPanelLayout);
        menuPanelLayout.setHorizontalGroup(
                menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(menuPanelLayout.createSequentialGroup()
                                .addContainerGap(150, Short.MAX_VALUE)
                                .addComponent(scrlA, GroupLayout.PREFERRED_SIZE, 300, GroupLayout.PREFERRED_SIZE)
                                .addGap(150, 150, 150))
                        .addGroup(menuPanelLayout.createSequentialGroup()
                                .addGroup(menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                                        .addGroup(menuPanelLayout.createSequentialGroup()
                                                .addGap(225, 225, 225)
                                                .addComponent(choices, GroupLayout.PREFERRED_SIZE, 150, GroupLayout.PREFERRED_SIZE))
                                        .addGroup(menuPanelLayout.createSequentialGroup()
                                                .addGap(237, 237, 237)
                                                .addComponent(btnA)))
                                .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        menuPanelLayout.setVerticalGroup(
                menuPanelLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addGroup(menuPanelLayout.createSequentialGroup()
                                .addGap(50, 50, 50)
                                .addComponent(scrlA, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(choices, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addGap(20, 20, 20)
                                .addComponent(btnA)
                                .addContainerGap(100, Short.MAX_VALUE))
        );
        return menuPanel;
    }

    public void switchView(String view) {
        CardLayout cl = (CardLayout) cards.getLayout();
        cl.show(cards, view);
    }

    public void runGame() {
        setupFrame();
        switchView("Menu View");
        this.setVisible(true);
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

    public void guardMvmtSystem(int mapType) {
        //TODO: Navigate guards on regular patrol, if implemented
        switch (mapType) {
            case 0 -> {}
            case 1 -> {
                //One guard, goes through Corridor to Office
                Guard g1 = new Guard();
                g1.rm = facility.rooms.get(1);
                g1.pattern = new HashMap<>();
                g1.pattern.put(1, facility.rooms.get(1));
                g1.pattern.put(2, facility.rooms.get(1));
                g1.pattern.put(3, facility.rooms.get(1));
                g1.pattern.put(4, facility.rooms.get(5));
                g1.pattern.put(5, facility.rooms.get(5));
                g1.pattern.put(6, facility.rooms.get(7));
                g1.pattern.put(7, facility.rooms.get(7));
                g1.pattern.put(8, facility.rooms.get(7));
                g1.pattern.put(9, facility.rooms.get(7));
                g1.pattern.put(10, facility.rooms.get(7));
                g1.pattern.put(11, facility.rooms.get(5));
                g1.pattern.put(12, facility.rooms.get(5));
                this.guards.add(g1);
            }
            case 2 -> {
                //Two guards - one going from checkpoint to corridor, one from generator to employee lounge,
                Guard g2 = new Guard();
                g2.rm = facility.rooms.get(4);
                g2.pattern = new HashMap<>();
                g2.pattern.put(1, facility.rooms.get(4));
                g2.pattern.put(2, facility.rooms.get(4));
                g2.pattern.put(3, facility.rooms.get(4));
                g2.pattern.put(4, facility.rooms.get(4));
                g2.pattern.put(5, facility.rooms.get(3));
                g2.pattern.put(6, facility.rooms.get(3));
                g2.pattern.put(7, facility.rooms.get(3));
                g2.pattern.put(8, facility.rooms.get(3));
                this.guards.add(g2);
                Guard g3 = new Guard();
                g3.rm = facility.rooms.get(12);
                g3.pattern = new HashMap<>();
                g3.pattern.put(1, facility.rooms.get(12));
                g3.pattern.put(2, facility.rooms.get(12));
                g3.pattern.put(3, facility.rooms.get(12));
                g3.pattern.put(4, facility.rooms.get(13));
                g3.pattern.put(5, facility.rooms.get(13));
                g3.pattern.put(6, facility.rooms.get(5));
                g3.pattern.put(7, facility.rooms.get(5));
                g3.pattern.put(8, facility.rooms.get(6));
                g3.pattern.put(9, facility.rooms.get(6));
                g3.pattern.put(10, facility.rooms.get(5));
                g3.pattern.put(11, facility.rooms.get(5));
                g3.pattern.put(12, facility.rooms.get(13));
                this.guards.add(g3);
            }
        }
    }

    public String getCommLogs() {
        StringBuilder txt = new StringBuilder();
        for (Integer index : logs.keySet()) {
            txt.append("[Turn ").append(index).append("] CASS: ").append(logs.get(index)).append("\n");
        }
        return txt.toString();
    }

    public Object[] getDocs() {
        List<String> d_names = new ArrayList<>();
        for (Document d : docs) {
            d_names.add(d.name);
        }
        if (d_names.isEmpty()) {
            return null;
        }
        return d_names.toArray();
    }

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

    public void useTurn(Object source, Component target) {
        if (player.bed != null) {
            player.bed.turnCount++;
            player.stress -= (int) (Math.random() * player.bed.restEfficiency) + 10;
            if (player.stress < 0) {
                player.stress = 0;
            }
        }
        updateLog(source);
        moveGuards();
        checkSecurity(target);
        player.setStatus();
        this.turn ++;
        if (this.turn >= this.maxTurns) {
            txt_color = ConsoleColor.DEFAULT;
            JOptionPane.showMessageDialog(null, "TURNS MAXED OUT - GAME OVER!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public boolean cameraAlerted() {
        for (Room r : facility.rooms) {
            for (Interactable i : r.roomObjects) {
                if (i instanceof SecurityCamera) {
                    if (Objects.equals(i.state, "ALERTED")) {
                        cameraCountdown--;
                        cameraAlertRoom = r;
                        return true;
                    }
                }
            }
        }
        cameraCountdown = 10;
        cameraAlertRoom = null;
        return false;
    }

    public void moveGuards() {
        for (Guard g : guards) {
            g.turn += 1;
            if (g.turn > g.pattern.size()) {
                g.turn = 1;
            }
            g.rm = g.pattern.get(g.turn);
        }
    }

    public void checkSecurity(Component target) {
        if (cameraAlerted()) {
            if (cameraCountdown > 0 && player.currentRoom == cameraAlertRoom) {
                if (player.currentView == PlayerView.TERMINAL_VIEW) {
                    term_println("> ALERT | Security Camera alerted! A Guard will be sent in " + this.cameraCountdown + " turn(s)! Reset the alerted camera!", (JTextArea) target);
                } else {
                    term_println("ALERT | Security Camera alerted! A Guard will be sent in " + this.cameraCountdown + " turn(s)! Reset the alerted camera!", (JTextArea) target);
                }
            }
        }
        for (Guard g : guards) {
            if (g.rm == player.currentRoom && !player.isHiding()) {
                if (g.alerted) {
                    txt_color = ConsoleColor.DEFAULT;
                    JOptionPane.showMessageDialog(null, "CAPTURED BY GUARD - GAME OVER", "GAME OVER", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } else {
                    g.alerted = true;
                        if (player.currentView == PlayerView.TERMINAL_VIEW) {
                            term_println("> ALERT | Spotted by guard! Imminent capture in 1 turn!", (JTextArea) target);
                        } else {
                            term_println("ALERT | Spotted by guard! Imminent capture in 1 turn!", (JTextArea) target);
                        }

                }
            } else {
                g.alerted = false;
            }
        }
        for (Room r : facility.rooms) {
            for (Interactable i : r.roomObjects) {
                if (i instanceof SecurityCamera) {
                    if (player.currentRoom == r && !player.isHiding() && !Objects.equals(i.state, "INACTIVE")) {
                        i.state = "ALERTED";
                    }
                }
            }
        }
    }

    public void gameLoop() {
        while (this.gameRunning && this.turn < this.maxTurns) {
            switch (player.currentView) {
                case DEFAULT_VIEW:
                    System.out.println();
                    this.txt_color = ConsoleColor.WHITE;
                    String input = scanner.nextLine();
                    try {
                        int view = Integer.parseInt(input);
                        if (view > 4 || view < 0) {
                            view /= 0;
                        }
                        switch (view) {
                            case 0:
                                System.out.println("Quitting game....");
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
                            case 4:
                                player.currentView = PlayerView.DOCUMENT_VIEW;
                                break;
                        }
                    } catch (Exception e) {
                        System.out.println("Invalid input! Type in a number between 1 and 3.");
                    }
                    continue;
                default:
                    clearScreen();
                    System.out.println();
                    System.exit(1);
            }
            if (this.turn >= this.maxTurns) {
                txt_color = ConsoleColor.DEFAULT;
                JOptionPane.showMessageDialog(null, "TURNS MAXED OUT - GAME OVER!", "GAME OVER", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }
}
