package app.interface_adapter.create_event;


import app.use_case.create_event.EventInputBoundary;
import app.use_case.create_event.EventInputData;

import java.util.List;

/**
 * Controller for the Event Use Case
 */
public class CreateEventController {

    private final EventInputBoundary userEventUseCaseInteractor;

    public CreateEventController(EventInputBoundary userEventUseCaseInteractor) {
        this.userEventUseCaseInteractor = userEventUseCaseInteractor;
    }

    /**
     * Execute the Event use Case
     * @param title
     * @param description
     * @param dateTime
     * @param capacity
     * @param latitude
     * @param longitude
     * @param tags
     * @param eventId
     * @param organizer
     */
    public void execute(String title, String description, String dateTime, int capacity, float latitude,
                        float longitude, List<String> tags, String eventId, String organizer) {
        final EventInputData eventInputData = new EventInputData( eventId, organizer,
                title, description, dateTime, capacity, latitude, longitude, tags);

        this.userEventUseCaseInteractor.execute(eventInputData);
    }

    public void switchToHomeView(){
        this.userEventUseCaseInteractor.switchToHomeView();
    }


}
