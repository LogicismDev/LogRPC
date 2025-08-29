package me.Logicism.LogRPC.presence.manual;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import com.jagrosh.discordipc.entities.PartyPrivacy;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class CustomizablePresence extends Presence implements PresenceData {

    private ActivityType activityType;
    private DisplayType displayType;
    private String details;
    private String detailsURL;
    private String state;
    private String stateURL;
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
    private PartyPrivacy partyPrivacy;

    public CustomizablePresence(long clientID, ActivityType activityType, DisplayType displayType, String details,
                                String detailsURL, String state, String stateURL, String largeImageKey,
                                String largeImageText, String smallImageKey, String smallImageText,
                                String mainButtonText, String mainButtonURL, String secondaryButtonText,
                                String secondaryButtonURL, long startTimestamp, long endTimestamp, int partySize, int maxPartySize, PartyPrivacy partyPrivacy) {
        super(clientID);

        this.activityType = activityType;
        this.displayType = displayType;
        this.details = details;
        this.detailsURL = detailsURL;
        this.state = state;
        this.stateURL = stateURL;
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
        this.partyPrivacy = partyPrivacy;
    }

    @Override
    public ActivityType getActivityType() {
        return activityType;
    }

    @Override
    public DisplayType getDisplayType() {
        return displayType;
    }

    @Override
    public String getDetails() {
        return details;
    }

    @Override
    public String getDetailsURL() {
        return detailsURL;
    }

    @Override
    public String getState() {
        return state;
    }

    @Override
    public String getStateURL() {
        return stateURL;
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
    public PartyPrivacy getPartyPrivacy() {
        return partyPrivacy;
    }

    @Override
    public String getTitle() {
        return details;
    }
}
