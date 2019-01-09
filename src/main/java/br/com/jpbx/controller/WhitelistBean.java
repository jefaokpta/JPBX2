/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.WhiteList;
import br.com.jpbx.model.WhiteListDAO;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;
import org.primefaces.event.CellEditEvent;

/**
 *
 * @author Jefferson@jpbx.com.br < https://jpbx.com.br >
 */
@Named(value = "whitelistBean")
@ViewScoped
public class WhitelistBean implements Serializable{

    private WhiteList ip;
    private List<WhiteList> ips;
    
    public WhitelistBean() {
        ips=new WhiteListDAO().getAllWhitelists();
    }
    
    public void deleteIp(WhiteList w){
        String res=new WhiteListDAO().deleteWhitelist(w);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        res, ""));
        else
            ips=new WhiteListDAO().getAllWhitelists();
    } 
    public void addWhitelist(){
        ip=new WhiteList();
        ip.setDatetime(Calendar.getInstance().getTime());
        RequestContext.getCurrentInstance()
            .execute("PF('newWhitelist').show()");
    }
    
    public String persistWhitelist(){
        String res=new WhiteListDAO().persistNewWhitelist(ip);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        res, ""));
        return "/restricted/whitelist";
    }
    public void onRowEdit(CellEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    new WhiteListDAO().updateObs(ips.get(event.getRowIndex())), ""));
    }
    public WhiteList getIp() {
        return ip;
    }

    public void setIp(WhiteList ip) {
        this.ip = ip;
    }

    public List<WhiteList> getIps() {
        return ips;
    }
    
    
}
