import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@WebServlet ("/OrderServlet")
public class OrderServlet extends HttpServlet {

    private OrderDbUtil orderDbUtil;

    @Resource(name="jdbc/javaproject")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            orderDbUtil = new OrderDbUtil(dataSource);

        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            //read the command parameter
            String theCommand = request.getParameter("command");

            if(theCommand == null) {
                theCommand = "LIST";
            }

            switch (theCommand) {
                case "ADDCURRENT":
                    loadCurrentCust(request, response);
                    break;
                case "ADDNEW":
                    addNewCustOrder(request, response);
                    break;
                case "LIST":
                    listOrders(request, response);
                    break;
                case "LOAD":
                    loadExistingOrder(request, response);
                    break;
                default:
                    System.out.println("OrderServlet doGet: " + theCommand);
                    listOrders(request, response);

            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }



    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            String theCommand = request.getParameter("command");

            if(theCommand == null) {
                theCommand = "LIST";
            }
            switch (theCommand) {
                case "ADD":
                    addOrder(request, response);
                    break;
                case "LOAD":
                    loadExistingOrder(request, response);
                    break;
                case "UPDATE":
                    updateOrderProduct(request, response);
                    updateOrder(request, response);
                    break;
                case "DELETE":
                    deleteOrder(request, response);
                    break;
                default:
                    System.out.println("OrderServlet doPost: " + theCommand);
                    listOrders(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void updateOrderProduct(HttpServletRequest request, HttpServletResponse response)  {
        //Get the data from the jsp from
        int theOrderId = Integer.parseInt(request.getParameter("orderId"));
        int theProdId = Integer.parseInt(request.getParameter("prodId"));
        int theColorId = Integer.parseInt(request.getParameter("colorId"));
        int theQty = Integer.parseInt(request.getParameter("qty"));
        BigDecimal thePrice = new BigDecimal(request.getParameter("price"));

        //set the OrderProduct record
        Products theProduct = new Products(theProdId);
        Color theColor = new Color(theColorId);

        OrderProduct theOrderProduct = new OrderProduct(theOrderId, theProduct, theColor, theQty, thePrice);
        theOrderProduct.setProduct(theProduct);
        theOrderProduct.setColor(theColor);

        orderDbUtil.updateOrderProduct(theOrderProduct);

    }

    private void loadCurrentCust(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Get customer Id from request Parameter
        String custId = request.getParameter("custId");

        //get customer record from db
        Customer theCustomer = CustomerDbUtil.getCustomer(custId);
        request.setAttribute("THE_CUSTOMER", theCustomer);

        //get tax location records from db
        List<Location> locations = LocationDbUtil.currentLocations();
        request.setAttribute("LOCATIONS", locations);

        //get product records from db
        List<Products> products = ProductsDbUtil.getProducts();
        request.setAttribute("PRODUCTS", products);

        //get color records from db
        List<Color> colors = ColorDbUtil.getColorList();
        request.setAttribute("COLORS", colors);

        //send to add-order.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/add-order.jsp");
        dispatcher.forward(request, response);

    }

    private void addNewCustOrder(HttpServletRequest request, HttpServletResponse response) {

    }

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
    }

    /*Load order details to update-order.jsp to view or update */
    private void loadExistingOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Get order Id from request parameter
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        System.out.println("OrderServlet load orderId: " + orderId);

        //get records from db
        Order theOrder = OrderDbUtil.getOrder(orderId);
        request.setAttribute("THE_ORDER", theOrder);

        List<OrderProduct> orderProduct = OrderDbUtil.getOrderProducts(orderId);
        request.setAttribute("ORDER_PROD", orderProduct);

        //Get product listing
        List<Products> products = ProductsDbUtil.getProducts();
        request.setAttribute("PRODUCTS", products);

        //Get color listing
        List<Color> colors = ColorDbUtil.getColorList();
        request.setAttribute("COLORS", colors);

        //send to update-order.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-order.jsp");
        dispatcher.forward(request, response);

    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Read order data from jsp form

        int theOrderId = Integer.parseInt(request.getParameter("orderId"));
        int theCustId = Integer.parseInt(request.getParameter("custId"));
        int theLocationId = Integer.parseInt(request.getParameter("locId"));
        System.out.println("updateOrder CustId: " + theCustId + " Order#: " + theOrderId + " LocID: " + theLocationId);

        //if null?
        BigDecimal theOrderTotal = BigDecimal.valueOf(0);
        BigDecimal theTaxableAmt = BigDecimal.valueOf(0);
        BigDecimal theSalesTaxAmt = BigDecimal.valueOf(0);
        BigDecimal thePaidAmt = BigDecimal.valueOf(0);
        BigDecimal theDiscountAmt = BigDecimal.valueOf(0);

        String thePymtType = request.getParameter("newPymtType");
        if (request.getParameter("newOrderTotal") != null) {
            theOrderTotal = new BigDecimal(request.getParameter("newOrderTotal"));
        }
        if (request.getParameter("newTaxableAmt") != null) {
            theTaxableAmt = new BigDecimal(request.getParameter("newTaxableAmt"));
        }
        if (request.getParameter("newSalesTaxAmt") != null) {
            theSalesTaxAmt = new BigDecimal(request.getParameter("newSalesTaxAmt"));
        }
        if (request.getParameter("newPaidAmt") != null) {
            thePaidAmt = new BigDecimal(request.getParameter("newPaidAmt"));
        }
        if (request.getParameter("newDiscountAmt") != null) {
            theDiscountAmt = new BigDecimal(request.getParameter("newDiscountAmt"));
        }
        String theOrderNote = request.getParameter("newOrderNote");

        LocalDate theOrderDate = null;
        if (request.getParameter("newOrderDate") != null) {
            System.out.println("newOrderDate is not null");
            if (request.getParameter("newOrderDate").isEmpty()) {
                System.out.println("newOrderDate is empty");

            } else theOrderDate = LocalDate.parse(request.getParameter("newOrderDate"));
        }
        LocalDate theDeliveryDate = null;
        if (request.getParameter("newDeliveryDate") != null) {
            System.out.println("newDeliveryDate is not null");
            if ( request.getParameter("newDeliveryDate").isEmpty() ) {
                System.out.println("newDeliveryDate is empty");
                //theDeliveryDate = null;
            } else theDeliveryDate = LocalDate.parse(request.getParameter("newDeliveryDate"));
        }

        //set the order record
        Customer theCustomer = new Customer(theCustId);
        Location theLocation = new Location(theLocationId);
        Order theOrder = new Order(theOrderId, theOrderDate, theCustomer, theOrderTotal, theLocation, thePymtType,
                theTaxableAmt, theSalesTaxAmt, theDeliveryDate, thePaidAmt, theDiscountAmt, theOrderNote);
        theOrder.setCustomer(theCustomer);
        theOrder.setLocation(theLocation);

        orderDbUtil.updateOrder(theOrder);

        listOrders(request, response);
    }

    /* Add new order details*/
    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Read data from form
        int theCustId = Integer.parseInt(request.getParameter("custId"));
        String theCustName = request.getParameter("custName");


        /*
        //create new Order
        Order newOrder = new Order(theOrderName, theOrderState, theOrderTaxRate, theOrderStartDate, theOrderEndDate);

        //add to DB
        OrderDbUtil.addOrder(newOrder);
        */
        //send back to list as redirect to avoid duplication
        response.sendRedirect(request.getContextPath() + "/OrderServlet?command=LIST");
    }

    /*List all existing order summaries */
    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Order> orders = orderDbUtil.listOrders();

        request.setAttribute("ORDER_LIST", orders);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-orders.jsp");
        dispatcher.forward(request, response);
    }
}
