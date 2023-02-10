package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class GIMPPresence extends Presence {

    public GIMPPresence(PresenceData data) {
        super(820986356930183199L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains("[")) {
            return data.getTitle().substring(data.getTitle().indexOf("[") + 1, data.getTitle().indexOf("]"));
        }

        return "Homepage";
    }

    @Override
    public String getLargeImageKey() {
        return "gimp";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
