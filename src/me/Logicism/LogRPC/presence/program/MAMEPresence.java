package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class MAMEPresence extends Presence {

    public MAMEPresence(PresenceData data) {
        super(837778226137792543L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().isEmpty() || data.getTitle().startsWith("No Driver Loaded")) {
            return "No Game Open";
        } else {
            return data.getTitle().split(" \\[")[0];
        }
    }

    @Override
    public String getLargeImageKey() {
        return "mame";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
