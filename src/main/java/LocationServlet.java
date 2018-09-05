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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

@WebServlet ("/LocationServlet")
public class LocationServlet extends HttpServlet {

    private LocationDbUtil locationDbUtil;

    @Resource(name="jdbc/javaproject")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            locationDbUtil = new LocationDbUtil(dataSource);

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
                case "LIST":
                    listLocations(request, response);
                    break;
                case "LOAD":
                    loadLocation(request, response);
                    break;
                default:
                    System.out.println("LocationServlet doGet: " + theCommand);
                    listLocations(request, response);

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
                    addLocation(request, response);
                    break;
                case "LOAD":
                    loadLocation(request, response);
                    break;
                case "UPDATE":
                    updateLocation(request, response);
                    break;
                default:
                    System.out.println("LocationServlet doPost: " + theCommand);

            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void loadLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        String theLocationId = request.getParameter("locationId");
        System.out.println("LocationServlet load locationId: " + theLocationId);

        //get record from db
        Location theLocation = LocationDbUtil.getLocation(theLocationId);

        request.setAttribute("THE_LOCATION", theLocation);
        System.out.println("Going to update-location.jsp");

        //send to update-location.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-location.jsp");
        dispatcher.forward(request, response);
    }

    private void updateLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //Read data from form
        int theLocationId = Integer.parseInt(request.getParameter("locationId"));
        System.out.println("updateLocation id: " + theLocationId);

        String theLocationName = request.getParameter("newLocation");
        BigDecimal theLocationTaxRate = new BigDecimal(request.getParameter("newTaxRate"));

        LocalDate theLocationStartDate = LocalDate.parse(request.getParameter("newStartDate"));
        LocalDate theLocationEndDate = null;
        if (request.getParameter("newEndDate") != null) {
            System.out.println("newEndDate is not null");
            if ( request.getParameter("newEndDate").isEmpty() ) {
                System.out.println("newEndDate is empty");
                theLocationEndDate = null;
            } else theLocationEndDate = LocalDate.parse(request.getParameter("newEndDate"));
        }
        //get record from db
        Location theLocation = new Location(theLocationId, theLocationName, theLocationTaxRate, theLocationStartDate, theLocationEndDate);

        locationDbUtil.updateLocation(theLocation);

        listLocations(request, response);
    }

    private void addLocation(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //Read data from form
        String theLocationName = request.getParameter("newLocation");
        System.out.println("tax rate: " + request.getParameter("newTaxRate"));
        BigDecimal theLocationTaxRate = new BigDecimal(request.getParameter("newTaxRate"));
        LocalDate theLocationStartDate = LocalDate.parse(request.getParameter("newStartDate"));
        LocalDate theLocationEndDate = null;
        //String eDate = request.getParameter("newEndDate");
        //System.out.println("addLocation end date: " + eDate);
        if (request.getParameter("newEndDate") != null) {
            System.out.println("newEndDate is not null");
            if ( request.getParameter("newEndDate").isEmpty() ) {
                System.out.println("newEndDate is empty");
                theLocationEndDate = null;
            } else theLocationEndDate = LocalDate.parse(request.getParameter("newEndDate"));
        }

        //create new Location
        Location newLocation = new Location(theLocationName, theLocationTaxRate, theLocationStartDate, theLocationEndDate);

        //add to DB
        LocationDbUtil.addLocation(newLocation);

        //send back to list as redirect to avoid duplication
        response.sendRedirect(request.getContextPath() + "/LocationServlet?command=LIST");
    }


    private void listLocations(HttpServletRequest request, HttpServletResponse response) throws Exception {

        List<Location> locations = LocationDbUtil.listLocations();

        request.setAttribute("LOCATION_LIST", locations);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-locations.jsp");
        dispatcher.forward(request, response);
    }
}
