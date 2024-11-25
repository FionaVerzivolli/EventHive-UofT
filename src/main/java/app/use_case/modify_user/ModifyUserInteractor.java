package app.use_case.modify_user;

import app.entity.User.User;
import org.springframework.stereotype.Component;


@Component
public class ModifyUserInteractor implements ModifyUserInputBoundary {
    private final ModifyUserDataAccessInterface userDataAccess;
    private final ModifyUserOutputBoundary outputBoundary;


    public ModifyUserInteractor(ModifyUserDataAccessInterface userDataAccess,
                                ModifyUserOutputBoundary outputBoundary) {
        this.userDataAccess = userDataAccess;
        this.outputBoundary = outputBoundary;
    }

    @Override
    public void execute(ModifyUserInputData modifyUserInputData) {
        /**
         * Executes the modify user operation.
         * This method first retrieves the user, verifies the password, checks for
         * duplicate usernames/emails, and then updates the user's information accordingly.
         * @param modifyUserInputData The data object containing all necessary inputs for modifying user details.
         */

        // Retrieve user from the database based on the current username.
        User user = userDataAccess.getUserByUsername(modifyUserInputData.getUsername());

        // If the user is not found, send a failure response through the output boundary.
        if (user == null) {
            outputBoundary.prepareFailView("User not found.");
            return;
        }

        // Verify the provided old password before making changes.
        // If the password is incorrect, send a failure response.
        if (!user.verifyPassword(modifyUserInputData.getOldPassword())) {
            outputBoundary.prepareFailView("Invalid password provided.");
            return;
        }

        // Check if the new username is already taken by another user.
        // If it is taken, send a failure response.
        if (!user.getUsername().equals(modifyUserInputData.getNewUsername()) &&
                userDataAccess.doesUsernameExist(modifyUserInputData.getNewUsername())) {
            outputBoundary.prepareFailView("Username is already taken.");
            return;
        }

        // Check if the new email address is already taken by another user.
        // If it is taken, send a failure response.
        if (!user.getEmail().equals(modifyUserInputData.getNewEmail()) &&
                userDataAccess.doesEmailExist(modifyUserInputData.getNewEmail())) {
            outputBoundary.prepareFailView("Email is already taken.");
            return;
        }

        // Update user details with the new values.
        user.setUsername(modifyUserInputData.getNewUsername());
        user.setEmail(modifyUserInputData.getNewEmail());

        // If the new password is provided, update the user's password.
        if (!modifyUserInputData.getNewPassword().isEmpty()) {
            user.setPassword(modifyUserInputData.getNewPassword());
        }

        // Attempt to save the updated user in the database.
        boolean updateSuccessful = userDataAccess.updateUser(user);

        // If the update is successful, present the success view.
        // Otherwise, present the failure view.
        if (updateSuccessful) {
            ModifyUserOutputData outputData = new ModifyUserOutputData(user.getUsername(), "User account successfully updated.");
            outputBoundary.prepareSuccessView(outputData);
        } else {
            outputBoundary.prepareFailView("Failed to update user account.");
        }
    }
}




