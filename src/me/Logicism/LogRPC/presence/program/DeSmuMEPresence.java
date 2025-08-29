package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class DeSmuMEPresence extends Presence {

    public DeSmuMEPresence(PresenceData data) {
        super(881758456112091156L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public DisplayType getDisplayType() {
        if (data.getTitle().contains("x64 SSE2 |")) {
            return DisplayType.DETAILS;
        }

        return DisplayType.NAME;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains("x64 SSE2 |")) {
            return data.getTitle().split(" \\| ")[1];
        }
        return "Playing a DS Game";
    }

    @Override
    public String getLargeImageKey() {
        return "desmume";
    }

    @Override
    public String getLargeImageText() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains("x64 SSE2 |")) {
            return data.getTitle().split(" \\| ")[0] + " - " + super.getLargeImageText();
        }

        return "DeSmuME 0.9.13 x64 SSE2 - " + super.getLargeImageText();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
