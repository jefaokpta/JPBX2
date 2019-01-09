/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Config;
import br.com.jpbx.model.ConfigDAO;
import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.model.PickupGrp;
import br.com.jpbx.model.PickupGrpDAO;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.util.ValidatePeer;
import br.com.jpbx.util.VerifyPassword;
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
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "newPeerBean")
@ViewScoped
public class NewPeerBean implements Serializable{

    private Peer peer;
    private List<Peer> peers;
    private String borderColor;
    private boolean submitCtrl;
    private Config agentRange;
    //private List<PickupGrp> grps;
    private Map<String,String> selectGrp;
    //private List<Profile> profiles;
    private Map<String,String> selectProfile;

    private String audioCodec1;
    private String audioCodec2;
    private String audioCodec3;
    private String videoCodec1;
    private String videoCodec2;
    private String videoCodec3;
    
    private int rangeStart;
    private int rangeFinish;
    
    public NewPeerBean() {
        peer=new Peer();
        peers=new PeerDAO().getAllPeers();
        submitCtrl=false;
  
        peer.setCompany((int) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("currentCompany"));
        peer.setFacilityPass(1234);
        peer.setCallLimit(1);
        peer.setLanguage("pt_BR");
        peer.setDtmfmode("rfc2833");
        peer.setNat("no");
        peer.setRecord(0);
        peer.setQualify("no");
        peer.setEmail("");
        peer.setFwd("0");
        //peer.setMailbox("");
        peer.setType("friend");
        peer.setHost("dynamic");
        peer.setPeerType("SIP");
        peer.setRtcpMux("yes");
        peer.setContext("jpbxRoute"+peer.getCompany());
        agentRange=new ConfigDAO().getConfig();
        //grps=new PickupGrpDAO().getAllGrps();
        selectGrp=new HashMap<>();
        for(PickupGrp p:new PickupGrpDAO().getAllGrps()) //MOSTRA GRUPOS COM BASE EM currentCompany
            if(p.getCompany()==peer.getCompany())
                selectGrp.put(p.getName(), String.valueOf(p.getId()));
        selectProfile=new HashMap<>();
        for(Profile p:new ProfileDAO().getAllProfilesForms())
            selectProfile.put(p.getName(), String.valueOf(p.getId()));
        
    }
    public String getPeerTypePag(){
        return "/WEB-INF/includes/peer"+peer.getPeerType()+".xhtml";
    }
    public String getPeerTypePagLot(){
        return "/WEB-INF/includes/peers"+peer.getPeerType()+".xhtml";
    }
    public void verifyFacilityPass(){
        submitCtrl=true;
        if(!new VerifyPassword().verifyPasswordRange(peer.getFacilityPass())){
            submitCtrl=false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "A senha deve apresentar 3 ou 4 números.", ""));
        }
    }
    public void verifyVoicemailPass(){
        if(peer.getMailbox()!=0){
            submitCtrl=true;
            if(!new VerifyPassword().verifyPasswordRange(peer.getMailbox())){
                submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha deve apresentar 3 ou 4 números.", ""));
            }
        }
    }
    public void verifyPeer(){
        borderColor="greenyellow";
        submitCtrl=true;
        if(!new ValidatePeer().validatePeerNumber(peer.getName())){
            borderColor="red";
            submitCtrl=false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O número de ramal deve ser entre 10 e 9999.", ""));
            return;
        }
        if(String.valueOf(peer.getName()).matches(agentRange.getAgentRange())){
            borderColor="red";
            submitCtrl=false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O número "+peer.getName()+" não pode ser usado pois"
                                    + " está reservado para Agentes.", ""));
            return;
        }
        for(Peer p:peers)
            if(p.getName()==peer.getName()){
                borderColor="red";
                submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "O Ramal "+peer.getName()+" já existe.\n"
                                        + "Por favor escolha outro.", ""));
                break;
            }
    }
    public void verifyPassword(){
        submitCtrl=true;       
        if(!new VerifyPassword().verifyPassword(peer.getSecret())){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha deve ter ao menos 2 letras e 2 números.", ""));
            submitCtrl=false;
        }
    }
    public void verifyPasswordWEB(){
        submitCtrl=true;       
        if(!new VerifyPassword().verifyPassword(peer.getUsername())){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha deve ter ao menos 2 letras e 2 números.", ""));
            submitCtrl=false;
        }
    }
    public String submitNewPeer(){
        if(submitCtrl){
            peer.setAllow(audioCodec1+(audioCodec2==null?"":","+audioCodec2)+
                    (audioCodec3==null?"":","+audioCodec3)); //CODECS DE AUDIO E VIDEO
            peer.setAllowVideo((videoCodec1==null?"":videoCodec1)+
                    (videoCodec2==null?"":","+videoCodec2)+
                    (videoCodec3==null?"":","+videoCodec3));
            if(peer.getAllowVideo().length()>0&&String.valueOf(peer.getAllowVideo().charAt(0)).equals(","))
                peer.setAllowVideo(peer.getAllowVideo().substring(1));
            
            peer.setCanal("SIP/"+peer.getName());
            
            String ret=new PeerDAO().persistNewPeer(peer);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/peers";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar Ramal ou Senha de Registro.", ""));
        return null;
    }
    public String submitNewPeersLot(){
        if(submitCtrl){
            peer.setAllow(audioCodec1+(audioCodec2==null?"":","+audioCodec2)+
                    (audioCodec3==null?"":","+audioCodec3)); //CODECS DE AUDIO E VIDEO
            peer.setAllowVideo((videoCodec1==null?"":videoCodec1)+
                    (videoCodec2==null?"":","+videoCodec2)+
                    (videoCodec3==null?"":","+videoCodec3));
            if(peer.getAllowVideo().length()>0&&String.valueOf(peer.getAllowVideo().charAt(0)).equals(","))
                peer.setAllowVideo(peer.getAllowVideo().substring(1));
            
            List<Peer> peersLot=new ArrayList<>();
            for(int i=rangeStart;i<=rangeFinish;i++){
                if(verifyPeers(i)){
                    peersLot.clear();
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Ramal "+i+" já existe. A criação não prosseguirá.", ""));
                    return null;
                }
                if(!new ValidatePeer().validatePeerNumber(i)){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "O número de ramal deve ser entre 10 e 9999. A criação não prosseguirá.", ""));
                    return null;
                }
                if(String.valueOf(i).matches(agentRange.getAgentRange())){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "O número "+i+" não pode ser usado pois"
                                            + " está reservado para Agentes. A criação não prosseguirá.", ""));
                    return null;
                }
                peer.setName(i);
                peer.setCanal("SIP/"+peer.getName());
                peersLot.add(new Peer(peer));
            }
            
            
            String ret=new PeerDAO().persistNewPeersLot(peersLot);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/peers";
        }       
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar Ramal ou Senha de Registro.", ""));
        return null;
    }
    boolean verifyPeers(int peer){
        for (Peer p: peers) {
            if(p.getName()==peer)
                return true;
        }
        return  false;
    }
    public String submitNewPeerWEB(){
        if(submitCtrl){
            peer.setAllow("g722,ulaw"); //CODECS DE AUDIO E VIDEO
            peer.setAllowVideo("vp8");
            
            peer.setCanal("SIP/"+peer.getName());
            peer.setSecret(peer.getName()+":"+peer.getUsername()); // ALEJANDO DADOS
            peer.setDtmfmode("rfc2833");
            peer.setNat("force_rport,comedia");
            
            String ret=new PeerDAO().persistNewPeerWEB(peer);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/peers";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar Ramal ou Senha de Registro.", ""));
        return null;
    }
    public String submitNewPeersWEBLot(){
        if(submitCtrl){
            peer.setAllow("g722,ulaw"); //CODECS DE AUDIO E VIDEO
            peer.setAllowVideo("vp8");
            
            //peer.setCanal("SIP/"+peer.getName());            
            peer.setDtmfmode("rfc2833");
            peer.setNat("force_rport,comedia");
            
            List<Peer> peersLot=new ArrayList<>();
            for(int i=rangeStart;i<=rangeFinish;i++){
                if(verifyPeers(i)){
                    peersLot.clear();
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Ramal "+i+" já existe. A criação não prosseguirá.", ""));
                    return null;
                }
                if(!new ValidatePeer().validatePeerNumber(i)){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "O número de ramal deve ser entre 10 e 9999. A criação não prosseguirá.", ""));
                    return null;
                }
                if(String.valueOf(i).matches(agentRange.getAgentRange())){
                    FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "O número "+i+" não pode ser usado pois"
                                            + " está reservado para Agentes. A criação não prosseguirá.", ""));
                    return null;
                }
                peer.setName(i);
                peer.setCanal("SIP/"+peer.getName());
                peer.setSecret(peer.getName()+":"+peer.getUsername()); // ALEJANDO DADOS
                peersLot.add(new Peer(peer));
            }
            
            String ret=new PeerDAO().persistNewPeersWEBLot(peersLot);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/peers";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar Ramal ou Senha de Registro.", ""));
        return null;
    }
    public String submitNewPeerVIRTUAL(){
        if(submitCtrl){
            peer.setAllow("ulaw"); //CODECS DE AUDIO E VIDEO
            peer.setAllowVideo("");
            
            peer.setSecret("jefaokpta"); // ALEJANDO DADOS
            peer.setDtmfmode("rfc2833");
            
            String ret=new PeerDAO().persistNewPeerVIRTUAL(peer);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/peers";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar Ramal ou Senha de Registro.", ""));
        return null;
    }
    public Peer getPeer() {
        return peer;
    }

    public int getRangeStart() {
        return rangeStart;
    }

    public void setRangeStart(int rangeStart) {
        this.rangeStart = rangeStart;
    }

    public int getRangeFinish() {
        return rangeFinish;
    }

    public void setRangeFinish(int rangeFinish) {
        this.rangeFinish = rangeFinish;
    }

    public Map<String, String> getSelectProfile() {
        return selectProfile;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public Map<String, String> getSelectGrp() {
        return selectGrp;
    }

    public String getAudioCodec1() {
        return audioCodec1;
    }

    public void setAudioCodec1(String audioCodec1) {
        this.audioCodec1 = audioCodec1;
    }

    public String getAudioCodec2() {
        return audioCodec2;
    }

    public void setAudioCodec2(String audioCodec2) {
        this.audioCodec2 = audioCodec2;
    }

    public String getAudioCodec3() {
        return audioCodec3;
    }

    public void setAudioCodec3(String audioCodec3) {
        this.audioCodec3 = audioCodec3;
    }

    public String getVideoCodec1() {
        return videoCodec1;
    }

    public void setVideoCodec1(String videoCodec1) {
        this.videoCodec1 = videoCodec1;
    }

    public String getVideoCodec2() {
        return videoCodec2;
    }

    public void setVideoCodec2(String videoCodec2) {
        this.videoCodec2 = videoCodec2;
    }

    public String getVideoCodec3() {
        return videoCodec3;
    }

    public void setVideoCodec3(String videoCodec3) {
        this.videoCodec3 = videoCodec3;
    }
    
}
