package Controller;

import Modele.DAO;
import Modele.DataSourceFactory;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "AddPO_InJSON", urlPatterns = {"/addPO"})
public class AddPurchaseOrderServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        
        DAO dao = new DAO(DataSourceFactory.getDataSource());
	int product_id = Integer.parseInt(request.getParameter("id"));
        /*int quantite = Integer.parseInt(request.getParameter("nbr"));*/
	String message;
        
        try {
            String mann = dao.ManbyProduct(product_id);
            int prix = dao.ProductPrice(product_id);
            dao.insertOrder(product_id, 1, product_id, 5, (float) prix, "2018-11-29","2018-12-01",mann);
            message = "Purchase Order ajouté";
        } catch (NumberFormatException | SQLException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message = ex.getMessage();
	}
		
	Properties resultat = new Properties();
	resultat.put("message", message);

	try (PrintWriter out = response.getWriter()) {
            response.setContentType("application/json;charset=UTF-8");
            Gson gson = new Gson();
            out.println(gson.toJson(resultat));
	}
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddPurchaseOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(AddPurchaseOrderServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
