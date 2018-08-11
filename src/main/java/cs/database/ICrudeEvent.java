package cs.database;

import cs.domain.Event;

/**
 * Created by radoslaw on 11.08.18.
 */
public interface ICrudeEvent {
    void save(Event event);
    void delete(Event event);
    void update(Event event);
    void insert(Event event);
}
