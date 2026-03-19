package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class AresPresence extends Presence {

    public AresPresence(PresenceData data) {
        super(1484099070275489792L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public DisplayType getDisplayType() {
        if (!data.getTitle().startsWith("ares v")) {
            return DisplayType.DETAILS;
        } else {
            return DisplayType.NAME;
        }
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().startsWith("ares v")) {
            return "No Game Open";
        } else {
            return data.getTitle();
        }
    }

    @Override
    public String getLargeImageKey() {
        return "https://raw.githubusercontent.com/ares-emulator/ares/refs/heads/master/ares/ares/resource/icon%402x.png";
    }

    @Override
    public String getLargeImageText() {
        return "ares Emulator - " + super.getLargeImageText();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
