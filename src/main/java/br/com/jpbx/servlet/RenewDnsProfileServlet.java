/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.model.Acl;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.util.ResolvDNS;
import java.io.IOException;
import java.util.Calendar;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@WebServlet(name = "RenewDnsProfileServlet", urlPatterns = {"/RenewDnsProfileServlet"})
public class RenewDnsProfileServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//        boolean reload=false;
//        for (Profile p : new ProfileDAO().getAllProfiles()) {
//            for (Acl a : p.getAcls()) {
//                if(!a.getDns().isEmpty()){
//                    String ip=new ResolvDNS().getIpAddress(a.getDns(), a.getIp());
//                    if(!ip.equals(a.getIp())){
//                        a.setIp(ip);
//                        a.setDatetime(Calendar.getInstance().getTime());
//                        new ProfileDAO().updateProfileLimited(p);
//                        reload=true;
//                    }
//                }
//            }
//        }
//        if(reload)
//            new ProfileDAO().writeProfile();
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
