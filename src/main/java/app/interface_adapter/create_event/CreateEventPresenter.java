package app.interface_adapter.create_event;

import app.interface_adapter.ViewManagerModel;
import app.interface_adapter.home.HomeViewModel;
import app.use_case.create_event.EventOutputBoundary;
import app.use_case.create_event.EventOutputData;
import app.view.CreateEventView;

/**
 * The Presenter for the create Event Use Case
 */
public class CreateEventPresenter implements EventOutputBoundary {
    private final ViewManagerModel viewManagerModel;
    private final CreateEventViewModel createEventViewModel;
    private final HomeViewModel homeViewModel;

    public CreateEventPresenter(ViewManagerModel viewManagerModel,
                                CreateEventViewModel createEventViewModel,
                                HomeViewModel homeViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.createEventViewModel = createEventViewModel;
        this.homeViewModel = homeViewModel;
    }

    /**
     * Prepares the success view for the Event user use case
     * This will probably just lead to the main screen of the application
     * whatever that ends up being
     *
     * @param outputData the output data
     */
    @Override
    public void prepareSuccessView(EventOutputData outputData) {

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
        final CreateEventState createEventState = createEventViewModel.getState();
        createEventState.setCapacityError(errorMessage); // Not sure which error I should use
        createEventViewModel.firePropertyChanged();
    }

    @Override
    public void switchToHomeView(){
        viewManagerModel.setState(homeViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}