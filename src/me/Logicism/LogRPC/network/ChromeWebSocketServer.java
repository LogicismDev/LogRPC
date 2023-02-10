package me.Logicism.LogRPC.network;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BrowserHTMLData;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.presence.PresenceType;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.java_websocket.WebSocket;
import org.java_websocket.handshake.ClientHandshake;
import org.java_websocket.server.WebSocketServer;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.InetSocketAddress;

public class ChromeWebSocketServer extends WebSocketServer {

    private WebSocket webSocket;
    private boolean inRoom = false;

    public ChromeWebSocketServer() {
        super(new InetSocketAddress("localhost", 3550));
    }

    @Override
    public void onOpen(WebSocket webSocket, ClientHandshake clientHandshake) {
        if (this.webSocket == null) {
            this.webSocket = webSocket;
            LogRPC.INSTANCE.getChromeMenuItem().setLabel("Extension - Connected");
        } else {
            webSocket.close();
        }
    }

    @Override
    public void onClose(WebSocket webSocket, int i, String s, boolean b) {
        if (this.webSocket != null) {
            this.webSocket = null;
            LogRPC.INSTANCE.getChromeMenuItem().setLabel("Extension - Disconnected");
        }
    }

    @Override
    public void onMessage(WebSocket webSocket, String s) {
        if (LogRPC.INSTANCE.getChromeMenuItem().getState()) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("action").equals("set")) {
                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.EXTENSION, new BrowserHTMLData(jsonObject)));
                } else if (jsonObject.getString("action").equals("clear")) {
                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (LogRPC.INSTANCE.getWiimmfiMenuItem().getState() && LogRPC.INSTANCE.getConfig().getWiimmfiPresenceType().equals("browser")) {
            try {
                JSONObject jsonObject = new JSONObject(s);

                if (!jsonObject.getString("html").contains("No room found!")) {
                    inRoom = true;

                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.WIIMMFI, new BrowserHTMLData(jsonObject)));
                } else if (jsonObject.getString("html").contains("No room found!")) {
                    inRoom = false;
                    LogRPC.INSTANCE.getEventManager().callEvent(new UpdatePresenceEvent(PresenceType.MANUAL, new JSONData(new JSONObject().put("details", "DefaultPresence"))));
                }
            } catch (JSONException e) {
                System.out.println(s);
            }
        }
    }

    @Override
    public void onError(WebSocket webSocket, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onStart() {

    }
}
