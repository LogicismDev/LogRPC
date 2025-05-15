package me.Logicism.LogRPC.presence.game;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class Nintendo3DSPresence extends Presence {

    public Nintendo3DSPresence(PresenceData data) {
        super(1259967368000569394L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        return data.getTitle();
    }

    @Override
    public String getState() {
        return super.getState();
    }

    @Override
    public String getLargeImageKey() {
        JSONData data = (JSONData) this.data;

        return "https://github.com/ninstar/Rich-Presence-U-DB/raw/main/titles/ctr/" + data.getAttribute("largeImageKey") + ".us.jpg";
    }

    @Override
    public String getSmallImageKey() {
        return "_tooltip";
    }

    @Override
    public String getSmallImageText() {
        return LogRPC.INSTANCE.getConfig().getNintendo3dsFriendCode();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
