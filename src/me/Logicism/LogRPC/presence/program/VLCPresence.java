package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class VLCPresence extends Presence {

    public VLCPresence(PresenceData data) {
        super(721748388143562852L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;
        if (!data.getTitle().equals("VLC media player") && data.getTitle().contains(" - VLC media player")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - VLC media player".length());
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "vlc";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
