/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.model.RelCall;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import br.com.jpbx.util.Biller;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta
 */
@WebServlet(name = "VerifyDebitProfileAndCompromiseServlet", urlPatterns = {"/VerifyDebitProfileAndCompromiseServlet"})
public class VerifyDebitProfileAndCompromiseServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
//        for (Route r : new RouteDAO().getAllRoutesLimited()) {
//            if(new Asterisk().getVar("GROUP_COUNT(route"+r.getId()+")").equals("0")){
//                for (RelCall rc : new RelCallDAO().getCallsRouteToDebit(r.getId())) {
//                    if(rc.getBillsec()>0){
//                        r.setCurrentMin(r.getCurrentMin()+rc.getBillsec());
//                        new RouteDAO().updateRouteCompromise(r);
//                    }
//                    rc.setDebitRoute(1);
//                    new RelCallDAO().hideHistory(rc);
//                }
//            }
//        }
//        for (Profile p : new ProfileDAO().getProfilesLimited()) {
//            if(new Asterisk().getVar("GROUP_COUNT(profile"+p.getId()+")").equals("0")){
//                for (RelCall rc : new RelCallDAO().getCallsProfileToDebit(p.getId())) {
//                    if(rc.getBillsec()>0){
//                        p.setCurrentValue(p.getCurrentValue()+new Biller().accountCall(rc.getBillsec(), rc.getAccountcode()));
//                        new ProfileDAO().updateProfileLimited(p);
//                    }
//                    rc.setDebitProfile(1);
//                    new RelCallDAO().hideHistory(rc);
//                }
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
