package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class LibreWriterPresence extends Presence {

    public LibreWriterPresence(PresenceData data) {
        super(820975831852646421L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - LibreOffice Writer")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - LibreOffice Writer".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "writer";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
