package me.Logicism.LogRPC.core.presence;

import com.jagrosh.discordipc.IPCClient;
import com.jagrosh.discordipc.IPCListener;
import com.jagrosh.discordipc.entities.pipe.PipeStatus;
import com.jagrosh.discordipc.exceptions.NoDiscordClientException;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.event.UpdatePresenceEvent;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PresenceListener implements IPCListener {

    @Override
    public void onReady(IPCClient client) {
        if (LogRPC.INSTANCE.getPresence() == null) {
            try {
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
