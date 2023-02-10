package me.Logicism.LogRPC.network;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BeatSaberData;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.URI;
import java.net.URL;

public class BeatSaberWebSocketClient extends WebSocketClient {

    public BeatSaberWebSocketClient() {
        super(URI.create("ws://127.0.0.1:2946/BSDataPuller/MapData"));
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        LogRPC.INSTANCE.getBeatSaberMenuItem().setLabel("Beat Saber - Connected");
    }

    @Override
    public void onMessage(String s) {
        if (LogRPC.INSTANCE.getBeatSaberMenuItem().getState()) {
            try {
                JSONObject object = new JSONObject(s);

                JSONObject beatSaberData = new JSONObject();

                if (object.getBoolean("InLevel")) {
                    beatSaberData.put("title", object.getString("SongName")
                            + " - " + object.getString("SongAuthor")).put("details",
                            object.getString("Difficulty"));

                    beatSaberData.put("inGamePaused", object.getBoolean("LevelPaused"));

                    if (!object.isNull("Hash")) {
                        BrowserData data = BrowserClient.executeGETRequest(new URL("https://api.beatsaver.com/maps/hash/" + object.getString("Hash")), null);

                        if (data.getResponseCode() == 200) {
                            BufferedReader br = new BufferedReader(new InputStreamReader(data.getResponse()));
                            StringBuilder sb = new StringBuilder();
                            String s1;

                            while ((s1 = br.readLine()) != null) {
                                sb.append(s1);
                            }

                            JSONObject beatSaverData = new JSONObject(sb.toString());

                            data.getResponse().close();

                            beatSaberData.put("beatSaverID", beatSaverData.getString("id"));
                        }
                    }
                } else {
                    beatSaberData.put("title", "Main Menu").put("details", "").put("startTimestamp", System.currentTimeMillis());
                }

                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.BEAT_SABER,
                        new BeatSaberData(beatSaberData)));
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {
        if (LogRPC.INSTANCE.getBeatSaberMenuItem().getLabel().equals("Beat Saber - Connected")) {
            LogRPC.INSTANCE.getBeatSaberMenuItem().setLabel("Beat Saber - Disconnected");

            try {
                LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
            } catch (JSONException jsonException) {
                jsonException.printStackTrace();
            }
        }
    }

    @Override
    public void onError(Exception e) {

    }

}
