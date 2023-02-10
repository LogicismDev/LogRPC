package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class LibreDrawPresence extends Presence {

    public LibreDrawPresence(PresenceData data) {
        super(820977407812304926L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - LibreOffice Draw")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - LibreOffice Draw".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "draw";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
