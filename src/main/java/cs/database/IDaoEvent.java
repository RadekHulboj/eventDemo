package cs.database;

import cs.domain.Event;

import java.sql.SQLException;
import java.util.List;

public interface IDaoEvent {
    List<Event> read() throws SQLException;
    void insert(Event event) throws SQLException;
}
