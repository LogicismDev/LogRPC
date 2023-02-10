package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class InDesignPresence extends Presence {

    public InDesignPresence(PresenceData data) {
        super(819011518884610058L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" @ ")) {
            return data.getTitle().split(" @ ")[0];
        } else {
            return "Adobe InDesign CC";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "indesign_large";
    }

    @Override
    public String getSmallImageKey() {
        return "indesign_small";
    }

    @Override
    public String getSmallImageText() {
        return "Editing";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
