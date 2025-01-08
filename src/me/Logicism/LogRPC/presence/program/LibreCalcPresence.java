package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class LibreCalcPresence extends Presence {

    public LibreCalcPresence(PresenceData data) {
        super(820976508490612737L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - LibreOffice Calc") || data.getTitle().contains(" — LibreOffice Calc")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - LibreOffice Calc".length());
        } else {
            return "Homepage";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "calc";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
