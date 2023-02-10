package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class ZoomPresence extends Presence {

    public ZoomPresence(PresenceData data) {
        super(819318714243612722L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        System.out.println(data.getTitle());

        if (data.getTitle().equals("Zoom Meeting") || data.getTitle().equals("Screen share viewing options")) {
            return "In a Meeting";
        } else if (data.getTitle().equals("Zoom")) {
            return "Homepage";
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "zoom";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
