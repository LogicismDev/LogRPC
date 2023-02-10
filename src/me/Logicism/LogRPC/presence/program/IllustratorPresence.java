package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class IllustratorPresence extends Presence {

    public IllustratorPresence(PresenceData data) {
        super(819011013600215091L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" @ ")) {
            return data.getTitle().split(" @ ")[0];
        } else {
            return "Adobe Illustrator CC";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "illustrator_large";
    }

    @Override
    public String getSmallImageKey() {
        return "illustrator_small";
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
