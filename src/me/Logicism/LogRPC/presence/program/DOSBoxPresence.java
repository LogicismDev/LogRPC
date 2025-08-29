package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class DOSBoxPresence extends Presence {

    public DOSBoxPresence(PresenceData data) {
        super(693311130856325180L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public DisplayType getDisplayType() {
        if (!data.getTitle().equals("DOSBox Status Window")) {
            if (!data.getTitle().split(" Program:   ")[1].startsWith("DOSBOX")) {
                return DisplayType.DETAILS;
            }
        }
        return DisplayType.NAME;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (!data.getTitle().equals("DOSBox Status Window")) {
            if (data.getTitle().split(" Program:   ")[1].startsWith("DOSBOX")) {
                return "No Program Open";
            } else {
                return data.getTitle().split(" Program:   ")[1] + ".EXE";
            }
        }

        return "none";
    }

    @Override
    public String getLargeImageKey() {
        return "dos";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
