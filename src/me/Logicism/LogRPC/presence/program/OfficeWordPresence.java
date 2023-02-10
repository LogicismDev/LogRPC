package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class OfficeWordPresence extends Presence {

    public OfficeWordPresence(PresenceData data) {
        super(820901533319168020L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - Word")) {
            return data.getTitle().substring(0, " - Word".length());
        } else {

            return "Homepage";

        }
    }

    @Override
    public String getLargeImageKey() {
        return "word";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
