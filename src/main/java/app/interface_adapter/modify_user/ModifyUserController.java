package app.interface_adapter.modify_user;

import app.use_case.modify_user.ModifyUserInputBoundary;
import app.use_case.modify_user.ModifyUserInputData;
import app.use_case.modify_user.ModifyUserOutputBoundary;
import org.springframework.stereotype.Component;

//Handling incoming requests related to modifying user details.

@Component
public class ModifyUserController {
    private final ModifyUserInputBoundary modifyUserInputBoundary;

    public ModifyUserController(ModifyUserInputBoundary modifyUserInputBoundary) {
        this.modifyUserInputBoundary = modifyUserInputBoundary;
    }

    public void modifyUser(String username, String newUsername, String newEmail, String oldPassword, String newPassword) {
        ModifyUserInputData inputData = new ModifyUserInputData(username, newUsername, newEmail, oldPassword, newPassword);
        modifyUserInputBoundary.execute(inputData);
    }
}
