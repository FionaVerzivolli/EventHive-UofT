package app.use_case.register;


import app.entity.User.User;
import app.entity.User.UserFactory;

/**
 * The Register Interactor
 * The Register Interactor handles the input
 * If a user with the inputted username exists, then get the userPresenter to prepare a fail view on frontend
 * Do a similar thing for other errors
 * Otherwise, interact with the database through hte userDAO to save the user and prepare a success view to the
 * front end.
 *
 * NOTE: userPresenter is just the outputBoundary class
 * Note: RegisterInteractor is an implementation of the inputBoundary interface
 */
public class RegisterInteractor implements RegisterInputBoundary {
    RegisterUserDataAccessInterface userDataAccessObject;
    RegisterOutputBoundary userPresenter;
    UserFactory userFactory;

    public RegisterInteractor(RegisterUserDataAccessInterface registerUserDataAccessInterface,
                              RegisterOutputBoundary registerOutputBoundary,
                              UserFactory userFactory) {

        this.userDataAccessObject = registerUserDataAccessInterface;
        this.userPresenter = registerOutputBoundary;
        this.userFactory = userFactory;
    }

    @Override
    public void execute(RegisterInputData registerInputData) {
        if (userDataAccessObject.existsByUsername(registerInputData.getUsername())) { // TODO: change it so it uses the DB
            userPresenter.prepareFailView("Username already exists.");
        }
        else{
            final User user = userFactory.create(registerInputData.getUsername(), registerInputData.getEmail(), registerInputData.getPassword());
            userDataAccessObject.save(user);

            final RegisterOutputData registerOutputData = new RegisterOutputData(user.getUsername(), false);
            userPresenter.prepareSuccessView(registerOutputData, "User created successfully! Please log in.");
        }
    }

    @Override
    public void switchToLoginView() {
        userPresenter.switchToLoginView();
    }
}
