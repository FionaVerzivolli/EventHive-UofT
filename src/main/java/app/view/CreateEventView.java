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

public class CreateEventView extends JPanel implements ActionListener, PropertyChangeListener {
    private static final String VIEW_NAME = "createEvent";

    private final CreateEventViewModel createEventViewModel;
    private final JTextField titleInputField, descriptionInputField, capacityInputField, tagsInputField, buildingInputField, floorInputField, roomInputField;
    private final JButton createEventButton;

    private CreateEventController createEventController;
    private JPanel parentPanel;

    public CreateEventView(CreateEventViewModel createEventViewModel) {
        this.createEventViewModel = createEventViewModel;
        this.createEventViewModel.addPropertyChangeListener(this);

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
        titleInputField = createInputField("Enter your title...");
        descriptionInputField = createInputField("Enter your description... (150 characters max)");
        capacityInputField = createInputField("Enter your capacity...");
        tagsInputField = createInputField("Enter event tags (comma-separated)...");
        buildingInputField = createInputField("Please select your building from the drop-down menu...");
        floorInputField = createInputField("Please enter the floor number of your event...");
        roomInputField = createInputField("Please enter the room number of your event...");

        // Create an event button
        createEventButton = createEventButton();

        // Add document listeners for input validation
        addDocumentListener(titleInputField, () -> updateState("Title"));
        addDocumentListener(descriptionInputField, () -> updateState("Description"));
        addDocumentListener(capacityInputField, () -> updateState("Capacity"));
        addDocumentListener(tagsInputField, () -> updateState("Tags"));
        addDocumentListener(buildingInputField, () -> updateState("Building"));
        addDocumentListener(floorInputField, () -> updateState("Floor number"));
        addDocumentListener(roomInputField, () -> updateState("Room number"));

        // Add labels and fields to the panel, starting with the Title
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createLabel("Title:"), gbc);
        gbc.gridx = 1;
        formPanel.add(titleInputField, gbc);

        // Add description input
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createLabel("Description:"), gbc);
        gbc.gridx = 1;
        formPanel.add(descriptionInputField, gbc);

        // Add capacity input
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(createLabel("Capacity:"), gbc);
        gbc.gridx = 1;
        formPanel.add(capacityInputField, gbc);

        // Add tags input
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(createLabel("Tags:"), gbc);
        gbc.gridx = 1;
        formPanel.add(tagsInputField, gbc);

        // Add building input
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(createLabel("Building:"), gbc);
        gbc.gridx = 1;
        formPanel.add(buildingInputField, gbc);

        // Add floor number input
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(createLabel("Floor Number (Optional):"), gbc);
        gbc.gridx = 1;
        formPanel.add(floorInputField, gbc);

        // Add room number input
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(createLabel("Room Number (Optional):"), gbc);
        gbc.gridx = 1;
        formPanel.add(roomInputField, gbc);

        // Add the create button
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        formPanel.add(createEventButton, gbc);

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
        System.out.println("Updated field: " + fieldName);
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
        // Handle button click event
        if (e.getSource() == createEventButton) {
            System.out.println("Event created!");
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
        final CreateEventState state = (CreateEventState) evt.getNewValue();
        //TODO: Add the potential errors
    }

    public String getViewName() {
        return VIEW_NAME;
    }

    public void setParentPanel(JPanel parentPanel) {
        this.parentPanel = parentPanel;
    }
}
