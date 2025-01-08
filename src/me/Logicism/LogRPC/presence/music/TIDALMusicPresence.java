package me.Logicism.LogRPC.presence.music;

import com.jagrosh.discordipc.entities.ActivityType;
import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class TIDALMusicPresence extends Presence {

    public TIDALMusicPresence(PresenceData data) {
        super(584458858731405315L, data);
    }

    @Override
    public ActivityType getActivityType() {
        return ActivityType.LISTENING;
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        return data.getTitle();
    }

    @Override
    public String getState() {
        JSONData data = (JSONData) this.data;

        return data.getDetails();
    }

    @Override
    public String getLargeImageKey() {
        JSONData data = (JSONData) this.data;

        if (!data.getAttribute("album_artwork_url").isEmpty()) {
            return data.getAttribute("album_artwork_url");
        }

        return "tidal";
    }

    @Override
    public String getLargeImageText() {
        JSONData data = (JSONData) this.data;

        return data.getAttribute("album") + " - " + super.getLargeImageText();
    }

    @Override
    public String getMainButtonText() {
        JSONData data = (JSONData) this.data;

        if (!data.getAttribute("song_url").isEmpty()) {
            return "Listen on TIDAL";
        }

        return super.getMainButtonText();
    }

    @Override
    public String getMainButtonURL() {
        JSONData data = (JSONData) this.data;

        if (!data.getAttribute("song_url").isEmpty()) {
            return data.getAttribute("song_url");
        }

        return super.getMainButtonURL();
    }

}
