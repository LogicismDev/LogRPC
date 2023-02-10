package me.Logicism.LogRPC.core.data;

import me.Logicism.LogRPC.LogRPC;
import org.json.JSONException;
import org.json.JSONObject;

public class WiimmfiData implements PresenceData {

    private String trackName;
    private String roomType;
    private int playersActive = 0;
    private long raceStart = 0;
    private String regionType;
    private int points = -1;

    public WiimmfiData(JSONObject object, String roomType) {
        try {
            try {
                trackName = object.getJSONArray("track").getString(2);
            } catch (JSONException e) {
                trackName = "";
            }
            if (trackName.isEmpty()) {
                trackName = "Playing Custom Mode";
            } else {
                raceStart = object.getLong("race_start");
            }


            for (int i = 0; i < object.getJSONArray("members").length(); i++) {
                JSONObject j = object.getJSONArray("members").getJSONObject(i);

                if (j.getString("ol_role").equals("host") || j.getString("ol_role").equals("guest")) {
                    playersActive++;
                }

                if (j.getString("fc").equals(LogRPC.INSTANCE.getConfig().getWiimmfiFriendCode())) {
                    regionType = j.getString("region");

                    if (!j.getString("rk").isEmpty()) {
                        if (j.getString("rk").equals("vs")) {
                            points = j.getInt("ev");
                        } else {
                            points = j.getInt("eb");
                        }
                    }

                    if (j.getString("ol_role").equals("viewer")) {
                        trackName = "Spectating Match";
                    } else if (j.getString("ol_role").equals("connect")) {
                        trackName = "Connecting to Match";
                    }
                }
            }

            this.roomType = roomType;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getTitle() {
        return trackName;
    }

    public String getRoomType() {
        return roomType;
    }

    public int getPlayersActive() {
        return playersActive;
    }

    public long getRaceStart() {
        return raceStart;
    }

    public int getPoints() {
        return points;
    }

    public String getRegionType() {
        return regionType;
    }
}
