package me.Logicism.LogRPC.presence.game;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class OverwatchPresence extends Presence {

    public OverwatchPresence(PresenceData data) {
        super(583356928688783369L, data);
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
        JSONData data = (JSONData) this.data;
        return data.getAttribute("state");
    }

    @Override
    public String getLargeImageKey() {
        JSONData data = (JSONData) this.data;
        return data.getBooleanAttribute("inQueue") ? "overwatch" : data.getAttribute("largeImageKey");
    }

    @Override
    public String getLargeImageText() {
        return "Overwatch 2 - " + super.getLargeImageText();
    }

    @Override
    public String getSmallImageKey() {
        JSONData data = (JSONData) this.data;

        if (data.getIntAttribute("ranking") != -1) {
            if (data.getIntAttribute("ranking") < 1500) {
                return "bronze";
            } else if (data.getIntAttribute("ranking") < 2000) {
                return "silver";
            } else if (data.getIntAttribute("ranking") < 2500) {
                return "gold";
            } else if (data.getIntAttribute("ranking") < 3000) {
                return "platinum";
            } else if (data.getIntAttribute("ranking") < 3500) {
                return "diamond";
            } else if (data.getIntAttribute("ranking") < 4000) {
                return "master";
            } else {
                return "grandmaster";
            }
        } else {
            return "overwatch";
        }
    }

    @Override
    public String getSmallImageText() {
        JSONData data = (JSONData) this.data;

        if (data.getIntAttribute("ranking") != -1) {
            if (data.getIntAttribute("ranking") < 1500) {
                return data.getIntAttribute("ranking") + " SR - Bronze - " + LogRPC.INSTANCE.getConfig().getOverwatchUsername();
            } else if (data.getIntAttribute("ranking") < 2000) {
                return data.getIntAttribute("ranking") + " SR - Silver - " + LogRPC.INSTANCE.getConfig().getOverwatchUsername();
            } else if (data.getIntAttribute("ranking") < 2500) {
                return data.getIntAttribute("ranking") + " SR - Gold - " + LogRPC.INSTANCE.getConfig().getOverwatchUsername();
            } else if (data.getIntAttribute("ranking") < 3000) {
                return data.getIntAttribute("ranking") + " SR - Platinum - " + LogRPC.INSTANCE.getConfig().getOverwatchUsername();
            } else if (data.getIntAttribute("ranking") < 3500) {
                return data.getIntAttribute("ranking") + " SR - Diamond - " + LogRPC.INSTANCE.getConfig().getOverwatchUsername();
            } else if (data.getIntAttribute("ranking") < 4000) {
                return data.getIntAttribute("ranking") + " SR - Masters - " + LogRPC.INSTANCE.getConfig().getOverwatchUsername();
            } else {
                return data.getIntAttribute("ranking") + " SR - Grandmasters - " + LogRPC.INSTANCE.getConfig().getOverwatchUsername();
            }
        } else {
            return LogRPC.INSTANCE.getConfig().getOverwatchUsername();
        }
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

    @Override
    public int getPartySize() {
        JSONData data = (JSONData) this.data;
        return data.getIntAttribute("partySize");
    }

    @Override
    public int getPartyMax() {
        return 5;
    }
}
