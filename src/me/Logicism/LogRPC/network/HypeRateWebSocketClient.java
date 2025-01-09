package me.Logicism.LogRPC.network;

import com.jagrosh.discordipc.entities.RichPresence;
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

public class HypeRateWebSocketClient extends WebSocketClient {

    private int hr = 0;
    private RichPresence.Builder presence;
    private JSONObject object;

    public HypeRateWebSocketClient() {
        super(URI.create("wss://hrproxy.fortnite.lol:2096/hrproxy"));
        setPresence(LogRPC.INSTANCE.getPresence());
        updateHR();
    }

    @Override
    public void onOpen(ServerHandshake serverHandshake) {
        send(new JSONObject().put("reader", LogRPC.INSTANCE.getConfig().getHrReader().equalsIgnoreCase("hyperate") ? "HypeRate" : "Pulsoid").put("identifier", LogRPC.INSTANCE.getConfig().getHrReader().equalsIgnoreCase("hyperate") ? LogRPC.INSTANCE.getConfig().getHypeRateId() : LogRPC.INSTANCE.getConfig().getPulsoidWidgetId()).put("service", "beatsaber").toString());
    }

    @Override
    public void onMessage(String s) {
        System.out.println(s);
        JSONObject jsonObject = new JSONObject(s);
        if (jsonObject.has("method") && jsonObject.getString("method").equalsIgnoreCase("ping")) {
            jsonObject.remove("method");
            jsonObject.put("method", "pong");

            send(jsonObject.toString());
        } else {
            hr = jsonObject.getInt("hr");

            updateHR();
        }
    }

    @Override
    public void onClose(int i, String s, boolean b) {

    }

    @Override
    public void onError(Exception e) {

    }

    public void updateHR() {
        if (object.getJSONObject("assets").has("large_image") && object.getJSONObject("assets").getString("large_text").contains("${hrRate}")) {
            presence.setLargeImage(object.getJSONObject("assets").getString("large_image"), object.getJSONObject("assets").getString("large_text").replace("${hrRate}", String.valueOf(hr)));
        }
        if (object.getJSONObject("assets").has("small_image") && object.getJSONObject("assets").getString("small_text").contains("${hrRate}")) {
            presence.setLargeImage(object.getJSONObject("assets").getString("small_image"), object.getJSONObject("assets").getString("small_text").replace("${hrRate}", String.valueOf(hr)));
        }
        if (object.has("buttons")) {
            if (object.getJSONArray("buttons").length() >= 1 && object.getJSONArray("buttons").getJSONObject(0).getString("label").contains("${hrRate}")) {
                presence.setMainButtonText(object.getJSONArray("buttons").getJSONObject(0).getString("label").replace("${hrRate}", String.valueOf(hr)));
            }
            if (object.getJSONArray("buttons").length() == 2 && object.getJSONArray("buttons").getJSONObject(1).getString("label").contains("${hrRate}")) {
                presence.setMainButtonText(object.getJSONArray("buttons").getJSONObject(1).getString("label").replace("${hrRate}", String.valueOf(hr)));
            }
        }
        if (object.getString("details").contains("${hrRate}")) {
            presence.setDetails(object.getString("details").replace("${hrRate}", String.valueOf(hr)));
        }
        if (object.has("state") && object.getString("state").contains("${hrRate}")) {
            presence.setDetails(object.getString("state").replace("${hrRate}", String.valueOf(hr)));
        }

        LogRPC.INSTANCE.setPresence(null, presence, false);
    }

    public void setPresence(RichPresence.Builder presence) {
        this.presence = presence;
        this.object = presence.build().toJson();
    }
}
