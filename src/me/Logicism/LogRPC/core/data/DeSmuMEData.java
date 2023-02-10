package me.Logicism.LogRPC.core.data;

import org.json.JSONException;
import org.json.JSONObject;

public class DeSmuMEData implements PresenceData {

    private String gameName;
    private String gameVer;
    private String imageKey;
    private String mapName;

    public DeSmuMEData(String gameName, String gameVer, String imageKey, String mapName) {
        this.gameName = gameName;
        this.gameVer = gameVer;
        this.imageKey = imageKey;
        this.mapName = mapName;
    }

    public String getGameVersion() {
        return gameVer;
    }

    public String getImageKey() {
        return imageKey;
    }

    public String getMapName() {
        return mapName;
    }

    @Override
    public String getTitle() {
        return gameName;
    }
}
