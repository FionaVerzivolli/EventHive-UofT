package app.interface_adapter.modify_user;

import app.use_case.modify_user.ModifyUserOutputBoundary;
import app.use_case.modify_user.ModifyUserOutputData;
import org.springframework.stereotype.Component;

@Component
public class ModifyUserPresenter implements ModifyUserOutputBoundary {

    private String message;
    private boolean success;

    @Override
    public void prepareSuccessView(ModifyUserOutputData outputData) {
        this.message = outputData.getMessage();
        this.success = true;
        // TODO: need update
    }

    @Override
    public void prepareFailView(String errorMessage) {
        this.message = errorMessage;
        this.success = false;
        // TODO: need update
    }

    public boolean isSuccess() {
        return success;
    }

    public String getMessage() {
        return message;
    }
}
