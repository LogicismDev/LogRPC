package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class OfficePowerpointPresence extends Presence {

    public OfficePowerpointPresence(PresenceData data) {
        super(820903906120171540L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - Powerpoint")) {
            return data.getTitle().substring(0, " - Powerpoint".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "powerpoint";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
