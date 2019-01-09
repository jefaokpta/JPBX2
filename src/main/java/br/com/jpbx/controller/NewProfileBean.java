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
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "newProfileBean")
@ViewScoped
public class NewProfileBean implements Serializable{

    private Profile profile;
    private List<Profile> profiles;
    private Acl acl;
    private String borderColor;
    private boolean submitCtrl;
    private boolean limitation;
    private List<Acl> acls;
    public NewProfileBean() {
        profile=new Profile();
        acls=new ArrayList<>();
        profiles=new ProfileDAO().getAllProfiles();
        submitCtrl=false;
        acl=new Acl();
        acl.setMask("255.255.255.255");
        limitation=false;
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
                            ip, ""));
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
            if(p.getName().toUpperCase().equals(profile.getName().toUpperCase())){
                borderColor="red";
                submitCtrl=false;
                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_FATAL,
                            "O Nome "+profile.getName()+" já existe.\n"
                                    + "Por favor escolha outro.", ""));
                break;
            }
    }
    public String submitNewProfile(){
        if(submitCtrl){
            if(limitation)
                profile.setLimited(1);
            //profile.setCurrentValue(profile.getSecureValue());
            profile.setAcls(acls);
            String ret=new ProfileDAO().persistNewProfile(profile);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                ret, ""));
            return "/restricted/profiles";
        }
        FacesContext.getCurrentInstance().addMessage(null,
            new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Não foi possivel validar o nome desta Autorização.", ""));
        return null;
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getBorderColor() {
        return borderColor;
    }

    public Acl getAcl() {
        return acl;
    }

    public void setAcl(Acl acl) {
        this.acl = acl;
    }

    public boolean isLimitation() {
        return limitation;
    }

    public void setLimitation(boolean limitation) {
        this.limitation = limitation;
    }

    public List<Acl> getAcls() {
        return acls;
    }

    public void setAcls(List<Acl> acls) {
        this.acls = acls;
    }
    
    
}
