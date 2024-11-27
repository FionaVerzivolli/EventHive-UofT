package app.view;

import app.interface_adapter.create_event.CreateEventController;
import app.interface_adapter.create_event.CreateEventState;
import app.interface_adapter.create_event.CreateEventViewModel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import java.awt.*;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;

public class CreateEventView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "createEvent";

    private final CreateEventViewModel createEventViewModel;
    private final JTextField orgInputField, titleInputField, descriptionInputField, timeInputField,
            capacityInputField, tagsInputField, latitudeInputField, longitudeInputField;
    private final JButton createEventButton;
    private final JButton homeButton;
    private CreateEventController createEventController;

    public CreateEventView(CreateEventViewModel createEventViewModel, CreateEventController controller) {
        this.createEventViewModel = createEventViewModel;
        this.createEventViewModel.addPropertyChangeListener(this);
        this.createEventController = controller;

        // Configure the layout
        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(20, 20, 20, 20));

        // Main panel for form
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(), "Create New Event",
                TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 16), Color.BLUE));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.CENTER;

        // Build components and generate placeholders
        orgInputField = createInputField("Enter the organizer(s)...");
        titleInputField = createInputField("Enter your title...");
        descriptionInputField = createInputField("Enter your description... (150 characters max)");
        timeInputField = createInputField("Please enter the time and date...");
        capacityInputField = createInputField("Enter your capacity...");
        latitudeInputField = createInputField("Please enter the latitude of the address");
        longitudeInputField = createInputField("Please enter the longitude of the address");
        tagsInputField = createInputField("Enter event tags (comma-separated)...");
        // Create an event button
        createEventButton = createEventButton();
        // Create a home button
        homeButton = createHomeButton();
        // Add document listeners for input validation
        addDocumentListener(titleInputField, () -> updateState("Title"));
        addDocumentListener(descriptionInputField, () -> updateState("Description"));
        addDocumentListener(capacityInputField, () -> updateState("Capacity"));
        addDocumentListener(tagsInputField, () -> updateState("Tags"));
        addDocumentListener(timeInputField, () -> updateState("Date and time"));
        addDocumentListener(orgInputField, () -> updateState("Organizers"));
        addDocumentListener(latitudeInputField, () -> updateState("Latitude"));
        addDocumentListener(longitudeInputField, () -> updateState("Longitude"));
        createEventButton.addActionListener(this::actionPerformed);
        homeButton.addActionListener(this::actionPerformed);

        // Add labels and fields to the panel, starting with the Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Organizers:"), gbc);
        gbc.gridx = 1;
        formPanel.add(orgInputField, gbc);

        // Add description input
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleInputField, gbc);

        // Add capacity input
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionInputField, gbc);

        // Add tags input
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Datetime:"), gbc);
        gbc.gridx = 1;
        formPanel.add(timeInputField, gbc);

        // Add building input
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(capacityInputField, gbc);

        // Add floor number input
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabel("Latitude:"), gbc);
        gbc.gridx = 1;
        formPanel.add(latitudeInputField, gbc);

        // Add room number input
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createLabel("Longitude:"), gbc);
        gbc.gridx = 1;
        formPanel.add(longitudeInputField, gbc);

        // Add room number input
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(createLabel("Tags:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tagsInputField, gbc);

        // Add the create button
        gbc.gridx = 0; gbc.gridy = 8; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(createEventButton, gbc);

        // Add the home button
        gbc.gridx = 0; gbc.gridy = 9; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(homeButton, gbc);

        // Add formPanel to the main panel
        this.add(formPanel, BorderLayout.CENTER);
    }

    private JTextField createInputField(String placeholder) {
        JTextField field = new JTextField(20);
        field.setFont(new Font("Arial", Font.PLAIN, 14));
        field.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        field.setToolTipText(placeholder);
        return field;
    }

    private JButton createEventButton() {
        JButton button = new JButton("Create Event");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Arial", Font.BOLD, 14));
        label.setForeground(Color.DARK_GRAY);
        return label;
    }

    // Updates state based on user input
    private void updateState(String fieldName) {
        // Implementation of state update (placeholder)
        CreateEventState currentState = createEventViewModel.getState();
        currentState.setEventId("123");
        if ("Title".equals(fieldName)) {
            currentState.setTitle(titleInputField.getText());
        } else if ("Description".equals(fieldName)) {
            currentState.setDescription(descriptionInputField.getText());
        } else if ("Capacity".equals(fieldName)) {
            currentState.setCapacity(parseInt(capacityInputField.getText()));
        } else if ("Tags".equals(fieldName)) {
            currentState.setTags(tagsInputField.getText());
        } else if ("Date and time".equals(fieldName)) {
            currentState.setDateTime(timeInputField.getText());
        } else if ("Organizers".equals(fieldName)) {
            currentState.setOrganizer(orgInputField.getText());
        } else if ("Latitude".equals(fieldName)) {
            currentState.setLatitude(parseFloat(latitudeInputField.getText()));
        } else if ("Longitude".equals(fieldName)) {
            currentState.setLongitude(parseFloat(longitudeInputField.getText()));
        }
        createEventViewModel.setState(currentState);
    }

    // Add a document listener to a JTextField
    private void addDocumentListener(JTextField textField, Runnable callback) {
        textField.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                callback.run();
            }
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                callback.run();
            }
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                callback.run();
            }
        });
    }

    @Override
    public void actionPerformed(java.awt.event.ActionEvent e) {
        if (e.getSource() == createEventButton) {
            CreateEventState currentState = createEventViewModel.getState();
            createEventController.execute(currentState.getTitle(), currentState.getDescription(), currentState.getDateTime(), currentState.getCapacity(),
                    currentState.getLatitude(), currentState.getLongitude(), currentState.getTags(), currentState.getEventId(), currentState.getOrganizer());
            // Handle button click event
            System.out.println("Event created!");
        } else if (e.getSource() == homeButton) {
//            navigateTo("Home");
            createEventController.switchToHomeView();
            System.out.println("Went home!");
        }
    }

    /**
     * This method gets called when a bound property is changed.
     *
     * @param evt A PropertyChangeEvent object describing the event source
     *            and the property that has changed.
     */
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreateEventState state = (CreateEventState) evt.getNewValue();
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setCreateEventController(CreateEventController controller) {
        this.createEventController = controller;
    }

//    /**
//     * Function to navigate from this screen to a different screen
//     * using the name of the new screen.
//     * @param viewName the name of the view you want to navigate to
//     * */
//    public void navigateTo(String viewName) {
//        // Check if the parentPanel is valid for debugging purposes
//        if (parentPanel != null && parentPanel.getLayout() instanceof CardLayout) {
//            // Debug statement in console:
//            System.out.println("Navigating to: " + viewName);
//
//            CardLayout layout = (CardLayout) parentPanel.getLayout();
//            layout.show(parentPanel, viewName);
//
//            // Revalidate and redraw the parent panel after you navigate to it
//            parentPanel.revalidate();
//            parentPanel.repaint();
//        } else {
//            // Console error statement if the navigation doesn't work
//            System.out.println("Navigation failed: parentPanel or layout is not set up correctly.");
//        }
//    }
    private JButton createHomeButton() {
        JButton button = new JButton("Home");
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(0, 180, 0));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
