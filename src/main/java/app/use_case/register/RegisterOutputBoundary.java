package app.use_case.register;


/**
 * The output boundary for the Register User Use Case
 * TODO: Note: this is implemented in RegisterPresenter
 */
public interface RegisterOutputBoundary {

    /**
     * Prepares the success view for the Register user use case
     * This will probably just lead to the main screen of the application
     * whatever that ends up being
     *
     * @param outputData the output data
     * @param s
     */
    void prepareSuccessView(RegisterOutputData outputData, String s);

    /**
     * Prepares the failure view for the Register user use case.
     * In this case, the result will probably be that you stay in the register
     * view but it tells you there is an error with what you have done
     * @param errorMessage the explanation of the failure
     */
    void prepareFailView(String errorMessage);

    /**
     * Switches to Login View
     */
    void switchToLoginView();
}
