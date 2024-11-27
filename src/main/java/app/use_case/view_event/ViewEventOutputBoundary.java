package app.use_case.view_event;

/**
 * The output boundary for the View Event Use Case.
 */
public interface ViewEventOutputBoundary {
    /**
     * Prepares the success view for the View Event use case.
     * @param outputData The output data including the list of relevant events.
     */
    void prepareSuccessView(ViewEventOutputData outputData);

    /**
     * Prepares the failure view for the View Event use case.
     * @param errorMessage The explanation of the failure.
     */
    void prepareFailView(String errorMessage);

    void switchToHomeView();
}