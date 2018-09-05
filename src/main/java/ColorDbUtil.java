import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ColorDbUtil {

    private DataSource dataSource;

    public ColorDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private final String dbDriver = "com.mysql.jdbc.Driver";
    private final String dbConn = "jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau";

    public static List<Color> getColorList() throws Exception {

        List<Color> color = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;

        try {
            //connect to DB
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?"
                + "user=patneau&password=patneau");
            //conn = dataSource.getConnection();

            String sql = "select * from color " +
                    "where logical_delete_flag=0 " +
                    "order by color_name";
            stmt = conn.createStatement();
            results = stmt.executeQuery(sql);

            //process result set
            while (results.next()) {
                int id = results.getInt("color_id");
                String colorName = results.getString("color_name");
                
                Color tempColor = new Color(id, colorName);
                color.add(tempColor);
            }

            return color;

        }
        finally {

            close(conn, stmt, results);
        }
    }

    private static void close(Connection conn, Statement stmt, ResultSet results) {
        try {
            if(results != null) {
                results.close();
            }
            if(stmt !=null) {
                stmt.close();
            }
            if(conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addColor(Color newColor)
        throws Exception {

        //define connection variables
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //get connection
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?"
                + "user=patneau&password=patneau");

            //create sql
            String sql = "insert into color (color_name) " +
                    "values (?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newColor.getColorName());

            //execute sql
            stmt.execute();


        } finally {
            close(conn, stmt, null);
        }
    }

    public Color getColor(String theColorId) throws Exception {

        Color theColor;
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        int colorId;

        try {
            //convert id to integer
            colorId = Integer.parseInt(theColorId);

            //connect to db
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?"
                + "user=patneau&password=patneau");

            //create sql
            String sql = "select * from color where color_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, colorId);
            results = stmt.executeQuery();

            //retrieve data
            if (results.next()) {
                String colorName = results.getString("color_name");

                theColor = new Color(colorId, colorName);
            } else {
                throw new Exception("Could not find color id: " + colorId);
            }
            return theColor;
        } finally {
            close(conn, stmt, results);
        }

    }

    public void updateColor(Color updateColor) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbConn);

            //create sql
            String sql = "update color " +
                    "set color_name=? " +
                    "where color_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, updateColor.getColorName());
            stmt.setInt(2, updateColor.getColorId());

            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }

    public void deleteColor(String id) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //convert id to integer
            int theId = Integer.parseInt(id);

            Class.forName(dbDriver);
            conn = DriverManager.getConnection(dbConn);

            //create sql
            String sql = "update color " +
                    "set logical_delete_flag=1 " +
                    "where color_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, theId);

            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }
}
