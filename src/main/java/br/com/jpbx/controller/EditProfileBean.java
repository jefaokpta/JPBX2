/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Acl;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import br.com.jpbx.util.ResolvDNS;
import br.com.jpbx.util.ValidateIP;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "editProfileBean")
@ViewScoped
public class EditProfileBean implements Serializable{

    private Profile profile;
    private List<Profile> profiles;
    private boolean submitCtrl;
    private String borderColor;
    private Acl acl;
    private List<Acl> acls;
    private boolean limitation;
    private boolean countRestart;
    
    public EditProfileBean() {
        profiles=new ProfileDAO().getAllProfiles();
        profile=(Profile) FacesContext.getCurrentInstance().getExternalContext().
                getSessionMap().get("editProfile");
        submitCtrl=true;
        acl=new Acl();
        acl.setMask("255.255.255.255");
        limitation=false;
        if(profile.getLimited()>0)
            limitation=true;
        acls=profile.getAcls();
    }
    public void addAcl(){
        if(!acl.getIp().equals("")){
            if(new ValidateIP().validIP(acl.getIp())){
                Acl testAcl=new Acl(acl.getIp(), acl.getMask(), acl.getDns());
                if(acls.contains(testAcl)){
                    FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O IP ou REDE "+testAcl.getIp()+" já consta na lista.", ""));
                    return;
                }
                acl.setDns("");
                acls.add(testAcl); 
                acl.setIp("");
            }
            else
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "Número de IP inválido. Verifique.", ""));
        }
        else{
            if(!acl.getDns().equals("")){
                String ip=new ResolvDNS().getIpAddress(acl.getDns());
                if(ip.split("\\.")[0].equals("Ops"))
                    FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            ip+" DNS desconhecido.", ""));
                else{
                    Acl testAcl=new Acl(acl.getIp(), acl.getMask(), acl.getDns());
                    if(acls.contains(testAcl)){
                        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "O IP ou REDE "+testAcl.getIp()+" já consta na lista.", ""));
                        return;
                    }
                    testAcl.setIp(ip);
                    acls.add(testAcl);
                    acl.setIp("");
                    acl.setDns("");
                }
            }
        }        
    }
    public void removeAcl(Acl a){
        acls.remove(a);
    }
    public void verifyProfileName(){
        borderColor="greenyellow";
        submitCtrl=true;
        for(Profile p:profiles)
            if(p.getName().toUpperCase().equals(profile.getName().toUpperCase())&&
                    p.getId()!=profile.getId()){
                borderColor="red";
                submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O nome "+profile.getName()+" já existe.\n"
                                    + "Por favor escolha outro.", ""));
                break;
            }
    }
    public String updateProfile(){
        if(submitCtrl){
            if(limitation){
                profile.setLimited(1);
            }
            else{
                profile.setLimited(0);
                profile.setCurrentValue(0);
                profile.setSecureValue(0);
                profile.setCutDate(0);
            }
            if(countRestart)
                profile.setCurrentValue(0);
            String ret=new ProfileDAO().updateProfile(profile);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/profiles";
        }
        FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                "Não foi possivel validar o nome do grupo.", ""));
        return null;
    }
    public String getBorderColor() {
        return borderColor;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public Acl getAcl() {
        return acl;
    }

    public void setAcl(Acl acl) {
        this.acl = acl;
    }

    public List<Acl> getAcls() {
        return acls;
    }

    public void setAcls(List<Acl> acls) {
        this.acls = acls;
    }

    public boolean isCountRestart() {
        return countRestart;
    }

    public void setCountRestart(boolean countRestart) {
        this.countRestart = countRestart;
    }


    public boolean isLimitation() {
        return limitation;
    }

    public void setLimitation(boolean limitation) {
        this.limitation = limitation;
    }
    
}
