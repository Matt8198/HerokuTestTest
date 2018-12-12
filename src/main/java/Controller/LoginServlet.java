/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.Customer;
import Modele.DAO;
import Modele.DataSourceFactory;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Utilisateur
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {

        String action = request.getParameter("action");
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        System.out.println(action + "   " + name + "   " + password);

        if (name != null && password != null) {
            switch (action) {
                case "Connexion":
                    startSession(request, response);
                case "Deconnexion":
                    exitSession(request, response);
            }
        } else {
            exitSession(request, response);
        }

    }

    private void exitSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.invalidate();
        showView("login.jsp", request, response);
    }

    private void startSession(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        HttpSession session = request.getSession();
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        session.setAttribute("name", name);
        if (password.equals("admin") && name.equals("admin")) {
            // TODO Diego : Loop through each Customer and display in table
        } else {
            // TODO Diego : Ne plus utiliser 
            System.out.println("HERE | name = " + name);
            DAO dao = new DAO(DataSourceFactory.getDataSource()
            );
            Customer c = null;
            try {
                c = dao.Customer(name);
            } catch (SQLException ex) {
                Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            Map<String, String> HM = new HashMap<String, String>();

            HM = c.getAllAttributs();

            for (Map.Entry<String, String> entry : HM.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                session.setAttribute(key, value);

            }
        }
        showView("vue_client.jsp", request, response);
    }

    private void showView(String jsp, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        getServletConfig().getServletContext().getRequestDispatcher("/views/" + jsp).forward(request, response);
        getServletConfig().getServletContext().getRequestDispatcher("/" + jsp).forward(request, response);
//        request.getRequestDispatcher(jsp).include(request, response);
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
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
