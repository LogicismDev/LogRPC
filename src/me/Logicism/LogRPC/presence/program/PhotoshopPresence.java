package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class PhotoshopPresence extends Presence {

    public PhotoshopPresence(PresenceData data) {
        super(818978967159046164L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" @ ")) {
            return data.getTitle().split(" @ ")[0];
        } else {
            return "Adobe Photoshop CC";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "photoshop_large";
    }

    @Override
    public String getSmallImageKey() {
        return "photoshop_small";
    }

    @Override
    public String getSmallImageText() {
        return "Editing";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
