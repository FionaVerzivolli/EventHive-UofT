package app.interface_adapter.display_event;

import app.interface_adapter.ViewManagerModel;
import app.use_case.display_event.DisplayEventOutputBoundary;
import app.use_case.display_event.DisplayEventOutputData;
import app.view.DisplayEventView;

/**
 * The Presenter for the create Event Use Case
 */
public class DisplayEventPresenter implements DisplayEventOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final DisplayEventViewModel displayEventViewModel;

    public DisplayEventPresenter(ViewManagerModel viewManagerModel,
                                DisplayEventViewModel displayEventViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.displayEventViewModel = displayEventViewModel;
    }

    /**
     * Prepares the success view for the Event user use case
     * This will probably just lead to the main screen of the application
     * whatever that ends up being
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(DisplayEventOutputData outputData) {
        final DisplayEventState displayEventState = displayEventViewModel.getState();
        displayEventState.setEvents(outputData.getEvents());
        displayEventViewModel.firePropertyChanged();
    }

    /**
     * Prepares the failure view for the Event user use case.
     * In this case, the result will probably be that you stay in the Event
     * view but it tells you there is an error with what you have done
     *
     * @param errorMessage the explanation of the failure
     */
    @Override
    public void prepareFailView(String errorMessage) {
        final DisplayEventState displayEventState = displayEventViewModel.getState();
        displayEventViewModel.firePropertyChanged();
    }
}