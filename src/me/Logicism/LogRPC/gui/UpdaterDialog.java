package me.Logicism.LogRPC.gui;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.network.BrowserClient;
import me.Logicism.LogRPC.network.BrowserData;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class UpdaterDialog extends JDialog {
    private JPanel contentPane;
    private JProgressBar progressBar1;

    public UpdaterDialog() {
        try {
            setIconImage(ImageIO.read(LogRPC.class.getClassLoader().getResourceAsStream("icon.png")));
        } catch (IOException ignored) {

        }

        setTitle("LogRPC");

        setContentPane(contentPane);
        setModal(true);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    BrowserData bd = BrowserClient.executeGETRequest(new URL("https://logicism.tv/downloads/LogRPCUpdater" + (System.getProperty("os.name").startsWith("Windows") ? ".exe" : ".jar")), null);

                    int resCode = bd.getResponseCode();

                    if (resCode == 200) {
                        long fileSize = bd.getResponseLength();
                        long downloadedFileSize = 0;

                        BufferedInputStream bis = new BufferedInputStream(bd.getResponse());
                        File file = new File(LogRPC.INSTANCE.getBaseDir(), "updater/LogRPCUpdater" + (System.getProperty("os.name").startsWith("Windows") ? ".exe" : ".jar"));
                        FileOutputStream fos = new FileOutputStream(file);
                        byte dataBuffer[] = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = bis.read(dataBuffer, 0, 1024)) >= 0) {
                            downloadedFileSize += bytesRead;

                            progressBar1.setValue(Math.toIntExact((downloadedFileSize * 100) / fileSize));

                            fos.write(dataBuffer, 0, bytesRead);
                        }

                        bd.getResponse().close();
                        fos.flush();
                        fos.close();

                        dispose();
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, "Download Failed! " + e.getMessage(), "LogRPC", JOptionPane.ERROR_MESSAGE);

                    dispose();
                }
            }
        }).start();
    }
}
