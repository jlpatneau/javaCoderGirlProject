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
            throws ServletException, IOException {

        try {
            //read the command parameter
            String theCommand = request.getParameter("command");

            if(theCommand == null) {
                theCommand = "LIST";
            }

            switch (theCommand) {
                case "LIST":
                    listOrders(request, response);
                    break;
                case "LOAD":
                    loadOrder(request, response);
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
                    loadOrder(request, response);
                    break;
                case "UPDATE":
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

    private void deleteOrder(HttpServletRequest request, HttpServletResponse response) {
    }

    private void loadOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {


        String theOrderId = request.getParameter("orderId");
        System.out.println("OrderServlet load orderId: " + theOrderId);

        //get record from db
        Order theOrder = OrderDbUtil.getOrder(theOrderId);
        request.setAttribute("THE_ORDER", theOrder);

        int orderId = Integer.parseInt(request.getParameter("orderId"));
        List<OrderProduct> orderProduct = OrderDbUtil.getOrderProducts(orderId);
        request.setAttribute("ORDER_PROD", orderProduct);

        //send to update-order.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-order.jsp");
        dispatcher.forward(request, response);

    }

    private void updateOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Read data from form
        /*
        int theOrderId = Integer.parseInt(request.getParameter("orderId"));
        System.out.println("updateOrder id: " + theOrderId);

        String theOrderName = request.getParameter("newOrder");
        String theOrderState = request.getParameter("newState");
        BigDecimal theOrderTaxRate = new BigDecimal(request.getParameter("newTaxRate"));

        LocalDate theOrderDate = LocalDate.parse(request.getParameter("newOrderDate"));
        LocalDate theDeliveryDate = null;
        if (request.getParameter("newDeliveryDate") != null) {
            System.out.println("newDeliveryDate is not null");
            if ( request.getParameter("newDeliveryDate").isEmpty() ) {
                System.out.println("newDeliveryDate is empty");
                theDeliveryDate = null;
            } else theDeliveryDate = LocalDate.parse(request.getParameter("newDeliveryDate"));
        }
        //get record from db
        Order theOrder = new Order(theOrderId, theOrderDate, theCustId, theOrderTotal, theLocationId, thePymtType,
                theTaxableAmt, theSalesTaxAmt, theDeliveryDate, thePaidAmt, tDiscountAmt, theOrderNote);

        orderDbUtil.updateOrder(theOrder);
        */
        listOrders(request, response);
    }

    private void addOrder(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Read data from form
        /*
        String theOrderName = request.getParameter("newOrder");
        String theOrderState = request.getParameter("newState");
        BigDecimal theOrderTaxRate = new BigDecimal(request.getParameter("newTaxRate"));
        LocalDate theOrderStartDate = LocalDate.parse(request.getParameter("newStartDate"));
        LocalDate theOrderEndDate = null;
        //String eDate = request.getParameter("newEndDate");
        //System.out.println("addOrder end date: " + eDate);
        if (request.getParameter("newEndDate") != null) {
            System.out.println("newEndDate is not null");
            if ( request.getParameter("newEndDate").isEmpty() ) {
                System.out.println("newEndDate is empty");
                theOrderEndDate = null;
            } else theOrderEndDate = LocalDate.parse(request.getParameter("newEndDate"));
        }

        //create new Order
        Order newOrder = new Order(theOrderName, theOrderState, theOrderTaxRate, theOrderStartDate, theOrderEndDate);

        //add to DB
        OrderDbUtil.addOrder(newOrder);
        */
        //send back to list as redirect to avoid duplication
        response.sendRedirect(request.getContextPath() + "/OrderServlet?command=LIST");
    }


    private void listOrders(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Order> orders = orderDbUtil.listOrders();

        request.setAttribute("ORDER_LIST", orders);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-orders.jsp");
        dispatcher.forward(request, response);
    }
}
