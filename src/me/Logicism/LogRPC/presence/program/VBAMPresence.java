package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class VBAMPresence extends Presence {

    public VBAMPresence(PresenceData data) {
        super(835659303808532601L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().split(" - ").length == 1) {
            if (data.getTitle().contains("VisualBoyAdvance-M")) {
                return "No Game Open";
            } else {
                return data.getTitle().split(" - ")[0];
            }
        }

        return "none";
    }

    @Override
    public String getState() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().split(" - ").length == 1) {
            if (data.getTitle().contains("VisualBoyAdvance-M")) {
                return data.getTitle();
            } else {
                return data.getTitle().split(" - ")[1];
            }
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "gb";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
