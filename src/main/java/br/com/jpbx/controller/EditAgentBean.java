/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "editAgentBean")
@ViewScoped
public class EditAgentBean implements Serializable{

    private Agent agent;
    
    public EditAgentBean() {
        agent=(Agent) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                get("editAgent");
    }
    public String updateAgent(){
        String res=new AgentDAO().updateAgent(agent);
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            res, ""));
        return "/restricted/agents";
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }
    
}
