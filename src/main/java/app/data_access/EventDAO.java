package app.data_access;

import app.entity.Event.CommonEvent;
import app.entity.User.CommonUserFactory;
import app.use_case.filter_event.FilterEventUserDataAccessInterface;
import app.use_case.modify_event.ModifyEventUserDataAccessInterface;
import app.use_case.display_event.DisplayEventDataAccessInterface;
import app.use_case.view_event.ViewEventInputData;
import app.use_case.view_event.ViewEventUserDataAccessInterface;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;


import app.entity.Event.Event;
import app.entity.User.CommonUserFactory;
import app.use_case.modify_user.ModifyUserDataAccessInterface;
import app.use_case.register.RegisterUserDataAccessInterface;
import app.use_case.create_event.EventUserDataAccessInterface;
import app.use_case.login.LoginUserDataAccessInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

/**
 * Firebase implementation of the DAO for storing user data.
 * This implementation persists data in Firestore.
 */
@Component
public class EventDAO implements EventUserDataAccessInterface, DisplayEventDataAccessInterface, FilterEventUserDataAccessInterface, ModifyEventUserDataAccessInterface, ViewEventUserDataAccessInterface {

    private final Firestore db;
    private final CollectionReference eventCollection;

    // Inject Firestore via constructor injection
    @Autowired
    public EventDAO(Firestore db, CommonUserFactory userFactory) {
        this.db = db;
        this.eventCollection = db.collection("Events");
    }

    /**
     * Function to save an event to the Firebase Database.
     *
     * @param event, the event we want to save.
     */
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

    /**
     * Function to retrieve all the events
     * */
    @Override
    public ArrayList<CommonEvent> loadEvents() {
        try {
            // Asynchronously retrieve the documents
            ApiFuture<QuerySnapshot> future = eventCollection.get();

            // Block on response to get the document snapshot
            QuerySnapshot document = future.get();

            // Prepare a list to hold the events
            ArrayList<CommonEvent> events = new ArrayList<>();

            // Iterate over the events in the collection
            for (QueryDocumentSnapshot doc : document.getDocuments()) {
                // Convert Firestore document into a CommonEvent object
                CommonEvent event = doc.toObject(CommonEvent.class);
                events.add(event); // Add it to the list
            }

            // Return the list of events
            return events;

        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error loading events from Firestore: " + e.getMessage());
            return new ArrayList<>(); // Return an empty list in case of error
        }
    }


    @Override
    public List<Event> findEvents(List<String> tags) {
        try {
            System.out.println("I love what");
            CollectionReference eventsRef = eventCollection;
            ApiFuture<QuerySnapshot> future;
            if (!tags.isEmpty()) {
                future = eventsRef.whereArrayContainsAny("tags", tags).get();
            } else {
                // Fetch all events if no tags are provided
                future = eventsRef.get();
            }
            // Get query results
            List<QueryDocumentSnapshot> documents = future.get().getDocuments();

            List<Event> events = new ArrayList<>();
            for (QueryDocumentSnapshot document : documents) {
                Map<String, Object> data = document.getData();

                // Create Event object
                String organizer = (String) data.get("organizer");
                String title = (String) data.get("title");
                String description = (String) data.get("description");
                String time = (String) data.get("time");
                Long capacityLong = (Long) data.get("capacity"); // Retrieve as Long
                int capacity = capacityLong.intValue(); // Convert to int
                Double latitudeLong = (Double) data.get("latitude");
                float latitude = latitudeLong.floatValue();
                Double longitudeLong = (Double) data.get("longitude");
                float longitude = longitudeLong.floatValue();
//                float longitude = (float) data.get("longitude");
                List<String> eventTags = (List<String>) data.get("tags");

//                System.out.println("Naoroj is testing here,");

                Event event = new CommonEvent("", organizer, title, description, time, capacity, latitude, longitude, eventTags);
                events.add(event);
            }
//            for (Event event: events){
//                System.out.println(event.getTitle());
//            }
            return events;
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error finding events in Firestore: " + e.getMessage());
            return new ArrayList<>(); // Return empty list on error

        }
    }

    /**
     * Function to delete an event from the Firebase Database.
     *
     * @param eventName the name of the event to delete.
     */
    @Override
    public void deleteEvent(String eventName) {
        try {
            // Reference to the document with the given name
            DocumentReference docRef = eventCollection.document(eventName);

            // Attempt to delete the document
            ApiFuture<WriteResult> writeResult = docRef.delete();

            // Wait for the operation to complete
            WriteResult result = writeResult.get();

            System.out.println("Event deleted at");
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error deleting event from Firestore: " + e.getMessage());
        }
    }

    @Override
    public Event viewEvent(String title) {
        try {
            // Reference to the document with the given title
            DocumentReference docRef = eventCollection.document(title);

            // Retrieve the document asynchronously
            ApiFuture<DocumentSnapshot> future = docRef.get();

            // Get the document snapshot
            DocumentSnapshot document = future.get();

            // Check if the document exists
            if (document.exists()) {
                // Convert the document to a CommonEvent object
                Map<String, Object> data = document.getData();

                if (data != null) {
                    String organizer = (String) data.get("organizer");
                    String description = (String) data.get("description");
                    String time = (String) data.get("time");
                    Long capacityLong = (Long) data.get("capacity");
                    int capacity = capacityLong.intValue();
                    Double latitudeLong = (Double) data.get("latitude");
                    float latitude = latitudeLong.floatValue();
                    Double longitudeLong = (Double) data.get("longitude");
                    float longitude = longitudeLong.floatValue();
                    List<String> tags = (List<String>) data.get("tags");

                    // Create and return the event object
                    return new CommonEvent(
                            title, // Title is used as the ID
                            organizer,
                            title,
                            description,
                            time,
                            capacity,
                            latitude,
                            longitude,
                            tags
                    );
                }
            } else {
                System.out.println("No event found with title: " + title);
            }
        } catch (InterruptedException | ExecutionException e) {
            System.err.println("Error viewing event from Firestore: " + e.getMessage());
        }

        // Return null if no event is found or an error occurs
        return null;
    }
}
