package app.use_case.view_event;

import app.entity.Event.Event;
import app.use_case.view_event.ViewEventInputData;
import java.util.List;


public interface ViewEventUserDataAccessInterface {

    Event viewEvent(String title);
    // TODO: need to verify after API integration and method implementation in Service Layer
}
