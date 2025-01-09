package me.Logicism.LogRPC.core.data;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONData implements PresenceData {

    private String title;
    private String details;
    private JSONObject data;

    public JSONData(JSONObject data) {
        try {
            if (data.has("title")) {
                this.title = data.getString("title");
            }
            this.details = data.getString("details");
            this.data = data;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public boolean contains(String key) {
        return data.has(key);
    }

    public String getAttribute(String key) {
        try {
            return data.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return "";
    }

    public int getIntAttribute(String key) {
        try {
            return data.getInt(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return -1;
    }

    public boolean getBooleanAttribute(String key) {
        try {
            return data.getBoolean(key);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return false;
    }

}
