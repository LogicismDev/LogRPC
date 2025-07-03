package me.Logicism.LogRPC.gui;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.gui.AutoCompletion;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class GameSearchDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox comboBox1;

    private String platform;

    public GameSearchDialog(String platform) {
        try {
            setIconImage(ImageIO.read(LogRPC.class.getClassLoader().getResourceAsStream("icon.png")));
        } catch (IOException ignored) {

        }

        setTitle("Game Search: " + platform);

        this.platform = platform;

        if (platform.equals("Nintendo 3DS")) {
            for (String game : LogRPC.INSTANCE.getNintendo3dsGames()) {
                comboBox1.addItem(game.split(",")[4]);
            }
        } else if (platform.equals("Wii U")) {
            for (String game : LogRPC.INSTANCE.getWiiUGames()) {
                comboBox1.addItem(game.split(",")[4]);
            }
        } else if (platform.equals("Nintendo Switch")) {
            for (String game : LogRPC.INSTANCE.getNintendoSwitchGames()) {
                if (game.contains("\"")) {
                    comboBox1.addItem(game.split("\"")[1]);
                } else {
                    comboBox1.addItem(game.split(",")[4]);
                }
            }
        } else if (platform.equals("Nintendo Switch 2")) {
            for (String game : LogRPC.INSTANCE.getNintendoSwitch2Games()) {
                if (game.contains("\"")) {
                    comboBox1.addItem(game.split("\"")[1]);
                } else {
                    comboBox1.addItem(game.split(",")[4]);
                }
            }
        }

        AutoCompletion.enable(comboBox1);

        setContentPane(contentPane);
        setModal(true);
        setLocationRelativeTo(null);
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
    }

    private void onOK() {
        if (platform.equals("Nintendo 3DS")) {
            try {
                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("title", comboBox1.getSelectedItem()).put("details", "Nintendo3DSPresence").put("largeImageKey", LogRPC.INSTANCE.getNintendo3dsGames().get(comboBox1.getSelectedIndex()).split(",")[0]))));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else if (platform.equals("Wii U")) {
            try {
                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("title", comboBox1.getSelectedItem()).put("details", "WiiUPresence").put("largeImageKey", LogRPC.INSTANCE.getWiiUGames().get(comboBox1.getSelectedIndex()).split(",")[0]))));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else if (platform.equals("Nintendo Switch")) {
            try {
                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("title", comboBox1.getSelectedItem()).put("details", "NintendoSwitchPresence").put("largeImageKey", LogRPC.INSTANCE.getNintendoSwitchGames().get(comboBox1.getSelectedIndex()).split(",")[0]))));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        } else if (platform.equals("Nintendo Switch 2")) {
            try {
                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("title", comboBox1.getSelectedItem()).put("details", "NintendoSwitch2Presence").put("largeImageKey", LogRPC.INSTANCE.getNintendoSwitch2Games().get(comboBox1.getSelectedIndex()).split(",")[0]))));
            } catch (JSONException ex) {
                ex.printStackTrace();
            }
        }
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
