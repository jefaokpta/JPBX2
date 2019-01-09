/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import br.com.jpbx.util.RelAgentDisponibilityFilter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.model.DualListModel;
import javax.faces.context.FacesContext;
import org.primefaces.event.TransferEvent;
/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "relAgentDisponibility")
@ViewScoped
public class RelAgentDisponibility implements Serializable{

    private List<String> agentSrc;
    private List<String> agentSelected;
    private DualListModel<String> agentSelection;
    private List<Agent> agents;
    private int currentCompany;
    private RelAgentDisponibilityFilter filter;
    
    public RelAgentDisponibility() {
        currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("currentCompany");
        agents=new AgentDAO().getAllAgents(currentCompany);
        agentSrc=new ArrayList<>();
        agentSelected=new ArrayList<>();
        for (Agent a : agents) {
            agentSrc.add(a.getAgent()+" - "+a.getName());
        }
        filter=new RelAgentDisponibilityFilter();
        filter.setAgentSelected(new ArrayList<Agent>());
        filter.setStartDate(new Date());
        filter.setEndDate(new Date());
        agentSelection=new DualListModel<>(agentSrc, agentSelected);
    }
    public void onTransfer(TransferEvent ev){
        for (Object item : ev.getItems()) {
            int agent=Integer.parseInt(item.toString().split(" ")[0]);
            if(ev.isAdd()){
                for (Agent a : agents) {
                    if(agent==a.getAgent()){
                        filter.getAgentSelected().add(a);
                        break;
                    }
                }
            }
            else
                for (Agent a : agents) {
                    if(agent==a.getAgent()){
                        filter.getAgentSelected().remove(a);
                        break;
                    }
                }
        }
    }
    public String showRel(){
        if(filter.getAgentSelected().isEmpty()){
            FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_WARN,
                                    "Escolha ao menos 1 agente.", ""));
            return null;
        }
        Calendar hours=Calendar.getInstance();
        hours.setTime(filter.getEndDate());
        hours.set(Calendar.HOUR_OF_DAY, 23);
        hours.set(Calendar.MINUTE, 59);      
        hours.set(Calendar.SECOND, 59);  
        filter.setEndDate(hours.getTime());
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("relAgentDisponibility", filter);
        return "/restricted/relAgentDisponibilityShow";
    }

    public DualListModel<String> getAgentSelection() {
        return agentSelection;
    }

    public void setAgentSelection(DualListModel<String> agentSelection) {
        this.agentSelection = agentSelection;
    }


    public RelAgentDisponibilityFilter getFilter() {
        return filter;
    }

    public void setFilter(RelAgentDisponibilityFilter filter) {
        this.filter = filter;
    }
    
    
    
    
}
