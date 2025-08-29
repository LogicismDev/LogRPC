package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class Snes9xPresence extends Presence {

    public Snes9xPresence(PresenceData data) {
        super(693881606309412874L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public DisplayType getDisplayType() {
        if (!data.getTitle().contains(" - ") && data.getTitle().contains("Snes9x")) {
            return DisplayType.NAME;
        } else if (data.getTitle().split(" - ").length != 1) {
            return DisplayType.DETAILS;
        }

        return DisplayType.NAME;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (!data.getTitle().contains(" - ") && data.getTitle().contains("Snes9x")) {
            return "No Game Open";
        } else if (data.getTitle().split(" - ").length != 1) {
            return data.getTitle().split(" - ")[0];
        }

        return "none";
    }

    @Override
    public String getLargeImageKey() {
        return "snes";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
