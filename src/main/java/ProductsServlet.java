import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/ProductsServlet")
public class ProductsServlet  extends HttpServlet {

    private ProductsDbUtil productDbUtil;

    @Resource(name="jdbc/javaprojects")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            productDbUtil = new ProductsDbUtil(dataSource);
        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        try {
            String cmd = request.getParameter("command");
            if (cmd == null) {
                cmd = "LIST";
                System.out.println("doGet cmd was null");
            }
            switch (cmd) {
                case "LIST":
                    listProducts(request, response);
                    break;
                case "LOAD":
                    System.out.println("doGet LOAD");
                    loadProduct(request, response);
                    break;

                default:
                    System.out.println("doGet Command = " + cmd);
                    listProducts(request, response);
            }

        }
        catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String cmd = request.getParameter("command");

            if (cmd == null) {
                cmd = "LIST";
                System.out.println("doPost cmd was null");
            }
            switch (cmd) {
                case "ADD":
                    System.out.println("doPost ADD");
                    addProduct(request, response);
                    break;
                case "LOAD":
                    System.out.println("doPost LOAD");
                    loadProduct(request, response);
                    break;
                case "UPDATE":
                    System.out.println("doPost UPDATE");
                    updateProduct(request, response);
                    break;
                case "DELETE":
                    System.out.println("doPost DELETE");
                    deleteProduct(request, response);
                    break;
                default:
                    System.out.println("doPost command = " + cmd);
                    listProducts(request, response);
            }

        } catch (Exception exc) {
            throw new ServletException(exc);
        }
    }

    private void deleteProduct(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        //convert id from parameter String to int
        int prodId = Integer.parseInt(request.getParameter("prodId"));

        //delete from db
        productDbUtil.deleteProduct(prodId);

        //return to list
        listProducts(request, response);
    }

    private void updateProduct(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        //System.out.println("Updating prodId: " + request.getParameter("prodId"));
        //System.out.println("prodPrice: " + request.getParameter("newProdPrice"));

        int prodId = Integer.parseInt(request.getParameter("prodId"));
        String prodName = request.getParameter("newProdName");
        BigDecimal prodPrice = new BigDecimal(request.getParameter("newProdPrice"));
        //System.out.println("updateProduct prodID: " + prodId);

        Products theProduct = new Products(prodId, prodName, prodPrice);

        productDbUtil.updateProduct(theProduct);

        listProducts(request, response);
    }

    private void loadProduct(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        String theProdId = request.getParameter("prodId");
        //System.out.println("Load prod id: " + theProdId);

        //get record from db
        Products theProduct = ProductsDbUtil.getProduct(theProdId);

        //put record into request attribute
        request.setAttribute("THE_PRODUCT", theProduct);

        //send to update-product.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-product.jsp");
        dispatcher.forward(request, response);

    }

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //read data from form
        String addProduct = request.getParameter("newProduct");
        BigDecimal addPrice = new BigDecimal(request.getParameter("newPrice"));

        //create new Product
        Products newProduct = new Products(addProduct, addPrice);

        //add to DB
        ProductsDbUtil.addProduct(newProduct);

        //send back to list as redirect to avoid duplication
        response.sendRedirect(request.getContextPath() + "/ProductsServlet?command=LIST");

    }

    private void listProducts(HttpServletRequest request, HttpServletResponse response)
            throws Exception  {
        //get products from db util
        List<Products> products = productDbUtil.getProducts();
        //add products to request
        request.setAttribute("PROD_LIST", products);
        //send to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-products.jsp");
        dispatcher.forward(request, response);
    }
}
