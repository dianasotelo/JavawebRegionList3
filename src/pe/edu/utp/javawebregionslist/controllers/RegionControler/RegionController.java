package pe.edu.utp.javawebregionslist.controllers.RegionControler;

import pe.edu.utp.javawebregionslist.models.HrService;
import pe.edu.utp.javawebregionslist.models.Region;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@WebServlet(name = "RegionController" , urlPatterns = "/regions")
public class RegionController extends HttpServlet {
    private Connection connection;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest("Post", request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        processRequest("Get", request, response);

    }

    private void processRequest(String post, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        String url = "index.jsp";
        HrService service = new HrService();
        service.setConnection(getConnection());
        //action = index
        if (action.equalsIgnoreCase("index")) {
            List<Region> regions = service.findAllRegions();
            request.setAttribute("regions", regions);//paso de conexion de objetos
            url = "ListRegions.jsp";

        }
        //action = show --> lista show encargada de mostrar una region en particular
        if (action.equalsIgnoreCase("show")) {
            int id = Integer.parseInt(request.getParameter("id"));
            //debo pasarle un objto de clase region, el controller entoncs debe tener un objeto region para ese id.
            request.setAttribute("region", service.findRegionById(id)); //pedido de Id
            url = "showRegion.jsp";

        }
        //action =new -->no le pide nada al request. Debe llevar a que el usuario visualice un documento para elegir la region
        if (action.equalsIgnoreCase("new")) {
            url = "newRegion.jsp";
        }
        //action = create --> la info q recibe crea una nueva region y luego devuelve al usuario a index
        if (action.equalsIgnoreCase("create")) {
            String name = request.getParameter("name");
            Region region = service.createRegion(name);
            request.setAttribute("regions", service.findRegionByName(name)); //LIS REGET va poder presentar las regiones
            url = "listRegions.jsp";
        }
        // action = edit --> modificar los regiones por Id
        if (action.equalsIgnoreCase("edit")) {
            int id = Integer.parseInt(request.getParameter("id"));//saber que edicion(id) voy a editar
            request.setAttribute("region", service.findRegionById(id));
            url = "editRegion.jsp";
        }
        //action=delete
        if (action.equalsIgnoreCase("delete")) {
            int id = Integer.parseInt(request.getParameter("id"));
            boolean result = service.deleteRegion(id); //elimino y voy a index
            request.setAttribute("regions", service.findAllRegions());
            url = "listRegions.jsp";
        }

        //action = update
        if (action.equalsIgnoreCase("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            boolean result = service.updateRegion(new Region(id, name));
            request.setAttribute("regions", service.findAllRegions());
            url = "listRegions.jsp";
        }
        request.getRequestDispatcher("url").forward(request, response);// se raliza la referencia al distpatcher , luego el web container si tiene la vista la presenta

    }

    private Connection getConnection() {
        if (connection == null) {
            try {
                InitialContext ctx = new InitialContext();
                DataSource dataSource = (DataSource) ctx.lookup("jdbc/MySQLDataSource");
                connection = dataSource.getConnection();
            } catch (NamingException | SQLException e) {
                e.printStackTrace();
            }
            return connection;
        }
    }
}
