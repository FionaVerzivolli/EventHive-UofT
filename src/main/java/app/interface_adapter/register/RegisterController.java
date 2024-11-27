package app.interface_adapter.register;


import app.use_case.register.RegisterInputBoundary;
import app.use_case.register.RegisterInputData;


/**
 * Controller for the Register Use Case
 */
public class RegisterController {

    private final RegisterInputBoundary userRegisterUseCaseInteractor;

    public RegisterController(RegisterInputBoundary userRegisterUseCaseInteractor) {
        this.userRegisterUseCaseInteractor = userRegisterUseCaseInteractor;
    }

    /**
     * Execute the Register use Case
     * @param username
     * @param email
     * @param password
     * TODO: Naoroj: Note that the registerController takes in the use case interactor and once it prepares
     * the data into the data structure inputData, it executes the use case interactor
     */
    public void execute(String username, String email, String password) {
        final RegisterInputData registerInputData = new RegisterInputData(
                username, email, password);

        this.userRegisterUseCaseInteractor.execute(registerInputData);
    }

    /**
     * Execute the switch to LoginView Use case
     */
    public void switchToLoginView(){
        this.userRegisterUseCaseInteractor.switchToLoginView();
    }
}
