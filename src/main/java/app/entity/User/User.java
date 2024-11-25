package app.entity.User;
import app.entity.Event.Event;
import java.util.List;

/**
 * Representation of a user in the program
 */
public interface User {
    /**
     * Return the username of the user.
     * @return the username of the user
     */
    String getUsername();

    /**
     * Return the password of the user
     * @return the password of the user
     */
    String getPassword();

    /**
     * Return the email of the user
     * @return the email of the user
     */
    String getEmail();

    /**
     * Add an Event element to the rsvpedList of the user
     * @param event an Event that the user RSVPs to
     */
    void addRsvpedEvent(Event event);

    /**
     * Remove an Event element IF IT EXISTS to the rsvpedList of the user
     * @param event an Event that the has RSVPED to but wants to cancel
     */
    void removeRsvpedEvent(Event event);

    /**
     * Method for when a user creates an Event, and this event is added to the users
     * CreatedEvent List
     * @param event an event that the user creates
     */
    void addCreatedEvent(Event event);

    /**
     * Remove an event that the user has created and remove it from the list
     * @param event an event that the user has created. PRECONDITION FROM THE GUI INPUT THAT THIS
     *              IS NECESSARILY AN EVENT THE USER CREATED
     */
    void removeCreatedEvent(Event event);

    /**
     * Return a list of the events the user RSVPs to
     * @return a list of the events user RSVPs
     */
    List<Event> getRsvpedEvents();

    /**
     * Return the list of events the user creates
     * @return the list of events the user creates
     */
    List<Event> getCreatedEvents();


    /**
     * Sets the username for the user.
     * @param username the new username to set
     */
    void setUsername(String username);

    /**
     * Sets the email for the user.
     * @param email the new email to set
     */
    void setEmail(String email);

    /**
     * Sets the password for the user.
     * @param password the new password to set
     */
    void setPassword(String password);

    /**
     * Verifies if the provided password matches the user's current password.
     * @param providedPassword the password to verify
     * @return true if the provided password matches the user's password, false otherwise
     */
    boolean verifyPassword(String providedPassword);
}
