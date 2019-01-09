/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.WebServerBdo;
import br.com.jpbx.model.WebServerBdoDAO;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "newWebServBdoBean")
@ViewScoped
public class NewWebServBdoBean implements Serializable{

    private WebServerBdo webServ;
    private boolean auth;
    
    public NewWebServBdoBean() {
        webServ=new WebServerBdo();
        webServ.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCompany"));
        
    }

    public String createWebServBdo(){ 
        webServ.setAuth(0);
        if(auth)
            webServ.setAuth(1);
        String res=new WebServerBdoDAO().persistNewBdo(webServ);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    res, ""));
        return "/restricted/webServBdos";
    }
    public WebServerBdo getWebServ() {
        return webServ;
    }

    public void setWebServ(WebServerBdo webServ) {
        this.webServ = webServ;
    }

    public boolean isAuth() {
        return auth;
    }

    public void setAuth(boolean auth) {
        this.auth = auth;
    }
    
}
