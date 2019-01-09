/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.util.Recall;
import java.io.File;
import java.io.FileWriter;
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
@WebServlet(name = "DoRecallServlet", urlPatterns = {"/DoRecallServlet"})
public class DoRecallServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String peer =request.getParameter("peer");
        Recall recall=(Recall) getServletContext().getAttribute("recall-"+peer);
        if(recall==null)
            return;
        FileWriter file=new FileWriter(new File("/var/spool/asterisk/outgoing/Recall-"+peer+".call"));
        file.write("Channel: "+recall.getChannel()+"\n");
        file.write("CallerID: RECHAMADA "+recall.getExtension()+" <"+peer+"> \n");
        file.write("Context: Jpbx-Peers \n");
        file.write("Extension: "+recall.getExtension()+"\n");
        file.write("Priority: 1\n");
        file.write("MaxRetries: 2\n");
        file.write("RetryTime: 10\n");
        file.write("WaitTime: "+recall.getWaitTime());
        
        file.flush();
        file.close();
        getServletContext().removeAttribute("recall-"+peer);
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
