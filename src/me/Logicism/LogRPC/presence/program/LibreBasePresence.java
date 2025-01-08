package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class LibreBasePresence extends Presence {

    public LibreBasePresence(PresenceData data) {
        super(820977843475054633L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - LibreOffice Base") || data.getTitle().contains(" — LibreOffice Base")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - LibreOffice Base".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "base";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
