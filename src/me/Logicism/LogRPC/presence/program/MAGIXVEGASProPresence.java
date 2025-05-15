package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class MAGIXVEGASProPresence extends Presence {

    public MAGIXVEGASProPresence(PresenceData data) {
        super(819020138463887392L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - VEGAS Pro 14.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 14.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 15.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 15.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 16.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 16.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 17.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 17.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 18.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 18.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 19.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 19.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 20.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 20.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 21.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 20.0".length()));
        } else if (data.getTitle().contains(" - VEGAS Pro 22.0")) {
            return data.getTitle().substring(0, data.getTitle().length() - (" - VEGAS Pro 20.0".length()));
        } else {
            return "Editing a Video";
        }
    }

    @Override
    public String getLargeImageKey() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().contains(" - VEGAS Pro 14.0")) {
            return "vegaspro14";
        } else if (data.getTitle().contains(" - VEGAS Pro 15.0") || data.getTitle().contains(" - VEGAS Pro 16.0") || data.getTitle().contains(" - VEGAS Pro 17.0") || data.getTitle().contains(" - VEGAS Pro 18.0")) {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/3/39/Vegas_Pro_15.0.png/240px-Vegas_Pro_15.0.png";
        } else if (data.getTitle().contains(" - VEGAS Pro 19.0") || data.getTitle().contains(" - VEGAS Pro 20.0")) {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2d/Vegas_Pro_19.svg/240px-Vegas_Pro_19.svg.png";
        } else if (data.getTitle().contains(" - VEGAS Pro 21.0") || data.getTitle().contains(" - VEGAS Pro 22.0")) {
            return "https://upload.wikimedia.org/wikipedia/commons/thumb/8/81/Vegas_Pro_21_logo.svg/240px-Vegas_Pro_21_logo.svg.png";
        }

        return null;
    }

    @Override
    public String getLargeImageText() {
        if (data.getTitle().contains(" - VEGAS Pro 14.0")) {
            return "Version 14.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 15.0")) {
            return "Version 15.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 16.0")) {
            return "Version 16.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 17.0")) {
            return "Version 17.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 18.0")) {
            return "Version 18.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 19.0")) {
            return "Version 19.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 20.0")) {
            return "Version 20.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 21.0")) {
            return "Version 21.0 - " + super.getLargeImageText();
        } else if (data.getTitle().contains(" - VEGAS Pro 22.0")) {
            return "Version 22.0 - " + super.getLargeImageText();
        } else {
            return super.getLargeImageText();
        }
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
