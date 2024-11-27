package app.use_case.view_event;

import app.entity.Event.Event;
import java.util.List;

public class ViewEventOutputData {
    private final Event event;

    public ViewEventOutputData(Event event) {
        this.event = event;
    }

    public Event getEvent() {
        return event;
    }
}