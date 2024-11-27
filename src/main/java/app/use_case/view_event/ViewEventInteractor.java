package app.use_case.view_event;

import app.entity.Event.Event;
import java.util.List;

public class ViewEventInteractor implements ViewEventInputBoundary {
    private final ViewEventUserDataAccessInterface eventDataAccessObject;
    private final ViewEventOutputBoundary viewEventPresenter;

    public ViewEventInteractor(ViewEventUserDataAccessInterface eventDataAccessObject,
                               ViewEventOutputBoundary viewEventPresenter) {
        this.eventDataAccessObject = eventDataAccessObject;
        this.viewEventPresenter = viewEventPresenter;
    }

    @Override
    public void execute(ViewEventInputData viewEventInputData) {
        Event event = eventDataAccessObject.viewEvent(viewEventInputData.getTitle());
        // guaranteed to be successful by the way its implemented, this usecase is related to a button on an event
        // in the UI
        ViewEventOutputData outputData = new ViewEventOutputData(event);
        viewEventPresenter.prepareSuccessView(outputData);
    }

    @Override
    public void switchToHomeView(){
        viewEventPresenter.switchToHomeView();
    }
}