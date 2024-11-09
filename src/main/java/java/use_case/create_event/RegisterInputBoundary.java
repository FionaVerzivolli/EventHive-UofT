package main.java.java.use_case.create_event;

import main.java.java.use_case.create_event.RegisterInputData;

/**
 * Input Boundary for the actions relating to registration.
 */
public interface RegisterInputBoundary {

    /**
     * Execute the registration use case.
     * @param registerInputData the input data
     */
    void execute(RegisterInputData registerInputData);

    /**
     * Executes the switch to the login view use case once
     * registered.
     * TODO note this idea of switching to different usecases after a certain
     * use case outcome
     */
    void switchToLoginView();
}

