/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Agent;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.QueueLog;
import br.com.jpbx.model.QueueLogDAO;
import br.com.jpbx.util.RelAgentDisponibilityFilter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "relAgentDisponibilityBean")
@ViewScoped
public class RelAgentDisponibilityBean implements Serializable{
    
    private RelAgentDisponibilityFilter filter;
    private List<List<QueueLog>> agents;
    private List<Queue> queues;

    public RelAgentDisponibilityBean() {
        filter=(RelAgentDisponibilityFilter) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("relAgentDisponibility");
        agents=new ArrayList<>();
        queues=new QueueDAO().getAllQueues(filter.getAgentSelected().get(0).getCompany());
        for (QueueLog q : new QueueLogDAO().getRelCalls(filter)) {
            try {
                q.setData1(new String(q.getData1().getBytes("ISO-8859-1"), "UTF-8"));
            } catch (UnsupportedEncodingException ex) {
                System.out.println("ERRO NO ENCODER: "+ex.getMessage());
            }
            for (Queue queue : queues) {
                if(queue.getName().equals(q.getQueuename())){
                    q.setQueuename(queue.getWebName());
                    break;
                }
            }
            switch(q.getEvent()){
                case "PAUSE":
                    q.setEvent("Entrou em Pausa");
                    break;
                case "UNPAUSE":
                    q.setEvent("Saiu da Pausa");                
                    break;
                case "ADDMEMBER":
                    q.setEvent("Logou na Fila");
                    q.setData1(q.getData1().split("/")[1]);
                    break;
                case "REMOVEMEMBER":
                    q.setEvent("Saiu da Fila");
                    q.setData1(q.getData1().split("/")[1]);
                    break;
            }
            if(agents.isEmpty()){
                List<QueueLog> ql=new ArrayList<>();
                ql.add(q);
                agents.add(ql);
                continue;
            }
            boolean addNewList = true;
            for (List<QueueLog> a : agents) {
                if(q.getAgent().equals(a.get(0).getAgent())){
                    a.add(q);
                    addNewList=false;
                    break;
                }
            }
            if(addNewList){
                List<QueueLog> ql=new ArrayList<>();
                ql.add(q);
                agents.add(ql);
            }
        }
    }
    public String agentName(String agent){
        for (Agent a : filter.getAgentSelected()) {
            if(agent.equals(String.valueOf(a.getAgent())))
                return a.getAgent()+ " "+a.getName();
        }
        return agent;
    }
    public RelAgentDisponibilityFilter getFilter() {
        return filter;
    }

    public List<List<QueueLog>> getAgents() {
        return agents;
    }
    
    
}
