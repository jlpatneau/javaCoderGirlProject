import javax.annotation.Resource;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.List;


@WebServlet("/CustomerServlet")
public class CustomerServlet extends HttpServlet {

    private CustomerDbUtil customerDbUtil;

    @Resource(name="jdbc/javaproject")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            customerDbUtil = new CustomerDbUtil(dataSource);

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
                    listCustomers(request, response);
                    break;
                case "LOAD":
                    loadCustomer(request, response);
                    break;
                default:
                    System.out.println("CustomerServlet doGet: " + theCommand);
                    listCustomers(request, response);

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
                    addCustomer(request, response);
                    break;
                case "LOAD":
                    loadCustomer(request, response);
                    break;
                case "UPDATE":
                    updateCustomer(request, response);
                    break;
                case "DELETE":
                    deleteCustomer(request, response);
                    break;
                default:
                    System.out.println("CustomerServlet doPost: " + theCommand);

            }
        } catch (Exception e) {
             throw new ServletException(e);
        }
    }

    private void deleteCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {

        int theCustId = Integer.parseInt(request.getParameter("custId"));

        customerDbUtil.deleteCustomer(theCustId);

        listCustomers(request, response);
    }

    private void updateCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Read data from form
        int theCustId = Integer.parseInt(request.getParameter("custId"));
        String theCustName = request.getParameter("newCustName");
        String theEmail = request.getParameter("newCustEmail");
        String stringMailing = request.getParameter("newMailing");
        Boolean theMailing;
        if (stringMailing == null) {
            theMailing = false;
        } else {
            theMailing = true;
        }
        //System.out.println("CustServlet updateCustomer mailing: " + request.getParameter("newMailing") + " : " + theMailing);

        //get record from db
        Customer theCustomer = new Customer(theCustId, theCustName, theEmail, theMailing);

        customerDbUtil.updateCustomer(theCustomer);

        listCustomers(request, response);

    }

    private void loadCustomer(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

         String theCustId = request.getParameter("custId");
            System.out.println("CustomerServlet load custId: " + theCustId);

         //get record from db
         Customer theCustomer = CustomerDbUtil.getCustomer(theCustId);

         request.setAttribute("THE_CUSTOMER", theCustomer);
            System.out.println("Going to update-customer.jsp");

        //send to update-customer.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-customer.jsp");
        dispatcher.forward(request, response);
    }

    private void addCustomer(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //read data from form
        String addCustomer = request.getParameter("newCustomer");
        String addEmail = request.getParameter("newEmail");
        String tempMailing = request.getParameter("newMailing");
        //System.out.println("tempMailing: " + tempMailing);
        Boolean addMailing = false;
        if (tempMailing != null) {
            addMailing = true;
        }

        //create new Customer
        Customer newCustomer = new Customer(addCustomer, addEmail, addMailing);

        //add to DB
        CustomerDbUtil.addCustomer(newCustomer);

        //send back to list as redirect to avoid duplication
        response.sendRedirect(request.getContextPath() + "/CustomerServlet?command=LIST");
    }

    private void listCustomers(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        List<Customer> customers = customerDbUtil.getCustomers();

        request.setAttribute("CUSTOMER_LIST", customers);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-customers.jsp");
        dispatcher.forward(request, response);

    }

}
