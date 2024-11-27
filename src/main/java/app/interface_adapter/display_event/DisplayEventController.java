package app.interface_adapter.display_event;


import app.entity.Event.CommonEvent;
import app.use_case.display_event.DisplayEventInputBoundary;
import app.use_case.display_event.DisplayEventInputData;
import app.use_case.display_event.DisplayEventInteractor;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for the Event Use Case
 */
public class DisplayEventController {

    private final DisplayEventInputBoundary displayEventInteractor;

    public DisplayEventController(DisplayEventInputBoundary displayEventInteractor) {
        this.displayEventInteractor = displayEventInteractor;
    }

    /**
     * Execute the create Event use Case
     */
    public ArrayList<CommonEvent> execute() {
        return this.displayEventInteractor.execute();
    }


}
