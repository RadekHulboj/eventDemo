package cs.database.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCHelper {

  private static Logger logger = LoggerFactory.getLogger(JDBCHelper.class);

  private JDBCHelper() {
  }

  static {
    try {
      Class.forName(JDBCConstants.DRIVER_NAME);
    } catch (ClassNotFoundException e) {
      logger.info("Driver class not found");
    }
  }

  public static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(JDBCConstants.URL, JDBCConstants.USERNAME, JDBCConstants.PASSWORD);
  }

  public static void closeConnection(Connection con) throws SQLException {
    if (con != null) {
      con.close();
    }
  }

  public static void closePreparedStatement(PreparedStatement ps) throws SQLException {
    if (ps != null) {
      ps.close();
    }
  }

  public static void closeResultSet(ResultSet rs) throws SQLException {
    if (rs != null) {
      rs.close();
    }
  }
}