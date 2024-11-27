package app.use_case.create_event;


/**
 * The output boundary for the Event User Use Case
 * TODO: Note: this is implemented in Event Presenter
 */
public interface EventOutputBoundary {

    /**
     * Prepares the success view for the Event user use case
     * This will probably just lead to the main screen of the application
     * whatever that ends up being
     * @param outputData the output data
     */
    void prepareSuccessView(EventOutputData outputData);

    /**
     * Prepares the failure view for the Event user use case.
     * In this case, the result will probably be that you stay in the Event
     * view but it tells you there is an error with what you have done
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    void switchToHomeView();

}