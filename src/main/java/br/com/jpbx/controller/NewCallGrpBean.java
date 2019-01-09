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
import br.com.jpbx.model.PickupGrp;
import br.com.jpbx.model.PickupGrpDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.QueueMember;
import br.com.jpbx.util.SelectMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "newCallGrpBean")
@ViewScoped
public class NewCallGrpBean implements Serializable{

    private Queue callGrp;
    private FacesContext fc;
    private Map<String,String> mohs;
    private List<Peer> peers; 
    private Map<String,String> pickupGrps;
    private List<String> peerSource;
    private List<String> peerTarget;
    private DualListModel<String> peerSelection;
    
    public NewCallGrpBean() {
        callGrp=new Queue();
        fc=FacesContext.getCurrentInstance();
        callGrp.setCompany((int) fc.getExternalContext().getSessionMap().get("currentCompany"));
        callGrp.setTimeout(15);
        callGrp.setType("C");
        callGrp.setContext("");
        callGrp.setMonitorType(0);
        callGrp.setMonitorFormat("");
        callGrp.setQueueYouAreNext("");
        callGrp.setQueueThereAre("");
        callGrp.setQueueCallsWaiting("");
        callGrp.setQueueHoldTime("");
        callGrp.setQueueMinutes("");
        callGrp.setQueueSeconds("");
        callGrp.setQueueLessThan("");
        callGrp.setQueueThankYou("");
        callGrp.setQueueReportHold("");
        callGrp.setAnnounceFrequency(0);
        callGrp.setAnnounceRoundSeconds(0);
        callGrp.setAnnounceHoldTime("");
        callGrp.setRetry(0);
        callGrp.setWrapuptime(0);
        callGrp.setMaxlen(0);
        callGrp.setServicelevel(0);
        callGrp.setJoinempty("no");
        callGrp.setLeavewhenempty("yes");
        callGrp.setEventmemberstatus(0);
        callGrp.setEventwhencalled(0);
        callGrp.setReportholdtime(0);
        callGrp.setMemberdelay(0);
        callGrp.setWeight(0);
        callGrp.setTimeoutrestart(0);
        callGrp.setPeriodicAnnounce("");
        callGrp.setPeriodicAnnounceFreq(0);
        callGrp.setQueueMaxCalls(0);
        callGrp.setQueueMaxTimeCall(0);
        callGrp.setAlertMail("");
        callGrp.setAutopause("no");
        callGrp.setRinginuse(0);
        callGrp.setRecord(0);
        
        peers=new ArrayList<>();
        for (Peer p : new PeerDAO().getAllPeers()) {
            if(p.getCompany()==callGrp.getCompany()){
                peers.add(p);               
            }
        }       
        peerTarget=new ArrayList<>();
        peerSource=new ArrayList<>();
        for (Peer p : peers) {
            peerSource.add(p.getName()+" - "+p.getCallerid());
        }
        peerSelection=new DualListModel<>(peerSource, peerTarget);
        
        mohs=new HashMap<>();
        for (Moh m : new MohDAO().getAllMohs()) {
            if(m.getCompany()==callGrp.getCompany())
                mohs.put(m.getName(), m.getMoh());
        }
        pickupGrps=new HashMap<>();
        for (PickupGrp p : new PickupGrpDAO().getAllGrps()) {
            if(p.getCompany()==callGrp.getCompany())
                pickupGrps.put(p.getName(), String.valueOf(p.getId()));
        }
    }
    public void onTransfer(TransferEvent ev){
        for (Object item : ev.getItems()) {
            peerTarget.add(item.toString());
        }
    }
    public String persistNewCallGrp(){
        if(!callGrp.getAnnounce().equals("select"))
            callGrp.setAnnounce("/etc/asterisk/jpbx/moh/"+callGrp.getAnnounce()+
                    "/"+callGrp.getAnnounce());
        callGrp.setName("CallGrp"+System.currentTimeMillis());
        List<QueueMember> members=new ArrayList<>();
        for (int i=0;i<peerTarget.size();i++) {
            for (Peer p : peers) {               
                if(p.getName()==Integer.parseInt(peerTarget.get(i).split(" ")[0])){
                    String canal=p.getCanal();
                    if(p.getPeerType().equals("VIRTUAL"))
                        if(p.getCanal().split("/").length==3)
                            canal="Local/"+p.getName()+"@callGrpVirtual";
                    members.add(new QueueMember(String.valueOf(p.getName()),
                            callGrp.getName(), canal, 0, 0,p.getId()));
                    break;
                }
            }
        }
//        for (Peer p : peerTarget) {
//            members.add(new QueueMember(String.valueOf(p.getName()),
//                callGrp.getName(), p.getCanal(), 0, 0,p.getId()));
//        }
        callGrp.setQueueMembers(members);
        String ret=new QueueDAO().persistNewQueue(callGrp);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/callGrps";
    }

    public List<String> getPeerSource() {
        return peerSource;
    }

    public void setPeerSource(List<String> peerSource) {
        this.peerSource = peerSource;
    }

    public List<String> getPeerTarget() {
        return peerTarget;
    }

    public void setPeerTarget(List<String> peerTarget) {
        this.peerTarget = peerTarget;
    }

    public DualListModel<String> getPeerSelection() {
        return peerSelection;
    }

    public void setPeerSelection(DualListModel<String> peerSelection) {
        this.peerSelection = peerSelection;
    }
    

    


    public Queue getCallGrp() {
        return callGrp;
    }

    public void setCallGrp(Queue callGrp) {
        this.callGrp = callGrp;
    }


    public Map<String, String> getMohs() {
        return mohs;
    }

    public Map<String, String> getPickupGrps() {
        return pickupGrps;
    }
    
}
