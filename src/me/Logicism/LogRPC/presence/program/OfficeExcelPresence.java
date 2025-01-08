package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class OfficeExcelPresence extends Presence {

    public OfficeExcelPresence(PresenceData data) {
        super(820903442674352158L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - Excel")) {
            return data.getTitle().substring(0, " - Excel".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "excel";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
