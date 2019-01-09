/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.asterisk.event.QueueStatusCenterControl;
import br.com.jpbx.asterisk.event.QueueStatusControl;
import br.com.jpbx.model.AgentLoggedQueue;
import br.com.jpbx.model.AgentLoggedQueueDAO;
import br.com.jpbx.model.AgentPause;
import br.com.jpbx.model.AgentPauseDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueAgent;
import br.com.jpbx.model.QueueAgentDAO;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.util.AgentLogged;
import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;

/**
 *
 * @author jefaokpta < jefferson@jpbx.com.br >
 */
@Named(value = "agentLoggedBean")
@ViewScoped
public class AgentLoggedBean implements Serializable{
    
    private AgentLogged log;
    private Map<String,String> queues;
    private List<AgentLoggedQueue> loggedQueues;
    private Map<String,String> pauses;
    private int queueId;
    private Map<String,QueueStatusControl> qstcc;
    //private String pause;
    
    public AgentLoggedBean() {     
        log=(AgentLogged) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                get("agentLogged");              
        queues=new HashMap<>();      
        for(QueueAgent qa: new QueueAgentDAO().getQueuesFromAgents(log.getAgent().getAgent(),log.getAgent().getCompany())){
            Queue q=new QueueDAO().getSingleQueue(qa.getQueue());
            queues.put(q.getWebName(), String.valueOf(q.getId()));
        }
        //pause="Disponível";
        pauses=new HashMap<>();
        for(AgentPause p:new AgentPauseDAO().getAllPauses(log.getAgent().getCompany())){
            pauses.put(p.getName(), p.getName());
        }
        loggedQueues=new AgentLoggedQueueDAO().getQueuesByAgent(
                String.valueOf(log.getAgent().getAgent()), log.getAgent().getCompany());
        qstcc=new QueueStatusCenterControl().getQstcc();
    }
    public void outQueue(int id){
        String res=new AgentLoggedQueueDAO().delAgent(id);
        if(!res.equals("ok")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                res, ""));
            return;
        }
        loggedQueues=new AgentLoggedQueueDAO().getQueuesByAgent(
                String.valueOf(log.getAgent().getAgent()), log.getAgent().getCompany());
    }
    public String statusBackColor(String status){
        //pause=status;
        if(status.equals("Disponível")||status.isEmpty())
            return "greenyellow";
        return "yellow" ;
    }
    public boolean pauseClockr(String status){
        return !status.equals("Disponível") ;
    }
    public String pauseTime(Date d){
        try{
            return String.valueOf(Math.floor(d.getTime()/1000));
        } catch(NullPointerException ex){
            return "";
        }
    }
    public void pauseUnpause(AgentLoggedQueue a){
        // a.setPause(pause);
        new AgentLoggedQueueDAO().updateAgentPause(a.getId(), a.getPause());
//        new AgentPauseActionDAO().persistPauseAction(new AgentPauseAction(Calendar.getInstance().getTime(),
//            Integer.parseInt(a.getAgent()), a.getQueue(),a.getCompany(), a.getPause()));
        new Asterisk().queuePause(a.getIface(),(!a.getPause().equals("Disponível")), a.getQueue(),a.getPause());
//        QueueStatusControl qsc=qstcc.get(a.getQueue());
//        for (br.com.jpbx.asterisk.event.QueueAgent qa : qsc.getAgents()) {
//            if(qa.getAgent().equals(a.getAgent())){
//                qa.setReason(a.getPause());
//                break;
//            }
//        }
        loggedQueues=new AgentLoggedQueueDAO().getQueuesByAgent(
                String.valueOf(log.getAgent().getAgent()), log.getAgent().getCompany());
    }
    public void enterQueue(){
        for (AgentLoggedQueue l : loggedQueues) {
            if(queueId==l.getQueueId()){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Este Agente consta presente na fila selecionada.", ""));
                return;
            }           
        }
        Queue q=new QueueDAO().getSingleQueue(queueId);
        if(log.getCanal().split("/")[0].toLowerCase().equals("khomp"))
            if(log.getCanal().split("/").length==3)
                log.setCanal("Local/"+(log.getCanal().split("/")[2].length()==3?"1"+log.getCanal().split("/")[2]:log.getCanal().split("/")[2])+"@Agents");
        new AgentLoggedQueueDAO().addAgent(new AgentLoggedQueue(q.getName(), 
                String.valueOf(log.getAgent().getAgent()), q.getWebName(), log.getCanal(),q.getId(),log.getPeer(),
                log.getAgent().getCompany()));
        loggedQueues=new AgentLoggedQueueDAO().getQueuesByAgent(
                String.valueOf(log.getAgent().getAgent()), log.getAgent().getCompany());
    }
    public String logout(){
        for (AgentLoggedQueue l : loggedQueues) {
            new AgentLoggedQueueDAO().delAgent(l.getId());
        }
        new Asterisk().astDBDEL("Agent", String.valueOf(log.getPeer()));
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("agentLogged");
        return "/agent?faces-redirect=true";
    }
    public void showHistory(){
        RequestContext.getCurrentInstance()
            .execute("PF('showHistory').show()");
    }  
    public void verifyAutoPause(){
        for (AgentLoggedQueue loggedQueue : loggedQueues) {
            QueueStatusControl qsc=qstcc.get(loggedQueue.getQueue());
            for (br.com.jpbx.asterisk.event.QueueAgent ag: qsc.getAgents()) {
                if(ag.getAgent().equals(loggedQueue.getAgent())){
                    if(!ag.getReason().equals(loggedQueue.getPause())){
                        String pause=ag.getReason();
                        if(pause.equals("Auto-Pause"))
                            pause="Pausa";
                        new AgentLoggedQueueDAO().updateAgentPause(loggedQueue.getId(), pause); 
                        break;
                    }
                }
            }            
        }
        loggedQueues=new AgentLoggedQueueDAO().getQueuesByAgent(
                    String.valueOf(log.getAgent().getAgent()), log.getAgent().getCompany());
    }
    public AgentLogged getLog() {
        return log;
    }

    public void setLog(AgentLogged log) {
        this.log = log;
    }

    public Map<String, String> getQueues() {
        return queues;
    }

    public List<AgentLoggedQueue> getLoggedQueues() {
        return loggedQueues;
    }

    public void setLoggedQueues(List<AgentLoggedQueue> loggedQueues) {
        this.loggedQueues = loggedQueues;
    }

    public int getQueueId() {
        return queueId;
    }

    public void setQueueId(int queueId) {
        this.queueId = queueId;
    }

    public Map<String, String> getPauses() {
        return pauses;
    }


    
    
}
