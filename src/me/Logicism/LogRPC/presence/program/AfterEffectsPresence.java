package me.Logicism.LogRPC.presence.program;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class AfterEffectsPresence extends Presence {

    public AfterEffectsPresence(PresenceData data) {
        super(819011167996870688L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - ")) {
            return data.getTitle().split(" - ")[0];
        } else {
            return "Adobe After Effects CC";
        }
    }

    @Override
    public String getLargeImageKey() {
        return "aftereffects_large";
    }

    @Override
    public String getSmallImageKey() {
        return "aftereffects_small";
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
