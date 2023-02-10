package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class AcrobatPresence extends Presence {

    public AcrobatPresence(PresenceData data) {
        super(819013081787007027L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - ")) {
            return data.getTitle().split(" - ")[0];
        } else {
            return "Adobe Acrobat Reader";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "acrobat_large";
    }

    @Override
    public String getSmallImageKey() {
        return "acrobat_small";
    }

    @Override
    public String getSmallImageText() {
        return "Reading";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
