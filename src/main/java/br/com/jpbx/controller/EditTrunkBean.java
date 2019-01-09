/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Trunk;
import br.com.jpbx.model.TrunkDAO;
import br.com.jpbx.util.Ping;
import java.io.Serializable;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "editTrunkBean")
@ViewScoped
public class EditTrunkBean implements Serializable{

    private Trunk trunk;
    private List<Trunk> trunks;
    private boolean submitCtrl;
    private String borderColor;
    private String audioCodec1;
    private String audioCodec2;
    private String audioCodec3;
    private String videoCodec1;
    private String videoCodec2;
    private String videoCodec3;
    private boolean register;
    
    public EditTrunkBean() {
        trunk=(Trunk) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editTrunk");
        trunks=new TrunkDAO().getAllTrunks();
        submitCtrl=true;
        if(trunk.getTecnology().equals("SIP")){
            //CODECS
            audioCodec1=trunk.getAllow();
            if(audioCodec1.contains(",")){
                String[] codecs=audioCodec1.split(",");
                audioCodec1=codecs[0];audioCodec2=codecs[1];
                if(codecs.length>2)
                    audioCodec3=codecs[2];
            }
            videoCodec1=trunk.getAllowVideo();
            if(videoCodec1.contains(",")){
                String[] codecs=videoCodec1.split(",");
                videoCodec1=codecs[0];videoCodec2=codecs[1];
                if(codecs.length>2)
                    videoCodec3=codecs[2];
            }
        }
        if(trunk.getTecnology().equals("IAX2")){
            audioCodec1=trunk.getAllow();
            if(audioCodec1.contains(",")){
                String[] codecs=audioCodec1.split(",");
                audioCodec1=codecs[0];audioCodec2=codecs[1];
                if(codecs.length>2)
                    audioCodec3=codecs[2];
            }
        }
        register=trunk.getReception()>0;
    }
    public void verifyTrunkName(){
        borderColor="greenyellow";
        submitCtrl=true;
        for (Trunk t : trunks) 
            if(t.getName().toUpperCase().equals(trunk.getName().toUpperCase()))
                if(t.getId()!=trunk.getId()){
                    borderColor="red";
                    submitCtrl=false;
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O Tronco "+trunk.getName()+" já existe.\n"
                                + "Por favor escolha outro.", ""));
                    break;
                }
    }
    public void doPing(){
        if(!trunk.getHost().equals(""))
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    new Ping().doPing(trunk.getHost()), ""));
    }
    public String getTrunkTypePag(){
        return "/WEB-INF/includes/editTrunk"+trunk.getTecnology()+".xhtml";
    }
    public String updateTrunkSIP(){
        if(submitCtrl){
            trunk.setAllow(audioCodec1+(audioCodec2==null?"":","+audioCodec2)+
                    (audioCodec3==null?"":","+audioCodec3)); //CODECS DE AUDIO E VIDEO
            trunk.setAllowVideo((videoCodec1==null?"":videoCodec1)+
                    (videoCodec2==null?"":","+videoCodec2)+
                    (videoCodec3==null?"":","+videoCodec3));
            if(trunk.getAllowVideo().length()>0&&String.valueOf(trunk.getAllowVideo().charAt(0)).equals(","))
                trunk.setAllowVideo(trunk.getAllowVideo().substring(1));
            
            trunk.setUsername(trunk.getUsername().trim());
            trunk.setCanal("SIP/"+trunk.getUsername());
            if(register)
                trunk.setReception(1);
            else{
                trunk.setReception(0);
                trunk.setRegister("");
            }          
            String ret=new TrunkDAO().updateTrunk(trunk);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/trunks";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar algum campo.", ""));
        return null;
    }
    public String updateTrunkIAX(){
        if(submitCtrl){
            trunk.setAllow(audioCodec1+(audioCodec2==null?"":","+audioCodec2)+
                    (audioCodec3==null?"":","+audioCodec3)); //CODECS DE AUDIO E VIDEO           
            
            trunk.setUsername(trunk.getUsername().trim());
            trunk.setCanal("IAX2/"+trunk.getUsername());
            String ret=new TrunkDAO().updateTrunk(trunk);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/trunks";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar algum campo.", ""));
        return null;
    }
    public String updateTrunk(){
        if(submitCtrl){
            if(trunk.getTechDigital().equals("Khomp")){
                trunk.setCanal(trunk.getTechDigital()+"/b"+trunk.getNumBoard()+
                    "c"+trunk.getChanRangeStart()+"-"+trunk.getChanRangeEnd());
                if(trunk.getOcupation()>0)
                    trunk.setCanal(trunk.getCanal().replace("b", "B"));
            }
            else{
                trunk.setCanal(trunk.getTechDigital()+"/g"+trunk.getNumBoard());
                if(trunk.getOcupation()>0)
                    trunk.setCanal(trunk.getCanal().replace("g", "G"));
            }
            String ret=new TrunkDAO().updateTrunk(trunk);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/trunks";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar algum campo.", ""));
        return null;
    }
    
    public boolean isRegister() {
        return register;
    }

    public void setRegister(boolean register) {
        this.register = register;
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

    public String getBorderColor() {
        return borderColor;
    }

    public Trunk getTrunk() {
        return trunk;
    }

    public void setTrunk(Trunk trunk) {
        this.trunk = trunk;
    }

  
    
    
}
