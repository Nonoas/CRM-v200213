package indi.nonoas.crm.common.delegate;

import java.util.LinkedList;
import java.util.List;

/**
 * @author : Nonoas
 * @time : 2020-08-02 22:51
 */
public class EventHandler {

    private final List<Event> events;

    public EventHandler() {
        events = new LinkedList<>();
    }

    public void addEvent(Event event) {
        events.add(event);
    }

    public void removeEvent(Event event) {
        events.remove(event);
    }

    public void execute() {
        for (Event event : events) {
            event.invoke();
        }
    }
}
