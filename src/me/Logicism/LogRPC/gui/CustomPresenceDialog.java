package me.Logicism.LogRPC.gui;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import me.Logicism.LogRPC.presence.manual.CustomizablePresence;
import org.json.JSONException;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.IOException;

public class CustomPresenceDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextField applicationIDTextField;
    private JTextField detailsTextField;
    private JTextField stateTextField;
    private JTextField largeImageKeyTextField;
    private JTextField largeImageTextTextField;
    private JTextField smallImageKeyTextField;
    private JTextField smallImageTextTextField;
    private JCheckBox enableMainButtonCheckBox;
    private JTextField secondaryButtonTextTextField;
    private JTextField mainButtonURLTextField;
    private JLabel mainButtonTextLabel;
    private JLabel mainButtonURLLabel;
    private JLabel secondaryButtonTextLabel;
    private JLabel secondaryButtonURLLabel;
    private JCheckBox enableSecondaryButtonCheckBox;
    private JTextField mainButtonTextTextField;
    private JTextField secondaryButtonURLTextField;
    private JSpinner partySizeSpinner;
    private JSpinner maxPartySizeSpinner;
    private JSpinner startTimestampSpinner;
    private JSpinner endTimestampSpinner;
    private JComboBox activityTypeComboBox;

    public CustomPresenceDialog() {
        try {
            setIconImage(ImageIO.read(LogRPC.class.getClassLoader().getResourceAsStream("icon.png")));
        } catch (IOException ignored) {

        }

        setTitle("Set Custom Presence");
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

        applicationIDTextField.setText(String.valueOf(LogRPC.INSTANCE.getClientID()));
        try {
            activityTypeComboBox.setSelectedItem(ActivityType.from(LogRPC.INSTANCE.getPresence().build().toJson().getInt("type")).toString());
            detailsTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getString("details"));
            if ((LogRPC.INSTANCE.getPresence().build().toJson().has("state"))) {
                stateTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getString("state"));
            }
            if (LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("assets").has("large_image")) {
                largeImageKeyTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("assets").getString("large_image"));
                largeImageTextTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("assets").getString("large_text"));
            }
            if (LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("assets").has("small_image")) {
                smallImageKeyTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("assets").getString("small_image"));
                smallImageTextTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("assets").getString("small_text"));
            }
            if (LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("timestamps").has("start")) {
                startTimestampSpinner.setValue(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("timestamps").getLong("start"));
            } else {
                startTimestampSpinner.setValue(-1);
            }
            if (LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("timestamps").has("end")) {
                endTimestampSpinner.setValue(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("timestamps").getLong("end"));
            } else {
                endTimestampSpinner.setValue(-1);
            }
            if (LogRPC.INSTANCE.getPresence().build().toJson().has("buttons")) {
                mainButtonTextLabel.setEnabled(true);
                mainButtonTextTextField.setEnabled(true);
                mainButtonURLLabel.setEnabled(true);
                mainButtonURLTextField.setEnabled(true);
                enableMainButtonCheckBox.setSelected(true);
                mainButtonTextTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONArray("buttons").getJSONObject(0).getString("label"));
                mainButtonURLTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONArray("buttons").getJSONObject(0).getString("url"));

                if (LogRPC.INSTANCE.getPresence().build().toJson().getJSONArray("buttons").length() == 2) {
                    secondaryButtonTextLabel.setEnabled(true);
                    secondaryButtonTextTextField.setEnabled(true);
                    secondaryButtonURLLabel.setEnabled(true);
                    secondaryButtonURLTextField.setEnabled(true);
                    enableSecondaryButtonCheckBox.setSelected(true);
                    secondaryButtonTextTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONArray("buttons").getJSONObject(1).getString("label"));
                    secondaryButtonURLTextField.setText(LogRPC.INSTANCE.getPresence().build().toJson().getJSONArray("buttons").getJSONObject(1).getString("url"));
                }
            }
            if (LogRPC.INSTANCE.getPresence().build().toJson().has("party")) {
                partySizeSpinner.setValue(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("party").getJSONArray("size").getInt(0));
                maxPartySizeSpinner.setValue(LogRPC.INSTANCE.getPresence().build().toJson().getJSONObject("party").getJSONArray("size").getInt(1));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        enableMainButtonCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainButtonTextLabel.setEnabled(enableMainButtonCheckBox.isSelected());
                mainButtonTextTextField.setEnabled(enableMainButtonCheckBox.isSelected());
                mainButtonURLLabel.setEnabled(enableMainButtonCheckBox.isSelected());
                mainButtonURLTextField.setEnabled(enableMainButtonCheckBox.isSelected());
            }
        });

        enableSecondaryButtonCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                secondaryButtonTextLabel.setEnabled(enableSecondaryButtonCheckBox.isSelected());
                secondaryButtonTextTextField.setEnabled(enableSecondaryButtonCheckBox.isSelected());
                secondaryButtonURLLabel.setEnabled(enableSecondaryButtonCheckBox.isSelected());
                secondaryButtonURLTextField.setEnabled(enableSecondaryButtonCheckBox.isSelected());
            }
        });
    }

    private void onOK() {
        if (!applicationIDTextField.getText().isEmpty() || !detailsTextField.getText().isEmpty()) {
            if (Long.parseLong(String.valueOf(startTimestampSpinner.getValue())) < -1) {
                startTimestampSpinner.setValue(-1);
            }
            if (Long.parseLong(String.valueOf(endTimestampSpinner.getValue())) < -1) {
                endTimestampSpinner.setValue(-1);
            }
            if (Integer.parseInt(String.valueOf(maxPartySizeSpinner.getValue())) < 0) {
                maxPartySizeSpinner.setValue(0);
            }
            if (Integer.parseInt(String.valueOf(partySizeSpinner.getValue())) < 0) {
                partySizeSpinner.setValue(0);
            } else if (Integer.parseInt(String.valueOf(partySizeSpinner.getValue())) > Integer.parseInt(String.valueOf(maxPartySizeSpinner.getValue()))) {
                partySizeSpinner.setValue(maxPartySizeSpinner.getValue());
            }

            LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new CustomizablePresence(Long.parseLong(applicationIDTextField.getText()), ActivityType.valueOf((String) activityTypeComboBox.getSelectedItem()), detailsTextField.getText(), stateTextField.getText(), largeImageKeyTextField.getText(), largeImageTextTextField.getText(), smallImageKeyTextField.getText(), smallImageTextTextField.getText(), enableMainButtonCheckBox.isSelected() ? mainButtonTextTextField.getText() : "", enableMainButtonCheckBox.isSelected() ? mainButtonURLTextField.getText() : "", enableSecondaryButtonCheckBox.isSelected() ? secondaryButtonTextTextField.getText() : "", enableSecondaryButtonCheckBox.isSelected() ? secondaryButtonURLTextField.getText() : "", Long.parseLong(String.valueOf(startTimestampSpinner.getValue())), Long.parseLong(String.valueOf(endTimestampSpinner.getValue())), Integer.parseInt(String.valueOf(partySizeSpinner.getValue())), Integer.parseInt(String.valueOf(maxPartySizeSpinner.getValue())))));
            dispose();
        }
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
