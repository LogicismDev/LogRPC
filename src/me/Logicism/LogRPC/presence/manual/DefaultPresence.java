package me.Logicism.LogRPC.presence.manual;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.presence.Presence;

public class DefaultPresence extends Presence {

    public DefaultPresence() {
        super(Long.parseLong(LogRPC.INSTANCE.getConfig().getDefaultClientID()));
    }

    @Override
    public ActivityType getActivityType() {
        return LogRPC.INSTANCE.getConfig().getDefaultDetails().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultDetails().equals("null") ? ActivityType.PLAYING : ActivityType.valueOf(LogRPC.INSTANCE.getConfig().getDefaultActivityType());
    }

    @Override
    public String getDetails() {
        return LogRPC.INSTANCE.getConfig().getDefaultDetails().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultDetails().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultDetails();
    }

    @Override
    public String getState() {
        return LogRPC.INSTANCE.getConfig().getDefaultState().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultState().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultState();
    }

    @Override
    public String getLargeImageKey() {
        return LogRPC.INSTANCE.getConfig().getDefaultLargeImageKey().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultLargeImageKey().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultLargeImageKey();
    }

    @Override
    public String getLargeImageText() {
        return LogRPC.INSTANCE.getConfig().getDefaultLargeImageText().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultLargeImageText().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultLargeImageText();
    }

    @Override
    public String getSmallImageKey() {
        return LogRPC.INSTANCE.getConfig().getDefaultSmallImageKey().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultSmallImageKey().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultSmallImageKey();
    }

    @Override
    public String getSmallImageText() {
        return LogRPC.INSTANCE.getConfig().getDefaultSmallImageText().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultSmallImageText().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultSmallImageText();
    }

    @Override
    public long getStartTimestamp() {
        return LogRPC.INSTANCE.getConfig().getDefaultStartTimestamp();
    }

    @Override
    public long getEndTimestamp() {
        return LogRPC.INSTANCE.getConfig().getDefaultEndTimestamp();
    }

    @Override
    public String getMainButtonText() {
        if (LogRPC.INSTANCE.getConfig().getDefaultMainButtonText().length() > 32) {
            return LogRPC.INSTANCE.getConfig().getDefaultMainButtonText().substring(0, 31);
        }

        return LogRPC.INSTANCE.getConfig().getDefaultMainButtonText().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultMainButtonText().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultMainButtonText();
    }

    @Override
    public String getMainButtonURL() {
        return LogRPC.INSTANCE.getConfig().getDefaultMainButtonURL().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultMainButtonURL().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultMainButtonURL();
    }

    @Override
    public String getSecondaryButtonText() {
        if (LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonText().length() > 32) {
            return LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonText().substring(0, 31);
        }

        return LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonText().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonText().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonText();
    }

    @Override
    public String getSecondaryButtonURL() {
        return LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonURL().isEmpty() || LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonURL().equals("null") ? null : LogRPC.INSTANCE.getConfig().getDefaultSecondaryButtonURL();
    }

    @Override
    public int getPartySize() {
        return LogRPC.INSTANCE.getConfig().getDefaultPartySize();
    }

    @Override
    public int getPartyMax() {
        return LogRPC.INSTANCE.getConfig().getDefaultMaxPartySize();
    }
}
