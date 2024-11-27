package app.use_case.home;

//TODO: I added these files to follow clean architecture but I don't use any of them. What should we do?
public interface HomeInputBoundary {
    void execute();

    void switchToLoginView();
    void switchtoCreateEventView();
    void switchToFilterEventView();
}
