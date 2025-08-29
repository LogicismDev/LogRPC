package me.Logicism.LogRPC.presence.game;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.NintendoSwitchData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

import java.time.Duration;
import java.time.Instant;

public class NintendoSwitch2AutoPresence extends Presence {

    public NintendoSwitch2AutoPresence(NintendoSwitchData data) {
        super(1385689410502263016L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.PLAYING;
    }

    @Override
    public DisplayType getDisplayType() {
        NintendoSwitchData data = (NintendoSwitchData) this.data;

        if (data.getTitle().equals("Not Playing")) {
            return DisplayType.NAME;
        } else {
            return DisplayType.DETAILS;
        }
    }

    @Override
    public String getDetails() {
        NintendoSwitchData data = (NintendoSwitchData) this.data;

        return data.getTitle();
    }

    @Override
    public String getState() {
        if (LogRPC.INSTANCE.getConfig().isEnableShowingNintendoPlaytime()) {
            NintendoSwitchData data = (NintendoSwitchData) this.data;

            Duration duration = Duration.between(Instant.ofEpochSecond(data.getTotalPlaytime()), Instant.now());

            if (duration.toDays() >= 10) {
                return "Played for " + (int) (data.getTotalPlaytime() < 600 ? (Math.floor((double) data.getTotalPlaytime() / 300) * 300) / 60 : (Math.floor((double) data.getTotalPlaytime() / 60) * 60) / 60) + " hours and more";
            } else {
                if (data.getTotalPlaytime() < 60) {
                    return "Played for a little while";
                } else {
                    return "Played for " + (int) (data.getTotalPlaytime() < 600 ? (Math.floor((double) data.getTotalPlaytime() / 300) * 300) / 60 : (Math.floor((double) data.getTotalPlaytime() / 60) * 60) / 60) + " hours and " + (duration.toMinutes() % 60) + " minutes";
                }
            }
        } else {
            return "";
        }
    }

    @Override
    public String getLargeImageKey() {
        NintendoSwitchData data = (NintendoSwitchData) this.data;

        return data.getImageKey();
    }

    @Override
    public String getSmallImageKey() {
        NintendoSwitchData data = (NintendoSwitchData) this.data;

        return data.getUserImageKey();
    }

    @Override
    public String getSmallImageText() {
        return LogRPC.INSTANCE.getConfig().getNintendoSwitchFriendCode();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }

    @Override
    public String getMainButtonText() {
        return "Nintendo eShop";
    }

    @Override
    public String getMainButtonURL() {
        NintendoSwitchData data = (NintendoSwitchData) this.data;

        return data.getShopUri();
    }
}
