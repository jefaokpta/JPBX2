/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta
 */
@WebServlet(name = "VerifyCompromiseAndProfileDateServlet", urlPatterns = {"/VerifyCompromiseAndProfileDateServlet"})
public class VerifyCompromiseAndProfileDateServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
//        // ulimit descriptors 65536.
//        new Asterisk().getInfos("ulimit descriptors 65536");
//        
//        Calendar day=Calendar.getInstance();
//        //out.print(day.get(Calendar.DAY_OF_MONTH));
//        for (Profile p : new ProfileDAO().getProfilesLimited()) {
//            if(day.get(Calendar.DAY_OF_MONTH)==p.getCutDate()){
//                p.setCurrentValue(0);
//                new ProfileDAO().updateProfileLimited(p);
//            }
//        }
//        for (Route r : new RouteDAO().getAllRoutesLimited()) {
//            if(day.get(Calendar.DAY_OF_MONTH)==r.getCutDay()){
//                r.setCurrentMin(0);
//                new RouteDAO().updateRouteCompromise(r);
//            }
//        }
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
        processRequest(request, response);
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
        processRequest(request, response);
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
