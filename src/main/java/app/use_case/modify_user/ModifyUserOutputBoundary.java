package app.use_case.modify_user;

/**
 * Output boundary for modifying user details.
 */
public interface ModifyUserOutputBoundary {
    /**
     * Prepares the success view for modifying user details.
     * @param outputData the output data for successful modification.
     */
    void prepareSuccessView(ModifyUserOutputData outputData);

    /**
     * Prepares the fail view for modifying user details.
     * @param errorMessage the explanation of the failure.
     */
    void prepareFailView(String errorMessage);
}

