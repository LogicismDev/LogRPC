package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class DaVinciResolvePresence extends Presence {
    public DaVinciResolvePresence(PresenceData data) {
        super(1004088618857549844L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().startsWith("DaVinci Resolve -")) {
            return data.getTitle().substring("DaVinci Resolve - ".length());
        } else if (data.getTitle().startsWith("DaVinci Resolve Studio -")) {
                return data.getTitle().substring("DaVinci Resolve Studio - ".length());
        } else {
            return "Editing a Video";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "resolve";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
