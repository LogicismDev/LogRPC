package me.Logicism.LogRPC.presence.manual;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class CustomizablePresence extends Presence implements PresenceData {

    private ActivityType activityType;
    private String details;
    private String state;
    private String largeImageKey;
    private String largeImageText;
    private String smallImageKey;
    private String smallImageText;
    private String mainButtonText;
    private String mainButtonURL;
    private String secondaryButtonText;
    private String secondaryButtonURL;
    private long startTimestamp;
    private long endTimestamp;
    private int partySize;
    private int maxPartySize;

    public CustomizablePresence(long clientID, ActivityType activityType, String details, String state, String largeImageKey,
                                String largeImageText, String smallImageKey, String smallImageText,
                                String mainButtonText, String mainButtonURL, String secondaryButtonText,
                                String secondaryButtonURL, long startTimestamp, long endTimestamp, int partySize, int maxPartySize) {
        super(clientID);

        this.activityType = activityType;
        this.details = details;
        this.state = state;
        this.largeImageKey = largeImageKey;
        this.largeImageText = largeImageText;
        this.smallImageKey = smallImageKey;
        this.smallImageText = smallImageText;
        this.mainButtonText = mainButtonText;
        this.mainButtonURL = mainButtonURL;
        this.secondaryButtonText = secondaryButtonText;
        this.secondaryButtonURL = secondaryButtonURL;
        this.startTimestamp = startTimestamp;
        this.endTimestamp = endTimestamp;
        this.partySize = partySize;
        this.maxPartySize = maxPartySize;
    }

    @Override
    public ActivityType getActivityType() {
        return activityType;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getLargeImageKey() {
        return largeImageKey;
    }

    @Override
    public String getLargeImageText() {
        return largeImageText;
    }

    @Override
    public String getSmallImageKey() {
        return smallImageKey;
    }

    @Override
    public String getSmallImageText() {
        return smallImageText;
    }

    @Override
    public String getMainButtonText() {
        if (!mainButtonText.isEmpty() && mainButtonText.length() > 32) {
            return mainButtonText.substring(0, 31);
        }

        return mainButtonText.isEmpty() ? null : mainButtonText;
    }

    @Override
    public String getMainButtonURL() {
        return mainButtonURL;
    }

    @Override
    public String getSecondaryButtonText() {
        if (!secondaryButtonText.isEmpty() && secondaryButtonText.length() > 32) {
            return secondaryButtonText.substring(0, 31);
        }

        return secondaryButtonText.isEmpty() ? null : secondaryButtonText;
    }

    @Override
    public String getSecondaryButtonURL() {
        return secondaryButtonURL;
    }

    @Override
    public long getStartTimestamp() {
        return startTimestamp;
    }

    @Override
    public long getEndTimestamp() {
        return endTimestamp;
    }

    @Override
    public int getPartySize() {
        return partySize;
    }

    @Override
    public int getPartyMax() {
        return maxPartySize;
    }

    @Override
    public String getTitle() {
        return details;
    }
}
