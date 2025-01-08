package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class MPCHCPresence extends Presence {

    public MPCHCPresence(PresenceData data) {
        super(427863248734388224L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (!data.getTitle().equals("Options") && !data.getTitle().equals("Open") && !data.getTitle().equals("Properties") && !data.getTitle().equals("About")) {
            return data.getTitle();
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "default";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
