package app.use_case.home;

public class HomeInteractor implements HomeInputBoundary{
    private HomeOutputBoundary homePresenter;

    public HomeInteractor(HomeOutputBoundary homePresenter){
        this.homePresenter = homePresenter;
    }


    @Override
    public void execute() {
//        homePresenter.presentSuccessView();
//        home
    }

    @Override
    public void switchToLoginView(){
        homePresenter.switchToLoginView();
    }

    @Override
    public void switchtoCreateEventView() {
        homePresenter.switchToCreateEventView();
    }

    @Override
    public void switchToFilterEventView() {homePresenter.switchToFilterEventView();}
}
