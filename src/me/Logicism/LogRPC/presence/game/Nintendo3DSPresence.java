package me.Logicism.LogRPC.presence.game;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class Nintendo3DSPresence extends Presence {

    public Nintendo3DSPresence(PresenceData data) {
        super(864487170759589980L, data);
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

        return data.getAttribute("largeImageKey") + "_us";
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
