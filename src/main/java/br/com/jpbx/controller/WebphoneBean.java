/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.servlet.http.HttpServletRequest;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "webphoneBean")
@ViewScoped
public class WebphoneBean implements Serializable{
    
    private boolean authView;
    private boolean phoneView;
    private int peer;
    private String pass;
    private Peer peerWeb;
    
    public WebphoneBean() {
        authView=true;
    }
    
    public String getUrlAddress(){
        //FacesContext fc=FacesContext.getCurrentInstance();
        HttpServletRequest req = (HttpServletRequest) 
                FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //String res=req.getRequestURL().toString().split("/")[2].replace(":8080", ":8181");
        
        return req.getRequestURL().toString().split("/")[2].split(":")[0];
    }
    public void loginWebPhone(){
        for (Peer p : new PeerDAO().getAllPeersWeb()) {
            if(p.getName()==peer)
                if(pass.equals(p.getUsername())){
                    authView=false;
                    phoneView=true;
                    peerWeb=p;
//                    String mux="yes";
//                    HttpServletRequest req =(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//                    if(req.getHeader("user-agent").contains("Firefox"))
//                        mux="no";
//                    if(!mux.equals(p.getRtcpMux())){
//                        p.setRtcpMux(mux);
//                        new PeerDAO().updatePeerWEBrtcpMux(p);
//                    }
                    RequestContext.getCurrentInstance()
                        .execute("register();"); 
                    return;
                }
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Ramal ou Senha Inválidos.", "")); 
    }

    public boolean isAuthView() {
        return authView;
    }

    public boolean isPhoneView() {
        return phoneView;
    }

    public int getPeer() {
        return peer;
    }

    public void setPeer(int peer) {
        this.peer = peer;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public Peer getPeerWeb() {
        return peerWeb;
    }
    
    
}
