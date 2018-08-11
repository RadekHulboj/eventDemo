package cs.database.dao;

import cs.database.helpers.JDBCHelper;
import cs.domain.Event;
import cs.domain.TypeEnum;
import cs.service.FileService;
import cs.service.JsonParser;

import java.io.IOException;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JDBCDaoImpl {
    private static final String INSERT_SQL_QUERY = "INSERT INTO EVENT(ID,state,timestamp,type,host) VALUES(?,?,?,?,?)";
    private static final String SELECT_ALL_SQL_QUERY = "SELECT ID,state,timestamp,type,host FROM EVENT";

    public static void main(String[] args) throws IOException, URISyntaxException, SQLException {

        FileService fileService = new FileService();
        String stringFrom = fileService.getStringFrom("db-sql/event.sql");
        JDBCHelper.getConnection().createStatement().execute(stringFrom);

        JsonParser jsonParser = new JsonParser();
        Event[] fromJson = jsonParser.createFromJson("logs/log.json");
        Event event = fromJson[0];


        insertEvent(event);
        List<Event> events = retrieveEvents();

    }

    private static List<Event> retrieveEvents() throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        List<Event> events = new ArrayList<>();
        try {
            con = JDBCHelper.getConnection();
            if (con == null) {
                System.out.println("Error getting the connection. Please check if the DB server is running");
                return events;
            }
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
                events.add(event);

            }
        } finally {
            cleanResources(con, ps, rs);
        }
        return events;
    }

    private static void insertEvent(Event event) throws SQLException {
        Connection con = null;
        PreparedStatement ps = null;
        try {
            con = JDBCHelper.getConnection();
            if (con == null) {
                System.out.println("Error getting the connection. Please check if the DB server is running");
                return;
            }
            con.setAutoCommit(false);
            ps = con.prepareStatement(INSERT_SQL_QUERY);
            ps.setString(1, event.getId());
            ps.setString(2, event.getState());
            ps.setDate(3, event.getTimestamp());
            ps.setInt(4, event.getType().toValue());
            ps.setString(5, event.getHost());

            ps.execute();
            System.out.println("insertEvent => " + ps.toString());
            con.commit();

        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
        } finally {
            JDBCHelper.closePrepaerdStatement(ps);
            JDBCHelper.closeConnection(con);
        }
    }

    private static void cleanResources(Connection con, PreparedStatement ps, ResultSet rs) throws SQLException {
        JDBCHelper.closeResultSet(rs);
        JDBCHelper.closePrepaerdStatement(ps);
        JDBCHelper.closeConnection(con);
    }

}