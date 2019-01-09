/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.WebServerBdo;
import br.com.jpbx.model.WebServerBdoDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "editWebServBdoBean")
@ViewScoped
public class EditWebServBdoBean implements Serializable{

    private WebServerBdo webServ;
    private boolean auth;
    
    public EditWebServBdoBean() {
        webServ=(WebServerBdo) javax.faces.context.FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editWebServBdo");
        if(webServ.getAuth()>0)
            auth=true;
    }

    public String editWebServBdo(){ 
        webServ.setAuth(0);
        if(auth)
            webServ.setAuth(1);
        String res=new WebServerBdoDAO().updateBdo(webServ);
        if(!res.equals("ok"))
            javax.faces.context.FacesContext.getCurrentInstance().addMessage(null,
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
