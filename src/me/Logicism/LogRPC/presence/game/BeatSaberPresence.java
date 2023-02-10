package me.Logicism.LogRPC.presence.game;

import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.data.BeatSaberData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class BeatSaberPresence extends Presence {

    public BeatSaberPresence(PresenceData data) {
        super(708741346403287113L, data);
    }

    @Override
    public String getDetails() {
        BeatSaberData data = (BeatSaberData) this.data;

        return data.getTitle();
    }

    @Override
    public String getState() {
        BeatSaberData data = (BeatSaberData) this.data;

        return data.getDetails() + (data.isInGamePaused() ? " (Paused)" : "");
    }

    @Override
    public String getLargeImageKey() {
        return "beat_saber_logo";
    }

    @Override
    public String getLargeImageText() {
        if (LogRPC.INSTANCE.getConfig().isEnableHrReaderForBeatSaber()) {
            return "${hrRate} BPM - LogRPC v" + LogRPC.VERSION;
        } else {
            return "LogRPC v" + LogRPC.VERSION;
        }
    }

    @Override
    public String getSmallImageKey() {
        return "beat_saber_block";
    }

    @Override
    public String getSmallImageText() {
        BeatSaberData data = (BeatSaberData) this.data;

        if (data.isInMenu()) {
            return "In the Menu";
        } else {
            return "In Game" + (data.isInGamePaused() ? " (Paused)" : "");
        }
    }

    @Override
    public String getMainButtonText() {
        BeatSaberData data = (BeatSaberData) this.data;

        if (data.getBeatSaverID() != null) {
            return "View Map on BeatSaver";
        }

        return null;
    }

    @Override
    public String getMainButtonURL() {
        BeatSaberData data = (BeatSaberData) this.data;

        if (data.getBeatSaverID() != null) {
            return "https://beatsaver.com/beatmap/" + data.getBeatSaverID();
        }

        return null;
    }

    @Override
    public String getSecondaryButtonText() {
        BeatSaberData data = (BeatSaberData) this.data;

        if (data.getBeatSaverID() != null) {
            return "Preview Map";
        }

        return null;
    }

    @Override
    public String getSecondaryButtonURL() {
        BeatSaberData data = (BeatSaberData) this.data;

        if (data.getBeatSaverID() != null) {
            return "https://skystudioapps.com/bs-viewer/?id=" + data.getBeatSaverID();
        }

        return null;
    }
}
