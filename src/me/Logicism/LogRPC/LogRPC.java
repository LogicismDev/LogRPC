package me.Logicism.LogRPC;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.entities.Callback;
import com.jagrosh.discordipc.entities.RichPresence;
import com.jagrosh.discordipc.entities.User;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import com.sun.jna.Native;
import com.sun.jna.PointerType;
import com.sun.jna.platform.win32.WinDef;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.StdCallLibrary;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.event.EventManager;
import me.Logicism.LogRPC.core.executors.DeSmuMERunnable;
import me.Logicism.LogRPC.core.executors.MusicRunnable;
import me.Logicism.LogRPC.core.executors.ProgramRunnable;
import me.Logicism.LogRPC.core.executors.WiimmfiRunnable;
import me.Logicism.LogRPC.core.gui.OverwatchMap;
import me.Logicism.LogRPC.core.presence.PresenceListener;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import me.Logicism.LogRPC.event.UpdatePresenceHandler;
import me.Logicism.LogRPC.gui.*;
import me.Logicism.LogRPC.network.*;
import me.Logicism.LogRPC.presence.manual.CustomizablePresence;
import org.apache.commons.io.IOUtils;
import org.json.JSONException;
import org.json.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LogRPC {

    public static String VERSION = "1.92";

    public static LogRPC INSTANCE;

    private LogRPCConfig config;

    private long clientID;

    private EventManager eventManager;

    private IPCClient client;
    private RichPresence.Builder presence;

    private CheckboxMenuItem discordMenuItem;
    private MenuItem checkForUpdatesMenuItem;

    private CheckboxMenuItem manualMenuItem;
    private CheckboxMenuItem programMenuItem;
    private CheckboxMenuItem musicMenuItem;
    private CheckboxMenuItem chromeMenuItem;
    private CheckboxMenuItem beatSaberMenuItem;
    private CheckboxMenuItem wiimmfiMenuItem;
    private CheckboxMenuItem desmumeMenuItem;

    private MenuItem defaultPresence;
    private MenuItem setManualMenuItem;
    private Menu presetPresencesMenu;
    private Menu gameConsolesMenu;
    private Menu pcGamesMenu;

    private ExecutorService programExecutor;
    private ExecutorService musicExecutor;
    private ExecutorService wiimmfiExecutor;
    private ExecutorService desmumeExecutor;

    private File desmumeRPCFile;
    private List<String> desmumeMapHeaders;

    private List<String> nintendo3dsGames = new ArrayList<>();
    private List<String> wiiUGames = new ArrayList<>();
    private List<String> nintendoSwitchGames = new ArrayList<>();

    private String owGamemode = "Quickplay";
    private OverwatchMap owMap = new OverwatchMap("eichenwalde", new String[]{"Hybrid", "Deathmatch", "Team Deathmatch"}, "Eichenwalde");
    private String owMapType = "Hybrid";
    private int owRanking = 1;
    private boolean owInQueue = false;
    private int owPartySize = 1;

    private HypeRateWebSocketClient hypeRateWebSocketClient;

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException | InstantiationException e) {
            e.printStackTrace();
        }

        INSTANCE = new LogRPC();

        try {
            INSTANCE.client.connect();
        } catch (NoDiscordClientException | RuntimeException e) {
            e.printStackTrace();

            JOptionPane.showMessageDialog(null, "Discord must be open to use this Program!", "LogRPC", JOptionPane.ERROR_MESSAGE);

            System.exit(0);
        }

        if (!INSTANCE.config.isExtensionDisabled()) {
            ChromeWebSocketServer webSocketServer = new ChromeWebSocketServer();
            webSocketServer.start();
        }

        if (!INSTANCE.config.isBeatSaberDisabled()) {
            BeatSaberWebSocketClient webSocketClient = new BeatSaberWebSocketClient();
            webSocketClient.connect();

            ExecutorService reconnectionExecutor = Executors.newSingleThreadExecutor();
            reconnectionExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    while (true) {
                        while (webSocketClient.isClosed()) {
                            webSocketClient.reconnect();
                        }

                        try {
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }

        if (INSTANCE.config.isCheckForUpdates()) {
            File updaterdir = new File("updater");
            if (!updaterdir.exists()) {
                updaterdir.mkdir();
            }

            File file = new File("updater/LogRPCUpdater" + (System.getProperty("os.name").startsWith("Windows") ? ".exe" : ".jar"));

            if (!file.exists()) {
                UpdaterDialog updaterDialog = new UpdaterDialog();
                updaterDialog.pack();
                updaterDialog.setLocationRelativeTo(null);
                updaterDialog.setVisible(true);
            }

            try {
                String updatedVersion = IOUtils.toString(new URL("https://raw.githubusercontent.com/LogicismDev/LogRPC/main/version"), StandardCharsets.UTF_8);

                if (Double.parseDouble(updatedVersion) > Double.parseDouble(VERSION)) {
                    UpdateDialog updateDialog = new UpdateDialog();
                    updateDialog.pack();
                    updateDialog.setLocationRelativeTo(null);
                    updateDialog.setVisible(true);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public LogRPC() {
        if (!SystemTray.isSupported()) {
            JOptionPane.showMessageDialog(null, "LogRPC requires the use of a system tray but we could not find one!", "LogRPC", JOptionPane.ERROR_MESSAGE);

            return;
        }

        try {
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            config = mapper.readValue(new File("config.yml"), LogRPCConfig.class);

            if (!config.isDeSmuMEDisabled()) {
                desmumeMapHeaders = IOUtils.readLines(LogRPC.class.getClassLoader().getResourceAsStream("desmume_mapheaders.txt"), StandardCharsets.UTF_8);
            }
            if (!config.isNintendo3dsDisabled()) {
                try {
                    BrowserData data = BrowserClient.executeGETRequest(new URL("https://raw.githubusercontent.com/ninstar/Rich-Presence-U-DB/main/titles/ctr.csv"), null);

                    BufferedReader br = new BufferedReader(new InputStreamReader(data.getResponse(), StandardCharsets.UTF_8));
                    List<String> lines = new ArrayList<>();
                    String s1;

                    while ((s1 = br.readLine()) != null) {
                        if (!s1.equals("ID,US,EU,JP,US TITLE,EU TITLE,JP TITLE")) {
                            lines.add(s1);
                        }
                    }
                    Collections.sort(lines);
                    nintendo3dsGames.addAll(lines);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Unable to grab the Nintendo 3DS games database, try again later!", "LogRPC", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!config.isWiiUDisabled()) {
                try {
                    BrowserData data = BrowserClient.executeGETRequest(new URL("https://raw.githubusercontent.com/ninstar/Rich-Presence-U-DB/main/titles/wup.csv"), null);

                    BufferedReader br = new BufferedReader(new InputStreamReader(data.getResponse(), StandardCharsets.UTF_8));
                    List<String> lines = new ArrayList<>();
                    String s1;

                    while ((s1 = br.readLine()) != null) {
                        if (!s1.equals("ID,US,EU,JP,US TITLE,EU TITLE,JP TITLE")) {
                            lines.add(s1);
                        }
                    }
                    Collections.sort(lines);
                    wiiUGames.addAll(lines);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Unable to grab the Nintendo Wii U games database, try again later!", "LogRPC", JOptionPane.ERROR_MESSAGE);
                }
            }
            if (!config.isNintendoSwitchDisabled()) {
                try {
                    BrowserData data = BrowserClient.executeGETRequest(new URL("https://raw.githubusercontent.com/ninstar/Rich-Presence-U-DB/main/titles/hac.csv"), null);

                    BufferedReader br = new BufferedReader(new InputStreamReader(data.getResponse(), StandardCharsets.UTF_8));
                    List<String> lines = new ArrayList<>();
                    String s1;

                    while ((s1 = br.readLine()) != null) {
                        if (!s1.equals("ID,US,EU,JP,US TITLE,EU TITLE,JP TITLE")) {
                            lines.add(s1);
                        }
                    }
                    Collections.sort(lines);
                    nintendoSwitchGames.addAll(lines);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Unable to grab the Nintendo Switch games database, try again later!", "LogRPC", JOptionPane.ERROR_MESSAGE);
                }
            }

            SystemTray systemTray = SystemTray.getSystemTray();

            PopupMenu popupMenu = new PopupMenu();
            popupMenu.add(new MenuItem("LogRPC v" + VERSION));
            popupMenu.addSeparator();

            discordMenuItem = new CheckboxMenuItem("Discord - Disconnected", true);
            checkForUpdatesMenuItem = new MenuItem("Check for Updates");

            manualMenuItem = new CheckboxMenuItem("Manual", true);
            programMenuItem = new CheckboxMenuItem("Program", false);
            musicMenuItem = new CheckboxMenuItem("Music", false);
            chromeMenuItem = new CheckboxMenuItem("Extension - Disconnected", false);
            beatSaberMenuItem = new CheckboxMenuItem("Beat Saber - Disconnected");
            wiimmfiMenuItem = new CheckboxMenuItem("Wiimmfi (Mario Kart Wii)", false);
            desmumeMenuItem = new CheckboxMenuItem("DeSmuME (Pokémon Gen 4)", false);

            discordMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    discordMenuItem.setState(discordMenuItem.getState());

                    if (discordMenuItem.getState()) {
                        reinitializeClient();
                    } else {
                        client.close();
                    }
                }
            });

            checkForUpdatesMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        String updatedVersion = IOUtils.toString(new URL("https://raw.githubusercontent.com/LogicismDev/LogRPC/main/version"), StandardCharsets.UTF_8);

                        if (Double.parseDouble(updatedVersion) > Double.parseDouble(LogRPC.VERSION)) {
                            UpdateDialog updateDialog = new UpdateDialog();
                            updateDialog.pack();
                            updateDialog.setLocationRelativeTo(null);
                            updateDialog.setVisible(true);
                        } else {
                            JOptionPane.showMessageDialog(null, "You are currently on the latest version!", "LogRPC", JOptionPane.INFORMATION_MESSAGE);
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            });

            manualMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    programMenuItem.setState(false);
                    if (programExecutor != null) {
                        programExecutor = null;
                    }
                    musicMenuItem.setState(false);
                    if (musicExecutor != null) {
                        musicExecutor = null;
                    }
                    chromeMenuItem.setState(false);
                    beatSaberMenuItem.setState(false);
                    wiimmfiMenuItem.setState(false);
                    if (wiimmfiExecutor != null) {
                        wiimmfiExecutor = null;
                    }
                    desmumeMenuItem.setState(false);
                    if (desmumeExecutor != null) {
                        desmumeExecutor = null;
                    }

                    manualMenuItem.setState(true);

                    defaultPresence.setEnabled(true);
                    setManualMenuItem.setEnabled(true);
                    presetPresencesMenu.setEnabled(true);
                    gameConsolesMenu.setEnabled(true);
                    pcGamesMenu.setEnabled(true);

                    try {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
            });
            programMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (System.getProperty("os.name").startsWith("Windows")) {
                        manualMenuItem.setState(false);
                        musicMenuItem.setState(false);
                        if (musicExecutor != null) {
                            musicExecutor = null;
                        }
                        chromeMenuItem.setState(false);
                        beatSaberMenuItem.setState(false);
                        wiimmfiMenuItem.setState(false);
                        if (wiimmfiExecutor != null) {
                            wiimmfiExecutor = null;
                        }
                        desmumeMenuItem.setState(false);
                        if (desmumeExecutor != null) {
                            desmumeExecutor = null;
                        }

                        programMenuItem.setState(true);

                        defaultPresence.setEnabled(false);
                        setManualMenuItem.setEnabled(false);
                        presetPresencesMenu.setEnabled(false);
                        gameConsolesMenu.setEnabled(false);
                        pcGamesMenu.setEnabled(false);

                        try {
                            eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                        } catch (JSONException jsonException) {
                            jsonException.printStackTrace();
                        }

                        programExecutor = Executors.newSingleThreadExecutor();
                        programExecutor.execute(new ProgramRunnable());
                    } else {
                        JOptionPane.showMessageDialog(null, "Program Presence is not supported (yet) on Mac/Linux Systems!");
                    }
                }
            });
            musicMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    manualMenuItem.setState(false);
                    programMenuItem.setState(false);
                    if (programExecutor != null) {
                        programExecutor = null;
                    }
                    chromeMenuItem.setState(false);
                    beatSaberMenuItem.setState(false);
                    wiimmfiMenuItem.setState(false);
                    if (wiimmfiExecutor != null) {
                        wiimmfiExecutor = null;
                    }
                    desmumeMenuItem.setState(false);
                    if (desmumeExecutor != null) {
                        desmumeExecutor = null;
                    }

                    musicMenuItem.setState(true);

                    defaultPresence.setEnabled(false);
                    setManualMenuItem.setEnabled(false);
                    presetPresencesMenu.setEnabled(false);
                    gameConsolesMenu.setEnabled(false);
                    pcGamesMenu.setEnabled(false);

                    try {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                    musicExecutor = Executors.newSingleThreadExecutor();
                    musicExecutor.execute(new MusicRunnable());
                }
            });
            chromeMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    manualMenuItem.setState(false);
                    programMenuItem.setState(false);
                    if (programExecutor != null) {
                        programExecutor = null;
                    }
                    musicMenuItem.setState(false);
                    if (musicExecutor != null) {
                        musicExecutor = null;
                    }
                    beatSaberMenuItem.setState(false);
                    wiimmfiMenuItem.setState(false);
                    if (wiimmfiExecutor != null) {
                        wiimmfiExecutor = null;
                    }
                    desmumeMenuItem.setState(false);
                    if (desmumeExecutor != null) {
                        desmumeExecutor = null;
                    }

                    chromeMenuItem.setState(true);

                    defaultPresence.setEnabled(false);
                    setManualMenuItem.setEnabled(false);
                    presetPresencesMenu.setEnabled(false);
                    gameConsolesMenu.setEnabled(false);
                    pcGamesMenu.setEnabled(false);

                    try {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
            });
            beatSaberMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    manualMenuItem.setState(false);
                    programMenuItem.setState(false);
                    if (programExecutor != null) {
                        programExecutor = null;
                    }
                    musicMenuItem.setState(false);
                    if (musicExecutor != null) {
                        musicExecutor = null;
                    }
                    chromeMenuItem.setState(false);
                    wiimmfiMenuItem.setState(false);
                    if (wiimmfiExecutor != null) {
                        wiimmfiExecutor = null;
                    }
                    desmumeMenuItem.setState(false);
                    if (desmumeExecutor != null) {
                        desmumeExecutor = null;
                    }

                    beatSaberMenuItem.setState(true);

                    defaultPresence.setEnabled(false);
                    setManualMenuItem.setEnabled(false);
                    presetPresencesMenu.setEnabled(false);
                    gameConsolesMenu.setEnabled(false);
                    pcGamesMenu.setEnabled(false);

                    try {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
            });
            wiimmfiMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    manualMenuItem.setState(false);
                    programMenuItem.setState(false);
                    if (programExecutor != null) {
                        programExecutor = null;
                    }
                    musicMenuItem.setState(false);
                    if (musicExecutor != null) {
                        musicExecutor = null;
                    }
                    chromeMenuItem.setState(false);
                    beatSaberMenuItem.setState(false);
                    desmumeMenuItem.setState(false);
                    if (desmumeExecutor != null) {
                        desmumeExecutor = null;
                    }

                    wiimmfiMenuItem.setState(true);

                    defaultPresence.setEnabled(false);
                    setManualMenuItem.setEnabled(false);
                    presetPresencesMenu.setEnabled(false);
                    gameConsolesMenu.setEnabled(false);
                    pcGamesMenu.setEnabled(false);

                    try {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                    if (config.getWiimmfiPresenceType().equals("headless") || config.getWiimmfiPresenceType().equals("headlessjson")) {
                        wiimmfiExecutor = Executors.newSingleThreadExecutor();
                        wiimmfiExecutor.execute(new WiimmfiRunnable());
                    } else if (config.getWiimmfiPresenceType().equals("browser")) {
                        try {
                            Desktop.getDesktop().browse(new URI(config.getWiimmfiPlayerURL()));
                        } catch (IOException | URISyntaxException ex) {
                            ex.printStackTrace();
                        }
                    }
                }
            });
            desmumeMenuItem.addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    if (desmumeRPCFile == null) {
                        JFileChooser chooser = new JFileChooser();
                        chooser.setDialogTitle("Open out.dat");
                        chooser.setFileFilter(new FileNameExtensionFilter("out.dat DeSmuME File", "dat"));
                        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                            try {
                                List<String> list = Files.readAllLines(chooser.getSelectedFile().toPath());
                                if (list.size() == 3 && chooser.getSelectedFile().getName().equals("out.dat")) {
                                    desmumeRPCFile = chooser.getSelectedFile();
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid File!", "LogRPC", JOptionPane.ERROR_MESSAGE);
                                }
                            } catch (IOException ex) {
                                JOptionPane.showMessageDialog(null, "Can't Open File! " + ex.getMessage(), "LogRPC", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            return;
                        }
                    }

                    manualMenuItem.setState(false);
                    programMenuItem.setState(false);
                    if (programExecutor != null) {
                        programExecutor = null;
                    }
                    musicMenuItem.setState(false);
                    if (musicExecutor != null) {
                        musicExecutor = null;
                    }
                    chromeMenuItem.setState(false);
                    beatSaberMenuItem.setState(false);
                    wiimmfiMenuItem.setState(false);
                    if (wiimmfiExecutor != null) {
                        wiimmfiExecutor = null;
                    }

                    desmumeMenuItem.setState(true);

                    defaultPresence.setEnabled(false);
                    setManualMenuItem.setEnabled(false);
                    presetPresencesMenu.setEnabled(false);
                    gameConsolesMenu.setEnabled(false);
                    pcGamesMenu.setEnabled(false);

                    try {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }

                    desmumeExecutor = Executors.newSingleThreadExecutor();
                    desmumeExecutor.execute(new DeSmuMERunnable());
                }
            });

            popupMenu.add(discordMenuItem);
            popupMenu.add(checkForUpdatesMenuItem);
            popupMenu.addSeparator();

            popupMenu.add(manualMenuItem);
            popupMenu.add(programMenuItem);
            popupMenu.add(musicMenuItem);
            popupMenu.add(chromeMenuItem);
            popupMenu.add(beatSaberMenuItem);
            popupMenu.add(wiimmfiMenuItem);
            popupMenu.add(desmumeMenuItem);

            if (config.isExtensionDisabled()) {
                chromeMenuItem.setEnabled(false);
            }
            if (config.isBeatSaberDisabled()) {
                beatSaberMenuItem.setEnabled(false);
            }
            if (config.isWiimmfiDisabled()) {
                wiimmfiMenuItem.setEnabled(false);
            }
            if (config.isDeSmuMEDisabled()) {
                desmumeMenuItem.setEnabled(false);
            }

            programMenuItem.setEnabled(System.getProperty("os.name").startsWith("Windows"));
            musicMenuItem.setEnabled(System.getProperty("os.name").startsWith("Windows") || System.getProperty("os.name").startsWith("Mac OS X"));

            popupMenu.addSeparator();

            defaultPresence = new MenuItem("Default Presence");
            defaultPresence.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                    } catch (JSONException jsonException) {
                        jsonException.printStackTrace();
                    }
                }
            });

            setManualMenuItem = new MenuItem("Set Manual Presence");
            setManualMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    CustomPresenceDialog cpd = new CustomPresenceDialog();
                    cpd.pack();
                    cpd.setVisible(true);
                }
            });

            presetPresencesMenu = new Menu("Preset Presences");

            for (String presence : config.getManualPresetPresences()) {
                presetPresencesMenu.add(new MenuItem(presence));
            }
            for (int i = 0; i < presetPresencesMenu.getItemCount(); i++) {
                int finalI = i;
                presetPresencesMenu.getItem(i).addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        eventManager.callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new CustomizablePresence(Long.parseLong(config.getDefaultClientID()), presetPresencesMenu.getItem(finalI).getLabel(), "", config.getDefaultLargeImageKey(), "LogRPC v" + LogRPC.VERSION, null, null, config.getDefaultMainButtonText(), config.getDefaultMainButtonURL(), config.getDefaultSecondaryButtonText(), config.getDefaultSecondaryButtonURL(), 0, -1, -1, -1)));
                    }
                });
            }

            popupMenu.add(defaultPresence);
            popupMenu.add(setManualMenuItem);
            popupMenu.add(presetPresencesMenu);

            gameConsolesMenu = new Menu("Game Consoles");

            MenuItem nintendo3ds = new MenuItem("Nintendo 3DS");
            nintendo3ds.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameSearchDialog gameSearchDialog = new GameSearchDialog("Nintendo 3DS");
                    gameSearchDialog.pack();
                    gameSearchDialog.setVisible(true);
                }
            });

            MenuItem wiiU = new MenuItem("Wii U");
            wiiU.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameSearchDialog gameSearchDialog = new GameSearchDialog("Wii U");
                    gameSearchDialog.pack();
                    gameSearchDialog.setVisible(true);
                }
            });

            MenuItem nintendoSwitch = new MenuItem("Nintendo Switch");

            nintendoSwitch.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    GameSearchDialog gameSearchDialog = new GameSearchDialog("Nintendo Switch");
                    gameSearchDialog.pack();
                    gameSearchDialog.setVisible(true);
                }
            });

            if (config.isNintendoSwitchDisabled()) {
                nintendoSwitch.setEnabled(false);
            }

            gameConsolesMenu.add(nintendo3ds);
            gameConsolesMenu.add(wiiU);
            gameConsolesMenu.add(nintendoSwitch);

            if (config.isNintendo3dsDisabled()) {
                nintendo3ds.setEnabled(false);
            }
            if (config.isWiiUDisabled()) {
                wiiU.setEnabled(false);
            }
            if (config.isNintendoSwitchDisabled()) {
                nintendoSwitch.setEnabled(false);
            }

            popupMenu.add(gameConsolesMenu);

            pcGamesMenu = new Menu("PC Games");

            MenuItem overwatch2Item = new MenuItem("Overwatch 2");
            overwatch2Item.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    OverwatchDialog overwatchDialog = new OverwatchDialog();
                    overwatchDialog.pack();
                    overwatchDialog.setVisible(true);
                }
            });

            pcGamesMenu.add(overwatch2Item);

            if (config.isOverwatchDisabled()) {
                overwatch2Item.setEnabled(false);
            }

            popupMenu.add(pcGamesMenu);

            popupMenu.addSeparator();
            MenuItem exitMenuItem = new MenuItem("Exit");
            exitMenuItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.exit(0);
                }
            });

            popupMenu.add(exitMenuItem);
            TrayIcon trayIcon = new TrayIcon(ImageIO.read(getClass().getClassLoader().getResourceAsStream("icon.png")).getScaledInstance(16, 16, Image.SCALE_SMOOTH), "LogRPC - v" + LogRPC.VERSION, popupMenu);

            systemTray.add(trayIcon);

            clientID = 389249289223929856L;

            eventManager = new EventManager();
            eventManager.registerEventHandler(new UpdatePresenceHandler());

            client = new IPCClient(clientID);
            client.setListener(new PresenceListener());
        } catch (IllegalStateException | IOException | AWTException e) {
            JOptionPane.showMessageDialog(null, "Cannot Load Program! " + e.getMessage(), "LogRPC", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

    public LogRPCConfig getConfig() {
        return config;
    }

    public long getClientID() {
        return clientID;
    }

    public void setClientID(long clientID) {
        this.clientID = clientID;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public CheckboxMenuItem getDiscordMenuItem() {
        return discordMenuItem;
    }

    public CheckboxMenuItem getManualMenuItem() {
        return manualMenuItem;
    }

    public CheckboxMenuItem getProgramMenuItem() {
        return programMenuItem;
    }

    public CheckboxMenuItem getMusicMenuItem() {
        return musicMenuItem;
    }

    public CheckboxMenuItem getChromeMenuItem() {
        return chromeMenuItem;
    }

    public CheckboxMenuItem getBeatSaberMenuItem() {
        return beatSaberMenuItem;
    }

    public CheckboxMenuItem getWiimmfiMenuItem() {
        return wiimmfiMenuItem;
    }

    public CheckboxMenuItem getDesmumeMenuItem() {
        return desmumeMenuItem;
    }

    public MenuItem getDefaultPresenceMenuItem() {
        return defaultPresence;
    }

    public MenuItem getSetManualMenuItem() {
        return setManualMenuItem;
    }

    public Menu getPresetPresencesMenu() {
        return presetPresencesMenu;
    }

    public Menu getGameConsolesMenu() {
        return gameConsolesMenu;
    }

    public Menu getPCGamesMenu() {
        return pcGamesMenu;
    }

    public ExecutorService getMusicExecutor() {
        return musicExecutor;
    }

    public void setMusicExecutor(ExecutorService musicExecutor) {
        this.musicExecutor = musicExecutor;
    }

    public File getDesmumeRPCFile() {
        return desmumeRPCFile;
    }

    public List<String> getDesmumeMapHeaders() {
        return desmumeMapHeaders;
    }

    public List<String> getNintendo3dsGames() {
        return nintendo3dsGames;
    }

    public List<String> getWiiUGames() {
        return wiiUGames;
    }

    public List<String> getNintendoSwitchGames() {
        return nintendoSwitchGames;
    }

    public boolean isOwInQueue() {
        return owInQueue;
    }

    public void setOwInQueue(boolean owInQueue) {
        this.owInQueue = owInQueue;
    }

    public OverwatchMap getOwMap() {
        return owMap;
    }

    public void setOwMap(OverwatchMap owMap) {
        this.owMap = owMap;
    }

    public String getOwGamemode() {
        return owGamemode;
    }

    public void setOwGamemode(String owGamemode) {
        this.owGamemode = owGamemode;
    }

    public String getOwMapType() {
        return owMapType;
    }

    public void setOwMapType(String owMapType) {
        this.owMapType = owMapType;
    }

    public int getOwRanking() {
        return owRanking;
    }

    public void setOwRanking(int owRanking) {
        this.owRanking = owRanking;
    }

    public int getOwPartySize() {
        return owPartySize;
    }

    public void setOwPartySize(int owPartySize) {
        this.owPartySize = owPartySize;
    }

    public HypeRateWebSocketClient getHypeRateWebSocketClient() {
        return hypeRateWebSocketClient;
    }

    public void setHypeRateWebSocketClient(HypeRateWebSocketClient hypeRateWebSocketClient) {
        this.hypeRateWebSocketClient = hypeRateWebSocketClient;
    }

    public IPCClient getClient() {
        return client;
    }

    public void setUser(User user) {

        getDiscordMenuItem().setLabel("Discord - " + client.getCurrentUser().getName() + "#" + client.getCurrentUser().getDiscriminator());
    }

    public RichPresence.Builder getPresence() {
        return presence;
    }

    public void setPresence(RichPresence.Builder presence, boolean setOnly) {
        this.presence = presence;

        if (!setOnly) {
            boolean presenceDisabled = false;
            if (client != null && !presenceDisabled && client.getStatus() == PipeStatus.CONNECTED) {
                client.sendRichPresence(presence.build(), new Callback(e -> System.out.println("Success"), err -> System.out.println(err)));
            }
        }
    }

    public void reinitializeClient() {
        if (client != null) {
            try {
                try {
                    client.close();
                } catch (IllegalStateException e) {

                }
                client = null;

                client = new IPCClient(clientID);
                client.setListener(new PresenceListener());

                client.connect();
            } catch (NoDiscordClientException | RuntimeException e) {
                JOptionPane.showMessageDialog(null, "Discord must be open to use this Program!", "LogRPC", JOptionPane.ERROR_MESSAGE);

                System.exit(0);
            }
        }
    }

    public interface User32 extends StdCallLibrary {
        User32 INSTANCE = Native.load("user32", User32.class);

        WinDef.HWND GetForegroundWindow();

        int GetWindowTextA(PointerType hWnd, byte[] lpString, int nMaxCount);

        int GetWindowThreadProcessId(WinDef.HWND hWnd, IntByReference procId);
    }

}
