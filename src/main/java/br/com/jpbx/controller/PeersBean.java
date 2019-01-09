/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.asterisk.event.PeerStatusCenterControl;
import br.com.jpbx.asterisk.event.PeerStatusControl;
import br.com.jpbx.model.Company;
import br.com.jpbx.model.CompanyDAO;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.model.Queue;
import br.com.jpbx.model.QueueDAO;
import br.com.jpbx.model.QueueMember;
import br.com.jpbx.model.User;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "peersBean")
@ViewScoped
public class PeersBean implements Serializable{

    private List<Company> companys;
    private List<Peer> peers;
    private List<Peer> filteredPeers;
    private Peer peer; 
    //private List<SipShowPeer> sipShowPeers;
    Map<String,PeerStatusControl> pscc;
    private List<Profile> profiles;
    private String ipReg;
    private String timeR;
    private String dnd;
    private String fwd;
    private String locked;
    private String nat;
    
    public PeersBean() {
        companys=new CompanyDAO().getAllCompanys();
        User userSession=(User) FacesContext.getCurrentInstance().
                getExternalContext().getSessionMap().get("user");
        
        if(!userSession.getNivel().equals("Administrador")){
            peers=new ArrayList<>();
            for(Peer p:new PeerDAO().getAllPeers())
                if(p.getCompany()==userSession.getCompany())
                    peers.add(p);
        }
        else
            peers=new PeerDAO().getAllPeers();
//        sipShowPeers=(List<SipShowPeer>) FacesContext.getCurrentInstance().
//                getExternalContext().getApplicationMap().get("SipShowPeers");
        pscc=new PeerStatusCenterControl().getPeerStatusController();
        profiles=new ProfileDAO().getAllProfiles();
    }
    public String editPeer(Peer p){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("editPeer", p);
        return "/restricted/editPeer";
    }
    public boolean defineButtonValid(int peer,String type){
        if(type.equals("VIRTUAL"))
            return false;
//        try{
//            for (SipShowPeer sp : sipShowPeers) {
//                if(sp.getName().equals(String.valueOf(peer)))
//                    return sp.getHost().contains("Unspecified");
//            }
//        }catch(NullPointerException | ConcurrentModificationException ex){
//            System.out.println("::::: DEU RUIM NO DISABLE DO BOTAO DO RAMAL: "+ex.getMessage());
//        }
        if(pscc.containsKey(type+"/"+peer)){
            return pscc.get(type+"/"+peer).getRegisterStatus().equals("Unregistered");
        }
        return true;
    }

    public void showDetails(Peer p){
        ipReg="Não Aplicável";
        timeR="Não Aplicável";
        nat="Não Aplicável";
//        for (SipShowPeer sp : sipShowPeers) {
//            if(sp.getName().equals(String.valueOf(p.getName()))){
//                ipReg=sp.getHost();
//                timeR=sp.getQualify();
//                nat=sp.getForcerPort().equals("no")?"Não":"Sim";
//                break;
//            }
//        }
        if(pscc.containsKey(p.getCanal())){
            PeerStatusControl psc=pscc.get(p.getCanal());
            ipReg=psc.getAddress();
            if(psc.getLastCall()!=null){
                Calendar lc=Calendar.getInstance();
                lc.setTime(psc.getLastCall());
                timeR=(lc.get(Calendar.DAY_OF_MONTH)<10?"0"+lc.get(Calendar.DAY_OF_MONTH):lc.get(Calendar.DAY_OF_MONTH))+"/"+
                        ((lc.get(Calendar.MONTH)+1)<10?"0"+(lc.get(Calendar.MONTH)+1):(lc.get(Calendar.MONTH)+1))+"/"+
                        lc.get(Calendar.YEAR)+" "+lc.get(Calendar.HOUR_OF_DAY)+":"+lc.get(Calendar.MINUTE);
            }else{
                timeR="Sem Informação";
            }
        }
        dnd=p.getDnd()>0?"Ativado":"Desativado";
        fwd=p.getFwd().equals("0")?"Desatvado":"Ativado para "+p.getFwd();
        locked=p.getLock()>0?"Ativado":"Desativado";
        peer=p;
        RequestContext.getCurrentInstance()
            .execute("PF('infoPeer').show()"); 
    }
    public String deletePeer(){
        String ret=new PeerDAO().deletePeer(peer);
        if(ret.equals("ok")){ // Apagaando ramal de grp de chamada
            for (Queue q : new QueueDAO().getAllCallGrps(peer.getCompany())) { 
                for (QueueMember qm : q.getQueueMembers()) {
                    if(qm.getMembername().equals(String.valueOf(peer.getName()))){
                        Queue callGrp=new QueueDAO().getSingleQueue(q.getId());
                        callGrp.getQueueMembers().remove(qm);
                        new QueueDAO().updateQueue(callGrp);
                    }       
                }
            }
        }
        else
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
        return "/restricted/peers";
    }
    public String showProfile(int id){
        if(id==0)
            return "Nenhum";
        for (Profile p : profiles) {
            if(p.getId()==id)
                return p.getName();
        }
        return "Indefinido";
    }
    public void alertDelete(Peer p){
        peer=p;
        RequestContext.getCurrentInstance()
            .execute("PF('alertPeer').show()"); 
    }
    public Peer getPeer() {
        return peer;
    }

    public String getIpReg() {
        return ipReg;
    }

    public String getTimeR() {
        return timeR;
    }

    public String getDnd() {
        return dnd;
    }

    public String getFwd() {
        return fwd;
    }

    public String getLocked() {
        return locked;
    }

    public String getNat() {
        return nat;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public List<Peer> getPeers() {
        return peers;
    }

    public List<Peer> getFilteredPeers() {
        return filteredPeers;
    }

    public void setFilteredPeers(List<Peer> filteredPeers) {
        this.filteredPeers = filteredPeers;
    }
    
}
