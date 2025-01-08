package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class MelonDSPresence extends Presence {

    public MelonDSPresence(PresenceData data) {
        super(881760348439478293L, data);
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
        return "melon";
    }

    @Override
    public String getLargeImageText() {
        JSONData data = (JSONData) this.data;
        String title = data.getTitle();
        if (title.contains("/60]")) {
            title = title.split("/60] ")[1];
        }

        return title + " - " + super.getLargeImageText();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
