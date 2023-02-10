package me.Logicism.LogRPC.event;

import me.Logicism.LogRPC.core.data.PresenceData;
import me.Logicism.LogRPC.core.event.Event;
import me.Logicism.LogRPC.core.presence.PresenceType;

public class UpdatePresenceEvent extends Event {

    private PresenceData data;

    public UpdatePresenceEvent(PresenceType type, PresenceData data) {
        this.type = type;
        this.data = data;
    }

    public PresenceData getData() {
        return data;
    }
}
