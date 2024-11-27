package app.interface_adapter.view_event;

import app.use_case.view_event.ViewEventInputBoundary;
import app.use_case.view_event.ViewEventInputData;


public class ViewEventController {
    private final ViewEventInputBoundary viewEventUseCaseInteractor;

    public ViewEventController(ViewEventInputBoundary viewEventUseCaseInteractor) {
        this.viewEventUseCaseInteractor = viewEventUseCaseInteractor;
    }

    public void execute(String title) {

        final ViewEventInputData viewEventInputData = new ViewEventInputData(title);

        this.viewEventUseCaseInteractor.execute(viewEventInputData);
    }

    public void switchToHomeView() {this.viewEventUseCaseInteractor.switchToHomeView();}
}
