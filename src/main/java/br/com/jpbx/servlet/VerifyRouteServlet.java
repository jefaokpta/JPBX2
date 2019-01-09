/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.model.CenterCostDAO;
import br.com.jpbx.model.CompanyDAO;
import br.com.jpbx.model.Route;
import br.com.jpbx.model.RouteDAO;
import br.com.jpbx.model.RouteTrunk;
import br.com.jpbx.model.Trunk;
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
@WebServlet(name = "VerifyRouteServlet", urlPatterns = {"/VerifyRouteServlet"})
public class VerifyRouteServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            Route r=new RouteDAO().getSingleRoute(Integer.parseInt(request.getParameter("route")));
            int acc=(int) new CenterCostDAO().getSingleCCost(Integer.parseInt(request.getParameter("acc"))).getCcost();
            for (RouteTrunk rt : r.getRoutes()) {
                if(rt.getCcost()==acc){
                    String t1="",tp1="";
                    Trunk tr;
                    if(rt.getTrunkId1()>0){
                        tr=new TrunkDAO().getSingleTrunk(rt.getTrunkId1());
                        t1=tr.getCanal();
                        tp1=tr.getTechPrefix();
                    }
                    String t2="",tp2="";
                    if(rt.getTrunkId2()>0){
                        tr=new TrunkDAO().getSingleTrunk(rt.getTrunkId2());
                        t2=tr.getCanal();
                        tp2=tr.getTechPrefix();
                    }
                    String t3="",tp3="";
                    if(rt.getTrunkId3()>0){
                        tr=new TrunkDAO().getSingleTrunk(rt.getTrunkId3());
                        t3=tr.getCanal();
                        tp3=tr.getTechPrefix();
                    }
                    out.print(t1+","+rt.getTrunkId1()+","+tp1+","+t2+","+rt.getTrunkId2()+","+tp2+","+t3+","+rt.getTrunkId3()+","+tp3+","+
                            r.getLimitBol()+","+(r.getLimitControl()-r.getCurrentMin())+","+r.getChanLimit()+","+r.getFlags()+","+r.getTimeout()+","+
                            new CompanyDAO().getSingleCompany(r.getCompany()).getMoh());
                    break;
                }
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
