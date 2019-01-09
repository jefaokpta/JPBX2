/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.model.AliasDAO;
import br.com.jpbx.model.PickupGrpDAO;
import br.com.jpbx.model.TrunkDAO;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta
 */
@WebServlet(name = "TranslateDialplanSrc", urlPatterns = {"/TranslateDialplanSrc"})
public class TranslateDialplanSrc extends HttpServlet {

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
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String act=request.getParameter("act").split("\\|")[0];
        String param;
        try{
            param=request.getParameter("act").split("\\|")[1];
        }catch(IndexOutOfBoundsException ex){
            param="";
        }
        try (PrintWriter out = response.getWriter()) {
            switch(act){
                case "peer":
                    if(param.equals("all"))
                        out.print("Ramal <br /> Qualquer");
                    else
                        out.print("Ramal <br /> "+param);
                    break;
                case "pickupgrp":
                    out.print("Grupo de Captura <br /> "+new PickupGrpDAO().getSingleGrp(Integer.parseInt(param)).getName());
                    break;
                case "trunk":
                    if(param.equals("all"))
                        out.print("Tronco <br /> Qualquer");
                    else
                        out.print("Tronco <br /> "+new TrunkDAO().getSingleTrunk(Integer.parseInt(param)).getName());
                    break;
                case "regex":
                    out.print("Expressão Regular <br /> "+param);
                    break;
                case "qlqr":
                    out.print("Qualquer");
                    break;
                case "fax":
                    out.print("Fax Virtual");
                    break;
                case "alias":
                    out.print("Alias <br /> "+new AliasDAO().getSingleAlias(Integer.parseInt(param)).getName());
                    break;
                default:
                    out.print("Indefinido");
            }
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
