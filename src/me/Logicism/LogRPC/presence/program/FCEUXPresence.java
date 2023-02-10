package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class FCEUXPresence extends Presence {

    public FCEUXPresence(PresenceData data) {
        super(693692813321437247L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (!data.getTitle().contains(":") && data.getTitle().startsWith("FCEUX")) {
            return "No Game Open";
        } else if (data.getTitle().split(":").length != 1) {
            return data.getTitle().split(":")[1];
        }

        return "none";
    }

    @Override
    public String getLargeImageKey() {
        return "fceux";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
