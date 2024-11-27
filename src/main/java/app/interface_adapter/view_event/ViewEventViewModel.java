package app.interface_adapter.view_event;

import app.interface_adapter.ViewModel;

public class ViewEventViewModel extends ViewModel <ViewEventState>{
    public static final String TITLE_LABEL = "View Event";

    public ViewEventViewModel() {
        super("viewEvent");
        setState(new ViewEventState());
    }
}
