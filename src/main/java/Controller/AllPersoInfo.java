/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Modele.DAO;
import Modele.DataSourceFactory;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Properties;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gson.*;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Utilisateur
 */
@WebServlet(name = "AllPersoInfo", urlPatterns = {"/AllPersoInfo"})
public class AllPersoInfo extends HttpServlet {

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
        DAO dao = new DAO(DataSourceFactory.getDataSource());

        Properties resultat = new Properties();
//        HttpSession session = request.getSession(false);
//
//        String name = (String) session.getAttribute("name");
//        System.out.println(name);
        String nomnom = "jumboeagle@example.com";
        Map<String, String> HM = new HashMap<>();
        try {
            HM = dao.Customer(nomnom).getAllAttributs();
            System.out.println(dao.Customer(nomnom));
            resultat.put("datasPerso", HM);
        } catch (SQLException ex) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            resultat.put("records", Collections.EMPTY_LIST);
            resultat.put("message", ex.getMessage());
        }

        try (PrintWriter out = response.getWriter()) {
            // On spécifie que la servlet va générer du JSON
            response.setContentType("application/json;charset=UTF-8");
            // Générer du JSON
            // Gson gson = new Gson();
            // setPrettyPrinting pour que le JSON généré soit plus lisible
            Gson gson = new GsonBuilder().setPrettyPrinting().create();

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
            Logger.getLogger(AllPersoInfo.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(AllPersoInfo.class.getName()).log(Level.SEVERE, null, ex);
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
