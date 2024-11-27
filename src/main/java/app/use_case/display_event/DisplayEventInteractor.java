package app.use_case.display_event;

import app.data_access.EventDAO;
import app.entity.Event.CommonEvent;
import app.entity.Event.EventFactory;
import app.interface_adapter.display_event.DisplayEventPresenter;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

/**
 * The Event Interactor
 * The Event Interactor handles the input
 * If a user with the inputted username exists, then get the userPresenter to prepare a fail view on frontend
 * Do a similar thing for other errors
 * Otherwise, interact with the database through hte userDAO to save the user and prepare a success view to the
 * front end.
 *
 * NOTE: userPresenter is just the outputBoundary class
 * Note: EventInteractor is an implementation of the inputBoundary interface
 */
public class DisplayEventInteractor implements DisplayEventInputBoundary {
    EventDAO displayEventDataAccessObject;
    DisplayEventOutputBoundary displayEventPresenter;
    EventFactory eventFactory;

    public DisplayEventInteractor(EventDAO eventDataAccessObject,
                          DisplayEventOutputBoundary eventPresenter,
                           EventFactory eventFactory) {

        this.displayEventDataAccessObject = eventDataAccessObject;
        this.displayEventPresenter = eventPresenter;
        this.eventFactory = eventFactory;
    }

    @Override
    public ArrayList<CommonEvent> execute() {
        ArrayList<CommonEvent> events = displayEventDataAccessObject.loadEvents();
        DisplayEventOutputData outputData = new DisplayEventOutputData(events);
        displayEventPresenter.prepareSuccessView(outputData);
        return events;
    }

}