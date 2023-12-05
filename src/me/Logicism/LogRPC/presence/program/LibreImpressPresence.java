package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class LibreImpressPresence extends Presence {

    public LibreImpressPresence(PresenceData data) {
        super(820977154753953843L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - LibreOffice Impress") || data.getTitle().contains(" — LibreOffice Impress")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - LibreOffice Impress".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "impress";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
