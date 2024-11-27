package app.interface_adapter.filter_event;

import app.interface_adapter.ViewModel;

public class FilterEventViewModel extends ViewModel<FilterEventState>{
    public static final String TITLE_LABEL = "Filter Event View";
    public static final String FILTER_EVENT_TITLE_LABEL = "Below are the events based on your filter";
    public static final String FILTER_EVENT_SEARCH_BAR = "Choose a tag you want to filter by";
    public static final String FILTER_EVENT_BUTTON_LABEL = "FILTER EVENTS";

    public FilterEventViewModel() {
        super("filterEvent");
        setState(new FilterEventState());
    }

}
