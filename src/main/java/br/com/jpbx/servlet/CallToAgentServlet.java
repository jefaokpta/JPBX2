/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.asterisk.event.QueueSummaryCenterControl;
import br.com.jpbx.util.MessageToAgent;
import br.com.jpbx.util.MessageToAgentCenter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@WebServlet(name = "CallToAgentServlet", urlPatterns = {"/CallToAgentServlet"})
public class CallToAgentServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        //CallToAgent call=(CallToAgent) getServletContext().getAttribute(request.getParameter("company")+"-"+request.getParameter("agent"));
        MessageToAgent call=new MessageToAgentCenter().getAgentMsgs().get(request.getParameter("chan"));
        JSONObject json=new JSONObject();
        if(call==null){
            json.put("exists", 0);
            out.print(json.toString());
            return;
        }     
        json.put("exists", 1);
        json.put("tel", call.getCallerid());
        json.put("queue", new QueueSummaryCenterControl().getQueueSummary().get(call.getQueueName()).getQueueName());
        out.print(json.toString());
        new MessageToAgentCenter().getAgentMsgs().remove(request.getParameter("chan"));
        //getServletContext().removeAttribute(request.getParameter("company")+"-"+request.getParameter("agent"));
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
