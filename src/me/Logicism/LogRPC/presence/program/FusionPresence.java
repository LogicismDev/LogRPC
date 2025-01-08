package me.Logicism.LogRPC.presence.program;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class FusionPresence extends Presence {

    public FusionPresence(PresenceData data) {
        super(835587751239090187L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().split(" - ").length == 1) {
            return "No Game Open";
        } else {
            return data.getTitle().split(" - ")[2];
        }
    }

    @Override
    public String getState() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().split(" - ").length != 1) {
            switch (data.getTitle().split(" - ")[1]) {
                case "GameGear":
                    return "Game Gear";
                case "MegaDrive":
                    return "Mega Drive";
                case "MegaDrive/32X":
                    return "Mega Drive / 32X";
                case "Genesis":
                    return "Genesis";
                case "Genesis/32X":
                    return "Genesis / 32X";
                case "MegaCD":
                    return "Mega CD";
                case "SegaCD":
                    return "Sega CD";
            }
        }

        return "";
    }

    @Override
    public String getLargeImageKey() {
        return "md";
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

}
