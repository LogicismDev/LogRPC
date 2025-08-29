package me.Logicism.LogRPC.core.data;

import org.json.JSONObject;

public class NintendoSwitchData implements PresenceData {

    private String gameName;
    private int platform;
    private long totalPlaytime;
    private long firstPlayedAt;
    private String imageKey;
    private String userImageKey;
    private String shopUri;
    private JSONObject gameObject;

    public NintendoSwitchData(String gameName, int platform, long totalPlaytime, long firstPlayedAt, String imageKey, String userImageKey, String shopUri, JSONObject gameObject) {
        this.gameName = gameName;
        this.platform = platform;
        this.totalPlaytime = totalPlaytime;
        this.firstPlayedAt = firstPlayedAt;
        this.imageKey = imageKey;
        this.userImageKey = userImageKey;
        this.shopUri = shopUri;
        this.gameObject = gameObject;
    }

    public int getPlatform() {
        return platform;
    }

    public long getTotalPlaytime() {
        return totalPlaytime;
    }

    public long getFirstPlayedAt() {
        return firstPlayedAt;
    }

    public String getImageKey() {
        return imageKey;
    }

    public String getUserImageKey() {
        return userImageKey;
    }

    public String getShopUri() {
        return shopUri;
    }

    public JSONObject getGameObject() {
        return gameObject;
    }

    @Override
    public String getTitle() {
        return gameName;
    }
}
