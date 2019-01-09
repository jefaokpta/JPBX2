/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "agentsBean")
@ViewScoped
public class AgentsBean implements Serializable{

    private Agent agent;
    private List<Agent> agents;
    
    public AgentsBean() {
        int currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        agents=new AgentDAO().getAllAgents(currentCompany);
    }
    public String editAgent(Agent a){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("editAgent", a);
        return "/restricted/editAgent";
    }
    public void alertDelete(Agent a){
        agent=a;
        RequestContext.getCurrentInstance()
            .execute("PF('alertAgent').show()");
    }  
    public String deleteAgent(){
        String res=new AgentDAO().deleteAgent(agent.getId());
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_FATAL,
                          res, ""));
        return "/restricted/agents";
    }
    
    public List<Agent> getAgents() {
        return agents;
    }

    public Agent getAgent() {
        return agent;
    }
}
