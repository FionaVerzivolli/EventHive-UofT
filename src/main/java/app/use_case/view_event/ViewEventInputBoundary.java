package app.use_case.view_event;

public interface ViewEventInputBoundary {
    void execute(ViewEventInputData viewEventInputData);
    void switchToHomeView();
}