package cs.database;

import cs.domain.Event;

/**
 * Created by radoslaw on 11.08.18.
 */
public interface IDaoEvent {
    void read(Event event);
    void insert(Event event);
}
