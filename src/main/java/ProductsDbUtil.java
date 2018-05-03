import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductsDbUtil {

    private DataSource dataSource;

    public ProductsDbUtil(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public List<Products> getProducts() throws Exception {

        List<Products> products = new ArrayList<>();

        Connection conn = null;
        Statement stmt = null;
        ResultSet results = null;

        try {
            //connect to DB
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");
            //conn = dataSource.getConnection();

            String sql = "select * from products "
                + "where logical_delete_flag = 0 order by prod_name";
            stmt = conn.createStatement();
            results = stmt.executeQuery(sql);

            //process result set
            while (results.next()) {
                int id = results.getInt("prod_id");
                String prod = results.getString("prod_name");
                BigDecimal price = results.getBigDecimal("prod_price");

                Products tempProd = new Products(id, prod, price);
                products.add(tempProd);
            }

            return products;

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

    public static void addProduct(Products newProduct) throws Exception {

        //define connection variables
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //get connection
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            //create sql
            String sql = "insert into products(prod_name, prod_price) " +
                    "values (?, ?)";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newProduct.getProdName());
            stmt.setBigDecimal(2, newProduct.getProdPrice());

            //execute sql
            stmt.execute();


        } finally {
            close(conn, stmt, null);
        }
    }


    public static Products getProduct(String theProdId) throws Exception {

        //System.out.println("getProducts id: " + theProdId);
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet results = null;
        Products theProduct = null;
        int prodId;

        try {
            prodId = Integer.parseInt(theProdId);
            //System.out.println("integer id: " + prodId);

            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?user=patneau&password=patneau");

            String sql = "select * from products "
                    + "where prod_id=?";
            stmt = conn.prepareStatement(sql);
            stmt.setInt(1, prodId);
            //System.out.println("prodId stmt: " + prodId);

            results = stmt.executeQuery();

            if (results.next()) {
                String prodName = results.getString("prod_name");
                BigDecimal prodPrice = results.getBigDecimal("prod_price");

                theProduct = new Products(prodId, prodName, prodPrice);

            } else{
                throw new Exception("Could not find prod_id: " + prodId);
            }

            return theProduct;
        } finally {
            close(conn, stmt, results);
        }
    }

    public void updateProduct(Products theProduct) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?"
                + "user=patneau&password=patneau");

            String sql = "update products "
                    + "set prod_name=?, prod_price=? "
                    + "where prod_id=?";
            stmt = conn.prepareStatement(sql);

            stmt.setString(1, theProduct.getProdName());
            stmt.setBigDecimal(2, theProduct.getProdPrice());
            stmt.setInt(3, theProduct.getProdId());

            //System.out.println("name: " + theProduct.getProdName());
            //System.out.println("price: " + theProduct.getProdPrice());
            //System.out.println("id: " + theProduct.getProdId());

            stmt.execute();

        } finally {
            close(conn, stmt, null);
        }
    }

    public void deleteProduct(int prodId) throws Exception {

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/javaproject?"
                    + "user=patneau&password=patneau");

            //set logical delete flag on db
            String sql = "update products "
                    + "set logical_delete_flag = 1 "
                    + "where prod_id=?";
            stmt = conn.prepareStatement(sql);

            //set parameters
            stmt.setInt(1, prodId);

            //execute sql
            stmt.execute();


        }finally {
            close(conn, stmt, null);
        }
    }
}
