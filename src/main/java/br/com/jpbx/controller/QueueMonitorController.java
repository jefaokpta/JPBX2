/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.asterisk.Asterisk;
import br.com.jpbx.asterisk.AsteriskDial;
import br.com.jpbx.asterisk.event.PeerStatusControl;
import br.com.jpbx.asterisk.event.QueueAgent;
import br.com.jpbx.asterisk.event.QueueStatusCenterControl;
import br.com.jpbx.asterisk.event.QueueStatusControl;
import br.com.jpbx.asterisk.event.QueueSummaryControl;
import br.com.jpbx.model.Agent;
import br.com.jpbx.model.AgentDAO;
import br.com.jpbx.model.AgentLoggedQueueDAO;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.util.FormaterSeconds;
import java.io.Serializable;
import java.util.ArrayList;
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
@Named(value = "queueMonitorController")
@ViewScoped
public class QueueMonitorController implements Serializable{

    private QueueSummaryControl qsc;
    private QueueStatusControl qstc;
    private List<QueueStatusControl> status;
    private int peerSupervisor;
    private String chanSupervisor;
    private List<Agent> agents;
    private int currentCompany;
    private QueueAgent qa;
    
    public QueueMonitorController() {
        currentCompany=(int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany");
        qsc=(QueueSummaryControl) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("queueMonitor");
        qstc=new QueueStatusCenterControl().getQstcc().get(qsc.getQueue());
        status=new ArrayList<>();
        status.add(qstc);
        agents=new AgentDAO().getAllAgents(currentCompany);
    }
    public String formatSeconds(int secs){
        return new FormaterSeconds().secondsToHours(secs);
    }
    public void resetQueue(){
        new Asterisk().queueReset(qsc.getQueue());
    }
    public void savePeerSupervisor(){
        if(peerSupervisor==0){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Insira seu ramal para ouvir as chamadas.", ""));
            return;
        }
        try{
            Peer p=new PeerDAO().getPeerByName(peerSupervisor);
            if(p.getCompany()!=currentCompany){
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ramal inserido inválido para esta empresa.", ""));
                return;
            }
            chanSupervisor=p.getCanal();
        }catch(NullPointerException ex){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Ramal inserido inválido.", ""));
        }
    }
    public void removeAgent(QueueAgent agent){
        String res=new AgentLoggedQueueDAO().delAgent(agent.getAgent(),agent.getQueue());
        if(!res.equals("ok")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                res, ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Agente "+agent.getAgent()+" Excluido.", agent.getAgent()+" "+agent.getAgentName()+" foi retirado desta fila."));    
    }
    public void doPause(QueueAgent agent){
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Agente "+agent.getAgent()+" em pausa.", "")); 
        new Asterisk().queuePause(agent.getPeer().getPeer(), true, agent.getQueue(), "Pausa");     
    }
    public void doUnpause(QueueAgent agent){
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Agente "+agent.getAgent()+" está disponível para ligações.", "")); 
        new Asterisk().queuePause(agent.getPeer().getPeer(), false, agent.getQueue(), "Disponível");   
    }
    public void spyCall(PeerStatusControl psc){
        if(chanSupervisor==null){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                                "Insira o ramal de Supervisão.", ""));
            return;
        }         
        String dstChan=psc.getPeer();
        if(dstChan.split("/")[0].equals("Local"))
            dstChan=psc.getChannel().split("-")[0];
        String res=new AsteriskDial().spyCall(chanSupervisor, dstChan);
        if(!res.equals("error")){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Não foi possível monitorar a ligação.", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Ouvindo ligação do ramal "+psc.getPeer().split("/")[1], ""));
    }
    public boolean showHideCallTime(int state){
        switch(state){
            case 5:
                return false;
            case 4:
                return true;
            case 6:
                return true;
            default:
                return false;
        }
    }
    public String textColor(int state){
        switch(state){
            case 6:
                return "white";
            default:
                return "black";
        }
    }
    public void alertPenalty(QueueAgent qa){
        this.qa=qa;
        RequestContext.getCurrentInstance()
            .execute("PF('alertPenalty').show()");
    }  
    public void changePenalty(){
        new Asterisk().changePenalty(qa.getPeer().getPeer(), qa.getQueue(), qa.getPenalty());
    }
    public String showPenalty(int p){
        switch(p){
            case 0:
                return "3";
            case 1:
                return "2";
            case 2:
                return "1";
            default:
                return "3";
        }
    }
    public String showRamalError(PeerStatusControl psc){
        if(psc.getPeer().split("/")[0].equals("Local")){
            String peer=psc.getPeer().split("/")[1];
            return peer.substring(0, peer.indexOf("@"));
        }
        if(psc.getPeer().split("/")[0].equals("Khomp")){
            if(psc.getAddress().isEmpty())
                psc.setAddress(String.valueOf(new PeerDAO().getPeerByChannel(psc.getPeer()).getName()));
            return psc.getAddress();
        }
        switch(psc.getRegisterStatus()){
            case "Unregistered":
                return psc.getPeer().split("/")[1]+" Não Registrado";
            case "Rejected":
                return psc.getPeer().split("/")[1]+" Não Registrado";
            case "Unreachable":
                return psc.getPeer().split("/")[1]+" Não Alcançável";
            default:
                return psc.getPeer().split("/")[1];
        }
    }
    public String statusBackColor(int state){
        switch(state){
            case 5:
                return "yellow";
            case 4:
                return "yellow";
            case 6:
                return "red";
            default:
                return "greenyellow";
        }
    }
    public QueueSummaryControl getQsc() {
        return qsc;
    }

    public QueueStatusControl getQstc() {
        for (QueueAgent qa : qstc.getAgents()) { // POE NOME NO AGENTE SE NAO TIVER
            if(qa.getAgentName()==null){
                for (Agent a : agents) {
                    if(qa.getAgent().equals(String.valueOf(a.getAgent()))){
                        qa.setAgentName(a.getName());
                        break;
                    }
                }
            }
        }
        return qstc;
    }

    public List<QueueStatusControl> getStatus() {
        return status;
    }

    public int getPeerSupervisor() {
        return peerSupervisor;
    }

    public void setPeerSupervisor(int peerSupervisor) {
        this.peerSupervisor = peerSupervisor;
    }

    public QueueAgent getQa() {
        return qa;
    }

    public void setQa(QueueAgent qa) {
        this.qa = qa;
    }
    
    
    
}
