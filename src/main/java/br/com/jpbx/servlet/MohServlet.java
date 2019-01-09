/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jefaokpta
 */
@WebServlet(name = "MohServlet", urlPatterns = {"/MohServlet"})
public class MohServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ServletOutputStream stream = null;
        BufferedInputStream buf = null;
        String type="audio/mpeg";
        String ext="mp3";
        try {
            stream = response.getOutputStream();
            File mp3 = new File("/etc/asterisk/jpbx/moh/"+request.getParameter("moh")+"/"+request.getParameter("moh")+".mp3");
            
            if(!mp3.exists()){
                mp3 = new File("/etc/asterisk/jpbx/moh/"+request.getParameter("moh")+"/"+request.getParameter("moh")+".wav");
                type="audio/wav";
                ext="wav";
            }
            //set response headers
            response.setContentType(type); 

            //response.addHeader("Content-Disposition", "attachment; filename=paranoid.mp3");
            response.addHeader("Content-Disposition", "filename="+request.getParameter("moh")+"."+ext);
            response.setContentLength((int) mp3.length());

            FileInputStream input = new FileInputStream(mp3);
            buf = new BufferedInputStream(input);
            int readBytes = 0;
            //read from the file; write to the ServletOutputStream
            while ((readBytes = buf.read()) != -1)
              stream.write(readBytes);
        } catch (FileNotFoundException ex) {
            System.out.println("DEU RUIM AUDIO SERVLET: "+ex.getMessage());
        } finally {
            if (stream != null)
                stream.close();
            if (buf != null)
                buf.close();
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
