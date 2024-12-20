package app.interface_adapter.create_event;

import app.interface_adapter.ViewModel;

/**
 * ViewModel class for the Create Event feature.
 * This class manages the state and labels for the Create Event UI.
 * It extends the {@link ViewModel} class and uses {@link CreateEventState} as its state.
 */
public class CreateEventViewModel extends ViewModel<CreateEventState> {
    public static final String TITLE_LABEL = "Event View";
    public static final String EVENT_TITLE_LABEL = "Choose event title";
    public static final String EVENT_DESCRIPTION_LABEL = "Set description";
    public static final String EVENT_DATETIME_LABEL = "Set date and time";
    public static final String EVENT_CAPACITY_LABEL = "Set capacity";
    public static final String EVENT_LATITUDE_LABEL = "Set event Latitude";
    public static final String EVENT_LONGITUDE_LABEL = "Set event Longitude";
    public static final String EVENT_TAGS_LABEL = "Set event tags";

    public static final String CREATE_EVENT_BUTTON_LABEL = "Create Event";
    // public static final String CANCEL_BUTTON_LABEL = "Cancel";

    public CreateEventViewModel() {
        super("createEvent");
        setState(new CreateEventState());
    }

}
