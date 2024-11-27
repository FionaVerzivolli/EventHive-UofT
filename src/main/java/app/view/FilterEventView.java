package app.view;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import app.entity.Event.Event;
import app.interface_adapter.filter_event.FilterEventController;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.filter_event.FilterEventViewModel;

public class FilterEventView extends JPanel implements PropertyChangeListener {
    private static final String VIEW_NAME = "filterEvent";

    private final FilterEventViewModel filterEventViewModel;
    private final JCheckBox musicCheckbox;
    private final JCheckBox sportsCheckbox;
    private final JCheckBox artCultureCheckbox;
    private final JCheckBox foodDrinkCheckbox;
    private final JCheckBox educationCheckbox;
    private final JCheckBox travelCheckbox;
    private final JCheckBox gamingCheckbox;
    private final JCheckBox festivalCheckbox;

//    private final JTextField locationTextField;
    private final JButton submitFilterButton;
    private final JPanel eventsPanel; // Panel to hold events dynamically

//    private final JList<String> filteredEventsInfo;
//    private final JScrollPane filteredEventsScrollPane;

    private FilterEventController filterEventController;

    public FilterEventView(FilterEventViewModel filterEventViewModel) {
        this.filterEventViewModel = filterEventViewModel;
        this.filterEventViewModel.addPropertyChangeListener(this);

        // Set layout to BorderLayout since we're using BorderLayout constraints
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Create a panel for checkboxes
        JPanel checkboxPanel = new JPanel();
        checkboxPanel.setLayout(new BoxLayout(checkboxPanel, BoxLayout.Y_AXIS));
        checkboxPanel.setBorder(new LineBorder(Color.GRAY, 1, true));

        // Initialize checkboxes
        musicCheckbox = new JCheckBox("Music");
        sportsCheckbox = new JCheckBox("Sports");
        artCultureCheckbox = new JCheckBox("Art and Culture");
        foodDrinkCheckbox = new JCheckBox("Food & Drink");
        educationCheckbox = new JCheckBox("Education");
        travelCheckbox = new JCheckBox("Travel");
        gamingCheckbox = new JCheckBox("Gaming");
        festivalCheckbox = new JCheckBox("Festival");

        // Add checkboxes to the panel
        checkboxPanel.add(musicCheckbox);
        checkboxPanel.add(sportsCheckbox);
        checkboxPanel.add(artCultureCheckbox);
        checkboxPanel.add(foodDrinkCheckbox);
        checkboxPanel.add(educationCheckbox);
        checkboxPanel.add(travelCheckbox);
        checkboxPanel.add(gamingCheckbox);
        checkboxPanel.add(festivalCheckbox);

        // Add the checkbox panel to the WEST of the main panel
        add(checkboxPanel, BorderLayout.WEST);

        // Create a list for filtered events
//        filteredEventsInfo = new JList<>(new DefaultListModel<>());
//        filteredEventsScrollPane = new JScrollPane(filteredEventsInfo);
//        filteredEventsScrollPane.setBorder(new LineBorder(Color.GRAY, 1, true));
//
//        // Add the scroll pane containing the list to the CENTER
//        add(filteredEventsScrollPane, BorderLayout.CENTER);
        // Create a scrollable panel for events
        eventsPanel = new JPanel();
        eventsPanel.setLayout(new BoxLayout(eventsPanel, BoxLayout.Y_AXIS));
        JScrollPane eventsScrollPane = new JScrollPane(eventsPanel);
        eventsScrollPane.setBorder(new LineBorder(Color.GRAY, 1, true));
        add(eventsScrollPane, BorderLayout.CENTER);

        // Create a submit button
        submitFilterButton = new JButton("Filter Events");
        submitFilterButton.addActionListener(e -> applyFilters());

        // Add the submit button to the SOUTH
        add(submitFilterButton, BorderLayout.SOUTH);
    }

    private void applyFilters() {
        // Get selected categories
        List<String> selectedCategories = new ArrayList<>();
        if (musicCheckbox.isSelected()) selectedCategories.add("Music");
        if (sportsCheckbox.isSelected()) selectedCategories.add("Sports");
        if (artCultureCheckbox.isSelected()) selectedCategories.add("Art and Culture");
        if (foodDrinkCheckbox.isSelected()) selectedCategories.add("Food & Drink");
        if (educationCheckbox.isSelected()) selectedCategories.add("Education");
        if (travelCheckbox.isSelected()) selectedCategories.add("Travel");
        if (gamingCheckbox.isSelected()) selectedCategories.add("Gaming");
        if (festivalCheckbox.isSelected()) selectedCategories.add("Festival");

        // Execute the filter event use case
        if (filterEventController != null) {
            filterEventController.execute(selectedCategories);
        } else {
            // Handle the case where the controller is not set
            JOptionPane.showMessageDialog(this, "FilterEventController is not set.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

//    @Override
//    public void propertyChange(PropertyChangeEvent evt) {
//        FilterEventState state = (FilterEventState) evt.getNewValue();
//        List<Event> events = state.getFilteredEvents();
//
//        DefaultListModel<String> listModel = new DefaultListModel<>();
//        for (Event event : events) {
//            listModel.addElement("Title: " + event.getTitle());
//            listModel.addElement("Organizer: " + event.getOrganizer());
//            listModel.addElement("Description: " + event.getDescription());
//            listModel.addElement("Date & Time: " + event.getDateTime());
//            listModel.addElement("Capacity: " + event.getCapacity());
//            listModel.addElement("Tags: " + String.join(", ", event.getTags()));
//            listModel.addElement("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------"); // Separator line
//        }
//
//        filteredEventsInfo.setModel(listModel);
//    }
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        FilterEventState state = (FilterEventState) evt.getNewValue();
        List<Event> events = state.getFilteredEvents();

        // Clear the events panel
        eventsPanel.removeAll();

        // Add each event to the panel
        for (Event event : events) {
            JPanel eventPanel = new JPanel();
            eventPanel.setLayout(new BoxLayout(eventPanel, BoxLayout.Y_AXIS));
            eventPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

            JLabel titleLabel = new JLabel("Title: " + event.getTitle());
            JLabel organizerLabel = new JLabel("Organizer: " + event.getOrganizer());
            JLabel descriptionLabel = new JLabel("Description: " + event.getDescription());
            JLabel dateTimeLabel = new JLabel("Date & Time: " + event.getDateTime());
            JLabel capacityLabel = new JLabel("Capacity: " + event.getCapacity());
            JLabel tagsLabel = new JLabel("Tags: " + String.join(", ", event.getTags()));

            JButton viewEventButton = new JButton("View Event");
            viewEventButton.addActionListener(e -> viewEvent(event));

            eventPanel.add(titleLabel);
            eventPanel.add(organizerLabel);
            eventPanel.add(descriptionLabel);
            eventPanel.add(dateTimeLabel);
            eventPanel.add(capacityLabel);
            eventPanel.add(tagsLabel);
            eventPanel.add(viewEventButton);
            eventPanel.setBorder(new LineBorder(Color.GRAY));

            eventsPanel.add(eventPanel);
        }

        // Refresh the events panel
        eventsPanel.revalidate();
        eventsPanel.repaint();
    }

    private void viewEvent(Event event) {
        // TODO: Handle logic to display event details (navigate to ViewEventView or show in a dialog)
        System.out.println("Viewing event: " + event.getTitle());
    }



    public String getViewName() {
        return VIEW_NAME;
    }

    public void setFilterEventsController(FilterEventController controller) {
        this.filterEventController = controller;
    }
}
