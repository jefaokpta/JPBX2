/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.linux.AstLogFail;
import br.com.jpbx.model.Config;
import br.com.jpbx.model.ConfigDAO;
import br.com.jpbx.util.Email;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@WebServlet(name = "VerifyAstUpServlet", urlPatterns = {"/VerifyAstUpServlet"})
public class VerifyAstUpServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

//        Config config=new ConfigDAO().getConfig();
//        if(config.getAstup()>0)
//            if(new Asterisk(getServletContext().getInitParameter("ASTERISK_IP"),
//                getServletContext().getInitParameter("ASTERISK_USER"),
//                getServletContext().getInitParameter("ASTERISK_PASS")).getInfos("core show uptime").isEmpty()){
//                AstLogFail fail=new AstLogFail();
//                fail.killAllAst();
//                fail.logfail();
//                System.out.println(":::::::::::::::::::::::::::: PARANDO TODOS SERVICOS JPBX PARA RECOLHER LOGS :::::::::::::::::::::::::::::::::");
//                fail.startAst();
//                new Email().emailRecoverAstFail(config.getEmail().split(","), config.getName());
//                System.out.println(":::::::::::::::::::::::::::: REINICIANDO TODOS SERVICOS JPBX  :::::::::::::::::::::::::::::::::");
//            }
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
