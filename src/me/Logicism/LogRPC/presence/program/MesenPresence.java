package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class MesenPresence extends Presence {

    public MesenPresence(PresenceData data) {
        super(528420146964856847L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().equals("Mesen")) {
            return "No Game Open";
        } else if (data.getTitle().startsWith("Mesen - ")) {
            return data.getTitle().substring(0, "Mesen - ".length());
        }

        return "none";
    }

    @Override
    public String getLargeImageKey() {
        return "mesen";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
