package cs.database;

import cs.database.helpers.JDBCHelper;
import cs.domain.Event;
import cs.domain.TypeEnum;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PersistEvent implements IDaoEvent {

  private static final String SELECT_ALL_SQL_QUERY = "SELECT ID,state,timestamp,type,host,duration,alert FROM EVENT";
  private static final String INSERT_SQL_QUERY = "INSERT INTO EVENT(ID,state,timestamp,type,host,duration,alert) VALUES(?,?,?,?,?,?,?)";

  @Override
  public List<Event> read() throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    ResultSet rs = null;
    List<Event> events = new ArrayList<>();
    try {
      con = JDBCHelper.getConnection();
      if (con != null) {
        ps = con.prepareStatement(SELECT_ALL_SQL_QUERY);
        rs = ps.executeQuery();
        System.out.println("retrieveEvents => " + ps.toString());
        while (rs.next()) {
          Event event = new Event();
          event.setId(rs.getString("ID"));
          event.setHost(rs.getString("HOST"));
          event.setState(rs.getString("state"));
          event.setTimestamp(rs.getDate(3));
          event.setType(TypeEnum.getEnum(rs.getInt(4)));
          event.setDuration(rs.getLong(6));
          event.setAlert(rs.getBoolean(7));
          events.add(event);
        }
      }
    } finally {
      JDBCHelper.closeConnection(con);
      JDBCHelper.closePreparedStatement(ps);
      JDBCHelper.closeResultSet(rs);
    }
    return events;
  }

  @Override
  public void insert(Event event) throws SQLException {
    Connection con = null;
    PreparedStatement ps = null;
    try {
      con = JDBCHelper.getConnection();
      if (con != null) {
        ps = con.prepareStatement(INSERT_SQL_QUERY);
        ps.setString(1, event.getId());
        ps.setString(2, event.getState());
        ps.setDate(3, event.getTimestamp());
        ps.setInt(4, event.getType() == null ? 0 : event.getType().toValue());
        ps.setString(5, event.getHost());
        ps.setInt(6, Math.toIntExact(event.getDuration()));
        ps.setBoolean(7, event.getAlert());
        ps.execute();
        System.out.println("insertEvent => " + ps.toString());
      }
    } finally {
      JDBCHelper.closeConnection(con);
      JDBCHelper.closePreparedStatement(ps);
    }
  }
}
