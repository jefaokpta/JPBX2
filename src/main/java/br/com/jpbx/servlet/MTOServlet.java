/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.servlet;

import br.com.jpbx.asterisk.event.PeerStatusCenterControl;
import br.com.jpbx.asterisk.event.PeerStatusControl;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@WebServlet(name = "MTOServlet", urlPatterns = {"/MTOServlet"})
public class MTOServlet extends HttpServlet {

    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        //response.setContentType("text/html;charset=UTF-8");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        //List<ShowChannel> csc;
        List<Peer> peers;
        //List<SipShowPeer> ssp;
//        try{
//            csc=new ArrayList<>((List<ShowChannel>)getServletContext().getAttribute("CoreShowChannels"));
//        }catch(NullPointerException ex){
//            out.print("FALHA EM SHOW CHANNELS <br /> Por favor aguarde.<br />"+ex.getMessage());
//            return;
//        }
//        try{
//            ssp=new ArrayList<>((List<SipShowPeer>)getServletContext().getAttribute("SipShowPeers"));
//        }catch(NullPointerException ex){
//            out.print("FALHA EM SIP SHOW <br />Por favor aguarde.<br />"+ex.getMessage());
//            return;
//        }
        try{
            peers=new ArrayList<>((List<Peer>)request.getSession().getAttribute("mtoPeers"));
        }catch(NullPointerException ex){
            peers=new PeerDAO().getAllPeersOfCompany(Integer.parseInt(request.getParameter("company")));
            request.getSession().setAttribute("mtoPeers", peers);
        }
        Map<String,PeerStatusControl> pscc=new PeerStatusCenterControl().getPeerStatusController();
        
        int comma=0;
        String json="[";
        for (Peer p : peers) {
            String color="default";
            String disable="disabled";
            String phone="phone";
            PeerStatusControl psc = null;
            if(p.getPeerType().equals("VIRTUAL")){
                disable="";
                color="success";
                for (Map.Entry<String, PeerStatusControl> entry : pscc.entrySet()) {
                    String key = entry.getKey();
                    PeerStatusControl value = entry.getValue();
                    if(value.getPeer().equals(String.valueOf(p.getName()))){
                        psc=value;
                        break;
                    }
                }
            }
            else
                psc=pscc.get(p.getCanal());

            if(psc!=null){
                if(!p.getPeerType().equals("VIRTUAL"))
                    if(!psc.getRegisterStatus().equals("Unregistered")){ //SE TEM REGISTRO
                        disable="";
                        color="success";
                    }
                switch(psc.getState()){
                    case 5:// SE ESTA CHAMANDO
                        color="warning";
                        break;
                    case 4:// SE ESTA CHAMANDO
                        color="warning";
                        break;
                    case 6:// SE ESTA OCUPADO
                        color="danger";
                        phone="phoneCall";
                        break;
                }
            }        
            // vamos criar o json
            if(comma>0)
                json+=","; // controle de virgula  a cada objeto criado
            comma++;
            json+="{"
                    + "\"enableClick\":"+(disable.equals("")?"1":"0")+","
                    + "\"color\":\""+color+"\","
                    + "\"disable\":\""+disable+"\","
                    + "\"name\":\""+p.getCallerid()+"\","
                    + "\"peer\":\""+p.getName()+"\","
                    + "\"phoneImg\":\""+phone+"\""
                    + "}";
        }
        json+="]";
        out.print(json);
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
