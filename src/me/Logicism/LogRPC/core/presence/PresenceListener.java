package me.Logicism.LogRPC.core.presence;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.executors.*;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.concurrent.Executors;

public class PresenceListener implements IPCListener {

    @Override
    public void onReady(IPCClient client) {
        if (LogRPC.INSTANCE.getPresence() == null) {
            try {
                if (LogRPC.INSTANCE.getConfig().isLastPresenceOnStartup()) {
                    PresenceType type = PresenceType.valueOf(LogRPC.INSTANCE.getConfig().getOverrideLastPresenceType().equals("NONE") ? LogRPC.INSTANCE.getCachedData().get("Last Presence") : LogRPC.INSTANCE.getConfig().getOverrideLastPresenceType());
                    System.out.println(type.name());
                    if (type != PresenceType.MANUAL) {
                        LogRPC.INSTANCE.getManualMenuItem().setState(false);

                        switch (type) {
                            case PROGRAM:
                                LogRPC.INSTANCE.getProgramMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);

                                LogRPC.INSTANCE.setProgramExecutor(Executors.newSingleThreadExecutor());
                                LogRPC.INSTANCE.getProgramExecutor().execute(new ProgramRunnable());
                                break;
                            case MUSIC:
                                LogRPC.INSTANCE.getMusicMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);

                                LogRPC.INSTANCE.setMusicExecutor(Executors.newSingleThreadExecutor());
                                LogRPC.INSTANCE.getMusicExecutor().execute(new MusicRunnable());
                                break;
                            case EXTENSION:
                                LogRPC.INSTANCE.getChromeMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);
                                break;
                            case BEAT_SABER:
                                LogRPC.INSTANCE.getBeatSaberMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);
                                break;
                            case WIIMMFI:
                                LogRPC.INSTANCE.getWiimmfiMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);

                                LogRPC.INSTANCE.setWiimmfiExecutor(Executors.newSingleThreadExecutor());
                                LogRPC.INSTANCE.getWiimmfiExecutor().execute(new WiimmfiRunnable());
                                break;
                            case NINTENDO_SWITCH:
                                LogRPC.INSTANCE.getNintendoSwitchMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);

                                LogRPC.INSTANCE.setNintendoSwitchExecutor(Executors.newSingleThreadExecutor());
                                LogRPC.INSTANCE.getNintendoSwitchExecutor().execute(new NintendoSwitchRunnable());
                                break;
                            case DESMUME:
                                if (LogRPC.INSTANCE.getDesmumeRPCFile() == null) {
                                    JFileChooser chooser = new JFileChooser();
                                    chooser.setDialogTitle("Open out.dat");
                                    chooser.setFileFilter(new FileNameExtensionFilter("out.dat DeSmuME File", "dat"));
                                    if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                                        try {
                                            List<String> list = Files.readAllLines(chooser.getSelectedFile().toPath());
                                            if (list.size() == 3 && chooser.getSelectedFile().getName().equals("out.dat")) {
                                                LogRPC.INSTANCE.setDesmumeRPCFile(chooser.getSelectedFile());
                                            } else {
                                                JOptionPane.showMessageDialog(null, "Invalid File!", "LogRPC", JOptionPane.ERROR_MESSAGE);

                                                LogRPC.INSTANCE.getManualMenuItem().setState(true);
                                                return;
                                            }
                                        } catch (IOException ex) {
                                            JOptionPane.showMessageDialog(null, "Can't Open File! " + ex.getMessage(), "LogRPC", JOptionPane.ERROR_MESSAGE);

                                            LogRPC.INSTANCE.getManualMenuItem().setState(true);
                                            return;
                                        }
                                    } else {
                                        LogRPC.INSTANCE.getManualMenuItem().setState(true);
                                        return;
                                    }
                                }

                                LogRPC.INSTANCE.getDesmumeMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);

                                LogRPC.INSTANCE.setDesmumeExecutor(Executors.newSingleThreadExecutor());
                                LogRPC.INSTANCE.getDesmumeExecutor().execute(new DeSmuMERunnable());
                            case VLC_MEDIA_PLAYER:
                                LogRPC.INSTANCE.getVLCMediaPlayerMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);

                                LogRPC.INSTANCE.setMediaPlayerExecutor(Executors.newSingleThreadExecutor());
                                LogRPC.INSTANCE.getMediaPlayerExecutor().execute(new MediaPlayerRunnable());
                            case MPCHC_MEDIA_PLAYER:
                                LogRPC.INSTANCE.getMPCHCMediaPlayerMenuItem().setState(true);

                                LogRPC.INSTANCE.getDefaultPresenceMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getSetManualMenuItem().setEnabled(false);
                                LogRPC.INSTANCE.getPresetPresencesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getGameConsolesMenu().setEnabled(false);
                                LogRPC.INSTANCE.getPCGamesMenu().setEnabled(false);

                                LogRPC.INSTANCE.setMediaPlayerExecutor(Executors.newSingleThreadExecutor());
                                LogRPC.INSTANCE.getMediaPlayerExecutor().execute(new MediaPlayerRunnable());
                        }
                    }
                }

                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        } else {
            LogRPC.INSTANCE.setPresence(LogRPC.INSTANCE.getPresence(), false);
        }

        LogRPC.INSTANCE.setUser(client.getCurrentUser());
    }

    @Override
    public void onDisconnect(IPCClient client, Throwable t) {
        LogRPC.INSTANCE.getDiscordMenuItem().setLabel("Discord - Disconnected");
        LogRPC.INSTANCE.reconnectClient();
    }
}
