package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class NotepadPlusPlusPresence extends Presence {

    public NotepadPlusPlusPresence(PresenceData data) {
        super(820981928659910717L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        return data.getTitle().substring(0, data.getTitle().length() - " - Notepad++".length()).split("\\\\")[data.getTitle().split("\\\\").length];
    }

    @Override
    public String getLargeImageKey() {
        return "notepad";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
