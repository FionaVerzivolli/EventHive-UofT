package app.interface_adapter.view_event;

import app.entity.Event.Event;

public class ViewEventState {
    public Event event;

    public Event getViewEvent() {
        return this.event;
    }

    public void setViewEvent(Event event) {
        this.event = event;
    }
}

