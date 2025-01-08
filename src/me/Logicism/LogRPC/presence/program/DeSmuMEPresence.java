package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
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
    public String getDetails() {
        return "Playing a DS Game";
    }

    @Override
    public String getLargeImageKey() {
        return "desmume";
    }

    @Override
    public String getLargeImageText() {
        JSONData data = (JSONData) this.data;

        return data.getTitle() + " - " + super.getLargeImageText();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
