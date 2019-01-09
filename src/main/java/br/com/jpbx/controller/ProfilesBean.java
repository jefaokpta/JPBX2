/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.jpbx.controller;

import br.com.jpbx.model.Peer;
import br.com.jpbx.model.PeerDAO;
import br.com.jpbx.model.Profile;
import br.com.jpbx.model.ProfileDAO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Jefferson A. Reis < jefaokpta@hotmail.com >
 */
@Named(value = "profilesBean")
@ViewScoped
public class ProfilesBean implements Serializable{

    private Profile profile;
    private List<Profile> profiles;
    public ProfilesBean() {
        profiles=new ProfileDAO().getAllProfiles();
    }
    public String deleteProfile(){
        List<Peer> peers=new PeerDAO().getAllPeers();
        String peersUseAuth="";
        for (Peer p : peers) {
            if(p.getAuthorization()==profile.getId())
                peersUseAuth+=" "+p.getName();
        }
        if(!peersUseAuth.equals("")){
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_FATAL,
                    "Não será apagado pois ramais usam esta Autorização: "+
                            peersUseAuth, ""));
        }
        else{
            String ret=new ProfileDAO().deleteProfile(profile);
            if(!ret.equals("ok"))
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_FATAL,
                                    ret, ""));
        }
        return "/restricted/profiles";
    }
    public void alertDelete(Profile p){
        profile=p;
        RequestContext.getCurrentInstance()
            .execute("PF('alertProfile').show()");
    }   
    public void showAcl(Profile p){
        profile=p;
        RequestContext.getCurrentInstance()
            .execute("PF('showAcl').show()");
    }  
    public String editProfile(Profile p){
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().
                put("editProfile", p);
        return "/restricted/editProfile";
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public List<Profile> getProfiles() {
        return profiles;
    }
    
}
