package app.data_access;

import app.entity.Event.CommonEvent;
import app.entity.User.CommonUserFactory;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

import app.entity.User.User;
import app.entity.User.UserFactory;
import app.entity.Event.Event;
import app.entity.User.CommonUserFactory;
import app.use_case.modify_user.ModifyUserDataAccessInterface;
import app.use_case.register.RegisterUserDataAccessInterface;
import app.use_case.create_event.EventUserDataAccessInterface;
import app.use_case.login.LoginUserDataAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Firebase implementation of the DAO for storing user data.
 * This implementation persists data in Firestore.
 */
@Component
public class FirebaseDAO implements RegisterUserDataAccessInterface, EventUserDataAccessInterface, LoginUserDataAccessInterface, ModifyUserDataAccessInterface{

    private final Firestore db;
    private final CollectionReference usersCollection;
    private final CollectionReference eventCollection;
    private final CommonUserFactory userFactory;

    // Inject Firestore via constructor injection
    @Autowired
    public FirebaseDAO(Firestore db, CommonUserFactory userFactory) {
        this.db = db;
        this.usersCollection = db.collection("Users");
        this.eventCollection = db.collection("Events");
        this.userFactory = userFactory;
    }

    @Override
    public boolean existsByUsername(String identifier) {
        try {
            return usersCollection.document(identifier).get().get().exists();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error checking user existence: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void save(User user) {
        Map<String, Object> userData = new HashMap<>();
        userData.put("username", user.getUsername());
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());

        try {
            DocumentReference docRef = usersCollection.document(user.getUsername());
            WriteResult result = docRef.set(userData).get();
            System.out.println("User saved at: " + result.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error saving user to Firestore: " + e.getMessage());
        }
    }

    public void printUsers() {
        try {
            ApiFuture<QuerySnapshot> future = usersCollection.get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            for (QueryDocumentSnapshot document : documents) {
                System.out.println("User: " + document.getId() + ", Data: " + document.getData());
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error retrieving users from Firestore: " + e.getMessage());
        }
    }

    /**
     * Function to save an event to the Firebase Database.
     * @param event, the event we want to save.
     * */
    public void saveEvent(Event event) {
        Map<String, Object> eventData = new HashMap<>();
        eventData.put("organizer", event.getOrganizer());
        eventData.put("title", event.getTitle());
        eventData.put("description", event.getDescription());
        eventData.put("time", event.getDateTime());
        eventData.put("capacity", event.getCapacity());
        eventData.put("latitude", event.getLatitude());
        eventData.put("longitude", event.getLongitude());
        eventData.put("tags", event.getTags());

        try {
            DocumentReference docRef = eventCollection.document(event.getTitle());
            WriteResult result = docRef.set(eventData).get();
            System.out.println("Event saved at: " + result.getUpdateTime());
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error saving event to Firestore: " + e.getMessage());
        }
    }


    @Override
    public User findUserByUsername(String username) {
        try {
            // Get a reference to the user document with the given username
            DocumentReference docRef = usersCollection.document(username);

            // Asynchronously retrieve the document
            ApiFuture<DocumentSnapshot> future = docRef.get();

            // Block on response to get the document snapshot
            DocumentSnapshot document = future.get();

            if (document.exists()) {
                // Convert document data to a User object
                Map<String, Object> data = document.getData();

                // Extract user fields from the data map
                String email = (String) data.get("email");
                String password = (String) data.get("password");

                // Create and return a User object
                UserFactory userFactory = new CommonUserFactory();
                User user = userFactory.create(username, email, password);
                System.out.println("Hello this is Naoroj yo, I'm skyler white yo");
                return user;
            } else {
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error finding user in Firestore: " + e.getMessage());
            return null;
        }
    }


    // retrieve user by username
    /**
     * Retrieves a User from the Firestore database by their username.
     * @param username The username of the user to be retrieved.
     * @return A User object if the user exists, null otherwise.
     */
    public User getUserByUsername(String username) {
        try {
            DocumentReference docRef = usersCollection.document(username);
            var snapshot = docRef.get().get();
            if (snapshot.exists()) {
                // Retrieve user details from the Firestore snapshot
                String retrievedUsername = snapshot.getString("username");
                String email = snapshot.getString("email");
                String password = snapshot.getString("password");

                // Using UserFactory to create a User object
                return userFactory.create(retrievedUsername, email, password);
            } else {
                System.out.println("User not found with username: " + username);
                return null;
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error getting user from Firestore: " + e.getMessage());
            return null;
        }
    }

    // check if username exists in the database
    /**
     * Checks if a given username exists in Firestore.
     * @param userId The username to be checked.
     * @return true if the username exists, false otherwise.
     */
    public boolean doesUsernameExist(String userId) {
        try {
            return usersCollection.document(userId).get().get().exists();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error checking if user ID exists in Firestore: " + e.getMessage());
            return false;
        }
    }

    //check if new email duplicated
    public boolean doesEmailExist(String email) {
        try {
            ApiFuture<QuerySnapshot> future = usersCollection.whereEqualTo("email", email).get();
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();
            return !documents.isEmpty();
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error checking if email exists in Firestore: " + e.getMessage());
            return false;
        }
    }

    //check if successfully updated the user
    public boolean updateUser(User user) {
        Map<String, Object> updatedData = new HashMap<>();
        updatedData.put("username", user.getUsername());
        updatedData.put("email", user.getEmail());
        updatedData.put("password", user.getPassword());

        try {
            DocumentReference docRef = usersCollection.document(user.getUsername());
            WriteResult result = docRef.set(updatedData).get();
            System.out.println("User updated at: " + result.getUpdateTime());
            return true;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error updating user in Firestore: " + e.getMessage());
            return false;
        }
    }
}
