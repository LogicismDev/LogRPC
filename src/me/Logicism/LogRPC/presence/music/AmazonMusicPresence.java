package me.Logicism.LogRPC.presence.music;

import me.Logicism.LogRPC.core.data.JSONData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class AmazonMusicPresence extends Presence {

    public AmazonMusicPresence(PresenceData data) {
        super(1063100070918094900L, data);
    }

    @Override
    public String getDetails() {
        JSONData data = (JSONData) this.data;

        if (data.getTitle().isEmpty() && !data.getDetails().isEmpty()) {
            return "Unknown Song";
        }

        return data.getTitle();
    }

    @Override
    public String getState() {
        JSONData data = (JSONData) this.data;

        if (!data.getTitle().isEmpty() && data.getDetails().isEmpty()) {
            return "Unknown Artist";
        }

        return data.getDetails();
    }

    @Override
    public String getLargeImageKey() {
        JSONData data = (JSONData) this.data;

        if (!data.getAttribute("album_artwork_url").isEmpty()) {
            return data.getAttribute("album_artwork_url");
        }

        return "amazon_music";
    }

    @Override
    public String getLargeImageText() {
        JSONData data = (JSONData) this.data;

        if (!data.getAttribute("album").isEmpty()) {
            return data.getAttribute("album") + " - " + super.getLargeImageText();
        }

        return super.getLargeImageText();
    }

    @Override
    public String getMainButtonText() {
        JSONData data = (JSONData) this.data;

        if (!data.getAttribute("song_url").isEmpty()) {
            return "Listen on Amazon Music";
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
