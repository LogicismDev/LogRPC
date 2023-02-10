package me.Logicism.LogRPC.core.event;

import com.jagrosh.discordipc.entities.User;
import me.Logicism.LogRPC.LogRPC;
import me.Logicism.LogRPC.core.presence.PresenceType;

public abstract class Event {

    protected PresenceType type;

    public PresenceType getType() {
        return type;
    }

    public LogRPC getInstance() {
        return LogRPC.INSTANCE;
    }

    public User getCurrentUser() {
        return LogRPC.INSTANCE.getClient().getCurrentUser();
    }

}
