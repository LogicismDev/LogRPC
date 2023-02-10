package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class DreamweaverPresence extends Presence {

    public DreamweaverPresence(PresenceData data) {
        super(893750562032590909L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        return data.getTitle();
    }

    @Override
    public String getLargeImageKey() {
        return "dreamweaver_large";
    }

    @Override
    public String getSmallImageKey() {
        return "dreamweaver_small";
    }

    @Override
    public String getSmallImageText() {
        return "Coding";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
