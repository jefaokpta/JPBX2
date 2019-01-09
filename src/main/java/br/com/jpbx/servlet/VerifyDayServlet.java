/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

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
@WebServlet(name = "VerifyDayServlet", urlPatterns = {"/VerifyDayServlet"})
public class VerifyDayServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            String weekday=String.valueOf(Calendar.getInstance().get(Calendar.DAY_OF_WEEK));
            System.out.println("DIA "+weekday);
            if(request.getParameter("seg").equals(weekday)){
                out.print("1");
                return;
            }
            if(request.getParameter("ter").equals(weekday)){
                out.print("1");
                return;
            }
            if(request.getParameter("qua").equals(weekday)){
                out.print("1");
                return;
            }
            if(request.getParameter("qui").equals(weekday)){
                out.print("1");
                return;
            }
            if(request.getParameter("sex").equals(weekday)){
                out.print("1");
                return;
            }
            if(request.getParameter("sab").equals(weekday)){
                out.print("1");
                return;
            }
            if(request.getParameter("dom").equals(weekday)){
                out.print("1");
                return;
            }
            out.print("0");
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
