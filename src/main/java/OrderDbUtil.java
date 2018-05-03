import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDbUtil {


    private DataSource dataSource;

    public OrderDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }


    public List<Order> listOrders() throws Exception {
        List<Order> orders = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?" +
                    "user=patneau&password=patneau");
            String sql = "select order_id, orders.cust_id, cust_name, order_date, order_total_amt,  " +
                    "delivery_date, paid_amt " +
                    "from orders, customer " +
                    "where orders.cust_id = customer.cust_id " +
                    "order by order_date";
            stmt = conn.createStatement();

            results = stmt.executeQuery(sql);

            while (results.next()) {
                int orderId = results.getInt("order_id");
                int custId = results.getInt("orders.cust_id");
                String custName = results.getString("cust_name");
                Date sqlOrderDate = results.getDate("order_date");
                LocalDate orderDate = sqlOrderDate.toLocalDate();
                BigDecimal orderTotalAmt = results.getBigDecimal("order_total_amt");
                LocalDate deliveryDate = null;
                Date sqlDeliveryDate = results.getDate("delivery_date");
                if (sqlDeliveryDate != null) {
                    deliveryDate = sqlDeliveryDate.toLocalDate();
                }
                BigDecimal paidAmt = results.getBigDecimal("paid_amt");

                Customer tempCustomer = new Customer(custId, custName);

                Order tempOrder = new Order(orderId, orderDate, tempCustomer, orderTotalAmt, deliveryDate, paidAmt);
                tempOrder.setCustomer(tempCustomer);
                //System.out.println(tempOrder.tempCustomer.getCustomer());

                //Order tempOrder = new Order(orderId, orderDate, custId, custName, orderTotalAmt, deliveryDate, paidAmt);

                orders.add(tempOrder);
            }
            return orders;

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
}
