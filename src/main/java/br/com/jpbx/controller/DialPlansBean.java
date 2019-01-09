/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Alias;
import br.com.jpbx.model.AliasDAO;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jefaokpta
 */
@Named(value = "dialPlansBean")
@ViewScoped
public class DialPlansBean implements Serializable{

    private DialPlanRule dialplan;
    private List<DialPlanRule> dialplans;
    private List<Alias> aliases;
    
    public DialPlansBean() {
        int currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        dialplans=new DialPlanRuleDAO().getAllDialplans(currentCompany);
        aliases=new AliasDAO().getAllAlias();
    }
    public String changePriority(DialPlanRule d){
        int priority=d.getPriority()-1;
        String exten=d.getDst();
        d.setPriority(d.getPriority()-1);
        for (DialPlanRule dial : dialplans) {
            if(dial.getDst().equals(exten))
                if(dial.getPriority()==priority){
                    dial.setPriority(dial.getPriority()+1);
                    String res=new DialPlanRuleDAO().changePriority(d, dial);
                    if(!res.equals("ok"))
                        FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    res, ""));
                    break;
                }
        }
        return "/restricted/dialplans";
    }
    public String onOffDialplan(DialPlanRule d){
        if(d.getActive()==1)
            d.setActive(0);
        else
            d.setActive(1);
        String res=new DialPlanRuleDAO().updateDialplanActive(d);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        res, ""));
        return "/restricted/dialplans";
    }
    public String editGrp(DialPlanRule d){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("editDialplan", d);
        return "/restricted/editDialplan";
    }
     public String copyDialplan(DialPlanRule d){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("copyDialplan", d);
        return "/restricted/newDialplan";
    }
    public void alertDelete(DialPlanRule d){
        dialplan=d;
        RequestContext.getCurrentInstance()
            .execute("PF('alertDialplan').show()");
    }  
    public String deleteDialplan(){
        int priority=dialplan.getPriority();
        String exten=dialplan.getDst();
        dialplans.remove(dialplan);
        for (DialPlanRule d : dialplans) {
            if(d.getDst().equals(exten))
                if(d.getPriority()>priority){
                    d.setPriority(d.getPriority()-1);
                    new DialPlanRuleDAO().updateDialplan(d);
                }
        }
        String res=new DialPlanRuleDAO().deleteDialplan(dialplan);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_FATAL,
                          res, ""));
        return "/restricted/dialplans";
    }
    public String translateDst(int id){
        for (Alias a : aliases) {
            if(a.getId()==id)
                return a.getName();
        }
        return "Indefinido";
    }
    public DialPlanRule getDialplan() {
        return dialplan;
    }

    public void setDialplan(DialPlanRule dialplan) {
        this.dialplan = dialplan;
    }

    public List<DialPlanRule> getDialplans() {
        return dialplans;
    }

    public void setDialplans(List<DialPlanRule> dialplans) {
        this.dialplans = dialplans;
    }
    
    
}
