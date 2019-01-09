/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
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
 * @author jefaokpta
 */
@WebServlet(name = "VerifyCallGrpRecServlet", urlPatterns = {"/VerifyCallGrpRecServlet"})
public class VerifyCallGrpRecServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        List<Peer> peers=new PeerDAO().getAllPeers();
        int attend=Integer.parseInt(request.getParameter("attend"));
        int callRec=0,attendRec=0;
        boolean callFound=false;
        try{
            int call=Integer.parseInt(request.getParameter("call"));
            for (Peer p : peers) {
                if(p.getName()==call){
                    callRec=p.getRecord();
                    callFound=true;
                    break;
                }
            }
            for (Peer p : peers) {
                if(p.getName()==attend){
                    attendRec=p.getRecord();
                    break;
                }
            }
            if(callFound)
                if(callRec==1&&attendRec==1){
                    out.print("1");
                    return;
                }
            out.print(attendRec);
        }catch(NumberFormatException ex){
            for (Peer p : peers) {
                if(p.getName()==attend){
                    out.print(p.getRecord());
                    return;
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
