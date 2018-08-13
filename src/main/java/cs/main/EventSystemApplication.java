package cs.main;

import cs.database.IDaoEvent;
import cs.database.PersistEvent;
import cs.database.helpers.JDBCHelper;
import cs.domain.Event;
import cs.service.FileService;
import cs.service.JsonParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

public class EventSystemApplication {

  public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
    createEventDbTable();

    final IDaoEvent persistEvent = new PersistEvent();
    addEventsToDb(getLogEventsFromJson(), persistEvent);
    List<Event> dbEvents = persistEvent.read();
  }

  private static void addEventsToDb(Event[] events, IDaoEvent persistEvent) {
    Arrays.stream(events).forEach((event) -> {
      try {
        persistEvent.insert(event);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  private static Event[] getLogEventsFromJson() throws URISyntaxException, IOException {
    JsonParser jsonParser = new JsonParser();
    return jsonParser.createFromJson("logs/log.json");
  }

  private static void createEventDbTable() throws URISyntaxException, IOException, SQLException {
    FileService fileService = new FileService();
    String createEventTableStr = fileService.getStringFrom("db_sql/event.sql");
    try (Statement statement = JDBCHelper.getConnection().createStatement()) {
      statement.execute(createEventTableStr);
    }
  }
}
