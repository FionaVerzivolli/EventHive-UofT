package app.use_case.modify_user;

import app.entity.User.User;

public interface ModifyUserDataAccessInterface {

    // Finds a user by username.
    User getUserByUsername(String username);

    // Checks if the given username is already taken.
    boolean doesUsernameExist(String username);

    // Checks if the given email is already taken.
    boolean doesEmailExist(String email);

    // Updates the user's details in the data source.
    boolean updateUser(User user);
}

