package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class FCEUXPresence extends Presence {

    public FCEUXPresence(PresenceData data) {
        super(693692813321437247L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public DisplayType getDisplayType() {
        if (data.getTitle().split(":").length == 1) {
            return DisplayType.DETAILS;
        } else {
            return DisplayType.NAME;
        }
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (!data.getTitle().contains(":") && data.getTitle().startsWith("FCEUX")) {
            return "No Game Open";
        } else if (data.getTitle().split(":").length != 1) {
            return data.getTitle().substring((data.getTitle().split(":")[0] + ": ").length());
        }

        return "none";
    }

    @Override
    public String getLargeImageKey() {
        return "fceux";
    }

    @Override
    public String getLargeImageText() {
        if (data.getTitle().split(":").length != 1) {
            return data.getTitle().split(":")[0] + " - " + super.getLargeImageText();
        }

        return "FCEUX - " + super.getLargeImageText();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
