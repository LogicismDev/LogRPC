package me.Logicism.LogRPC.core.data;

import org.json.JSONException;
import org.json.JSONObject;

public class BeatSaberData extends JSONData {

    private String beatSaverID;
    private boolean inMenu;
    private boolean inGamePaused;

    public BeatSaberData(JSONObject data) {
        super(data);

        try {
            if (data.has("inGamePaused")) {
                this.inGamePaused = data.getBoolean("inGamePaused");
            }
            if (data.has("beatSaverID")) {
                this.beatSaverID = data.getString("beatSaverID");
            }
            this.inMenu = this.getDetails().isEmpty();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getBeatSaverID() {
        return beatSaverID;
    }

    public boolean isInMenu() {
        return inMenu;
    }

    public boolean isInGamePaused() {
        return inGamePaused;
    }
}
