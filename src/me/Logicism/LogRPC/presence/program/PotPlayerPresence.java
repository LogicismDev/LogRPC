package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class PotPlayerPresence extends Presence {

    public PotPlayerPresence(PresenceData data) {
        super(1326769135836987532L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;
        if (!data.getTitle().equals("PotPlayer") && data.getTitle().contains(" - PotPlayer")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - PotPlayer".length());
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "potplayer";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
