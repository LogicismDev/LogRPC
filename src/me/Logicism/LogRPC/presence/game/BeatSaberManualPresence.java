package me.Logicism.LogRPC.presence.game;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class BeatSaberManualPresence extends Presence {

    public BeatSaberManualPresence(PresenceData data) {
        super(708741346403287113L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        return "Slashing Blocks";
    }

    @Override
    public String getState() {
        return "LogRPC v" + LogRPC.VERSION;
    }

    @Override
    public String getLargeImageKey() {
        return "beat_saber_logo";
    }

    @Override
    public String getSmallImageKey() {
        return "beat_saber_block";
    }

    @Override
    public String getSmallImageText() {
        return "Manual Presence";
    }

    @Override
    public String getMainButtonText() {
        return null;
    }

    @Override
    public String getMainButtonURL() {
        return null;
    }

    @Override
    public String getSecondaryButtonText() {
        return null;
    }

    @Override
    public String getSecondaryButtonURL() {
        return null;
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
