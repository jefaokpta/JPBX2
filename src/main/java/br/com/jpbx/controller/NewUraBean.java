/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Moh;
import br.com.jpbx.model.MohDAO;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.Ura;
import br.com.jpbx.model.UraDAO;
import br.com.jpbx.model.UraOption;
import br.com.jpbx.util.Translate;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author jefaokpta
 */
@Named(value = "newUraBean")
@ViewScoped
public class NewUraBean implements Serializable{

    private Ura ura;
    private Map<String,String> mohs;
    private Map<String,String> peers;
    private Map<String,String> callGrps;
    private Map<String,String> subUras;
    private Map<String,String> queues;
    private boolean timeoutPeer;
    private boolean timeoutCallGrp;
    private boolean timeoutSubUra;
    private boolean timeoutRule;
    private boolean timeoutQueue;
    private boolean invalidPeer;
    private boolean invalidCallGrp;
    private boolean invalidSubUra;
    private boolean invalidRule;
    private boolean invalidQueue;
    private boolean optionPeer;
    private boolean optionCallGrp;
    private boolean optionSubUra;
    private boolean optionRule;
    private boolean optionQueue;
    private List<UraOption> uraOptions;
    private UraOption uraOption;
    private List<Queue> queuesList;
    private boolean habPeers;
    private List<Peer> peersList;
    private List<Ura> uraList;
    private List<Queue> callGrpList;
    
    public NewUraBean() {
        ura=new Ura();
        ura.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
        ura.setLanguage("pt_BR");
        mohs=new HashMap<>();
        for (Moh m : new MohDAO().getAllMohs()) {
            if(m.getCompany()==ura.getCompany())
                mohs.put(m.getName(), m.getMoh());
        }
        peers=new HashMap<>();
        peersList=new PeerDAO().getAllPeers();
        for (Peer p : peersList) {
            if(p.getCompany()==ura.getCompany())
                peers.put(p.getName()+" - "+p.getCallerid(), String.valueOf(p.getName()));
        }
        callGrps=new HashMap<>();
        callGrpList=new QueueDAO().getAllCallGrps();
        for (Queue q : callGrpList) {
            if(q.getCompany()==ura.getCompany())
                callGrps.put(q.getWebName(), String.valueOf(q.getId()));
        }
        queues=new HashMap<>();
        queuesList=new QueueDAO().getAllQueues(ura.getCompany());
        for (Queue q : queuesList) {
            queues.put(q.getWebName(), String.valueOf(q.getId()));
        }
        subUras=new HashMap<>();
        uraList=new UraDAO().getAllUras();
        for (Ura u : uraList) {
            if(u.getCompany()==ura.getCompany())
                subUras.put(u.getWebName(), u.getName());
        }
        uraOptions=new ArrayList<>();
        uraOption=new UraOption();
    }
    public String submitNewUra(){
        if(!ura.getBackground().equals("empty")){
            ura.setDialPeers(0);
            if(habPeers)
                ura.setDialPeers(1);
            ura.setName("URA_"+System.currentTimeMillis());
            ura.setUraOptions(uraOptions);
            String ret=new UraDAO().persistNewUra(ura);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/uras";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                        "Um áudio de saudação é obrigatório!", ""));
        return null;
    }
    public void removeOption(UraOption op){
        uraOptions.remove(op);
    }
    public void addOption(){
        if(uraOption.getOption()>=0){
            if(!uraOptions.contains(uraOption)){
                uraOptions.add(new UraOption(uraOption.getOption(), 
                        uraOption.getAction(), uraOption.getParam()));
                uraOption.setParam("");
                return;
            }
            FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_FATAL,
                          "Opção "+uraOption.getOption()+" já existe!", ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                  new FacesMessage(FacesMessage.SEVERITY_FATAL,
                          "Opção deve ser um número positivo!", ""));
    }
    public String translateAction(String action){
        return new Translate().transUraOption(action);
    }
    public String translateParam(String act,String param){
        switch(act){
            case "hangup":
                return "";
            case "begin":
                return "";
            case "peer":
                return new Translate().transUraParam(peersList, act, param);
            case "callGrp":
                return new Translate().transUraParam(callGrpList, act, param);
            case "subUra":
                return new Translate().transUraParam(uraList, act, param);
            case "queue":
                return new Translate().transUraParam(queuesList, act, param);
            case "rule":
                return param; 
            default:
                return "Indefinido";
        }
    }
    public void changeInvalidAction(){
        switch(ura.getInvalidAction()){
            case "peer":
                invalidPeer=true;
                invalidCallGrp=false;
                invalidSubUra=false;
                invalidRule=false;
                invalidQueue=false;
                break;
            case "callGrp":
                invalidCallGrp=true;
                invalidPeer=false;
                invalidSubUra=false;
                invalidRule=false;
                invalidQueue=false;
                break;
            case "subUra":
                invalidSubUra=true;
                invalidPeer=false;
                invalidCallGrp=false;
                invalidRule=false;
                invalidQueue=false;
                break;
            case "rule":
                invalidRule=true;
                invalidPeer=false;
                invalidCallGrp=false;
                invalidSubUra=false;
                invalidQueue=false;
                break;
            case "queue":
                invalidRule=false;
                invalidPeer=false;
                invalidCallGrp=false;
                invalidSubUra=false;
                invalidQueue=true;
                break;
            default:
                invalidPeer=false;
                invalidCallGrp=false;
                invalidSubUra=false;
                invalidRule=false;
                invalidQueue=false;
        }
    }
    public void changeTimeoutAction(){
        switch(ura.getTimeoutAction()){
            case "peer":
                timeoutPeer=true;
                timeoutCallGrp=false;
                timeoutSubUra=false;
                timeoutRule=false;
                timeoutQueue=false;
                break;
            case "callGrp":
                timeoutCallGrp=true;
                timeoutPeer=false;
                timeoutSubUra=false;
                timeoutRule=false;
                timeoutQueue=false;
                break;
            case "subUra":
                timeoutSubUra=true;
                timeoutPeer=false;
                timeoutCallGrp=false;
                timeoutRule=false;
                timeoutQueue=false;
                break;
            case "rule":
                timeoutRule=true;
                timeoutPeer=false;
                timeoutCallGrp=false;
                timeoutSubUra=false;
                timeoutQueue=false;
                break;
            case "queue":
                timeoutRule=false;
                timeoutPeer=false;
                timeoutCallGrp=false;
                timeoutSubUra=false;
                timeoutQueue=true;
                break;
            default:
                timeoutPeer=false;
                timeoutCallGrp=false;
                timeoutSubUra=false;
                timeoutRule=false;
                timeoutQueue=false;
        }
    }
    public void changeOptionAction(){
        switch(uraOption.getAction()){
            case "peer":
                optionPeer=true;
                optionCallGrp=false;
                optionSubUra=false;
                optionRule=false;
                optionQueue=false;
                break;
            case "callGrp":
                optionCallGrp=true;
                optionPeer=false;
                optionSubUra=false;
                optionRule=false;
                optionQueue=false;
                break;
            case "subUra":
                optionSubUra=true;
                optionPeer=false;
                optionCallGrp=false;
                optionRule=false;
                optionQueue=false;
                break;
            case "rule":
                optionRule=true;
                optionPeer=false;
                optionCallGrp=false;
                optionSubUra=false;
                optionQueue=false;
                break;
            case "queue":
                optionRule=false;
                optionPeer=false;
                optionCallGrp=false;
                optionSubUra=false;
                optionQueue=false;
                optionQueue=true;
                break;
            default:
                optionPeer=false;
                optionCallGrp=false;
                optionSubUra=false;
                optionRule=false;
                optionQueue=false;
        }
    }

    public boolean isTimeoutQueue() {
        return timeoutQueue;
    }

    public boolean isInvalidQueue() {
        return invalidQueue;
    }

    public boolean isOptionQueue() {
        return optionQueue;
    }

    public Map<String, String> getQueues() {
        return queues;
    }

    public UraOption getUraOption() {
        return uraOption;
    }

    public void setUraOption(UraOption uraOption) {
        this.uraOption = uraOption;
    }

    public boolean isHabPeers() {
        return habPeers;
    }

    public void setHabPeers(boolean habPeers) {
        this.habPeers = habPeers;
    }

    public List<UraOption> getUraOptions() {
        return uraOptions;
    }

    public void setUraOptions(List<UraOption> uraOptions) {
        this.uraOptions = uraOptions;
    }

    public boolean isOptionPeer() {
        return optionPeer;
    }

    public boolean isOptionCallGrp() {
        return optionCallGrp;
    }

    public boolean isOptionSubUra() {
        return optionSubUra;
    }

    public boolean isOptionRule() {
        return optionRule;
    }

    public boolean isInvalidPeer() {
        return invalidPeer;
    }

    public boolean isInvalidCallGrp() {
        return invalidCallGrp;
    }

    public boolean isInvalidSubUra() {
        return invalidSubUra;
    }

    public boolean isInvalidRule() {
        return invalidRule;
    }

    public boolean isTimeoutPeer() {
        return timeoutPeer;
    }

    public boolean isTimeoutCallGrp() {
        return timeoutCallGrp;
    }

    public boolean isTimeoutSubUra() {
        return timeoutSubUra;
    }

    public boolean isTimeoutRule() {
        return timeoutRule;
    }

    public Map<String, String> getCallGrps() {
        return callGrps;
    }

    public Map<String, String> getSubUras() {
        return subUras;
    }

    

    public Map<String, String> getPeers() {
        return peers;
    }

    public Ura getUra() {
        return ura;
    }

    public void setUra(Ura ura) {
        this.ura = ura;
    }

    public Map<String, String> getMohs() {
        return mohs;
    }
    
}
