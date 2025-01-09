package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class MPCHCPresence extends Presence {

    public MPCHCPresence(PresenceData data) {
        super(427863248734388224L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (!data.getTitle().equals("Options") && !data.getTitle().equals("Open") && !data.getTitle().equals("Properties") && !data.getTitle().equals("About") || data.contains("start_time")) {
            return data.getTitle();
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "default";
    }

    @Override
    public String getSmallImageKey() {
        JSONData data = (JSONData) this.data;
        switch (data.getIntAttribute("details")) {
            case 0:
                return "https://cdn.rcd.gg/PreMiD/resources/stop.png";
            case 1:
                return "https://cdn.rcd.gg/PreMiD/resources/pause.png";
            case 2:
                return "https://cdn.rcd.gg/PreMiD/resources/play.png";
        }

        return super.getSmallImageKey();
    }

    @Override
    public String getSmallImageText() {
        JSONData data = (JSONData) this.data;
        switch (data.getIntAttribute("playbackState")) {
            case 0:
                return "Stopped";
            case 1:
                return "Paused";
            case 2:
                return "Playing";
        }

        return super.getSmallImageText();
    }

    @Override
    public long getStartTimestamp() {
        JSONData data = (JSONData) this.data;

        if (data.contains("start_time") && data.getIntAttribute("playbackState") == 2) {
            return data.getIntAttribute("start_time");
        } else if (data.getIntAttribute("playbackState") == 0 || data.getIntAttribute("playbackState") == 1) {
            return -1;
        }
        return 0;
    }

    @Override
    public long getEndTimestamp() {
        JSONData data = (JSONData) this.data;

        if (data.contains("end_time") && data.getIntAttribute("playbackState") == 2) {
            return data.getIntAttribute("end_time");
        }
        return super.getEndTimestamp();
    }
}
