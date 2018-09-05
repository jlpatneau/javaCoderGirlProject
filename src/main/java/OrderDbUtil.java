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
                    "order by order_date desc";
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

    public static Order getOrder(int orderId) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        Order theOrder;
        //int orderId;

        try {

            //System.out.println("integer id: " + orderId);

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            String sql = "select o.order_date, c.cust_id, c.cust_name, order_total_amt, "
                    + "o.location_id, l.location_name, l.tax_rate, o.pymt_type, o.taxable_amt, "
                    + "o.sales_tax_amt, o.delivery_date, o.discount_amt, o.paid_amt, o.order_note "
                    + "from orders o, customer c, location_tax l "
                    + "where o.cust_id = c.cust_id and "
                    + "o.location_id = l.location_id and o.order_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            //System.out.println("orderId: " + orderId);

            results = stmt.executeQuery();

            if (results.next()) {
                Date sqlOrderDate = results.getDate("order_date");
                LocalDate orderDate = sqlOrderDate.toLocalDate();
                int custId = results.getInt("cust_id");
                String custName = results.getString("cust_name");
                BigDecimal totalAmt = results.getBigDecimal("order_total_amt");
                int locId = results.getInt("location_id");
                String locName = results.getString("location_name");
                BigDecimal taxRate = results.getBigDecimal("tax_rate");
                String pymtType = results.getString("pymt_type");
                BigDecimal taxableAmt = results.getBigDecimal("taxable_amt");
                BigDecimal taxAmt = results.getBigDecimal("sales_tax_amt");

                LocalDate deliveryDate = null;
                if (results.getDate("delivery_date") != null) {
                    Date sqlDeliveryDate = results.getDate("delivery_date");
                    deliveryDate = sqlDeliveryDate.toLocalDate();
                }

                BigDecimal discountAmt = results.getBigDecimal("discount_amt");
                BigDecimal paidAmt = results.getBigDecimal("paid_amt");
                String note = results.getString("order_note");

                Customer customer = new Customer(custId, custName);
                Location location = new Location(locId, locName, taxRate);
                theOrder = new Order(orderId, orderDate, customer, totalAmt, location, pymtType, taxableAmt,
                        taxAmt, deliveryDate, discountAmt, paidAmt, note);

            } else{
                throw new Exception("Could not find order_id: " + orderId);
            }

            return theOrder;
        } finally {
            close(conn, stmt, results);
        }
    }

    public static List<OrderProduct> getOrderProducts(int orderId) throws Exception {
        List<OrderProduct> orderProducts = new ArrayList<>();

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;

        try {
            //orderId = Integer.parseInt(theOrderId);
            //System.out.println("integer id: " + orderId);

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            String sql = "select op.prod_id, p.prod_name, op.color_id, c.color_name, op.qty, op.sale_price "
                    + "from order_products op, products p, color c "
                    + "where op.order_id=? and "
                    + "op.prod_id = p.prod_id and "
                    + "op.color_id = c.color_id "
                    + "order by p.prod_name, c.color_name";

            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, orderId);
            //System.out.println("orderId: " + orderId);
            results = stmt.executeQuery();

            while (results.next()) {
                int prodId = results.getInt("prod_id");
                String prodName = results.getString("prod_name");
                int colorId = results.getInt("color_id");
                String colorName = results.getString("color_name");
                int qty = results.getInt("qty");
                BigDecimal price = results.getBigDecimal("sale_price");

                Products product = new Products(prodId, prodName);
                Color color = new Color(colorId, colorName);

                OrderProduct tempOrderProd = new OrderProduct(orderId, product, color, qty, price);
                tempOrderProd.setProduct(product);
                tempOrderProd.setColor(color);

                System.out.println("OrderDB getOrderProduct Product: " + tempOrderProd.getProduct().getProdId());
                System.out.println("OrderDB getOrderProduct Color: " + tempOrderProd.getColor().getColorId());
                orderProducts.add(tempOrderProd);

            }

            return orderProducts;
        } finally {
            close(conn, stmt, results);
        }
    }

    public void updateOrderProduct(OrderProduct theOrderProduct) {


    }

    public void updateOrder(Order theOrder) {

    }
}

