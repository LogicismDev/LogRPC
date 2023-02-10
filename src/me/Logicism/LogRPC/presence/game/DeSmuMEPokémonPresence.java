package me.Logicism.LogRPC.presence.game;

import me.Logicism.LogRPC.core.data.DeSmuMEData;
import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.presence.Presence;

public class DeSmuMEPokémonPresence extends Presence {

    public DeSmuMEPokémonPresence(PresenceData data) {
        super(510206500052926496L, data);
    }

    @Override
    public String getDetails() {
        DeSmuMEData deSmuMEData = (DeSmuMEData) data;

        return deSmuMEData.getTitle();
    }

    @Override
    public String getState() {
        DeSmuMEData deSmuMEData = (DeSmuMEData) data;

        return deSmuMEData.getMapName();
    }

    @Override
    public String getLargeImageKey() {
        DeSmuMEData deSmuMEData = (DeSmuMEData) data;

        return deSmuMEData.getImageKey();
    }

    @Override
    public String getLargeImageText() {
        DeSmuMEData deSmuMEData = (DeSmuMEData) data;

        return deSmuMEData.getGameVersion() + " - " + super.getLargeImageText();
    }

    @Override
    public long getStartTimestamp() {
        return 0;
    }
}
