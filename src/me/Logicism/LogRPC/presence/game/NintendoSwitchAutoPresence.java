package me.Logicism.LogRPC.presence.game;

import com.jagrosh.discordipc.entities.ActivityType;
import com.jagrosh.discordipc.entities.DisplayType;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.NintendoSwitchData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;
import sun.rmi.runtime.Log;

import java.time.Duration;
import java.time.Instant;

public class NintendoSwitchAutoPresence extends Presence {

    public NintendoSwitchAutoPresence(NintendoSwitchData data) {
        super(1259967215323840564L, data);
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
        NintendoSwitchData data = (NintendoSwitchData) this.data;
        if (data.getGameObject().has("userIcon")) {
            if (data.getGameObject().getString("onlineStatus").equals("ONLINE") && data.getGameObject().isNull("vsMode") && data.getGameObject().isNull("coopRule")) {
                return "Online";
            } else if (data.getGameObject().getString("onlineStatus").startsWith("VS_MODE") && !data.getGameObject().isNull("vsMode")) {
                String stateDesc = "";
                if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("REGULAR")) {
                    stateDesc = "Regular Battle";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("BANKARA")) {
                    stateDesc = "Anarchy Battle";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("FEST")) {
                    stateDesc = "Splatfest Battle";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("LEAGUE")) {
                    stateDesc = "League Battle";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("X Battle")) {
                    stateDesc = "X Battle";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("VnNNb2RlLTI=")) {
                    stateDesc = "Anarchy Battle (Series)";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("VnNNb2RlLTUx")) {
                    stateDesc = "Anarchy Battle (Open)";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("VnNNb2RlLTY=")) {
                    stateDesc = "Splatfest Battle (Open)";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("VnNNb2RlLTc=")) {
                    stateDesc = "Splatfest Battle (Pro)";
                } else if (data.getGameObject().getJSONObject("vsMode").getString("mode").equals("VnNNb2RlLTg=")) {
                    stateDesc = "Tricolour Battle";
                }

                return "VS Mode - " + (data.getGameObject().getString("onlineStatus").endsWith("_MATCHING") ? "In Lobby" : "In Game") + (stateDesc.isEmpty() ? "" : " (" + stateDesc + ")");
            } else if (data.getGameObject().getString("onlineStatus").startsWith("COOP_MODE") && !data.getGameObject().isNull("coopRule")) {
                String stateDesc = "";
                if (data.getGameObject().getString("coopMode").equals("REGULAR")) {
                    stateDesc = "Salmon Run";
                } else if (data.getGameObject().getString("coopMode").equals("BIG_RUN")) {
                    stateDesc = "Big Run";
                }

                return "Co-Op Mode" + (data.getGameObject().getString("onlineStatus").endsWith("_MATCHING") ? "In Lobby" : "In Game") + (stateDesc.isEmpty() ? "" : " (" + stateDesc + ")");
            } else {
                Duration duration = Duration.between(Instant.ofEpochSecond(data.getFirstPlayedAt()), Instant.now());

                return "Played for " + (data.getTotalPlaytime() < 600 ? (Math.floor((double) data.getTotalPlaytime() / 300) * 300) / 60 : (Math.floor((double) data.getTotalPlaytime() / 60) * 60) / 60) + " Hours and " + (duration.toMinutes() % 60) + " Minutes";
            }
        } else {
            if (LogRPC.INSTANCE.getConfig().isEnableShowingNintendoPlaytime()) {
                if (!data.getGameObject().getJSONObject("result").getJSONObject("presence").getString("state").equals("INACTIVE")) {
                    Duration duration = Duration.between(Instant.ofEpochSecond(data.getTotalPlaytime()), Instant.now());

                    if (duration.toDays() >= 10) {
                        return "Played for " + (data.getTotalPlaytime() < 600 ? (Math.floor((double) data.getTotalPlaytime() / 300) * 300) / 60 : (Math.floor((double) data.getTotalPlaytime() / 60) * 60) / 60) + " hours and more";
                    } else {
                        if (data.getTotalPlaytime() < 60) {
                            return "Played for a little while";
                        } else {
                            return "Played for " + (data.getTotalPlaytime() < 600 ? (Math.floor((double) data.getTotalPlaytime() / 300) * 300) / 60 : (Math.floor((double) data.getTotalPlaytime() / 60) * 60) / 60) + " hours and " + (duration.toMinutes() % 60) + " minutes";
                        }
                    }
                }
            } else {
                return "";
            }
        }

        return "";
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
