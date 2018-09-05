import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;

public class LocationDbUtil {

    private DataSource dataSource;

    public LocationDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    private static void close(Connection conn, Statement stmt, ResultSet results) {
        try {
            if (results != null) {
                results.close();
            }
            if (stmt != null) {
                stmt.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Location getLocation(String theLocationId) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        Location theLocation;
        int locationId;

        try {
            locationId = Integer.parseInt(theLocationId);
            //System.out.println("integer id: " + locationId);

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            String sql = "select * from location_tax "
                    + "where location_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, locationId);
            //System.out.println("locationId: " + locationId);

            results = stmt.executeQuery();

            if (results.next()) {
                String locationName = results.getString("location_name");
                BigDecimal taxRate = results.getBigDecimal("tax_rate");

                Date sqlStartDate = results.getDate("tax_start_date");
                LocalDate startDate = sqlStartDate.toLocalDate();

                LocalDate endDate = null;
                if (results.getDate("tax_end_date") != null) {
                    Date sqlEndDate = results.getDate("tax_end_date");
                    endDate = sqlEndDate.toLocalDate();
                }

                theLocation = new Location(locationId, locationName, taxRate, startDate, endDate);

            } else{
                throw new Exception("Could not find location_id: " + locationId);
            }

            return theLocation;
        } finally {
            close(conn, stmt, results);
        }
    }

    public void updateLocation(Location theLocation) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?" +
                    "user=patneau&password=patneau");
            String sql = "update location_tax " +
                    "set location_name = ?, " +
                    "tax_rate = ?, " +
                    "tax_start_date = ?, " +
                    "tax_end_date = ? " +
                    "where location_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, theLocation.getLocationName());
            stmt.setBigDecimal(2, theLocation.getTaxRate());
            stmt.setDate(3, Date.valueOf(theLocation.getTaxStartDate()));

            System.out.println("Updating location - end Date: " + theLocation.getTaxEndDate());
            if (theLocation.getTaxEndDate() != null) {
                stmt.setDate(4, Date.valueOf(theLocation.getTaxEndDate()));
            } else stmt.setDate(4, null);

            stmt.setInt(5, theLocation.getLocationId());

            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }

    public static void addLocation(Location newLocation) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?" +
                    "user=patneau&password=patneau");
            String sql = "insert into location_tax " +
                    "(location_name, tax_rate, tax_start_date, tax_end_date) " +
                    "values (?, ?, ?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newLocation.getLocationName());
            stmt.setBigDecimal(2, newLocation.getTaxRate());
            stmt.setDate(3, Date.valueOf(newLocation.getTaxStartDate()));
            if (newLocation.getTaxEndDate() != null) {
                stmt.setDate(4, Date.valueOf(newLocation.getTaxEndDate()));
            } else stmt.setDate(4, null);
            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }

    public static List<Location> listLocations() throws Exception {
        List<Location> locations = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?" +
                    "user=patneau&password=patneau");
            String sql = "select * from location_tax " +
                    "order by (tax_end_date is null) desc, tax_end_date desc";
            stmt = conn.createStatement();

            results = stmt.executeQuery(sql);

            while (results.next()) {
                int id = results.getInt("location_id");
                String locationName = results.getString("location_name");
                BigDecimal taxRate = results.getBigDecimal("tax_rate");
                Date sqlStartDate = results.getDate("tax_start_date");
                LocalDate startDate = sqlStartDate.toLocalDate();

                LocalDate endDate = null;
                Date sqlEndDate = results.getDate("tax_end_date");
                if (sqlEndDate != null) {
                    endDate = sqlEndDate.toLocalDate();
                }

                Location tempLocation = new Location(id, locationName, taxRate, startDate, endDate);

                locations.add(tempLocation);
            }
            return locations;
        } finally {
            close(conn, stmt, results);
        }
    }

    public static List<Location> currentLocations() throws Exception {
        List<Location> locations = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?" +
                    "user=patneau&password=patneau");
            String sql = "select * from location_tax " +
                    "where tax_end_date is null " +
                    "order by tax_end_date";
            stmt = conn.createStatement();

            results = stmt.executeQuery(sql);

            while (results.next()) {
                int id = results.getInt("location_id");
                String locationName = results.getString("location_name");
                BigDecimal taxRate = results.getBigDecimal("tax_rate");
                /*
                Date sqlStartDate = results.getDate("tax_start_date");
                LocalDate startDate = sqlStartDate.toLocalDate();

                LocalDate endDate = null;
                Date sqlEndDate = results.getDate("tax_end_date");
                if (sqlEndDate != null) {
                    endDate = sqlEndDate.toLocalDate();
                }
                */

                Location tempLocation = new Location(id, locationName, taxRate);

                locations.add(tempLocation);
            }
            return locations;
        } finally {
            close(conn, stmt, results);
        }
    }
}
