/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.DialPlanAction;
import br.com.jpbx.model.DialPlanRule;
import br.com.jpbx.model.DialPlanRuleDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "callGrpsBean")
@ViewScoped
public class CallGrpsBean implements Serializable{

    private Queue callGrp;
    private List<Queue> callGrps;
    
    public CallGrpsBean() {
        User userSession=(User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");
        if(!userSession.getNivel().equals("Administrador")){ //CARREGA APENAS A PROPRIA EMPRESA
            callGrps=new ArrayList<>();
            for(Queue q:new QueueDAO().getAllCallGrps()){
                if(q.getCompany()==userSession.getCompany())
                    callGrps.add(q);
            }
        }   
        else
            callGrps=new QueueDAO().getAllCallGrps();
    }
    public String editCallGrp(Queue q){
        FacesContext.getCurrentInstance().getExternalContext()
                .getSessionMap().put("editCallGrp", q);
        return "/restricted/editCallGrp";
    }
    public void alertDelete(Queue q){
        callGrp=q;
        RequestContext.getCurrentInstance()
            .execute("PF('alertCallGrp').show()"); 
    }
    public void showMembers(Queue q){
        callGrp=q;
        RequestContext.getCurrentInstance()
            .execute("PF('showMembers').show()"); 
    }
    public String deleteCallGrp(){
        for (DialPlanRule dr : new DialPlanRuleDAO().getAllDialplans(callGrp.getCompany())) {
            for (DialPlanAction da : dr.getActions()) {
                if(da.getAct().equals("callgrp")&&da.getArg1().equals(String.valueOf(callGrp.getId()))){
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Este Grupo de Chamada não pode ser apagado pois é usado na regra: "
                                        +dr.getId()+ " - "+dr.getName()+", Empresa "+dr.getCompany(), ""));
                    return "/restricted/callGrps";
                }
            }
        }
        String ret=new QueueDAO().deleteQueue(callGrp);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/callGrps";
    }
    public Queue getCallGrp() {
        return callGrp;
    }

    public void setCallGrp(Queue callGrp) {
        this.callGrp = callGrp;
    }

    public List<Queue> getCallGrps() {
        return callGrps;
    }

    
    
    
    
}
