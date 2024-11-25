package app.use_case.modify_user;

/**
 * Output data for modifying user account details.
 */
public class ModifyUserOutputData {
    private final String username;
    private final String message;

    public ModifyUserOutputData(String username, String message) {
        this.username = username;
        this.message = message;
    }

    public String getUsername() {
        return username;
    }


    /**
     * Gets the message summarizing the modification result.
     * @return the result message
     */
    public String getMessage() {
        return message;
    }
}

