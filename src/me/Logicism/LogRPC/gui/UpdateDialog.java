package me.Logicism.LogRPC.gui;

import me.Logicism.LogRPC.LogRPC;
import org.apache.commons.io.IOUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class UpdateDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JTextArea a32CHANGELOGUpdateTextArea;

    public UpdateDialog() {
        try {
            setIconImage(ImageIO.read(LogRPC.class.getClassLoader().getResourceAsStream("icon.png")));
        } catch (IOException ignored) {

        }

        setTitle("LogSM");

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

        String changelog = null;
        try {
            changelog = IOUtils.toString(new URL("https://raw.githubusercontent.com/LogicismDev/LogRPC/main/changelog"), StandardCharsets.UTF_8);
        } catch (IOException e) {

        }
        a32CHANGELOGUpdateTextArea.setText(changelog);
    }

    private void onOK() {
        File file = new File("updater/LogRPCUpdater" + (System.getProperty("os.name").startsWith("Windows") ? ".exe" : ".jar"));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Runtime.getRuntime().exec("java -jar " + file.getPath());
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        }).start();

        dispose();

        System.exit(0);
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
}
