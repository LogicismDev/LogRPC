package me.Logicism.LogRPC.presence;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.PresenceData;

public abstract class Presence {

    protected long clientID;
    protected PresenceData data;

    public Presence(long clientID) {
        this.clientID = clientID;
    }

    public Presence(long clientID, PresenceData data) {
        this.clientID = clientID;
        this.data = data;
    }

    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    public String getDetails() {
        return "none";
    }

    public String getState() {
        return "";
    }

    public String getLargeImageKey() {
        return null;
    }

    public String getLargeImageText() {
        return "LogRPC v" + LogRPC.VERSION;
    }

    public String getSmallImageKey() {
        return null;
    }

    public String getSmallImageText() {
        return null;
    }

    public String getMainButtonText() {
        return null;
    }

    public String getMainButtonURL() {
        return null;
    }

    public String getSecondaryButtonText() {
        return null;
    }

    public String getSecondaryButtonURL() {
        return null;
    }

    public long getStartTimestamp() { return -1; }

    public long getEndTimestamp() { return -1; }

    public int getPartySize() {
        return 0;
    }

    public int getPartyMax() {
        return 0;
    }

    public long getClientID() {
        return clientID;
    }

    public void setData(PresenceData data) {
        this.data = data;
    }
}
