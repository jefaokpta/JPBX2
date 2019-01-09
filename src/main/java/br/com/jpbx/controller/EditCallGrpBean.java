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
@Named(value = "editCallGrpBean")
@ViewScoped
public class EditCallGrpBean implements Serializable{

    private Queue callGrp;
    private Map<String,String> mohs;
    private Map<String,String> pickupGrps;
    private List<Peer> peers;
    
    private List<String> peerSource;
    private List<String> peerTarget;
    private DualListModel<String> peerSelection;
    
    public EditCallGrpBean() {
        callGrp=(Queue) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editCallGrp");
        peers=new ArrayList<>();
        for (Peer p : new PeerDAO().getAllPeers()) {
            if(p.getCompany()==callGrp.getCompany()){
                peers.add(p);               
            }
        }
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
        peerSource=new ArrayList<>();
        for (Peer p : peers) {
            peerSource.add(p.getName()+" - "+p.getCallerid());
        }
        peerTarget=new ArrayList<>();       
        for (QueueMember qm : callGrp.getQueueMembers()) {
            for (Peer p : peers) {
                if(qm.getPeerId()==p.getId()){
                    peerTarget.add(p.getName()+" - "+p.getCallerid());
                    peerSource.remove(p.getName()+" - "+p.getCallerid());
                    break;
                }
            }
        }
        peerSelection=new DualListModel<>(peerSource, peerTarget);
        if(!callGrp.getAnnounce().equals("select"))
            callGrp.setAnnounce(callGrp.getAnnounce().substring(callGrp.getAnnounce().lastIndexOf("/")+1));
    }

    public void onTransfer(TransferEvent ev){      
        for (Object item : ev.getItems()) {
            if(ev.isAdd()){
                for (Peer p : peers) {               
                        if(p.getName()==Integer.parseInt(item.toString().split(" ")[0])){
                            String canal=p.getCanal();
                            if(p.getPeerType().equals("VIRTUAL"))
                                if(p.getCanal().split("/").length==3)
                                    canal="Local/"+p.getName()+"@callGrpVirtual";
                            callGrp.getQueueMembers().add(new QueueMember(String.valueOf(p.getName()),
                                    callGrp.getName(), canal, 0, 0,p.getId()));
                            break;
                        }
                    }
            }else{
                List<QueueMember> currentMembers=new ArrayList<>(callGrp.getQueueMembers());
                for (QueueMember cum : currentMembers) {
                    if(cum.getMembername().equals(item.toString().split(" ")[0])){
                        callGrp.getQueueMembers().remove(cum);
                        break;
                    }
                }
            }
        }
    }
    public String updateCallGrp(){
        if(!callGrp.getAnnounce().equals("select"))
            callGrp.setAnnounce("/etc/asterisk/jpbx/moh/"+callGrp.getAnnounce()+
                    "/"+callGrp.getAnnounce());
 
        
//        QueueMember comparedMember;       
        //callGrp.getQueueMembers().clear(); // ZERA MEMBROS
//        System.out.println("TAMANHO ::::::::::::::"+ callGrp.getQueueMembers().size());
//        for (int i=0;i<peerTarget.size();i++) { // ADD CONHECIDO OU NOVO
//            for (QueueMember qm : currentMembers) {
//                if(qm.getMembername().equals(peerTarget.get(i).split(" ")[0])){
//                    System.out.println("OPA "+qm.getMembername()+" "+peerTarget.get(i).split(" ")[0]);
//                    break;
//                }
//                else{
//                    System.out.println("OPA "+qm.getMembername()+" "+peerTarget.get(i).split(" ")[0]);
//                    for (Peer p : peers) {               
//                        if(p.getName()==Integer.parseInt(peerTarget.get(i).split(" ")[0])){
//                            callGrp.getQueueMembers().add(new QueueMember(String.valueOf(p.getName()),
//                                    callGrp.getName(), p.getCanal(), 0, 0,p.getId()));
//                            break;
//                        }
//                    }
//                }
//            }          
//        }
//        System.out.println("TAMANHO ::::::::::::::"+ callGrp.getQueueMembers().size());
        String ret=new QueueDAO().updateQueue(callGrp);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/callGrps";
    }
    public Map<String, String> getMohs() {
        return mohs;
    }

    public Map<String, String> getPickupGrps() {
        return pickupGrps;
    }

    public Queue getCallGrp() {
        return callGrp;
    }

    public void setCallGrp(Queue callGrp) {
        this.callGrp = callGrp;
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

    
}
