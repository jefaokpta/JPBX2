/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.WebServerBdo;
import br.com.jpbx.model.WebServerBdoDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "webServBdoBean")
@ViewScoped
public class WebServBdoBean implements Serializable{

    private List<WebServerBdo> webs;
    private WebServerBdo webServ;
    
    public WebServBdoBean() {
        webs=new WebServerBdoDAO().getAllWebServerBdo();
    }
    public String deleteBdo(){
        String ret=new WebServerBdoDAO().deleteBdo(webServ.getId());
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/webServBdos";
    }
    public void alertDelete(WebServerBdo w){
        webServ=w;
        RequestContext.getCurrentInstance()
            .execute("PF('alertWebServBdo').show()");
    }   
    public String editGrp(WebServerBdo w){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("editWebServBdo", w);
        return "/restricted/editWebServBdo";
    }

    public List<WebServerBdo> getWebs() {
        return webs;
    }

    public WebServerBdo getWebServ() {
        return webServ;
    }

    public void setWebServ(WebServerBdo webServ) {
        this.webServ = webServ;
    }
    
}
