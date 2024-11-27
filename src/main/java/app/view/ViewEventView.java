package app.view;

import app.entity.Event.Event;
import app.interface_adapter.filter_event.FilterEventState;
import app.interface_adapter.view_event.ViewEventController;
import app.interface_adapter.view_event.ViewEventViewModel;

import java.awt.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

public class ViewEventView extends JPanel implements PropertyChangeListener{
    private static final String VIEW_NAME = "viewEvent";

    private ViewEventViewModel viewEventViewModel;
    private final JButton rsvpButton;
    private final JButton homeButton;
    private ViewEventController viewEventController;

    public ViewEventView(ViewEventViewModel viewEventViewModel) {
        this.viewEventViewModel = viewEventViewModel;
        this.viewEventViewModel.addPropertyChangeListener(this);

        this.setLayout(new BorderLayout());
        this.setBorder(new EmptyBorder(10, 10, 10, 10));

        rsvpButton = new JButton("RSVP");
        rsvpButton.addActionListener(e -> rsvpEvent());

        homeButton = new JButton("Go Home");
        homeButton.addActionListener(e -> goBackHome());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Align buttons horizontally
        buttonPanel.add(rsvpButton);
        buttonPanel.add(homeButton);

        // Add the button panel to the bottom (SOUTH) of the main layout
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void rsvpEvent() {
        // TODO: store the username in all states so rsvp event works
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // do nothing since the property of one event never changes
    }

    public void goBackHome() {
        viewEventController.switchToHomeView();
    }

    public String getViewName(){
        return VIEW_NAME;
    }

    public void setViewEventController(ViewEventController controller) {
        this.viewEventController = controller;
    }
}
