package app.interface_adapter.register;

import app.interface_adapter.login.LoginState;
import app.interface_adapter.login.LoginViewModel;
import app.use_case.register.RegisterOutputBoundary;
import app.interface_adapter.ViewManagerModel;

import app.use_case.register.RegisterOutputData;

/**
 * The Presenter for the register Use Case
 */
public class RegisterPresenter implements RegisterOutputBoundary {
    private final RegisterViewModel registerViewModel;
    private final LoginViewModel loginViewModel;
    private final ViewManagerModel viewManagerModel;

    public RegisterPresenter(ViewManagerModel viewManagerModel,
                           RegisterViewModel registerViewModel,
                             LoginViewModel loginViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.registerViewModel = registerViewModel;
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void prepareSuccessView(RegisterOutputData response, String s) {
        // On success go to login view
        final LoginState loginState = loginViewModel.getState();
        loginState.setUsername("");
        loginState.setPassword("");
        this.loginViewModel.setState(loginState);
        loginViewModel.firePropertyChanged();

        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();

    }

    @Override
    public void prepareFailView(String error) {
        final RegisterState registerState = registerViewModel.getState();
        registerState.setUsernameError(error);
        registerViewModel.firePropertyChanged();
    }

    @Override
    public void switchToLoginView() {
        viewManagerModel.setState(loginViewModel.getViewName());
        viewManagerModel.firePropertyChanged();
    }
}
