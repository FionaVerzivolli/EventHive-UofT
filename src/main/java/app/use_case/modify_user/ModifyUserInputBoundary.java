package app.use_case.modify_user;

public interface ModifyUserInputBoundary {
    /**
     * Executes the modify user use case.
     * @param modifyUserInputData An object containing all the information needed to modify a user's account details.
     *                            It includes fields such as current username, new username, new email, old password, and new password.
     */
    void execute(ModifyUserInputData modifyUserInputData);

}
