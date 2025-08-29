package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class VLCPresence extends Presence {

    public VLCPresence(PresenceData data) {
        super(721748388143562852L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.WATCHING;
    }

    @Override
    public DisplayType getDisplayType() {
        JSONData data = (JSONData) this.data;
        if (!data.getTitle().equals("VLC media player") && data.getTitle().contains(" - VLC media player") || data.contains("start_time")) {
            return DisplayType.DETAILS;
        }

        return DisplayType.NAME;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;
        if (!data.getTitle().equals("VLC media player") && data.getTitle().contains(" - VLC media player")) {
            return data.getTitle().substring(0, data.getTitle().length() - " - VLC media player".length());
        } else if (data.contains("start_time")) {
            return data.getTitle();
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "vlc";
    }

    @Override
    public String getSmallImageKey() {
        JSONData data = (JSONData) this.data;
        switch (data.getAttribute("details")) {
            case "stopped":
                return "https://cdn.rcd.gg/PreMiD/resources/stop.png";
            case "paused":
                return "https://cdn.rcd.gg/PreMiD/resources/pause.png";
            case "playing":
                return "https://cdn.rcd.gg/PreMiD/resources/play.png";
        }

        return super.getSmallImageKey();
    }

    @Override
    public String getSmallImageText() {
        JSONData data = (JSONData) this.data;
        switch (data.getAttribute("details")) {
            case "stopped":
                return "Stopped";
            case "paused":
                return "Paused";
            case "playing":
                return "Playing";
        }

        return super.getSmallImageText();
    }

    @Override
    public long getStartTimestamp() {
        JSONData data = (JSONData) this.data;

        if (data.contains("start_time") && data.getAttribute("details").equals("playing")) {
            return data.getIntAttribute("start_time") * 1000L;
        } else if (data.getAttribute("details").equals("stopped") || data.getAttribute("details").equals("paused")) {
            return -1;
        }
        return 0;
    }

    @Override
    public long getEndTimestamp() {
        JSONData data = (JSONData) this.data;

        if (data.contains("end_time") && data.getAttribute("details").equals("playing")) {
            return data.getIntAttribute("end_time") * 1000L;
        }
        return super.getEndTimestamp();
    }
}
