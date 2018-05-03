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
import java.util.List;

@WebServlet("/ColorServlet")
public class ColorServlet extends HttpServlet {

    private ColorDbUtil colorDbUtil;

    @Resource(name="jdbc/javaprojects")
    private DataSource dataSource;

    @Override
    public void init() throws ServletException {
        super.init();
        try {
            colorDbUtil = new ColorDbUtil(dataSource);
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
                System.out.println("DoGet cmd was null");
            }
            switch (cmd) {
                case "LOAD":
                    System.out.println("doGet LOAD");
                    loadColor(request, response);
                    break;
                case "LIST":
                    System.out.println("doGet LIST");
                    listColors(request, response);
                    break;
                default:
                    System.out.println("DoGet: " + cmd);
                    listColors(request, response);
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
                System.out.println("DoPost cmd was null");
            }

            switch (cmd) {
                case "ADD":
                    System.out.println("doPost ADD");
                    addColor(request, response);
                    break;
                case "LOAD":
                    System.out.println("doPost LOAD");
                    loadColor(request, response);
                    break;
                case "UPDATE":
                    System.out.println("doPost UPDATE");
                    updateColor(request, response);
                    break;
                case "DELETE":
                    System.out.println("doPost DELETE");
                    deleteColor(request, response);
                    break;
                default:
                    System.out.println("DoPost: " + cmd);
                    listColors(request, response);
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    private void deleteColor(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //read id from form
        String id = request.getParameter("colorId");

        //update db
        colorDbUtil.deleteColor(id);

        //send back to list
        listColors(request, response);
    }

    private void updateColor(HttpServletRequest request, HttpServletResponse response)
            throws Exception {

        //read data from form
        int id = Integer.parseInt(request.getParameter("colorId"));
        String color = request.getParameter("newColor");

        //create new color ojbect
        Color updateColor = new Color(id, color);

        //update db
        colorDbUtil.updateColor(updateColor);

        //send back to list
        listColors(request, response);
    }

    private void loadColor(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        //read id from form data
        String theColorId = request.getParameter("colorId");
        //get record from db
        Color theColor = colorDbUtil.getColor(theColorId);
        //put record into request attribute
        request.setAttribute("THE_COLOR", theColor);
        //send to update-color.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/update-color.jsp");
        dispatcher.forward(request, response);

    }

    private void addColor(HttpServletRequest request, HttpServletResponse response)
        throws Exception {

        //read data from form
        String addColor = request.getParameter("newColor");

        //create new Color
        Color newColor = new Color(addColor);

        //add to DB
        ColorDbUtil.addColor(newColor);

        //send back to list as redirect to avoid duplication
        response.sendRedirect(request.getContextPath() + "/ColorServlet?command=LIST");
    }

    private void listColors(HttpServletRequest request, HttpServletResponse response)
            throws Exception  {
        //get products from db util
        List<Color> colors = colorDbUtil.getColorList();
        //add products to request
        request.setAttribute("COLOR_LIST", colors);
        //send to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("/list-colors.jsp");
        dispatcher.forward(request, response);
    }
}
