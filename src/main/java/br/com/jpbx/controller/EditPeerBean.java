/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.model.PickupGrp;
import br.com.jpbx.model.PickupGrpDAO;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.util.HandleAudio;
import br.com.jpbx.util.VerifyPassword;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "editPeerBean")
@ViewScoped
public class EditPeerBean implements Serializable{

    private Peer peer;
    private boolean submitCtrl;
    private String borderColor;
    private String audioCodec1;
    private String audioCodec2;
    private String audioCodec3;
    private String videoCodec1;
    private String videoCodec2;
    private String videoCodec3;
    private Map<String,String> selectGrp;
    private Map<String,String> selectProfile;
    private UploadedFile upFile;
    private boolean editPass;
    private String passAux;
    
    public EditPeerBean() {
        peer=(Peer) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editPeer");
        submitCtrl=true;
        selectGrp=new HashMap<>();
        
        for(PickupGrp p:new PickupGrpDAO().getAllGrps()) //MOSTRA GRUPOS COM BASE EM currentCompany
            if(p.getCompany()==peer.getCompany())
                selectGrp.put(p.getName(), String.valueOf(p.getId()));
        selectProfile=new HashMap<>(); //MOSTRA TODOS PROFILES
        
        for(Profile p:new ProfileDAO().getAllProfilesForms())
            selectProfile.put(p.getName(), String.valueOf(p.getId()));
        
        
        if(peer.getPeerType().equals("SIP")){
            //CODECS
            audioCodec1=peer.getAllow();
            if(audioCodec1.contains(",")){
                String[] codecs=audioCodec1.split(",");
                audioCodec1=codecs[0];audioCodec2=codecs[1];
                if(codecs.length>2)
                    audioCodec3=codecs[2];
            }
            videoCodec1=peer.getAllowVideo();
            if(videoCodec1.contains(",")){
                String[] codecs=videoCodec1.split(",");
                videoCodec1=codecs[0];videoCodec2=codecs[1];
                if(codecs.length>2)
                    videoCodec3=codecs[2];
            }
        }
    }
    public String getPeerTypePag(){
        return "/WEB-INF/includes/editPeer"+peer.getPeerType()+".xhtml";
    }
    public void verifyFacilityPass(){
        //submitCtrl=true;
        if(!new VerifyPassword().verifyPasswordRange(peer.getFacilityPass())){
           // submitCtrl=false;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "A senha deve apresentar 3 ou 4 números.", ""));
        }
    }
    public void verifyVoicemailPass(){
        if(peer.getMailbox()!=0){
            //submitCtrl=true;
            if(!new VerifyPassword().verifyPasswordRange(peer.getMailbox())){
               // submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha deve apresentar 3 ou 4 números.", ""));
            }
        }
    }
    public void removeVoicemailGreeting(){
        String ret=new HandleAudio().removeVoicemailGreeting(peer.getName());
        if(!ret.equals("ok")){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return;
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO,
                                "Saudação Original restaurada com sucesso. "
                                        + "Não é necessário salvar.", ""));
    }
    public void verifyPassword(){
       // submitCtrl=true;       
        if(!new VerifyPassword().verifyPassword(peer.getSecret())){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha deve ter ao menos 2 letras e 2 números.", ""));
      //      submitCtrl=false;
        }
    }
    public void verifyPasswordWEB(){
       // submitCtrl=true;       
        if(!new VerifyPassword().verifyPassword(peer.getUsername())){
            FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha deve ter ao menos 2 letras e 2 números.", ""));
          //  submitCtrl=false;
        }
    }
    public String updatePeer(){
        if(editPass){
            if(!new VerifyPassword().verifyPassword(passAux)){
                FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "A senha deve ter ao menos 2 letras e 2 números.", ""));
                return null;
            }       
            //peer.setUsername(passAux);
            peer.setSecret(passAux);
        }
        // VERIFICACOES DE SENHAS VOICE E FEATURE
        if(peer.getMailbox()!=0){
            if(!new VerifyPassword().verifyPasswordRange(peer.getMailbox())){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha de Voicemail deve apresentar 3 ou 4 números.", ""));
                return null;
            }
        }
        if(!new VerifyPassword().verifyPasswordRange(peer.getFacilityPass())){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "A senha de facilidades deve apresentar 3 ou 4 números.", ""));
            return null;
        }
        
        peer.setAllow(audioCodec1+(audioCodec2==null?"":","+audioCodec2)+
                (audioCodec3==null?"":","+audioCodec3)); //CODECS DE AUDIO E VIDEO
        peer.setAllowVideo((videoCodec1==null?"":videoCodec1)+
                (videoCodec2==null?"":","+videoCodec2)+
                (videoCodec3==null?"":","+videoCodec3));
        if(peer.getAllowVideo().length()>0&&String.valueOf(peer.getAllowVideo().charAt(0)).equals(","))
            peer.setAllowVideo(peer.getAllowVideo().substring(1));

        String ret=new PeerDAO().updatePeer(peer,editPass);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            ret, ""));
        return "/restricted/peers";
    }
    public String updatePeerWEB(){
        if(editPass){
            if(!new VerifyPassword().verifyPassword(passAux)){
                FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    "A senha deve ter ao menos 2 letras e 2 números.", ""));
                return null;
            }       
            peer.setUsername(passAux);
            peer.setSecret(peer.getName()+":"+peer.getUsername());
        }
        // VERIFICACOES DE SENHAS VOICE E FEATURE
        if(peer.getMailbox()!=0){
            if(!new VerifyPassword().verifyPasswordRange(peer.getMailbox())){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha de Voicemail deve apresentar 3 ou 4 números.", ""));
                return null;
            }
        }
        if(!new VerifyPassword().verifyPasswordRange(peer.getFacilityPass())){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "A senha de facilidades deve apresentar 3 ou 4 números.", ""));
            return null;
        }
        String ret=new PeerDAO().updatePeerWEB(peer,editPass);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            ret, ""));
        return "/restricted/peers";
    }
    public String updatePeerVIRTUAL(){
        // VERIFICACOES DE SENHAS VOICE E FEATURE
        if(peer.getMailbox()!=0){
            if(!new VerifyPassword().verifyPasswordRange(peer.getMailbox())){
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "A senha de Voicemail deve apresentar 3 ou 4 números.", ""));
                return null;
            }
        }
        if(!new VerifyPassword().verifyPasswordRange(peer.getFacilityPass())){
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "A senha de facilidades deve apresentar 3 ou 4 números.", ""));
            return null;
        }
        String ret=new PeerDAO().updatePeerVIRTUAL(peer);
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            ret, ""));
        return "/restricted/peers";
    }

    public void fileUploadListener(FileUploadEvent e){
        // Get uploaded file from the FileUploadEvent
        this.upFile = e.getFile();
        String ret=new HandleAudio().saveAudioForConvert(upFile, peer.getName());
        if(!ret.equals("ok"))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    ret, ""));
        else
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Alteração feita com sucesso! Não é necessário salvar.", ""));
    }
    public UploadedFile getUpFile() {
        return upFile;
    }

    public boolean isEditPass() {
        return editPass;
    }

    public void setEditPass(boolean editPass) {
        this.editPass = editPass;
    }

    public void setUpFile(UploadedFile upFile) {
        this.upFile = upFile;
    }

    public Peer getPeer() {
        return peer;
    }

    public Map<String, String> getSelectGrp() {
        return selectGrp;
    }

    public void setPeer(Peer peer) {
        this.peer = peer;
    }

    public String getBorderColor() {
        return borderColor;
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

    public Map<String, String> getSelectProfile() {
        return selectProfile;
    }

    public String getPassAux() {
        return passAux;
    }

    public void setPassAux(String passAux) {
        this.passAux = passAux;
    }
    
}
