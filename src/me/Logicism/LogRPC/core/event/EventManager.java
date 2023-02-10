package me.Logicism.LogRPC.core.event;

import me.Logicism.LogRPC.event.UpdatePresenceEvent;

import java.util.LinkedList;
import java.util.List;

public class EventManager {

    private List<EventHandler> events = new LinkedList<>();

    public void registerEventHandler(EventHandler handle) {
        events.add(handle);
    }

    public void callEvent(Event e) {
        for (EventHandler eventHandler : events) {
            if (e instanceof UpdatePresenceEvent) {
                eventHandler.onUpdatePresence((UpdatePresenceEvent) e);
            }
        }
    }

}
