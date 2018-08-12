package cs.database.helpers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JDBCHelper {

    static {
        try {
            Class.forName(JDBCConstants.DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            System.out.println("Driver class not found");
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection conn = DriverManager.getConnection(JDBCConstants.URL, JDBCConstants.USERNAME, JDBCConstants.PASSWORD);
        if (conn == null) {
            System.out.println("Error getting the connection. Please check if the DB server is running");
        }
        return conn;
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