package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.presence.Presence;

public class NO$GBAPresence extends Presence {

    public NO$GBAPresence() {
        super(862184084871839755L);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        return "Playing a DS/GBA Game";
    }

    @Override
    public String getLargeImageKey() {
        return "no_gba";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
