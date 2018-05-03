import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDbUtil {

    private DataSource dataSource;

    public CustomerDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Customer> getCustomers() throws Exception {
        List<Customer> customers = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?" +
                "user=patneau&password=patneau");
            String sql = "select * from customer " +
                    "where logical_delete_flag=0 order by cust_name";
            stmt = conn.createStatement();

            results = stmt.executeQuery(sql);

            while (results.next()) {
                int id = results.getInt("cust_id");
                String custName = results.getString("cust_name");
                String custEmail = results.getString("cust_email");
                Boolean custMailing = results.getBoolean("cust_mailing");

                Customer tempCustomer = new Customer(id, custName, custEmail, custMailing);

                customers.add(tempCustomer);
            }
            return customers;
        } finally {
            close(conn, stmt, results);
        }

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

    public static void addCustomer(Customer newCustomer) throws Exception {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?" +
                    "user=patneau&password=patneau");

            //create sql
            String sql = "insert into customer " +
                    "(cust_name, cust_email, cust_mailing) " +
                    "values (?, ?, ?)";

            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newCustomer.getCustName());
            stmt.setString(2, newCustomer.getCustEmail());
            stmt.setBoolean(3, newCustomer.getMailing());

            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }

    public static Customer getCustomer(String theCustId) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        Customer theCustomer = null;
        int custId;

        try {
            custId = Integer.parseInt(theCustId);

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            String sql = "select * from customer "
                    + "where cust_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, custId);

            results = stmt.executeQuery();

            if (results.next()) {

                String custName = results.getString("cust_name");
                String custEmail = results.getString("cust_email");
                Boolean custMailing = results.getBoolean("cust_mailing");
                System.out.println("CustDbUtil load : " + custId + " mailin : " + custMailing);
                theCustomer = new Customer(custId, custName, custEmail, custMailing);

            } else {
                throw new Exception("Cannot find customer for Id : " + custId);
            }
            return theCustomer;

        } finally {
            close(conn, stmt, results);
        }
    }

    public void updateCustomer(Customer theCustomer) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            String sql = "update customer " +
                    "set cust_name = ?, cust_email = ?, cust_mailing = ? " +
                    "where cust_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, theCustomer.getCustName());
            stmt.setString(2, theCustomer.getCustEmail());
            stmt.setBoolean(3, theCustomer.getMailing());
            stmt.setInt(4, theCustomer.getCustId());

            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }

    public void deleteCustomer(int theCustId) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            String sql = "update customer " +
                    "set logical_delete_flag=1 " +
                    "where cust_id = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, theCustId);

            stmt.execute();
        } finally {
            close(conn, stmt, null);
        }
    }
}
