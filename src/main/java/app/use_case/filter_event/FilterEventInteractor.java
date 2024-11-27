package app.use_case.filter_event;

import app.entity.Event.Event;
import java.util.List;

// The FilterEventInteractor filtering events in the application, ituses the Event Data Access to
// retrieve relevant events and sends the results through the presenter.

public class FilterEventInteractor implements FilterEventInputBoundary {

    private final FilterEventUserDataAccessInterface filterEventDataAccessObject;
    private final FilterEventOutputBoundary filterEventPresenter;

    public FilterEventInteractor(FilterEventUserDataAccessInterface eventDataAccessObject,
                                 FilterEventOutputBoundary filterEventPresenter) {
        this.filterEventDataAccessObject = eventDataAccessObject;
        this.filterEventPresenter = filterEventPresenter;
    }

    @Override
    public void execute(FilterEventInputData filterEventInputData) {
        // Retrieve events based on the provided criteria.
        System.out.println(filterEventDataAccessObject);
        List<Event> filteredEvents = filterEventDataAccessObject.findEvents(filterEventInputData.getTags());
        final FilterEventOutputData filterEventOutputData = new FilterEventOutputData(filteredEvents);
//        for (Event event: filteredEvents){
//                System.out.println(event.getTitle());
//                System.out.println("diddy");
//        }

        filterEventPresenter.prepareSuccessView(filterEventOutputData);


//        if (filteredEvents.isEmpty()) {
//            filterEventPresenter.prepareFailView("No events found matching the criteria.");
//        } else {
//            FilterEventOutputData outputData = new FilterEventOutputData(filteredEvents);
//            filterEventPresenter.prepareSuccessView(outputData);
//        }
    }
}