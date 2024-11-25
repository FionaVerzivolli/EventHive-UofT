package app.entity.User;
import app.entity.Event.Event;

import java.util.List;
import java.util.Objects;
// TODO: is this clean architecture for entities to rely on another?
// TODO: 12am stupid question, is it clean architecture to have an implementation of adding events to a user
// This doesn't rely on the database but still idk?
/**
 * Implementation of the User interface
 */
public class CommonUser implements User{
    private String username;
    private String password;
    private String email;

    // notice this way of setting up for a general Event interface is better than
    // using a specific implementation
    private List<Event> rsvpedEvents;
    private List<Event> createdEvents;

    // Note that the two list attributes are not put in the constructor

    public CommonUser(String username, String email, String password){
        this.username = username;
        this.password = password;
        this.email = email;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public String getEmail() {
        return this.email;
    }
    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public void addRsvpedEvent(Event event) {
        // notice we use dependency injection design pattern here
        this.rsvpedEvents.add(event);
    }

    @Override
    public void removeRsvpedEvent(Event event) {
        // TODO: might need a special event.equals method
        // TODO: check edge cases on implementation
        for (int i = 0; i < this.rsvpedEvents.size(); i++){
            if (this.rsvpedEvents.get(i).equals(event)){
                this.rsvpedEvents.remove(i);
                break;
            }
        }

    }

    @Override
    public void addCreatedEvent(Event event) {
        this.createdEvents.add(event);
    }

    @Override
    public void removeCreatedEvent(Event event) {
        // TODO: might need a special event.equals method
        // TODO: check edge cases on implementation
        for (int i = 0; i < this.createdEvents.size(); i++){
            if (this.createdEvents.get(i).equals(event)){
                this.createdEvents.remove(i);
                break;
            }
        }
    }

    @Override
    public List<Event> getRsvpedEvents() {
        return this.rsvpedEvents;
    }

    @Override
    public List<Event> getCreatedEvents() {
        return this.createdEvents;
    }



    //Setters for users' info modification
    /**
     * Sets the username for the user.
     * @param username the new username to set
     */
    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * Sets the email for the user.
     * @param email the new email to set
     */
    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the password for the user.
     * @param password the new password to set
     */
    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Verifies if the provided password matches the user's current password.
     * @param providedPassword the password to verify
     * @return true if the provided password matches the user's password, false otherwise
     */
    @Override
    public boolean verifyPassword(String providedPassword) {
        return Objects.equals(this.password, providedPassword);
    }

}
