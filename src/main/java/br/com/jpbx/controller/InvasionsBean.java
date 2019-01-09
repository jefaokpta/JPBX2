/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Config;
import br.com.jpbx.model.ConfigDAO;
import br.com.jpbx.model.Invasion;
import br.com.jpbx.model.InvasionDAO;
import br.com.jpbx.util.ValidateIP;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jefaokpta <jefferson@jpbx.com.br>
 */
@Named(value = "invasionsBean")
@ViewScoped
public class InvasionsBean implements Serializable{

    private Invasion invasion;
    private List<Invasion> invasions;
    private boolean autoInvasion;
    private Config conf;
    
    public InvasionsBean() {
        invasions=new InvasionDAO().getallInvasions();
        conf=new ConfigDAO().getConfig();
        if(conf.getInvasion()>0)
            autoInvasion=true;
    }

    public void switchInvasion(){
        if(autoInvasion){
            conf.setInvasion(1);
            new ConfigDAO().updateConfig(conf.getInvasion());
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Anti Invasão Ativado", "Ataques serão bloqueadas automaticamente."));
            return;
        }
        conf.setInvasion(0);
        new ConfigDAO().updateConfig(conf.getInvasion());
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_WARN,
                    "Anti Invasão Desativado", "Atenção! Ataques não serão bloqueados."));
    }
    public void deleteInvasion(Invasion i){
        String res=new InvasionDAO().deleteInvasion(i);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        res, ""));
        else
            invasions=new InvasionDAO().getallInvasions();
    } 
    public void addInvasion(){
        invasion=new Invasion();
        invasion.setMask("255.255.255.255");
        invasion.setDatetime(Calendar.getInstance().getTime());
        RequestContext.getCurrentInstance()
            .execute("PF('newInvasion').show()");
    }
    
    public String persistInvasion(){
        if(new ValidateIP().validIP(invasion.getIp())){
            String res=new InvasionDAO().persistNewInvasion(invasion);
            if(!res.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            res, ""));
            return "/restricted/invasions";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Formato de IP inválido.", ""));
        return "/restricted/invasions";
    }
    public Invasion getInvasion() {
        return invasion;
    }

    public void setInvasion(Invasion invasion) {
        this.invasion = invasion;
    }

    public List<Invasion> getInvasions() {
        return invasions;
    }

    public boolean isAutoInvasion() {
        return autoInvasion;
    }

    public void setAutoInvasion(boolean autoInvasion) {
        this.autoInvasion = autoInvasion;
    }
    
    
}
