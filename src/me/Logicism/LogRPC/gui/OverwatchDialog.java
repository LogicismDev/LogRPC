package me.Logicism.LogRPC.gui;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.gui.OverwatchMap;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class OverwatchDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;
    private JComboBox comboBox2;
    private JComboBox comboBox3;
    private JSpinner spinner1;
    private JCheckBox inQueueCheckBox;
    private JSpinner spinner2;

    private List<OverwatchMap> normalMaps = new ArrayList<>();
    private List<OverwatchMap> arcadeMaps = new ArrayList<>();

    public OverwatchDialog() {
        try {
            setIconImage(ImageIO.read(LogRPC.class.getClassLoader().getResourceAsStream("icon.png")));
        } catch (IOException ignored) {

        }

        setTitle("Overwatch 2 Presence");

        normalMaps.add(new OverwatchMap("eichenwalde", new String[]{"Hybrid", "Deathmatch", "Team Deathmatch"}, "Eichenwalde"));
        normalMaps.add(new OverwatchMap("oasis", new String[]{"Control", "Capture The Flag"}, "Oasis"));
        normalMaps.add(new OverwatchMap("lijiang", new String[]{"Control", "Capture The Flag"}, "Lijiang Tower"));
        normalMaps.add(new OverwatchMap("watchpoint-gibraltar", new String[]{"Escort"}, "Watchpoint: Gibraltar"));
        normalMaps.add(new OverwatchMap("ilios", new String[]{"Control", "Capture The Flag"}, "Ilios"));
        normalMaps.add(new OverwatchMap("dorado", new String[]{"Escort", "Deathmatch", "Team Deathmatch"}, "Dorado"));
        normalMaps.add(new OverwatchMap("kings-row", new String[]{"Hybrid", "Deathmatch", "Team Deathmatch"}, "King's Row"));
        normalMaps.add(new OverwatchMap("route-66", new String[]{"Escort"}, "Route 66"));
        normalMaps.add(new OverwatchMap("overwatch", new String[]{"Escort"}, "Circuit Royal"));
        normalMaps.add(new OverwatchMap("overwatch", new String[]{"Hybrid"}, "Midtown"));
        normalMaps.add(new OverwatchMap("overwatch", new String[]{"Push"}, "Colosseo"));
        normalMaps.add(new OverwatchMap("overwatch", new String[]{"Push"}, "New Queen Street"));
        normalMaps.add(new OverwatchMap("busan", new String[]{"Control"}, "Busan"));
        normalMaps.add(new OverwatchMap("nepal", new String[]{"Control", "Capture The Flag"}, "Nepal"));
        normalMaps.add(new OverwatchMap("havana", new String[]{"Escort"}, "Havana"));
        normalMaps.add(new OverwatchMap("junkertown", new String[]{"Escort"}, "Junkertown"));
        normalMaps.add(new OverwatchMap("rialto", new String[]{"Escort"}, "Rialto"));
        normalMaps.add(new OverwatchMap("numbani", new String[]{"Hybrid"}, "Numbani"));
        normalMaps.add(new OverwatchMap("blizzard-world", new String[]{"Hybrid"}, "Blizzard World"));
        normalMaps.add(new OverwatchMap("hollywood", new String[]{"Hybrid", "Deathmatch", "Team Deathmatch"}, "Hollywood"));
        normalMaps.add(new OverwatchMap("hanamura", new String[]{"Assault"}, "Hanamura"));
        normalMaps.add(new OverwatchMap("volskaya", new String[]{"Assault"}, "Volskaya Industries"));
        normalMaps.add(new OverwatchMap("temple-of-anubis", new String[]{"Assault"}, "Temple of Anubis"));

        arcadeMaps.add(new OverwatchMap("ayutthaya", new String[]{"Capture The Flag"}, "Ayutthaya"));
        arcadeMaps.add(new OverwatchMap("seasonal", new String[]{"Seasonal"}, "Overwatch Event"));
        arcadeMaps.add(new OverwatchMap("castillo", new String[]{"Elimination", "Team Deathmatch"}, "Castillo"));
        arcadeMaps.add(new OverwatchMap("petra", new String[]{"Deathmatch", "Team Deathmatch"}, "Petra"));
        arcadeMaps.add(new OverwatchMap("black-forest", new String[]{"Elimination", "Team Deathmatch"}, "Black Forest"));
        arcadeMaps.add(new OverwatchMap("ecopoint-antarctica", new String[]{"Elimination", "Team Deathmatch"}, "Ecopoint: Antarctica"));
        arcadeMaps.add(new OverwatchMap("necropolis", new String[]{"Elimination", "Team Deathmatch"}, "Necropolis"));
        arcadeMaps.add(new OverwatchMap("chateau", new String[]{"Deathmatch", "Team Deathmatch"}, "Château Guillard"));
        arcadeMaps.add(new OverwatchMap("overwatch", new String[]{"Deathmatch", "Team Deathmatch"}, "Kanezaka"));
        arcadeMaps.add(new OverwatchMap("overwatch", new String[]{"Deathmatch", "Team Deathmatch"}, "Malevento"));

        Collections.sort(arcadeMaps);
        Collections.sort(normalMaps);

        spinner1.setModel(new SpinnerNumberModel(LogRPC.INSTANCE.getOwRanking() != -1 ? LogRPC.INSTANCE.getOwRanking() : 0, 0, 5000, 1));
        spinner2.setModel(new SpinnerNumberModel(LogRPC.INSTANCE.getOwPartySize(), 0, 5, 1));

        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        comboBox1.addItem("Competitive");
        comboBox1.addItem("Arcade");

        DefaultComboBoxModel<OverwatchMap> dcbm = new DefaultComboBoxModel<>(new OverwatchMap[]{new OverwatchMap("overwatch", new String[]{"Idle"}, "Main Menu")});
        comboBox2.setRenderer(new OverwatchMapRenderer());
        comboBox2.setModel(dcbm);

        DefaultComboBoxModel<String> dcbm1 = new DefaultComboBoxModel<>(new String[]{"Idle"});
        comboBox3.setModel(dcbm1);

        spinner1.setEnabled(false);

            comboBox1.setSelectedItem(LogRPC.INSTANCE.getOwGamemode());

            dcbm.removeAllElements();

            if (LogRPC.INSTANCE.getOwGamemode() != null) {
                if (LogRPC.INSTANCE.getOwGamemode().equals("Main Menu")) {
                    if (!spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (!inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(true);
                    }

                    dcbm.addElement(new OverwatchMap("overwatch", new String[]{"Idle"}, "Main Menu"));
                } else if (LogRPC.INSTANCE.getOwGamemode().equals("Competitive")) {
                    if (!spinner1.isEnabled()) {
                        spinner1.setEnabled(true);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : normalMaps) {
                        dcbm.addElement(map);
                    }
                } else if (LogRPC.INSTANCE.getOwGamemode().equals("Quickplay")) {
                    if (spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : normalMaps) {
                        dcbm.addElement(map);
                    }
                } else if (LogRPC.INSTANCE.getOwGamemode().equals("Arcade")) {
                    if (spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : arcadeMaps) {
                        dcbm.addElement(map);
                    }
                    for (OverwatchMap map : normalMaps) {
                        for (String type : map.getTypes()) {
                            if (type.equals("Elimination") || type.equals("Deathmatch") || type.equals("Team Deathmatch") || type.equals("Capture the Flag")) {
                                dcbm.addElement(map);
                                break;
                            }
                        }
                    }
                } else if (LogRPC.INSTANCE.getOwGamemode().equals("Custom")) {
                    if (spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : normalMaps) {
                        dcbm.addElement(map);
                    }
                    for (OverwatchMap map : arcadeMaps) {
                        dcbm.addElement(map);
                    }
                }

                comboBox2.setSelectedItem(LogRPC.INSTANCE.getOwMap());
                comboBox3.setSelectedItem(LogRPC.INSTANCE.getOwMapType());
            } else {
                dcbm.addElement(new OverwatchMap("overwatch", new String[]{"Idle"}, "Main Menu"));
            }

            inQueueCheckBox.setSelected(LogRPC.INSTANCE.isOwInQueue());

        comboBox1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dcbm.removeAllElements();

                if (comboBox1.getSelectedItem().equals("Main Menu")) {
                    if (!spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (!inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(true);
                    }

                    dcbm.addElement(new OverwatchMap("overwatch", new String[]{"Idle"}, "Main Menu"));
                } else if (comboBox1.getSelectedItem().equals("Competitive")) {
                    if (!spinner1.isEnabled()) {
                        spinner1.setEnabled(true);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : normalMaps) {
                        dcbm.addElement(map);
                    }
                } else if (comboBox1.getSelectedItem().equals("Quickplay")) {
                    if (spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : normalMaps) {
                        dcbm.addElement(map);
                    }
                } else if (comboBox1.getSelectedItem().equals("Arcade")) {
                    if (spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : arcadeMaps) {
                        dcbm.addElement(map);
                    }
                    for (OverwatchMap map : normalMaps) {
                        for (String type : map.getTypes()) {
                            if (type.equals("Elimination") || type.equals("Deathmatch") || type.equals("Team Deathmatch") || type.equals("Capture the Flag")) {
                                dcbm.addElement(map);
                                break;
                            }
                        }
                    }
                } else if (comboBox1.getSelectedItem().equals("Custom")) {
                    if (spinner1.isEnabled()) {
                        spinner1.setEnabled(false);
                    }
                    if (inQueueCheckBox.isEnabled()) {
                        inQueueCheckBox.setEnabled(false);
                    }

                    for (OverwatchMap map : normalMaps) {
                        dcbm.addElement(map);
                    }
                        for (OverwatchMap map : arcadeMaps) {
                            dcbm.addElement(map);
                        }
                }

                pack();
            }
        });

        comboBox2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                OverwatchMap map = (OverwatchMap) comboBox2.getSelectedItem();

                if (map != null) {
                    dcbm1.removeAllElements();
                    for (String type : map.getTypes()) {
                        if ((comboBox1.getSelectedItem().equals("Competitive") || comboBox1.getSelectedItem().equals("Quickplay")) && (!type.equals("Elimination") && !type.equals("Deathmatch") && !type.equals("Team Deathmatch") && !type.equals("Capture the Flag"))) {
                            dcbm1.addElement(type);
                        } else if (comboBox1.getSelectedItem().equals("Arcade") && (type.equals("Elimination") || type.equals("Deathmatch") || type.equals("Team Deathmatch") || type.equals("Capture the Flag"))) {
                            dcbm1.addElement(type);
                        } else {
                            dcbm1.addElement(type);
                        }
                    }
                }
            }
        });

        inQueueCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBox2.setEnabled(!inQueueCheckBox.isSelected());
                comboBox3.setEnabled(!inQueueCheckBox.isSelected());
            }
        });
    }

    private void onOK() {
        try {
            LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("title", comboBox1.getSelectedItem() != "Main Menu" ? (comboBox1.getSelectedItem() + ": " + (inQueueCheckBox.isSelected() ? "In Queue" : "In Game")) : "Main Menu").put("details", "OverwatchPresence").put("largeImageKey", ((OverwatchMap) comboBox2.getSelectedItem()).getKey()).put("state", comboBox1.getSelectedItem() != "Main Menu" ? ((OverwatchMap) comboBox2.getSelectedItem()).getName() + " - " + comboBox3.getSelectedItem() : (inQueueCheckBox.isSelected() && inQueueCheckBox.isEnabled()) ? "In Queue" : "Idle").put("partySize", spinner2.getValue()).put("inQueue", inQueueCheckBox.isSelected()).put("disableTime", comboBox1.getSelectedItem() == "Main Menu").put("ranking", !comboBox1.getSelectedItem().equals("Competitive") ? -1 : spinner1.getValue()))));
        } catch (JSONException ex) {
            ex.printStackTrace();
        }
        LogRPC.INSTANCE.setOwGamemode((String) comboBox1.getSelectedItem());
        LogRPC.INSTANCE.setOwMap((OverwatchMap) comboBox2.getSelectedItem());
        LogRPC.INSTANCE.setOwMapType((String) comboBox3.getSelectedItem());
        LogRPC.INSTANCE.setOwRanking((Integer) spinner1.getValue());
        LogRPC.INSTANCE.setOwInQueue(inQueueCheckBox.isSelected());
        LogRPC.INSTANCE.setOwPartySize((Integer) spinner2.getValue());
        dispose();
    }

    private void onCancel() {
        dispose();
    }
}

class OverwatchMapRenderer extends JLabel implements ListCellRenderer<OverwatchMap> {

    public OverwatchMapRenderer() {
        setOpaque(true);
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends OverwatchMap> list, OverwatchMap map, int index, boolean isSelected, boolean cellHasFocus) {
        setText(map.getName());

        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }

        return this;
    }
}
