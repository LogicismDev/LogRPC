package me.Logicism.LogRPC.presence.game;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class WiiUPresence extends Presence {

    public WiiUPresence(PresenceData data) {
        super(864486365218603008L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        return data.getTitle();
    }

    @Override
    public String getState() {
        return super.getState();
    }

    @Override
    public String getLargeImageKey() {
        JSONData data = (JSONData) this.data;

        return data.getAttribute("largeImageKey") + "_us";
    }

    @Override
    public String getSmallImageKey() {
        return "_tooltip";
    }

    @Override
    public String getSmallImageText() {
        return LogRPC.INSTANCE.getConfig().getWiiUNNID();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
