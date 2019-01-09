/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import br.com.jpbx.model.ConfigDAO;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "newAgentBean")
@ViewScoped
public class NewAgentBean implements Serializable{

    private Agent agent;
    private int agentStart;
    private int agentEnd;
    
    public NewAgentBean() {
        agent=new Agent();
        agent.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
    }
    public String persistNewAgent(){
        if(String.valueOf(agent.getAgent()).matches(new ConfigDAO().getConfig().getAgentRange())){
            for (Agent a : new AgentDAO().getAllAgents(agent.getCompany())) {
                if(a.getAgent()==agent.getAgent()){
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Agente "+agent.getAgent()+" já existente.", ""));
                    return "/restricted/agents";
                }
            }
            String res=new AgentDAO().persistNewAgent(agent);
            if(!res.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            res, ""));
            return "/restricted/agents";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O número "+agent.getAgent()+" não pode ser usado pois"
                                    + " não está reservado para Agentes.", ""));
            return null;
    }
    public String persistNewManyAgents(){
        if(agentStart>=agentEnd){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O Agente fim deve ser maior que o Agente início.", ""));
            return null;
        }
        if(!String.valueOf(agentStart).matches(new ConfigDAO().getConfig().getAgentRange())){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O número "+agentStart+" não pode ser usado pois"
                                    + " não está reservado para Agentes.", ""));
            return null;
        }
        List<Agent> agents=new AgentDAO().getAllAgents(agent.getCompany());
        for(int i=agentStart;i<=agentEnd;i++){
            for (Agent a : agents) {
                if(a.getAgent()==i){
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Processo interrompido! Agente "+a.getAgent()+" já existente.", ""));
                    return null;
                }
            }
        }       
        String res=new AgentDAO().persistManyAgents(agent, agentStart, agentEnd);
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

    public int getAgentStart() {
        return agentStart;
    }

    public void setAgentStart(int agentStart) {
        this.agentStart = agentStart;
    }

    public int getAgentEnd() {
        return agentEnd;
    }

    public void setAgentEnd(int agentEnd) {
        this.agentEnd = agentEnd;
    }
    
}
