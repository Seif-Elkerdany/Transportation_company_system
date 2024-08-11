package GUI;

import javafx.event.Event;
import javafx.event.EventType;

public class EventLabelHandler extends Event {

    protected String newLabel;

    public EventLabelHandler(String tempLabel) {
        super(EventType.ROOT);
        this.newLabel = tempLabel;
    }

    public String getNewLabel() {
        return newLabel;
    }
}
