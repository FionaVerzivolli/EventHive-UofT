package app.interface_adapter.filter_event;

import app.interface_adapter.ViewManagerModel;
import app.use_case.filter_event.FilterEventOutputBoundary;
import app.use_case.filter_event.FilterEventOutputData;
import app.entity.Event.Event;
import java.util.List;
import app .view.FilterEventView;

/**
 * The presenter for the Filter Event Use Case
 */
public class FilterEventPresenter implements FilterEventOutputBoundary{

    private final ViewManagerModel viewManagerModel;
    private final FilterEventViewModel filterEventViewModel;

    public FilterEventPresenter(ViewManagerModel viewManagerModel,
                                FilterEventViewModel filterEventViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.filterEventViewModel = filterEventViewModel;
    }

    /**
     * Prepares the succeess view for the Filter Event Use case which would be a list of the
     * Events matching the filter
     * @param outputData The output data including the list of filtered events.
     */
    @Override
    public void prepareSuccessView(FilterEventOutputData outputData) {
        final FilterEventState filterEventState = filterEventViewModel.getState();
        filterEventState.setFilteredEvents(outputData.getFilteredEvents());
        this.filterEventViewModel.setState(filterEventState);
        filterEventViewModel.firePropertyChanged();

    }

    /**
     * Prepares the fail view for the Filter Event use case, probably just hte same as the
     * success view except it says its empty
     * @param errorMessage The explanation of the failure.
     */
    @Override
    public void prepareFailView(String errorMessage) {

    }
}
