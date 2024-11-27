package app.interface_adapter.home;

import app.use_case.home.HomeInputBoundary;
/**
 * convert raw user data to something useful, and then store it in
 * the input data or input boundary
 * AFAIK don't need anything for the home view specifically
 * create an input data object containing that info
 * */

public class HomeController {
    private final HomeInputBoundary homeUseCaseInteractor;
    public HomeController(HomeInputBoundary homeUseCaseInteractor) {
        this.homeUseCaseInteractor = homeUseCaseInteractor;
    }

    public void switchToLoginView(){
        this.homeUseCaseInteractor.switchToLoginView();
    }

    public void switchToCreateEventView() {
        this.homeUseCaseInteractor.switchtoCreateEventView();
    }

    public void switchToFilterEventView() {this.homeUseCaseInteractor.switchToFilterEventView(); }
}
