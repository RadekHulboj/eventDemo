package cs.database;

import cs.domain.Event;

import java.sql.SQLException;
import java.util.List;

/**
 * Created by radoslaw on 11.08.18.
 */
public interface IDaoEvent {
    List<Event> read() throws SQLException;
    void insert(Event event) throws SQLException;
}
