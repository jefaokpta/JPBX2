/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.model.CenterCost;
import br.com.jpbx.model.RelCallDAO;
import br.com.jpbx.util.RelCallFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@WebServlet(name = "RelCallsTotalsServlet", urlPatterns = {"/RelCallsTotalsServlet"})
public class RelCallsTotalsServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String[] total=new RelCallDAO().getRelCallsTotals((RelCallFilter) request.getSession().getAttribute("filter"),
                (List<CenterCost>)request.getSession().getAttribute("ccosts"));
        out.print("<span class=\"label label-success\">Atendidas "+total[0]+"</span>\n" +
"                            <span class=\"label label-default\">Não Atendidas "+total[1]+"</span>\n" +
"                            <span class=\"label label-warning\">Ocupadas "+total[2]+"</span>\n" +
"                            <span class=\"label label-danger\">Outras "+total[3]+"</span>\n" +
"                            <span class=\"label label-info\">Ligações "+total[4]+"</span>\n" +
"                            <span class=\"label label-info\">Minutos "+total[6]+"</span>\n" +
"                            <span class=\"label label-primary\">Total R$ "+total[5]+"</span>");
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
