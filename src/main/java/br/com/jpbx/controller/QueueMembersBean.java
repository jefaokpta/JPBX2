/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueAgent;
import br.com.jpbx.model.QueueAgentDAO;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "queueMembersBean")
@ViewScoped
public class QueueMembersBean implements Serializable{

    private Queue queue;
    private List<String> agentSource;
    private List<String> agentTarget;
    private DualListModel<String> agentSelection;
    
    public QueueMembersBean() {
        queue=(Queue) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("queueMembers");
        agentSource=new ArrayList<>();
        List<Agent> agents=new AgentDAO().getAllAgents(queue.getCompany());
        for (Agent a : agents) {
            agentSource.add(a.getAgent()+" - "+a.getName());
        }
        agentTarget=new ArrayList<>();
        for (QueueAgent qa : new QueueAgentDAO().getAgentFromQueue(queue.getId())) {
            for (Agent a : agents) {
                if(qa.getAgent()==a.getAgent()){
                    agentTarget.add(a.getAgent()+" - "+a.getName());
                    agentSource.remove(a.getAgent()+" - "+a.getName());
                }
            }
        }
        
        agentSelection=new DualListModel<>(agentSource, agentTarget);
        
    }
    public void onTransfer(TransferEvent ev){      //item.toString().split(" ")[0]
        for (Object item : ev.getItems()) {
            if(ev.isAdd()){
                agentTarget.add(item.toString());
            }else{
                agentTarget.remove(item.toString());
            }
        }
    }
    public String updateMembers(){
        List<QueueAgent> agents=new ArrayList<>();
        for (String s : agentTarget) {
            agents.add(new QueueAgent(Integer.parseInt(s.split(" ")[0]), queue.getId(), queue.getCompany()));
        }
        String res=new QueueAgentDAO().updateQueue(agents,queue.getId());
        if(!res.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                res, ""));
        return "queues";
    }

    public DualListModel<String> getAgentSelection() {
        return agentSelection;
    }

    public void setAgentSelection(DualListModel<String> agentSelection) {
        this.agentSelection = agentSelection;
    }

    public Queue getQueue() {
        return queue;
    }
    
}
