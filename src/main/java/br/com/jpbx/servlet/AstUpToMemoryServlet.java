/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.asterisk.ShowChannel;
import br.com.jpbx.asterisk.SipShowPeer;
import java.io.IOException;
import java.util.ArrayList;
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
@WebServlet(name = "AstUpToMemoryServlet", urlPatterns = {"/AstUpToMemoryServlet"})
public class AstUpToMemoryServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        //PrintWriter out = response.getWriter();
        
//        try{
//            List<SipShowPeer> ssps=new ArrayList<>();
//            Asterisk ast=new Asterisk(getServletContext().getInitParameter("ASTERISK_IP"),
//                getServletContext().getInitParameter("ASTERISK_USER"),
//                getServletContext().getInitParameter("ASTERISK_PASS"));
//            String[] line;
//            String qualify="";
//            for (int vzs = 0; vzs < 30; vzs++) {       
//                for (String str : ast.getInfos("sip show peers")) { // ESTADO DOS RAMAIS
//                    try{
//                        line=str.replaceAll("\\s+", " ").split(" ");
//                        if(String.valueOf(line[6].charAt(0)).matches("[A-Z]")){                   
//                            for (int i = 6; i < line.length; i++)
//                               qualify+=line[i];
//                        }
//                        else{
//                            for (int i = 7; i < line.length; i++)
//                               qualify+=line[i];
//                        }
//                        //ssps.add(new SipShowPeer(line[0].split("/")[0], line[1], line[2], line[3],qualify));
//                        qualify="";
//
//                    } catch(NumberFormatException | IndexOutOfBoundsException ex){}
//                } // FIM DA COLETA
//                //FacesContext.getCurrentInstance().getExternalContext().getApplicationMap().put("SipShowPeers", ssps);
//                getServletContext().setAttribute("SipShowPeers", ssps);
//                
//                // ESTADO DAS LIGACOES
//                List<ShowChannel> coreShowChannels=new ArrayList<>();
//                for (String s : ast.getInfos("core show channels verbose")) {
//                    if(s.contains("Channel")||s.contains("active")||s.contains("calls"))
//                        continue;
//                    line=s.replaceAll("\\s+", " ").split(" ");
//                    String t=line[8].matches("^[0-9]{2}:[0-9]{2}:[0-9]{2}$")?line[8]:line[9];
//                    //String state=(line.length<9?line[4]:line[3]);
//                  //  coreShowChannels.add(new ShowChannel(line[0], line[4], t));
//                }
//                getServletContext().setAttribute("CoreShowChannels", coreShowChannels);
//                // FIM ESTADO LIGACOES
//                // ABAIXO APENAS JPBX CALLCENTER
//                // PAUSA DAS FILAS
//                
//                // FIM PAUSAS DA FILA
//                Thread.sleep(2000);
//                ssps.clear();
//            }
//        } catch (IOException ex) {
//            System.out.println("ASTERISK IO "+ex.getMessage());
//        } catch (InterruptedException ex) {
//            System.out.println("SLEEP "+ex.getMessage());
//        }       
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
