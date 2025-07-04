package me.Logicism.LogRPC.presence.game;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class WiiUPresence extends Presence {

    public WiiUPresence(PresenceData data) {
        super(1259966953573847130L, data);
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

        return "https://github.com/ninstar/Rich-Presence-U-DB/raw/main/titles/wup/" + data.getAttribute("largeImageKey") + ".us.jpg";
    }

    @Override
    public String getSmallImageKey() {
        return "id";
    }

    @Override
    public String getSmallImageText() {
        return LogRPC.INSTANCE.getConfig().getWiiUNNID();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
