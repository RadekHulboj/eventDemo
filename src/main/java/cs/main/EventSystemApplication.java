package cs.main;

import cs.business.OperationalEvent;
import cs.database.IDaoEvent;
import cs.database.PersistEvent;
import cs.database.helpers.JDBCHelper;
import cs.domain.Event;
import cs.service.FileService;
import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EventSystemApplication {

  private static Logger logger = LoggerFactory.getLogger(EventSystemApplication.class);

  public static void main(String[] args) throws SQLException, IOException, URISyntaxException {
    resetDatabaseEachTimeExecuted();
    addEventsToDbFrom(getLogEventsFromJson("logs/log.json"));
    printEventsFromDb();
  }

  private static void resetDatabaseEachTimeExecuted() throws URISyntaxException, IOException, SQLException {
    logger.info("Create Event table in Hsqldb");
    executeStatementFromSql("db_sql/createTableEvent.sql");
    logger.info("Delete all data from Event table");
    executeStatementFromSql("db_sql/eventDeleteAll.sql");
  }

  private static void printEventsFromDb() throws SQLException {
    logger.info("Display merged events by id");
    final IDaoEvent persistEvent = new PersistEvent();
    List<Event> dbEvents = persistEvent.read();
    dbEvents.stream().forEach(event -> logger
        .info(String.format("Id: %s duration: %s [ms] alert: %s",
                event.getId(),
                event.getDuration().toString(),
                event.getAlert()))
    );
  }

  private static void addEventsToDbFrom(Event[] events) {
    logger.info("Insert Event model to Hsqldb");
    final IDaoEvent persistEvent = new PersistEvent();
    Arrays.stream(events).forEach((event) -> {
      try {
        persistEvent.insert(event);
      } catch (SQLException e) {
        e.printStackTrace();
      }
    });
  }

  private static Event[] getLogEventsFromJson(String logs) throws URISyntaxException, IOException {
    logger.info("Convert 6 json input data file and merged to 3 Event objects");
    OperationalEvent operationalEvent = new OperationalEvent();
    List<Event> events = operationalEvent.buildEvents(logs);
    return events.toArray(new Event[0]);
  }

  private static void executeStatementFromSql(String pathToSql) throws URISyntaxException, IOException, SQLException {
    FileService fileService = new FileService();
    String createEventTableStr = fileService.getStringFrom(pathToSql);
    try (Statement statement = JDBCHelper.getConnection().createStatement()) {
      statement.execute(createEventTableStr);
    }
  }
}
