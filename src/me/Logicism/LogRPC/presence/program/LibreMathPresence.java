package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class LibreMathPresence extends Presence {

    public LibreMathPresence(PresenceData data) {
        super(820978236858826753L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - LibreOffice Math")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - LibreOffice Math".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "math";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
